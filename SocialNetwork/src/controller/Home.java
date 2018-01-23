package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Post;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.UtenteDao;

public class Home extends HttpServlet{
	private static final long serialVersionUID = 1L;
		
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Utente utente = (Utente) req.getSession().getAttribute("user");
		if(utente == null) {
			resp.sendRedirect("login.html");
			return;
		}
					
		UtenteDao dao =DatabaseManager.getInstance().getDaoFactory().getUtenteDAO();
		List<Post> posts = dao.getPostsOfMyGroups(utente);
		req.setAttribute("posts", posts);
				
		req.getRequestDispatcher("home.jsp").forward(req, resp);
	}	
}













