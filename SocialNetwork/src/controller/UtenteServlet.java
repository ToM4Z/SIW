package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.UtenteDao;

public class UtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String emailUtente = req.getParameter("to");
		if(emailUtente!=null) {
			UtenteDao dao =DatabaseManager.getInstance().getDaoFactory().getUtenteDAO();
			Utente utente = dao.findByPrimaryKey(emailUtente);
			
			req.setAttribute("utente", utente);
		}
		req.getRequestDispatcher("utente.jsp").forward(req, resp);
	}

}
