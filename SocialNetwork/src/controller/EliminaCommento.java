package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


import model.Commento;
import persistence.DatabaseManager;
import persistence.dao.CommentoDao;


public class EliminaCommento extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
	}

	
	private class DelCommento{
		
		private String idCommento;
		private String elimina;
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String linea = br.readLine();
		DelCommento jc = new Gson().fromJson(linea, DelCommento.class);
		System.out.println(linea);
		
		CommentoDao commentoDao = DatabaseManager.getInstance().getDaoFactory().getCommentoDAO();
		Commento commento = commentoDao.findByPrimaryKey(Long.parseLong(jc.idCommento));
		commentoDao.delete(commento);
		resp.getWriter().write("true");
		
	}

}
