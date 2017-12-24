package persistence.dao;

import java.util.List;
import model.Gruppo;

public interface GruppoDao {	
	public void save(Gruppo gruppo);
	public Gruppo findByPrimaryKey(String nome,String canale);
	public List<Gruppo> findAll();       
	public void update(Gruppo gruppo); 
	public void delete(Gruppo gruppo);
}
