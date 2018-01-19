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
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.CanaleDao;
import persistence.dao.UtenteDao;


public class GestioneBlacklist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String nomeCanale = req.getParameter("channel");
		CanaleDao canaleDao =DatabaseManager.getInstance().getDaoFactory().getCanaleDAO();
		
		Canale canale = canaleDao.findByPrimaryKey(nomeCanale);
		
		List<String> out = new LinkedList<>();
		
		//System.out.println("Blacklist: "+canale.getBlacklist().size()+" membri: "+canale.getMembri().size());
		for (Utente u : canale.getMembri()) {
			boolean blacklist = false;
			
			for (Utente u1 : canale.getBlacklist()) {
				
				if (u.getEmail().equals(u1.getEmail()))
					blacklist = true;
			}
			if (blacklist) {
				out.add("<h4 onclick = javascript:rimuoviBlacklist('"+u.getEmail()+"')>"+u.getNome()+" "+u.getCognome()+" Rimuovi dalla Blacklist</h4>");
				//System.out.println("admin"+u.getUsername());
			}
			else {
				out.add("<h4 onclick = javascript:aggiungiBlacklist('"+u.getEmail()+"')>"+u.getNome()+" "+u.getCognome()+" Aggiungi alla Blacklist</h4>");
				//System.out.println("non admin"+u.getUsername());
			}
		}
		
		req.setAttribute("righe", out);
		req.setAttribute("canale", nomeCanale);
		
		req.getRequestDispatcher("gestioneBlacklist.jsp").forward(req, resp);
	}
	
	private class Blacklist{
		
		private String nomeCanale;
		private String user;
		private String azione;
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String linea = br.readLine();
		Blacklist a = new Gson().fromJson(linea, Blacklist.class);
		//System.out.println(linea);
    	  
        if (a.azione.equals("aggiungi")) {
            
            CanaleDao canaleDao = DatabaseManager.getInstance().getDaoFactory().getCanaleDAO();
        	UtenteDao utenteDao = DatabaseManager.getInstance().getDaoFactory().getUtenteDAO();
        	Canale canale = canaleDao.findByPrimaryKey(a.nomeCanale);
        	Utente utente = utenteDao.findByPrimaryKey(a.user);

        	canaleDao.addUserToBlackList(canale, utente);
        	//System.out.println("aggiunto alla blacklist");
        }
        
        else if (a.azione.equals("rimuovi")) {

        	CanaleDao canaleDao = DatabaseManager.getInstance().getDaoFactory().getCanaleDAO();
        	UtenteDao utenteDao = DatabaseManager.getInstance().getDaoFactory().getUtenteDAO();
        	Canale canale = canaleDao.findByPrimaryKey(a.nomeCanale);
        	Utente utente = utenteDao.findByPrimaryKey(a.user);

        	canaleDao.removeUserFromBlackList(canale, utente);
        }
        
        resp.getWriter().write("true");
		
	}
	

}
