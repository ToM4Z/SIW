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

import model.Gruppo;
import model.Post;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.GruppoDao;

public class UpdateReaction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private class Input{		
		private String nomeCanale;
		private String nomeGruppo;
	}
	
	@SuppressWarnings("unused")
	private class Info{		
		private Long postId;
		private int like;
		private int nLike;
		private int dislike;
		private int nDislike;
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Utente utente = (Utente) req.getSession().getAttribute("user");
		List<Info> info = new LinkedList<>();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		Input i = new Gson().fromJson(br.readLine(), Input.class);
        
        GruppoDao gruppoDao = DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
        Gruppo gruppo = gruppoDao.findByPrimaryKey(i.nomeGruppo, i.nomeCanale);
		
		for (Post p : gruppo.getPost()) {
			Info in = new Info();
			in.postId=p.getId();
			in.nLike=p.getLike().size();
			in.nDislike=p.getDislike().size();
			in.like=0;
			for(String s : p.getLike()) 				
				if (s.equals(utente.getEmail()))
					in.like=1;
			
			in.dislike=0;
			for(String s : p.getDislike())				
				if (s.equals(utente.getEmail()))
					in.dislike=1;
			
			info.add(in);
		}
		
		resp.getWriter().write(new Gson().toJson(info));
	}

}
