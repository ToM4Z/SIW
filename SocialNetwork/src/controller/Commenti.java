package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Commento;
import model.Post;
import persistence.DatabaseManager;
import persistence.dao.CommentoDao;
import persistence.dao.PostDao;


public class Commenti extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Long idPost = Long.parseLong(req.getParameter("idPost"));
		PostDao postDao = DatabaseManager.getInstance().getDaoFactory().getPostDAO();
		
		Post post = postDao.findByPrimaryKey(idPost);
		
		req.setAttribute("commenti", post.getCommenti());
		req.setAttribute("post", post);
		
		req.getRequestDispatcher("commenti.jsp").forward(req, resp);
		
	}
	
	

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
	}

}
