package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

import model.Canale;
import persistence.DatabaseManager;
import persistence.dao.CanaleDao;
import persistence.dao.GruppoDao;


public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
	}
	
	private class Searched{
		
		private String search;
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String linea = br.readLine();
		System.out.println(linea);
        Searched s = new Gson().fromJson(linea, Searched.class);
        
        CanaleDao canaleDao = DatabaseManager.getInstance().getDaoFactory().getCanaleDAO();
        
        List<Canale> canali = canaleDao.findAll();
        List<String> results = new LinkedList<>();
        CharSequence toSearch = s.search.toLowerCase().substring(0, s.search.length()-1);
        
        results.add("<ul>");
        
        for (Canale c : canali) {
        	
        	if (c.getNome().toLowerCase().contains(toSearch)) {
        		
        		results.add("<li><a href = canale?channel="+c.getNome()+">" +c.getNome()+"</a></li>");
        	}
        }
        results.add("<ul>");
        System.out.println(results.size());
        resp.getWriter().write(new Gson().toJson(results));
        
	}
	
	

}
