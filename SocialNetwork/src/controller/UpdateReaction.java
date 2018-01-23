package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import model.Gruppo;
import model.Post;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.CanaleDao;
import persistence.dao.GruppoDao;

public class UpdateReaction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	private class Input{
		
		private String nomeCanale;
		private String nomeGruppo;
	}
	
	private class Info{
		
		private Long postId;
		private int like;
		private int nLike;
		private int dislike;
		private int nDislike;
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		Utente utente = (Utente) session.getAttribute("user");
		List<Info> info = new LinkedList<>();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String linea = br.readLine();
		//System.out.println(linea);
        Input i = new Gson().fromJson(linea, Input.class);
        
        GruppoDao gruppoDao = DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
        
		Gruppo gruppo = gruppoDao.findByPrimaryKey(i.nomeGruppo, i.nomeCanale);
		//System.out.println(gruppo.getNome());
		for (Post p : gruppo.getPost()) {
			Info in = new Info();
			in.postId=p.getId();
			in.like = 0;
			in.nLike=p.getLike().size();
			in.nDislike=p.getDislike().size();
			in.like=0;
			for(String s : p.getLike()) {
				
				if (s.equals(utente.getEmail())) {
					in.like=1;
				}
			}
			in.dislike=0;
			for(String s : p.getDislike()) {
				
				if (s.equals(utente.getEmail())) {
					in.dislike=1;
				}
			}
			
			info.add(in);
		}
		
		String out = new Gson().toJson(info);
		//System.out.println(out);
		
		resp.getWriter().write(out);
	}

}
