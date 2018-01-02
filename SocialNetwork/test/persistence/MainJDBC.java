package persistence;

import java.util.Calendar;

import model.Utente;
import model.Canale;
import model.Gruppo;
import persistence.dao.UtenteDao;
import persistence.dao.CanaleDao;
import persistence.dao.GruppoDao;

public class MainJDBC {
	
	public static void main(String args[]) {
		
		DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.POSTGRESQL);
		UtilDao util = factory.getUtilDAO();
		
		util.dropDatabase();
		
		util.createDatabase();
		
		UtenteDao utentedao = factory.getUtenteDAO();
		CanaleDao canaledao = factory.getCanaleDAO();
		GruppoDao gruppodao = factory.getGruppoDAO();

		Calendar cal = Calendar.getInstance();
		cal.set(1996, 9, 24);		
		Utente u1 = new Utente("thomas@thomas.com","Thomas","Voce","ToMaZ",cal.getTime(),Calendar.getInstance().getTime());
		
		Canale c1 = new Canale("UNICAL","per studenti unical",Calendar.getInstance().getTime(),u1);
		c1.addMembro(u1);
		
		Gruppo g1 = new Gruppo("home2",Calendar.getInstance().getTime(),c1);
		g1.addAdmin(u1);
		g1.addMembro(u1);
		
		utentedao.save(u1);
		utentedao.setPassword(u1, "ciccio");
		canaledao.save(c1);
		gruppodao.save(g1);
				
		u1 = new Utente("danielesalim@outlook.it","Daniele","Salimonti","ds.hitman",cal.getTime(),Calendar.getInstance().getTime());
		
		//c1 = new Canale("UNICAL2","2",Calendar.getInstance().getTime(),u1);
		c1.addMembro(u1);
		
		g1 = new Gruppo("home",Calendar.getInstance().getTime(),c1);
		g1.addAdmin(u1);
		g1.addMembro(u1);
		
		utentedao.save(u1);
		utentedao.setPassword(u1, "pasticcio");
		//canaledao.save(c1);
		gruppodao.save(g1);
		
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
