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
import model.Gruppo;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.CanaleDao;
import persistence.dao.GruppoDao;

public class CreaCanale extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private class Channel{
		private String nome,descrizione;
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		Utente utente = (Utente) session.getAttribute("user");
		CanaleDao canaleDao = DatabaseManager.getInstance().getDaoFactory().getCanaleDAO();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
			Channel ch = new Gson().fromJson(br.readLine(), Channel.class);
					
			if (canaleDao.findByPrimaryKey(ch.nome) == null) {
				
				Canale canale = new Canale(ch.nome, ch.descrizione, Calendar.getInstance().getTime(), utente);			
				canaleDao.save(canale);
		
				GruppoDao gruppoDao = DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
				
				Gruppo home = new Gruppo("home", Calendar.getInstance().getTime(), canale);			
				home.addAdmin(utente);
				home.addMembro(utente);			
				gruppoDao.save(home);
				
				resp.getWriter().write("success");
			}else 
				resp.getWriter().write("AlreadyExists");
		}catch(Exception e) {
			System.out.println("Error to create Channel: "+e.getMessage());
			resp.getWriter().write("error");
		}
	}
}
