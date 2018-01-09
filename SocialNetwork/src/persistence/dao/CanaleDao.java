package persistence.dao;

import java.util.List;

import model.Canale;
import model.Utente;

public interface CanaleDao {	
	public void save(Canale canale);
	public Canale findByPrimaryKey(String nome);
	public List<Canale> findAll();
	public void update(Canale canale);
	public void delete(Canale canale);
	
	public void addUserToBlackList(Canale canale,Utente utente);
	public void removeUserFromBlackList(Canale canale,Utente utente);
	
	public void addUserToChannel(Canale canale,Utente utente);
	public void removeUserFromChannel(Canale canale,Utente utente);
}
