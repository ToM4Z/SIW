package controller;

import java.io.IOException;
import java.io.PrintWriter;
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

public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (DatabaseManager.getInstance().getDaoFactory().getUtenteDAO()
				.findByPrimaryKey(req.getParameter("email")) != null) {

			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();
			out.println("<html><body>");
			out.println("<h1>Email già esistente!</h1>");
			req.getRequestDispatcher("login.html").include(req, resp);
			out.println("</body></html>");
			
		} else {
			Utente user = new Utente();
			user.setEmail(req.getParameter("email"));
			user.setNome(req.getParameter("nome"));
			user.setCognome(req.getParameter("cognome"));
			user.setUsername(req.getParameter("username"));
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				user.setDataDiNascita(format.parse(req.getParameter("datadinascita")));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			user.setDataIscrizione(Calendar.getInstance().getTime());

			DatabaseManager.getInstance().getDaoFactory().getUtenteDAO().save(user);
			DatabaseManager.getInstance().getDaoFactory().getUtenteDAO().setPassword(user,
					req.getParameter("password"));

			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();
			out.println("<html><body>");
			out.println("<h1>Registrazione Completata!</h1>");
			req.getRequestDispatcher("login.html").include(req, resp);
			out.println("</body></html>");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}