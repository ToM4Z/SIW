package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Gruppo;
import model.Messaggio;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.GruppoDao;
import persistence.dao.MessaggioDao;

@WebServlet(value="/receiveMessage")
public class ReceiveMessage extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Utente utente = (Utente) req.getSession().getAttribute("user");
		if(utente == null)
			resp.sendRedirect("login.html");
		
		try {
	        GruppoDao gruppoDao = DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
	        MessaggioDao messaggioDao = DatabaseManager.getInstance().getDaoFactory().getMessaggioDAO();
	        
			String gruppo = req.getParameter("gruppo");
			String canale = req.getParameter("canale");
			boolean first = Boolean.valueOf(req.getParameter("first"));
			
			Gruppo group = gruppoDao.findByPrimaryKey(gruppo,canale);
			
			String tmp = (String) req.getSession().getAttribute("lastMessageID");
			if(first || tmp == null) {
				Messaggio lastMex = new Messaggio();
				lastMex.setGruppo(group);

				//System.out.println("new:");
				
				List<Messaggio> mex = messaggioDao.getOther50(lastMex);
				if(mex.size() != 0) {
					req.getSession().setAttribute("lastMessageID", ""+mex.get(mex.size()-1).getId());
					//System.out.println("lastMessageID: "+mex.get(mex.size()-1).getId());
					
					resp.getWriter().write(new Gson().toJson(createListMex(utente,mex)));
				}else
					resp.getWriter().write("empty");
				//System.out.println("end");
			}else {
				Messaggio lastMex = new Messaggio();
				lastMex.setId(Long.valueOf(tmp));
				lastMex.setGruppo(group);
				//System.out.println("tmp: "+lastMex.getId());
				
				List<Messaggio> mex = messaggioDao.getAfter(lastMex);
				
				if(mex.size() != 0) {
					req.getSession().setAttribute("lastMessageID", ""+mex.get(mex.size()-1).getId());
					//System.out.println("2lastMessageID: "+mex.get(mex.size()-1).getId());
					
					resp.getWriter().write(new Gson().toJson(createListMex(utente,mex)));
				}else
					resp.getWriter().write("empty");
			}
		}catch(Exception e) {
			resp.getWriter().write("error");
			e.printStackTrace();
		}
	}
	
	private List<String> createListMex(Utente me,List<Messaggio> mex){
		List<String> json = new LinkedList<>();
		
		SimpleDateFormat parser = new SimpleDateFormat("hh.mm");
		for(int i=0;i<mex.size();++i) {
			Messaggio m = mex.get(i);
			String side = m.getMittente().getEmail().equals(me.getEmail()) ? "right" : "left";			
			String data = parser.format(m.getData());
			
			json.add("<li><span class=\"mex mex-"+side+"\">"
						+ "<span class=\"mex-header mex-header-"+side+"\">"
						+m.getMittente().getUsername()+"</span><br>"+m.getContenuto()
						+"<span class=\"mex-footer mex-footer-"+side+"\">"+data+"</span><br>"
						+"</span></li>"); 
		}
		return json;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().removeAttribute("lastMessageID");
		//System.out.println("lastMessageID removed");
	}
}
