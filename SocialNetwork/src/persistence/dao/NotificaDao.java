package persistence.dao;

import java.util.List;

import model.Notifica;
import model.Utente;

public interface NotificaDao {
	
	public void save(Notifica notifica);  // Create
	public Notifica findByPrimaryKey(Long id);     // Retrieve
	public List<Notifica> getNotificheUtente(Utente u);       
	public void update(Notifica notifica); //Update
	public void delete(Notifica notifica);

}
