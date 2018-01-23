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
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.CanaleDao;
import persistence.dao.GruppoDao;


public class CreaGruppo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private class GruppoCanale{
		private String nomeGruppo,nomeCanale;
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		
		Utente utente = (Utente) req.getSession().getAttribute("user");
		
		CanaleDao canaleDao = DatabaseManager.getInstance().getDaoFactory().getCanaleDAO();
		GruppoDao gruppoDao = DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		GruppoCanale md = new Gson().fromJson(br.readLine(), GruppoCanale.class);
        
		String nomeCanale = md.nomeCanale;
		String nomeGruppo = md.nomeGruppo;
		
		if (gruppoDao.findByPrimaryKey(nomeGruppo, nomeCanale) == null) {
		
			Canale canale = canaleDao.findByPrimaryKey(nomeCanale);
			
			Gruppo gruppo = new Gruppo(nomeGruppo, Calendar.getInstance().getTime(), canale);
			
			gruppo.addAdmin(utente);
			gruppo.addMembro(utente);
			gruppo.addAdmin(canale.getAdmin());
			gruppo.addMembro(canale.getAdmin());
			
			gruppoDao.save(gruppo);
			
			resp.getWriter().write("success");
		}else
			resp.getWriter().write("AlreadyExists");
	}
}
