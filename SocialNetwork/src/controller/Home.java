package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Post;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.UtenteDao;

public class Home extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		Utente utente = (Utente) session.getAttribute("user");
		resp.setCharacterEncoding("UTF-8");
		
		UtenteDao dao =DatabaseManager.getInstance().getDaoFactory().getUtenteDAO();
		List<Post> posts = dao.getPostsOfMyGroups(utente);
		req.setAttribute("posts", posts);
				
		req.getRequestDispatcher("home.jsp").forward(req, resp);
	}	
}













