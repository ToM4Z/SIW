package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Post;
import persistence.DatabaseManager;
import persistence.dao.PostDao;


public class EliminaPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String idPostString = req.getParameter("post");
		Long idPost = Long.parseLong(idPostString);
		String gruppo = req.getParameter("group");
		String canale = req.getParameter("channel");
		PostDao postDao = DatabaseManager.getInstance().getDaoFactory().getPostDAO();
		
		Post post = postDao.findByPrimaryKey(idPost);
		
		postDao.delete(post);
		
		if (gruppo == "")
			resp.sendRedirect("home");
		else
			resp.sendRedirect("gruppo?group="+gruppo+"&channel="+canale);
		
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doGet(req, resp);
	}

}
