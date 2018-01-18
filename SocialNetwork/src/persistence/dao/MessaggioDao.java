package persistence.dao;

import java.util.List;

import model.Messaggio;

public interface MessaggioDao {

	public void save(Messaggio messaggio);
	public Messaggio findByPrimaryKey(Long id);
	public List<Messaggio> findAll();
	public void update(Messaggio messaggio);
	public void delete(Messaggio messaggio);
	
	public List<Messaggio> getAfter(Messaggio m);
	public List<Messaggio> getOther50(Messaggio m);
}
