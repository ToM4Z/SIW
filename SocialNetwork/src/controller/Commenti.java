package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Post;
import persistence.DatabaseManager;
import persistence.dao.PostDao;


public class Commenti extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getSession().getAttribute("user") == null) {
			resp.sendRedirect("login.html");
			return;
		}
		
		Long idPost = Long.parseLong(req.getParameter("idPost"));
		PostDao postDao = DatabaseManager.getInstance().getDaoFactory().getPostDAO();
		
		Post post = postDao.findByPrimaryKey(idPost);
		
		req.setAttribute("commenti", post.getCommenti());
		req.setAttribute("post", post);
		
		req.getRequestDispatcher("commenti.jsp").forward(req, resp);
		
	}
}
