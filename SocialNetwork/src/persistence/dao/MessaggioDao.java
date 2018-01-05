package persistence.dao;

import java.util.List;

import model.Messaggio;

public interface MessaggioDao {

	public void save(Messaggio messaggio);
	public Messaggio findByPrimaryKey(Long id);
	public List<Messaggio> findAll();
	public void update(Messaggio messaggio);
	public void delete(Messaggio messaggio);
}
