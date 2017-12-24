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

		Canale c1 = new Canale("UNICAL","per studenti unical",Calendar.getInstance().getTime(),u1.getId_utente());
		//u1 non ha ancora ricevuto l'id :-/
		
		/*Gruppo gruppo1 = new Gruppo();
		//l'id del gruppo e' gestito tramite la classe IDBroker
		gruppo1.setNome("Colori");
		gruppo1.addStudente(studente1);
		gruppo1.addStudente(studente2);
		
		Indirizzo indirizzo1 = new Indirizzo();
		indirizzo1.setNome("Logica Computazionale");
		studente1.setIndirizzo(indirizzo1);
		studente2.setIndirizzo(indirizzo1);
		
		Indirizzo indirizzo2 = new Indirizzo();
		indirizzo2.setNome("Robotica");
		studente3.setIndirizzo(indirizzo2);
		
		Indirizzo indirizzo3 = new Indirizzo();
		indirizzo3.setNome("Archeologia");
		studente3.setIndirizzo(indirizzo3);
		
		Corso corso1 = new Corso();
		corso1.setNome("Web Computing");
		corso1.addStudente(studente1);
		corso1.addStudente(studente2);
		corso1.addStudente(studente3);
		
		Corso corso2 = new Corso();
		corso2.setNome("Ingegneria del Software");
		corso2.addStudente(studente1);		
		corso2.addStudente(studente3);

		//CREATE
		indirizzoDao.save(indirizzo1);
		indirizzoDao.save(indirizzo2);
		indirizzoDao.save(indirizzo3);
		
		studenteDao.save(studente1);
		studenteDao.save(studente2);
		studenteDao.save(studente3);
		
		corsoDao.save(corso1);
		corsoDao.save(corso2);
		
		gruppoDao.save(gruppo1);
		
		Dipartimento dipartimento1 = new Dipartimento("Matematica e Informatica");
		Dipartimento dipartimento2 = new Dipartimento("Biologia");
		
		dipartimentoDao.save(dipartimento1);
		dipartimentoDao.save(dipartimento2);
		
		CorsoDiLaurea corsoDiLaurea1 = new CorsoDiLaurea();
		corsoDiLaurea1.addCorso(corso1);
		corsoDiLaurea1.setDipartimento(dipartimento1);
		
		CorsoDiLaurea corsoDiLaurea2 = new CorsoDiLaurea();
		corsoDiLaurea2.addCorso(corso1);
		corsoDiLaurea2.addCorso(corso2);
		corsoDiLaurea2.setDipartimento(dipartimento2);
		
		corsoDiLaureaDao.save(corsoDiLaurea1);
		corsoDiLaureaDao.save(corsoDiLaurea2);
		
		//RETRIEVE
		System.out.println("Retrieve all gruppo");
		for(Gruppo g : gruppoDao.findAll()) {
			System.out.println(g.getNome());  // NB: non viene invocato il metodo getStudenti()
			System.out.println(".-.-.-.");
			g.addStudente(studente3);
			System.out.println(".-.-.-.");

		}

		System.out.println("Retrieve all gruppo: proxy all'opera");
		for(Gruppo g : gruppoDao.findAll()) {
			System.out.println(g);
		}

//		gruppo1.addStudente(studente3);
//		gruppoDao.update(gruppo1);
		
		System.out.println("Retrieve all gruppo: proxy all'opera");
		for(Gruppo g : gruppoDao.findAll()) {
			System.out.println(g);
		}
		
		System.out.println("Elenco studenti");
		for(Studente s : studenteDao.findAll()) {
			System.out.println(s);
		}	
		
		System.out.println("Elenco Corsi");
		for(Corso c : corsoDao.findAll()) {
			System.out.println(c);
		}	
		
		System.out.println("Elenco Corsi di Laurea");
		for(CorsoDiLaurea cl : corsoDiLaureaDao.findAll()) {
			System.out.println(cl);
		}	*/
	}
}
