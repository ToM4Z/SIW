package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Canale;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.CanaleDao;


public class IscrizioneCanale extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		Utente utente = (Utente) session.getAttribute("user");
		String nomeCanale = req.getParameter("channel");
		String iscritto = req.getParameter("iscritto");
		CanaleDao dao =DatabaseManager.getInstance().getDaoFactory().getCanaleDAO();
		Canale canale = dao.findByPrimaryKey(nomeCanale);
		
		if (iscritto.equals("false")) {
			
			canale.addMembro(utente);
			
			dao.addUserToChannel(canale, utente);
			dao.update(canale);
		}
		else if (iscritto.equals("true")) {
			
			canale.removeMembro(utente);
			dao.removeUserFromChannel(canale, utente);
			dao.update(canale);
		}
		
		resp.sendRedirect("canale?channel="+nomeCanale);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doGet(req, resp);
	}

}
