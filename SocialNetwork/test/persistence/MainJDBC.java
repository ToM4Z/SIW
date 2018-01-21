package persistence;

import java.util.Calendar;
import java.util.List;

import model.Utente;
import model.Canale;
import model.Gruppo;
import model.Notifica;
import model.Post;
import persistence.dao.UtenteDao;
import persistence.dao.CanaleDao;
import persistence.dao.GruppoDao;
import persistence.dao.NotificaDao;
import persistence.dao.PostDao;

public class MainJDBC {
	
	public static void main(String args[]) {
		
		DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
		UtilDao util = factory.getUtilDAO();
		
		util.dropDatabase();
		
		util.createDatabase();
		
		
		UtenteDao utentedao = factory.getUtenteDAO();
		CanaleDao canaledao = factory.getCanaleDAO();
		GruppoDao gruppodao = factory.getGruppoDAO();
		NotificaDao notificadao = factory.getNotificaDAO();
		PostDao postdao = factory.getPostDAO();
		
		
		Calendar cal = Calendar.getInstance();
		cal.set(1996, 9, 24);		
		Utente u1 = new Utente("thom_96@hotmail.it","Thomas","Voce","ToMaZ",cal.getTime(),Calendar.getInstance().getTime());
		
		Canale c1 = new Canale("UNICAL","per studenti unical",Calendar.getInstance().getTime(),u1);
		c1.addMembro(u1);
		
		Gruppo g1 = new Gruppo("home",Calendar.getInstance().getTime(),c1);
		g1.addAdmin(u1);
		g1.addMembro(u1);
		c1.addGruppo(g1);
		
		utentedao.save(u1);
		utentedao.setPassword(u1, "a");
		canaledao.save(c1);
		gruppodao.save(g1);
				
		Post a = new Post();
		a.setCreatore(u1);
		a.setCanale(c1);
		a.setGruppo(g1);
		a.setContenuto("ciao");
		
		postdao.save(a);
		
		Utente u2 = new Utente("danielesalim@outlook.it","Daniele","Salimonti","ds.hitman",cal.getTime(),Calendar.getInstance().getTime());
		
		c1.addMembro(u1);
		
		g1 = new Gruppo("home2",Calendar.getInstance().getTime(),c1);
		g1.addAdmin(u1);
		g1.addMembro(u1);
		c1.addGruppo(g1);

		utentedao.save(u2);
		utentedao.setPassword(u2, "pasticcio");
		canaledao.addUserToBlackList(c1, u2);
		
		gruppodao.save(g1);
		
		a = new Post();
		a.setCreatore(u1);
		a.setCanale(c1);
		a.setGruppo(g1);
		a.setContenuto("primo post");
		
		postdao.save(a);
		
		Notifica n = new Notifica(u1,"<a href=\"canale?channel=UNICAL\">UNICAL</a>");
		notificadao.save(n);
		
		List<Post> prova = utentedao.getPostsOfMyGroups(u1);
		
		for (Post s : prova) {
			System.out.println(s.getContenuto());
		}
		
		System.out.println("\n");
		for(Canale c : canaledao.findAll()) {
			System.out.println("Canale: "+c.getNome());
			System.out.println("Descrizione: "+c.getDescrizione());
			System.out.println("Data Creazione: "+c.getData_creazione());
			Utente admin = utentedao.findByPrimaryKey(c.getAdmin().getEmail());
			System.out.println("Admin: "+admin.getCognome()+" "+admin.getNome());
			System.out.println("Utenti iscritti al canale: ");
			for(Utente u : c.getMembri()) {
				System.out.println(u.getEmail()+": "+u.getCognome()+" "+u.getNome());
			}
		}
		
	}
}
