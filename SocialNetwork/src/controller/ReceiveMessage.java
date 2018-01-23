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
	
	@SuppressWarnings("unused")
	private class ListMex{
		private List<Mex> messaggi;
		private int numberMessageSession;
	}

	@SuppressWarnings("unused")
	private class Mex{
		private String email,username,data,contenuto;
		private int right;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Utente utente = (Utente) session.getAttribute("user");
		resp.setCharacterEncoding("UTF-8");
		
		if(utente == null) {
			resp.getWriter().write("error");
			return;
		}
        MessaggioDao messaggioDao = DatabaseManager.getInstance().getDaoFactory().getMessaggioDAO();
		
		if(req.getParameter("first").equals("1")){
	        GruppoDao gruppoDao = DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();

			String gruppo = req.getParameter("gruppo");
			String canale = req.getParameter("canale");

			Gruppo group = gruppoDao.findByPrimaryKey(gruppo,canale);
			
			Messaggio lastMex = new Messaggio();
			lastMex.setGruppo(group);
			
			List<Messaggio> mex = messaggioDao.getOther50(lastMex);
			
			String attribute = "lastMessage"+gruppo+canale;
			int n = 0;
			
			while(session.getAttribute(attribute+n)!=null)
				++n;
			attribute += n;
			
			if(mex.size()==0)
				session.setAttribute(attribute, lastMex);
			else
				session.setAttribute(attribute, mex.get(mex.size()-1));
			
			ListMex lm = new ListMex();
			lm.messaggi=createListMex(utente,mex);
			lm.numberMessageSession=n;
			resp.getWriter().write(new Gson().toJson(lm));
			
		}else {
			Messaggio lastMex = (Messaggio) session.getAttribute("lastMessage"+req.getParameter("attrSession"));
			List<Messaggio> mex;
			if(lastMex.getId() != null)
				mex = messaggioDao.getAfter(lastMex);
			else
				mex = messaggioDao.getOther50(lastMex);
			
			if(mex.size() != 0)
				session.setAttribute("lastMessage"+req.getParameter("attrSession"), mex.get(mex.size()-1));
			
			resp.getWriter().write(new Gson().toJson(createListMex(utente,mex)));
		}
	}
	
	private List<Mex> createListMex(Utente me,List<Messaggio> messages){
		List<Mex> json = new LinkedList<>();
		Calendar today = Calendar.getInstance();
		Calendar calendar = Calendar.getInstance();
		for(int i=0;i<messages.size();++i) {
			Messaggio m = messages.get(i);	
			
			Mex mex = new Mex();
			mex.email = m.getMittente().getEmail();
			mex.username = m.getMittente().getUsername();
			mex.contenuto = m.getContenuto();
			mex.right = m.getMittente().getEmail().equals(me.getEmail()) ? 1 : 0;
			
			calendar.setTime(m.getData());
			int day = calendar.get(Calendar.DATE);
			int month = calendar.get(Calendar.MONTH)+1;
			int year = calendar.get(Calendar.YEAR);
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int min = calendar.get(Calendar.MINUTE);
			mex.data = "";
			if(!(year == today.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)))
				mex.data = day+"/"+ (month<10 ? "0"+month : month) +"/"+ year +" ";			
			mex.data += (hour<10 ? "0"+hour : hour) +":"+ (min<10 ? "0"+min : min);
			
			json.add(mex);
		}
		return json;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().removeAttribute("lastMessage"+req.getParameter("attrSession"));
	}
}
