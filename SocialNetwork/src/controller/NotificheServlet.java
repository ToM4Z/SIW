package controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Notifica;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.NotificaDao;

public class NotificheServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Utente utente = (Utente) req.getSession().getAttribute("user");
		if(utente == null)
			return;

		NotificaDao notificadao = DatabaseManager.getInstance().getDaoFactory().getNotificaDAO();
		String tmp = (String) req.getSession().getAttribute("lastNotifyID");
		
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
		    req.getSession().setAttribute("lastNotifyID", ""+notifiche.get(notifiche.size()-1).getId());
		resp.getWriter().write(new Gson().toJson(createList(notifiche)));			
	}
	
	private List<String> createList(List<Notifica> notifiche){
		List<String> list = new LinkedList<>();
		for(Notifica n: notifiche)
			list.add("<div id=notifica"+n.getId()+"><li>"+n.getContenuto()+"<a onclick=\"javascript:eliminaNotifica("+n.getId()+")\"><span class=\"glyphicon glyphicon-trash\"></span></a></li><div>");
		return list;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().removeAttribute("lastNotifyID");
	}
}
