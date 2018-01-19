package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.UtenteDao;

@WebServlet(value="/loginFB")
public class LoginFB extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Utente user = (Utente) session.getAttribute("user");
		if(user == null) {
			String email = req.getParameter("emailFB");
			System.out.println(req.getParameter("emailFB")+" "+req.getParameter("name")+" "+req.getParameter("surname")+" "+req.getParameter("birthday"));
			UtenteDao utentedao = DatabaseManager.getInstance().getDaoFactory().getUtenteDAO(); 
			user = utentedao.findByPrimaryKey(email);
			if(user != null) {
				session.setAttribute("user", user);
				resp.sendRedirect("index");
			}else
				req.getRequestDispatcher("register.jsp").forward(req, resp);
				//resp.sendRedirect("register.jsp");
		}else
			resp.sendRedirect("index");
	}
}
