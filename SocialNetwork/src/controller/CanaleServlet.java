package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Canale;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.CanaleDao;


public class CanaleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private class InfoChannel{
		private boolean iscritto = false;
		private boolean blacklist = false;
		private Canale canale;
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Utente utente = (Utente) req.getSession().getAttribute("user");
		
		InfoChannel channel = new InfoChannel();
		CanaleDao dao =DatabaseManager.getInstance().getDaoFactory().getCanaleDAO();
		channel.canale = dao.findByPrimaryKey(req.getParameter("channel"));
		
		
		for (Utente u : channel.canale.getMembri())			
			if (u.getEmail().equals(utente.getEmail())) {
				channel.iscritto=true;
				break;
			}		
		
		for (Utente u : channel.canale.getBlacklist())			
			if (u.getEmail().equals(utente.getEmail())) {			
				channel.blacklist = true;
				break;
			}
		
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(new Gson().toJson(channel));		
	}
		
	private class ModificaDescrizione{		
		private String nomeCanale;
		private String modifica;
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        ModificaDescrizione md = new Gson().fromJson(br.readLine(), ModificaDescrizione.class);
        
        CanaleDao canaleDao = DatabaseManager.getInstance().getDaoFactory().getCanaleDAO();
        
        Canale canale = canaleDao.findByPrimaryKey(md.nomeCanale);        
        canale.setDescrizione(md.modifica);        
        canaleDao.update(canale);
        
        resp.getWriter().write("success");
	}

}
