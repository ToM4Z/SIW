package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;


import model.Canale;
import model.Commento;
import model.Gruppo;
import model.Post;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.CanaleDao;
import persistence.dao.CommentoDao;
import persistence.dao.GruppoDao;
import persistence.dao.PostDao;


public class CreaCommento extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}
	
	private class JsonCommento{
		
		private String idPost;
		private String commento;
		
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		Utente utente = (Utente) session.getAttribute("user");
		CommentoDao commentoDao = DatabaseManager.getInstance().getDaoFactory().getCommentoDAO();
		PostDao postDao = DatabaseManager.getInstance().getDaoFactory().getPostDAO();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String linea = br.readLine();
		//System.out.println(linea);
        JsonCommento jc = new Gson().fromJson(linea, JsonCommento.class);
		
        Post post = postDao.findByPrimaryKey(Long.parseLong(jc.idPost));
		
		Commento commento = new Commento(jc.commento, utente, post);
		
		commentoDao.save(commento);
		
		resp.getWriter().write("true");
	}

}
