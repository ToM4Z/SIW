package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Gruppo;
import persistence.DatabaseManager;
import persistence.dao.GruppoDao;


public class EliminaGruppo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String nomeGruppo = req.getParameter("group");
		String nomeCanale = req.getParameter("channel");
		
		GruppoDao gruppoDao = DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
		
		Gruppo gruppo = gruppoDao.findByPrimaryKey(nomeGruppo, nomeCanale);
		
		gruppoDao.delete(gruppo);
		
		resp.sendRedirect("canale?channel="+nomeCanale);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doGet(req, resp);
	}

}
