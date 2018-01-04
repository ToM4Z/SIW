package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Gruppo;
import model.Post;
import persistence.DatabaseManager;
import persistence.dao.CanaleDao;
import persistence.dao.GruppoDao;



public class GruppoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String nomeGruppo = req.getParameter("to");
		String nomeCanale = req.getParameter("at");
		GruppoDao dao =DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
		
		Gruppo gruppo = dao.findByPrimaryKey(nomeGruppo, nomeCanale);
		req.setAttribute("gruppo", gruppo);
		
		
		req.getRequestDispatcher("gruppo.jsp").forward(req, resp);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doGet(req, resp);
	}

}
