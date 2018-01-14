package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;


import model.Gruppo;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.GruppoDao;
import persistence.dao.NotificaDao;
import persistence.dao.UtenteDao;


public class GestioneAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String nomeGruppo = req.getParameter("group");
		String nomeCanale = req.getParameter("channel");
		GruppoDao gruppoDao =DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
		
		Gruppo gruppo = gruppoDao.findByPrimaryKey(nomeGruppo, nomeCanale);
		
		List<String> out = new LinkedList<>();
		
		System.out.println("membri "+gruppo.getMembri().size());
		System.out.println("admins: "+gruppo.getAdmins().size());
		
		for (Utente u : gruppo.getMembri()) {
			boolean admin = false;
			
			for (Utente u1 : gruppo.getAdmins()) {
				
				if (u.getEmail().equals(u1.getEmail()))
					admin = true;
			}
			if (admin) {
				out.add("<h4 onclick = javascript:rimuoviAdmin('"+u.getEmail()+"')>"+u.getNome()+" "+u.getCognome()+" Rimuovi dagli admin</h4>");
				//System.out.println("admin"+u.getUsername());
			}
			else {
				out.add("<h4 onclick = javascript:aggiungiAdmin('"+u.getEmail()+"')>"+u.getNome()+" "+u.getCognome()+" Aggiungi dagli admin</h4>");
				//System.out.println("non admin"+u.getUsername());
			}
		}
		
		req.setAttribute("righe", out);
		req.setAttribute("gruppo", nomeGruppo);
		req.setAttribute("canale", nomeCanale);
		
		req.getRequestDispatcher("gestioneAdmin.jsp").forward(req, resp);
	}
	
	private class Admin{
		
		private String nomeCanale;
		private String nomeGruppo;
		private String user;
		private String azione;
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String linea = br.readLine();
		Admin a = new Gson().fromJson(linea, Admin.class);
    	  
        if (a.azione.equals("aggiungi")) {
            
            GruppoDao gruppoDao = DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
        	UtenteDao utenteDao = DatabaseManager.getInstance().getDaoFactory().getUtenteDAO();
        	Gruppo gruppo = gruppoDao.findByPrimaryKey(a.nomeGruppo, a.nomeCanale);
        	Utente utente = utenteDao.findByPrimaryKey(a.user);

        	gruppoDao.addUserToAdmin(gruppo, utente); 
        }
        
        else if (a.azione.equals("rimuovi")) {

            GruppoDao gruppoDao = DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
        	UtenteDao utenteDao = DatabaseManager.getInstance().getDaoFactory().getUtenteDAO();
        	Gruppo gruppo = gruppoDao.findByPrimaryKey(a.nomeGruppo, a.nomeCanale);
        	Utente utente = utenteDao.findByPrimaryKey(a.user);
        	
        	gruppoDao.removeUserFromAdmin(gruppo, utente);
        }
        
        resp.getWriter().write("true");
		
	}

}
