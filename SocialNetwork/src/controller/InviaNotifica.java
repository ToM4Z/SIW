package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Gruppo;
import model.Notifica;
import model.Utente;
import persistence.DatabaseManager;
import persistence.dao.GruppoDao;
import persistence.dao.NotificaDao;
import persistence.dao.UtenteDao;


public class InviaNotifica extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		Utente utente = (Utente) session.getAttribute("user");
		NotificaDao notificaDao = DatabaseManager.getInstance().getDaoFactory().getNotificaDAO();
		String tipo = req.getParameter("tipo");
		String nomeGruppo = req.getParameter("group");
		String nomeCanale = req.getParameter("channel");
		
		if (tipo.equals("richiestaIscrizione")) {
			
			
			GruppoDao gruppoDao = DatabaseManager.getInstance().getDaoFactory().getGruppoDAO();
			Gruppo gruppo = gruppoDao.findByPrimaryKey(nomeGruppo, nomeCanale);
			
			System.out.println("in inviaNotifica, nome gruppo: "+gruppo.getNome());
			
			String contenuto = "<a href = utentiInAttesa?group="+gruppo.getNome()+"&channel="+gruppo.getCanale().getNome()+">L'utente "+utente.getUsername()
				+" ha chiesto di iscriversi al gruppo "+gruppo.getNome()+"</a>";
			
			for (Utente admin : gruppo.getAdmins()) {
				Notifica notifica = new Notifica(admin, contenuto);
				notificaDao.save(notifica);
				System.out.println("notifica inviata a "+admin.getEmail());
			}
			
			gruppoDao.addUserToAttesa(gruppo, utente);
			System.out.println("aggiunto in attesa in "+gruppo.getNome());
			
		}
		
		resp.sendRedirect("canale?channel="+nomeCanale);
		
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doGet(req, resp);
	}

}
