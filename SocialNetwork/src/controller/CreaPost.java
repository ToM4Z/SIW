package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Canale;
import model.Gruppo;
import model.Post;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.CanaleDao;
import persistence.dao.GruppoDao;
import persistence.dao.PostDao;


public class CreaPost extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private class CanaleGruppo{		
		private String canale;
		private String gruppo;
		private String contenuto;		
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		
		Utente utente = (Utente) req.getSession().getAttribute("user");
		PostDao postDao = DatabaseManager.getInstance().getDaoFactory().getPostDAO();
		CanaleDao canaleDao = DatabaseManager.getInstance().getDaoFactory().getCanaleDAO();
		GruppoDao gruppoDao = DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String linea = br.readLine();
		//System.out.println(linea);
        CanaleGruppo cg = new Gson().fromJson(linea, CanaleGruppo.class);
		
        Canale canale = canaleDao.findByPrimaryKey(cg.canale);
        Gruppo gruppo = gruppoDao.findByPrimaryKey(cg.gruppo, cg.canale);
		
		Post post = new Post(utente, cg.contenuto, canale, gruppo, Calendar.getInstance().getTime());
		
		
		postDao.save(post);
		
		resp.getWriter().write("true");
	}
}
