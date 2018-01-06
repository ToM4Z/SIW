package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Canale;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.UtenteDao;


public class BarraCanali extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession(true);
		Utente utente = (Utente) session.getAttribute("user");
		
		UtenteDao dao =DatabaseManager.getInstance().getDaoFactory().getUtenteDAO();
		
		List<Canale> canali = dao.getMyChannels(utente);
		req.setAttribute("canali", canali);
		
		req.getRequestDispatcher("barraCanali.jsp").forward(req, resp);
	}	

}
