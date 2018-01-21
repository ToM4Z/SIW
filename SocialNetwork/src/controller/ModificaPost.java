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

import model.Post;
import persistence.DatabaseManager;
import persistence.dao.PostDao;


public class ModificaPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
	}

	private class Modifica{
		
		private String idPost;
		private String modifica;
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String linea = br.readLine();
		System.out.println(linea);
        Modifica m = new Gson().fromJson(linea, Modifica.class);
        
        PostDao postDao = DatabaseManager.getInstance().getDaoFactory().getPostDAO();
        
        Post post = postDao.findByPrimaryKey(Long.parseLong(m.idPost));
        post.setContenuto(m.modifica);
        
        postDao.update(post);
        
        resp.getWriter().write("true");
	}

}
