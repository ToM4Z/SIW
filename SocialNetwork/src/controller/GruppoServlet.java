package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Canale;
import model.Gruppo;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.CanaleDao;
import persistence.dao.GruppoDao;

public class GruppoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		if(req.getSession().getAttribute("user") == null) {
			resp.sendRedirect("login.html");
			return;
		}
		String nomeGruppo = req.getParameter("group");
		String nomeCanale = req.getParameter("channel");
		GruppoDao dao =DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
		CanaleDao canaleDao =DatabaseManager.getInstance().getDaoFactory().getCanaleDAO();
		
		Canale canale = canaleDao.findByPrimaryKey(nomeCanale);
		Gruppo gruppo = dao.findByPrimaryKey(nomeGruppo, nomeCanale);
		req.setAttribute("gruppo", gruppo);
		
		boolean admin = false;
		boolean iscritto = false;
		Utente utente = (Utente) req.getSession().getAttribute("user");
		boolean blacklist = false;
		for (Utente u : canale.getBlacklist()) {
			
			if (u.getEmail().equals(utente.getEmail()))
				blacklist = true;
		}
		
		for(Utente u : gruppo.getAdmins())			
			if (u.getEmail().equals(utente.getEmail()))
				admin = true;
		
		for (Utente u : gruppo.getMembri())			
			if (u.getEmail().equals(utente.getEmail()))
				iscritto = true;		
		
		req.setAttribute("admin", admin);
		req.setAttribute("iscritto", iscritto);
		
		boolean canaleAdmin = false;
		
		if (utente.getEmail().equals(gruppo.getCanale().getAdmin().getEmail()))
			canaleAdmin = true;
		
		req.setAttribute("canaleAdmin", canaleAdmin);
		req.setAttribute("blacklist", blacklist);
		
		req.getRequestDispatcher("gruppo.jsp").forward(req, resp);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
