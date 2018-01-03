package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utente;
import persistence.DatabaseManager;
import persistence.UtenteCredenziali;

public class Login extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		UtenteCredenziali user = DatabaseManager.getInstance().getDaoFactory().getUtenteDAO().findByPrimaryKeyCredential(email);
		
		if(user != null) {
			if(password.equals(user.getPassword())) {
				HttpSession session = req.getSession();
				session.setAttribute("user", (Utente) user);
				
				req.getRequestDispatcher("home").forward(req, resp);
			}else {
				resp.setContentType("text/html");
				PrintWriter out = resp.getWriter();
				out.println("<html><body>");
				out.println("<h1>Password errata!</h1>");
				req.getRequestDispatcher("login.html").include(req, resp);
				out.println("</body></html>");
			}
		}else {			
			resp.setContentType("text/html");
			PrintWriter out = resp.getWriter();
			out.println("<html><body>");
			out.println("<h1>Account con email "+email+" non esistente!</h1>");
			req.getRequestDispatcher("login.html").include(req, resp);
			out.println("</body></html>");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}	
}
