package persistence;

import persistence.dao.CanaleDao;
import persistence.dao.CommentoDao;
import persistence.dao.GruppoDao;
import persistence.dao.PostDao;
import persistence.dao.UtenteDao;

public class PostgresDAOFactory extends DAOFactory {
	
	private static  DataSource dataSource;
	// --------------------------------------------
	static {
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			//questi vanno messi in file di configurazione!!!	
//			dataSource=new DataSource("jdbc:postgresql://52.39.164.176:5432/xx","xx","p@xx");
			dataSource=new DataSource("jdbc:postgresql://localhost:5432/test","postgres","postgres"); //utente e password del db!!!
		} 
		catch (Exception e) {
			System.err.println("PostgresDAOFactory.class: failed to load MySQL JDBC driver\n"+e);
			e.printStackTrace();
		}
	}
	// --------------------------------------------

	@Override
	public CanaleDao getCanaleDAO() {
		return new CanaleDaoJDBC(dataSource);
	}

	@Override
	public GruppoDao getGruppoDAO() {
		return new GruppoDaoJDBC(dataSource);
	}

	@Override
	public UtenteDao getUtenteDAO() {
		return new UtenteDaoJDBC(dataSource);
	}

	@Override
	public PostDao getPostDAO() {
		return null;
	}

	@Override
	public CommentoDao getCommentoDAO() {
		return null;
	}

}