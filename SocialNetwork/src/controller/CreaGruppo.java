package controller;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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


public class CreaGruppo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String nomeCanale;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		nomeCanale = req.getParameter("channel");
		RequestDispatcher dispacher = req.getRequestDispatcher("creaGruppo.jsp");
		dispacher.forward(req, resp);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
		HttpSession session = req.getSession(true);
		Utente utente = (Utente) session.getAttribute("user");
		
		
		CanaleDao canaleDao = DatabaseManager.getInstance().getDaoFactory().getCanaleDAO();
		GruppoDao gruppoDao = DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
		
		String nome = req.getParameter("nome");
		
		if (gruppoDao.findByPrimaryKey(nome, nomeCanale) == null) {
		
			Canale canale = canaleDao.findByPrimaryKey(nomeCanale);
			
			//System.out.println(nomeCanale);
			
			Gruppo gruppo = new Gruppo(nome, Calendar.getInstance().getTime(), canale);
			
			gruppo.addAdmin(utente);
			gruppo.addMembro(utente);
			
			gruppoDao.save(gruppo);
			
			req.setAttribute("gruppo", gruppo);
			req.setAttribute("creazione", true);
		}
		
		else {
			
			req.setAttribute("creazione", false);
		}
		
		RequestDispatcher dispacher = req.getRequestDispatcher("creaGruppo.jsp");
		dispacher.forward(req, resp);
	}

}
