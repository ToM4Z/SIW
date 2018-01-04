package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.UtenteDao;

public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//controllo se l'email è già registrata nel db
		if(checkEmail(req.getParameter("email")))
			resp.getWriter().write("true");
		else
			resp.getWriter().write("false");
	}
	
	private boolean checkEmail(String s) {
		UtenteDao utentedao = DatabaseManager.getInstance().getDaoFactory().getUtenteDAO();
		return utentedao.findByPrimaryKey(s) != null;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if(!checkEmail(req.getParameter("email"))){
			Utente user = new Utente();
			user.setEmail(req.getParameter("email"));
			user.setNome(req.getParameter("nome"));
			user.setCognome(req.getParameter("cognome"));
			user.setUsername(req.getParameter("username"));
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				user.setDataDiNascita(format.parse(req.getParameter("datadinascita")));
			} catch (ParseException e) {
				user.setDataDiNascita(null);
			}
			user.setDataIscrizione(Calendar.getInstance().getTime());
	
			UtenteDao utentedao = DatabaseManager.getInstance().getDaoFactory().getUtenteDAO();
			utentedao.save(user);
			utentedao.setPassword(user,req.getParameter("password"));
			
			req.getRequestDispatcher("login.html").include(req, resp);
		}
	}
}