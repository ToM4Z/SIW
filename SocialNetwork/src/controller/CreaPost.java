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
import persistence.dao.PostDao;


public class CreaPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		RequestDispatcher dispacher = req.getRequestDispatcher("creaPost.jsp");
		dispacher.forward(req, resp);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession(true);
		Utente utente = (Utente) session.getAttribute("user");
		PostDao canaleDao = DatabaseManager.getInstance().getDaoFactory().getPostDAO();
		
		String contenuto = req.getParameter("contenuto");
		
			
		Post post = new Post(utente, contenuto, gruppo, canale, Calendar.getInstance().getTime());
		
		
		PostDao.save(post);
		
		
		req.setAttribute("post", post);
		
		RequestDispatcher dispacher = req.getRequestDispatcher("creaPost.jsp");
		dispacher.forward(req, resp);
	}
}
