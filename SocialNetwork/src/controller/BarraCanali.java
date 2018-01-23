package controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Canale;
import model.Gruppo;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.UtenteDao;


public class BarraCanali extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
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
		
		resp.setCharacterEncoding("UTF-8");
	    resp.getWriter().write(new Gson().toJson(canaliegruppi));
	}
}
