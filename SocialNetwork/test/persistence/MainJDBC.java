package persistence;

import java.util.Calendar;
import java.util.Date;

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
		Utente u1 = new Utente("Thomas","Voce","ToMaZ",cal.getTime(),Calendar.getInstance().getTime());
		utentedao.save(u1);
		
		Canale c1 = new Canale("UNICAL","per studenti unical",Calendar.getInstance().getTime(),u1.getId_utente());
		canaledao.save(c1);
		
		Gruppo g1 = new Gruppo("home",Calendar.getInstance().getTime(),c1.getNome());
		gruppodao.save(g1);
		
		for(Canale c : canaledao.findAll()) {
			System.out.println("Canale: "+c.getNome());
			System.out.println("Descrizione: "+c.getDescrizione());
			System.out.println("Data Creazione: "+c.getData_creazione());
			Utente admin = utentedao.findByPrimaryKey(c.getAdmin());
			System.out.println("Admin: "+admin.getCognome()+" "+admin.getNome());
			System.out.println("Utenti iscritti al canale: ");
			for(Utente u : c.getMembri()) {
				System.out.println(u.getCognome()+" "+u.getNome());
			}
		}
	}
}
