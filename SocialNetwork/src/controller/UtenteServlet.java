package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.UtenteDao;

public class UtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		UtenteDao dao =DatabaseManager.getInstance().getDaoFactory().getUtenteDAO();
		String emailUtente = req.getParameter("to");
		Utente f;
		if(!emailUtente.equals(""))
			f = dao.findByPrimaryKey(emailUtente);
		else
			f = (Utente) req.getSession().getAttribute("user");
		
		Utente utente = new Utente();
		utente.setEmail(f.getEmail());
		utente.setUsername(f.getUsername());
		utente.setNome(f.getNome());
		utente.setCognome(f.getCognome());
		utente.setDataDiNascita(f.getDataDiNascita());
		utente.setDataIscrizione(f.getDataIscrizione());
		
		resp.getWriter().write(new Gson().toJson(utente));
	}

}
