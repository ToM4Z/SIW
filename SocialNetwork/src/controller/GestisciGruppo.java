package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Gruppo;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.GruppoDao;
import persistence.dao.UtenteDao;


public class GestisciGruppo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String esito = req.getParameter("esito");
		
		if (esito.equals("cancellazione")) {
			
			HttpSession session = req.getSession();
			Utente utente = (Utente) session.getAttribute("user");
			
			String nomeGruppo = req.getParameter("group");
			String nomeCanale = req.getParameter("channel");
			
			GruppoDao gruppoDao =  DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
			Gruppo gruppo = gruppoDao.findByPrimaryKey(nomeGruppo, nomeCanale);
			
			gruppoDao.removeUserFromGroup(gruppo, utente);
			
			resp.sendRedirect("canale?channel="+nomeCanale);
			
		}
		else {
			String nomeGruppo = req.getParameter("group");
			String nomeCanale = req.getParameter("channel");
			String emailUtente = req.getParameter("utente");
			GruppoDao gruppoDao =  DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
			UtenteDao utenteDao =  DatabaseManager.getInstance().getDaoFactory().getUtenteDAO();
			Gruppo gruppo = gruppoDao.findByPrimaryKey(nomeGruppo, nomeCanale);
			Utente utente = utenteDao.findByPrimaryKey(emailUtente);
			
			if (esito.equals("y")) {
				
				gruppoDao.removeUserFromAttesa(gruppo, utente);
				gruppoDao.addUserToGroup(gruppo, utente);
			}
			else if (esito.equals("n")) {
				
				gruppoDao.removeUserFromAttesa(gruppo, utente);
			}
			
			resp.sendRedirect("utentiInAttesa?group="+nomeGruppo+"&channel="+nomeCanale);
		}
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doGet(req, resp);
	}

}
