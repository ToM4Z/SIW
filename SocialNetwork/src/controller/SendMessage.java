package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Gruppo;
import model.Messaggio;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.GruppoDao;
import persistence.dao.MessaggioDao;

@WebServlet(value="/sendMessage")
public class SendMessage extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Utente utente = (Utente) req.getSession().getAttribute("user");
		if(utente == null)
			resp.sendRedirect("login.html");
		
		try {
			String gruppo = req.getParameter("gruppo");
			String canale = req.getParameter("canale");
			String testo = req.getParameter("testo");
			
	        GruppoDao gruppoDao = DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
	        MessaggioDao messaggioDao = DatabaseManager.getInstance().getDaoFactory().getMessaggioDAO();
			
			Gruppo group = gruppoDao.findByPrimaryKey(gruppo,canale);
			
			Messaggio mex = new Messaggio(utente, group, testo);
			
			messaggioDao.save(mex);
			
		}catch(Exception e) {
			resp.getWriter().write("error");
			e.printStackTrace();
		}
	}
}