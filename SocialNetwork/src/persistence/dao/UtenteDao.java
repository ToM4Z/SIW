package persistence.dao;

import java.util.List;

import model.Utente;
import persistence.UtenteCredenziali;

public interface UtenteDao {	
	public void save(Utente utente);
	public Utente findByPrimaryKey(String email);	// Retrieve
	public List<Utente> findAll();       
	public void update(Utente utente);			//Update
	public void delete(Utente utente);			//Delete	
	
	public void setPassword(Utente user, String password);
	public UtenteCredenziali findByPrimaryKeyCredential(String email);     // Retrieve
}
