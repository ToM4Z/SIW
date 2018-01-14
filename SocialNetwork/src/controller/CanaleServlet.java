package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Canale;
import model.Gruppo;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.CanaleDao;


public class CanaleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		Utente utente = (Utente) session.getAttribute("user");
		boolean iscritto = false;
		String nomeCanale = req.getParameter("channel");
		CanaleDao dao =DatabaseManager.getInstance().getDaoFactory().getCanaleDAO();
		
		//System.out.println(nomeCanale);
		Canale canale = dao.findByPrimaryKey(nomeCanale);
		
		for (Utente u : canale.getMembri()) {
			
			System.out.println(u.getEmail());
			
			if (u.getEmail().equals(utente.getEmail()))
				iscritto=true;
		}
		
		for (Gruppo g : canale.getGruppi()) {
			
			System.out.println(g.getNome());
			
			for(Utente u : g.getMembri()) {
				System.out.println(u.getNome());
			}
		}
		
		req.setAttribute("iscritto", iscritto);
		req.setAttribute("canale", canale);
		
		//System.out.println(canale.getNome());
		
		req.getRequestDispatcher("canale.jsp").forward(req, resp);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
