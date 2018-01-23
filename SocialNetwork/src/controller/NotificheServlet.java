package controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import model.Notifica;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.NotificaDao;

public class NotificheServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	@SuppressWarnings("unused")
	private class Notify{
		private long id;
		private String contenuto;
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		Utente utente = (Utente) req.getSession().getAttribute("user");
		if(utente == null) {
			resp.getWriter().write("error");
			return;
		}

		NotificaDao notificadao = DatabaseManager.getInstance().getDaoFactory().getNotificaDAO();
		String tmp = (String) req.getSession().getAttribute("lastNotifyID"+req.getParameter("numberNotifySession"));
		
		List<Notifica> notifiche = null;
		if(tmp == null) 
			notifiche = notificadao.getNotificheUtente(utente);	
		else {	
			Notifica notifica = new Notifica();
			notifica.setId(Long.valueOf(tmp));
			notifica.setUtente(utente);
			
			notifiche = notificadao.getAfter(notifica);
		}
		
		if(notifiche.size()!=0)			
		    req.getSession().setAttribute("lastNotifyID"+req.getParameter("numberNotifySession"), ""+notifiche.get(notifiche.size()-1).getId());
		resp.getWriter().write(new Gson().toJson(createList(notifiche)));			
	}
	
	private List<Notify> createList(List<Notifica> notifiche){
		List<Notify> list = new LinkedList<>();
		for(Notifica n: notifiche) {
			Notify m = new Notify();
			m.id = n.getId();
			m.contenuto = n.getContenuto();
			list.add(m);
		}			
		return list;
	}
	//list.add("<div id=notifica"+n.getId()+"><li>"+n.getContenuto()+"<a onclick=\"javascript:eliminaNotifica("+n.getId()+")\"><span class=\"glyphicon glyphicon-trash\"></span></a></li><div>");
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if(req.getParameter("initNotify").equals("1")) {
			String attribute = "lastNotifyID";
			int n = 0;
			
			while(session.getAttribute(attribute+n)!=null)
				++n;
			attribute += n;
			resp.getWriter().write(new Gson().toJson(n));
		}else			
			req.getSession().removeAttribute("lastNotifyID"+req.getParameter("numberNotifySession"));
	}
}
