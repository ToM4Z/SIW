package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Gruppo;
import model.Post;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.GruppoDao;
import persistence.dao.PostDao;


public class Commenti extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Utente utente = (Utente) req.getSession().getAttribute("user");
		if(utente == null) {
			resp.sendRedirect("login.html");
			return;
		}
		
		Long idPost = Long.parseLong(req.getParameter("idPost"));
		PostDao postDao = DatabaseManager.getInstance().getDaoFactory().getPostDAO();
		GruppoDao gruppoDao = DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
		
		boolean admin = false;
		
		Post post = postDao.findByPrimaryKey(idPost);
		Gruppo gruppo = gruppoDao.findByPrimaryKey(post.getGruppo().getNome(), post.getCanale().getNome());
		
		for (Utente u : gruppo.getAdmins()) {
			
			if (u.getEmail().equals(utente.getEmail()))
				admin = true;
		}
		
		req.setAttribute("commenti", post.getCommenti());
		req.setAttribute("post", post);
		req.setAttribute("admin", admin);
		
		req.getRequestDispatcher("commenti.jsp").forward(req, resp);
		
	}
}
