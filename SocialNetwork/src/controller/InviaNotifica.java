package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import model.Gruppo;
import model.Notifica;
import model.Post;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.GruppoDao;
import persistence.dao.NotificaDao;
import persistence.dao.PostDao;



public class InviaNotifica extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private class TipoNotifica{
		
		private String nomeGruppo;
		private String nomeCanale;
		private String tipo;
		private String idPost;
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		Utente utente = (Utente) session.getAttribute("user");
		NotificaDao notificaDao = DatabaseManager.getInstance().getDaoFactory().getNotificaDAO();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String linea = br.readLine();
		System.out.println(linea);
        TipoNotifica t = new Gson().fromJson(linea, TipoNotifica.class);
		
		if (t.tipo.equals("richiestaIscrizione")) {
			
			
			GruppoDao gruppoDao = DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
			Gruppo gruppo = gruppoDao.findByPrimaryKey(t.nomeGruppo, t.nomeCanale);
			
			//System.out.println("in inviaNotifica, nome gruppo: "+gruppo.getNome());
			
			String contenuto = "<a href = utentiInAttesa?group="+gruppo.getNome()+"&channel="+gruppo.getCanale().getNome()+">L'utente "+utente.getUsername()
				+" ha chiesto di iscriversi al gruppo "+gruppo.getNome()+"</a>";
			
			for (Utente admin : gruppo.getAdmins()) {
				Notifica notifica = new Notifica(admin, contenuto);
				notificaDao.save(notifica);
				//System.out.println("notifica inviata a "+admin.getEmail());
			}
			
			gruppoDao.addUserToAttesa(gruppo, utente);
			//System.out.println("aggiunto in attesa in "+gruppo.getNome());
			
		}
		
		if (t.tipo.equals("commento")) {
			
			PostDao postDao = DatabaseManager.getInstance().getDaoFactory().getPostDAO();
			Post post = postDao.findByPrimaryKey(Long.parseLong(t.idPost));
			Utente destinatario = post.getCreatore();
			boolean invia = true;
			String contenuto = "<a href = commenti?idPost="+t.idPost +">Il tuo post nel gruppo "+post.getGruppo().getNome()
					+"del canale "+post.getCanale().getNome()+"è stato commentato</a>";
			
			for (Notifica n : destinatario.getNotifiche()) {
				
				if (n.getContenuto().equals(contenuto)) {
					invia = false;
				}
			}
			
			if (invia) {
				Notifica notifica = new Notifica(destinatario, contenuto);
				notificaDao.save(notifica);
				System.out.println("notifica commento inviata");
			}
			
		}
		
		resp.getWriter().write("true");
	}

}
