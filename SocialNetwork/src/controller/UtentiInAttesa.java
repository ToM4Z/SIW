package controller;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Gruppo;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.GruppoDao;


public class UtentiInAttesa extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String nomeGruppo = req.getParameter("group");
		String canale = req.getParameter("channel");
		boolean vuoto = false;
		GruppoDao gruppoDao = DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
		
		Gruppo gruppo = gruppoDao.findByPrimaryKey(nomeGruppo, canale);
		
		Set<Utente> inAttesa = gruppo.getUtentiInAttesa();
		
		//System.out.println("in servlet utentiInAttesa "+inAttesa.size() +"del gruppo "+gruppo.getNome());
		
		vuoto = inAttesa.isEmpty();
		
		req.setAttribute("utentiInAttesa", inAttesa);
		req.setAttribute("gruppo", gruppo);
		req.setAttribute("vuoto", vuoto);
		
		req.getRequestDispatcher("utentiInAttesa.jsp").forward(req, resp);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doGet(req, resp);
	}

}
