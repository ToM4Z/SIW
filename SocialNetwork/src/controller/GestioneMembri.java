package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

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
import persistence.dao.UtenteDao;


public class GestioneMembri extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getSession().getAttribute("user") == null) {
			resp.sendRedirect("login.html");
			return;
		}
		String nomeGruppo = req.getParameter("group");
		String nomeCanale = req.getParameter("channel");
		GruppoDao gruppoDao =DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
		CanaleDao canaleDao =DatabaseManager.getInstance().getDaoFactory().getCanaleDAO();
		Gruppo gruppo = gruppoDao.findByPrimaryKey(nomeGruppo, nomeCanale);
		Canale canale = canaleDao.findByPrimaryKey(nomeCanale);
		
		List<String> out = new LinkedList<>();
		
		System.out.println(gruppo.getMembri().size());
		
		for (Utente u : canale.getMembri()) {
			boolean in = false;
			
			for (Utente u1 : gruppo.getMembri()) {
				
				if (u.getEmail().equals(u1.getEmail()))
					in = true;
			}
			if (in) {
				out.add("<div id=\"mem"+u.getUsername()+"\"><h4 onclick = javascript:rimuoviMembro('"+u.getEmail()+"')>"+u.getUsername()+" Rimuovi membro</h4></div>");
				//System.out.println("admin"+u.getUsername());
			}
			else {
				out.add("<div id= \"mem"+u.getUsername()+"\"><h4 onclick = javascript:aggiungiMembro('"+u.getEmail()+"')>"+u.getUsername()+" Aggiungi membro</h4></div>");
				//System.out.println("non admin"+u.getUsername());
			}
		}
		
		req.setAttribute("righe", out);
		req.setAttribute("nomegruppo", nomeGruppo);
		req.setAttribute("nomecanale", nomeCanale);
		
		req.getRequestDispatcher("gestioneMembri.jsp").forward(req, resp);
		
	}
	
	
	private class Membri{
		
		private String nomeCanale;
		private String nomeGruppo;
		private String user;
		private String azione;
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String linea = br.readLine();
		Membri m = new Gson().fromJson(linea, Membri.class);
		//System.out.println(linea);
    	  
        if (m.azione.equals("aggiungi")) {
            
        	UtenteDao utenteDao = DatabaseManager.getInstance().getDaoFactory().getUtenteDAO();
        	GruppoDao gruppoDao =DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
        	Utente utente = utenteDao.findByPrimaryKey(m.user);
        	Gruppo gruppo = gruppoDao.findByPrimaryKey(m.nomeGruppo, m.nomeCanale);

        	gruppoDao.addUserToGroup(gruppo, utente);
        	System.out.println("aggiunto ai membri");
        	resp.getWriter().write(new Gson().toJson(utente.getUsername()));
        }
        
        else if (m.azione.equals("rimuovi")) {

        	UtenteDao utenteDao = DatabaseManager.getInstance().getDaoFactory().getUtenteDAO();
        	GruppoDao gruppoDao =DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
        	Utente utente = utenteDao.findByPrimaryKey(m.user);
        	Gruppo gruppo = gruppoDao.findByPrimaryKey(m.nomeGruppo, m.nomeCanale);

        	gruppoDao.removeUserFromGroup(gruppo, utente);
        	resp.getWriter().write(new Gson().toJson(utente.getUsername()));
        }
        
	}

}
