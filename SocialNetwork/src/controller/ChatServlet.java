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
import model.Messaggio;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.CanaleDao;
import persistence.dao.GruppoDao;
import persistence.dao.MessaggioDao;


public class ChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String nomeCanale = req.getParameter("channel");
		String nomeGruppo = req.getParameter("group");
		String time = req.getParameter("time");
		
		GruppoDao gruppoDao = DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
		
		Gruppo gruppo = gruppoDao.findByPrimaryKey(nomeGruppo, nomeCanale);
		
		List<String> list = new LinkedList<>();
		
		if (time.equals("first")) {
		
			for (Messaggio m : gruppo.getChat()) {
				
				String mex = m.getMittente().getUsername()+" : "+m.getContenuto();
				list.add(mex);
			}
			resp.getWriter().write(new Gson().toJson(list));
			System.out.println("qui");
		}
		
		else{
			
			if (!gruppo.getChat().isEmpty()) {
				String message;
				Messaggio m = gruppo.getChat().get(gruppo.getChat().size()-1);
				message = m.getMittente().getUsername()+" : "+m.getContenuto();
				resp.getWriter().write(new Gson().toJson(message));
			}
		}
	}
	
	private class GetMessage{
		
		private String gruppo;
		private String canale;
		private String contenuto;
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		Utente utente = (Utente) session.getAttribute("user");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String linea = br.readLine();
		System.out.println(linea);
        GetMessage gm = new Gson().fromJson(linea, GetMessage.class);
        
        GruppoDao gruppoDao = DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
        MessaggioDao messaggioDao = DatabaseManager.getInstance().getDaoFactory().getMessaggioDAO();
		
		Gruppo gruppo = gruppoDao.findByPrimaryKey(gm.gruppo, gm.canale);
		
		Messaggio mex = new Messaggio(utente, gruppo, gm.contenuto);
		
		messaggioDao.save(mex);
		
		
	}

}
