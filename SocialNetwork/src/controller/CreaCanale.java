package controller;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Canale;
import model.Gruppo;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.CanaleDao;
import persistence.dao.GruppoDao;



public class CreaCanale extends HttpServlet {
	private static final long serialVersionUID = 1L;
    

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		RequestDispatcher dispacher = req.getRequestDispatcher("creaCanale.jsp");
		dispacher.forward(req, resp);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession(true);
		Utente utente = (Utente) session.getAttribute("user");
		CanaleDao canaleDao = DatabaseManager.getInstance().getDaoFactory().getCanaleDAO();
		
		String nome = req.getParameter("nome");
		String descrizione = req.getParameter("descrizione");
		
		if (canaleDao.findByPrimaryKey(nome) == null) {
			
			Canale canale = new Canale(nome, descrizione, Calendar.getInstance().getTime(), utente);
			
			
			canaleDao.save(canale);
			
			Gruppo home = new Gruppo("Home", Calendar.getInstance().getTime(), canale);
			
			GruppoDao gruppoDao = DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
			
			home.addAdmin(utente);
			home.addMembro(utente);
			
			gruppoDao.save(home);
			
			req.setAttribute("canale", canale);
			req.setAttribute("creazione", true);
		}
		else {
			req.setAttribute("creazione", false);
		}
		
		RequestDispatcher dispacher = req.getRequestDispatcher("creaCanale.jsp");
		dispacher.forward(req, resp);
		
			
		
			
	}
	

}
