package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Gruppo;
import persistence.DatabaseManager;
import persistence.dao.GruppoDao;



public class GruppoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String nomeGruppo = req.getParameter("group");
		String nomeCanale = req.getParameter("channel");
		GruppoDao dao =DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
		//System.out.println("gruppo"+nomeGruppo);
		//System.out.println("canale"+nomeCanale);
		Gruppo gruppo = dao.findByPrimaryKey(nomeGruppo, nomeCanale);
		req.setAttribute("gruppo", gruppo);
		
		
		req.getRequestDispatcher("gruppo.jsp").forward(req, resp);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doGet(req, resp);
	}

}
