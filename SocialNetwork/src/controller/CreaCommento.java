package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Commento;
import model.Post;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.CommentoDao;
import persistence.dao.PostDao;


public class CreaCommento extends HttpServlet {
	private static final long serialVersionUID = 1L;
       	
	private class JsonCommento{		
		private String idPost;
		private String commento;		
	}
	
	private class InfoCommento{
		
		private String username;
		private Long idCommento;
		
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Utente utente = (Utente) req.getSession().getAttribute("user");
		CommentoDao commentoDao = DatabaseManager.getInstance().getDaoFactory().getCommentoDAO();
		PostDao postDao = DatabaseManager.getInstance().getDaoFactory().getPostDAO();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String linea = br.readLine();
		//System.out.println(linea);
        JsonCommento jc = new Gson().fromJson(linea, JsonCommento.class);
		
        Post post = postDao.findByPrimaryKey(Long.parseLong(jc.idPost));
		
		Commento commento = new Commento(jc.commento, utente, post);
		
		commentoDao.save(commento);
		
		InfoCommento i = new InfoCommento();
		i.username=utente.getUsername();
		i.idCommento=commento.getId();
		
		resp.getWriter().write(new Gson().toJson(i));
	}

}
