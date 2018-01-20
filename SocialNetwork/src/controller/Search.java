package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

import model.Canale;
import persistence.DatabaseManager;
import persistence.dao.CanaleDao;


public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
    	
	private class Searched{		
		private String search;
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String linea = br.readLine();
        Searched s = new Gson().fromJson(linea, Searched.class);
        CharSequence toSearch = s.search.toLowerCase().substring(0, s.search.length());
        
        List<String> results = new LinkedList<>();
        
        if(!toSearch.equals("")) {
        	CanaleDao canaleDao = DatabaseManager.getInstance().getDaoFactory().getCanaleDAO();
	        
	        List<Canale> canali = canaleDao.findAll();
	                
	        for (Canale c : canali) {
	        	
	        	if (c.getNome().toLowerCase().contains(toSearch)) {
	        		
	        		results.add("<li><a href = canale?channel="+c.getNome()+">" +c.getNome()+"</a></li>");
	        	}
	        }
	        resp.getWriter().write(new Gson().toJson(results));
        }else
	        resp.getWriter().write(new Gson().toJson(results));
	}
}