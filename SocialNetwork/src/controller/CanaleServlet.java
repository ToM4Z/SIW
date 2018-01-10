package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Canale;
import persistence.DatabaseManager;
import persistence.dao.CanaleDao;


public class CanaleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String nomeCanale = req.getParameter("channel");
		CanaleDao dao =DatabaseManager.getInstance().getDaoFactory().getCanaleDAO();
		
		//System.out.println(nomeCanale);
		Canale canale = dao.findByPrimaryKey(nomeCanale);
		req.setAttribute("canale", canale);
		
		//System.out.println(canale.getNome());
		
		req.getRequestDispatcher("canale.jsp").forward(req, resp);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
