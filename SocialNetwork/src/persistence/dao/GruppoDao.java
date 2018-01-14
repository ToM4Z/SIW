package persistence.dao;

import java.util.List;
import model.Gruppo;
import model.Utente;

public interface GruppoDao {	
	public void save(Gruppo gruppo);
	public Gruppo findByPrimaryKey(String nome,String canale);
	public List<Gruppo> findAll();       
	public void update(Gruppo gruppo); 
	public void delete(Gruppo gruppo);
	public void addUserToGroup(Gruppo gruppo, Utente utente);
	public void removeUserFromGroup(Gruppo gruppo, Utente utente);
	public void addUserToAdmin(Gruppo gruppo, Utente utente);
	public void removeUserFromAdmin(Gruppo gruppo, Utente utente);
	public void addUserToAttesa(Gruppo gruppo, Utente utente);
	public void removeUserFromAttesa(Gruppo gruppo, Utente utente);
}
