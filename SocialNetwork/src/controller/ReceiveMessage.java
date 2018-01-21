package controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		HttpSession session = req.getSession();
		Utente utente = (Utente) session.getAttribute("user");
		if(utente == null) {
			resp.getWriter().write("error");
			return;
		}
        MessaggioDao messaggioDao = DatabaseManager.getInstance().getDaoFactory().getMessaggioDAO();
		
		if(Boolean.valueOf(req.getParameter("first"))){
	        GruppoDao gruppoDao = DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();

			String gruppo = req.getParameter("gruppo");
			String canale = req.getParameter("canale");

			Gruppo group = gruppoDao.findByPrimaryKey(gruppo,canale);
			
			Messaggio lastMex = new Messaggio();
			lastMex.setGruppo(group);
			
			List<Messaggio> mex = messaggioDao.getOther50(lastMex);
			if(mex.size()==0)
				session.setAttribute("lastMessage", lastMex);
			else
				session.setAttribute("lastMessage", mex.get(mex.size()-1));
			resp.getWriter().write(new Gson().toJson(createListMex(utente,mex)));
			
		}else {
			Messaggio lastMex = (Messaggio) session.getAttribute("lastMessage");
			List<Messaggio> mex;
			if(lastMex.getId() != null)
				mex = messaggioDao.getAfter(lastMex);
			else
				mex = messaggioDao.getOther50(lastMex);
			
			if(mex.size() != 0)
				session.setAttribute("lastMessage", mex.get(mex.size()-1));
			
			resp.getWriter().write(new Gson().toJson(createListMex(utente,mex)));
		}
	}
	
	private List<String> createListMex(Utente me,List<Messaggio> mex){
		List<String> json = new LinkedList<>();
		Calendar calendar = Calendar.getInstance();
		for(int i=0;i<mex.size();++i) {
			Messaggio m = mex.get(i);	
			calendar.setTime(m.getData());
			String side = m.getMittente().getEmail().equals(me.getEmail()) ? "right" : "left";
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int min = calendar.get(Calendar.MINUTE);
			String data = (hour<10 ? "0"+hour : hour) +":"+ (min<10 ? "0"+min : min);
			String email = m.getMittente().getEmail();
			String username = m.getMittente().getUsername();
			
			json.add("<li class=\"li-mex\"><span class=\"mex mex-"+side+"\">"
						+ "<span class=\"mex-header mex-header-"+side+"\">"
						+"<a href=\"utente?to="+email+"\">"+username+"</a></span><br><p>"+m.getContenuto()
						+"</p><span class=\"mex-footer mex-footer-"+side+"\">"+data+"</span><br>"
						+"</span></li>"); 
		}
		return json;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().removeAttribute("lastMessage");
	}
}
