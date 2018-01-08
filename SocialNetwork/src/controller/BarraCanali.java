package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import model.Canale;
import model.Gruppo;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.UtenteDao;


public class BarraCanali extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doPost(req, resp);
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		
		UtenteDao dao =DatabaseManager.getInstance().getDaoFactory().getUtenteDAO();
		Utente utente = (Utente) req.getSession().getAttribute("user");
		
		List<Canale> canali = dao.getMyChannels(utente);
		
		List <List<String>> canaliegruppi = new LinkedList<>();
		
		
		for (Canale c: canali) {
			List<String> l = new LinkedList<>();
			l.add(c.getNome());
			for (Gruppo g : c.getGruppi()) {
				l.add(g.getNome());
			}
			canaliegruppi.add(l);
		}
		
	    
		//System.out.println("qui");
	   // System.out.println(new Gson().toJson(canaliegruppi));
	    resp.getWriter().write(new Gson().toJson(canaliegruppi));
		
		 
		
		
		//System.out.println("qui");
		//resp.getWriter().write(new Gson().toJson(canali));
		
		
	}

}
