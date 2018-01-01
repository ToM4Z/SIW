package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.setAttribute("username", "mario");
		String username = (String) session.getAttribute("username");
		String messaggio;
		if (username == null){
			messaggio = "Login";
			req.setAttribute("loggato", false);
		}else{
			messaggio = "Benvenuto Sig. " + username;
			req.setAttribute("loggato", true);
		}
		
		req.setAttribute("mex", messaggio);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("home.jsp");
		    
		dispatcher.forward(req, resp);
	}
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
