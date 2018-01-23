package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Post;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.PostDao;

public class Reaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private class Reazione{		
		private String tipo;
		private String idPost;
	}
	
	@SuppressWarnings("unused")
	private class Invia{		
		private int nLike;
		private int nDislike;
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Utente utente = (Utente) req.getSession().getAttribute("user");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		Reazione r = new Gson().fromJson(br.readLine(), Reazione.class);
		
		PostDao postDao = DatabaseManager.getInstance().getDaoFactory().getPostDAO();
		Post post = postDao.findByPrimaryKey(Long.parseLong(r.idPost));
		
		if(r.tipo.equals("addLike")) 
			postDao.addLike(post, utente);
				
		if(r.tipo.equals("removeLike"))
			postDao.removeLike(post, utente);
				
		if(r.tipo.equals("addDislike"))
			postDao.addDislike(post, utente);
				
		if(r.tipo.equals("removeDislike"))
			postDao.removeDislike(post, utente);
		
		
		Invia i = new Invia();
		i.nLike = post.getLike().size();
		i.nDislike = post.getDislike().size();
		
		resp.getWriter().write(new Gson().toJson(i));
	}

}
