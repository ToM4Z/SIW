package persistence;

import persistence.dao.CanaleDao;
import persistence.dao.CommentoDao;
import persistence.dao.GruppoDao;
import persistence.dao.PostDao;
import persistence.dao.UtenteDao;

public class PostgresDAOFactory extends DAOFactory {	
	private static DataSource dataSource;
	
	// --------------------------------------------
	static {
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			dataSource=new DataSource("jdbc:postgresql://localhost:5432/SocialNetwork","postgres","daniele");
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
	public UtilDao getUtilDAO() {
		return new UtilDao(dataSource);
	}

	@Override
	public PostDao getPostDAO() {
		
		return new PostDaoJDBC(dataSource);
	}

	@Override
	public CommentoDao getCommentoDAO() {
		
		return new CommentoDaoJDBC(dataSource);
	}

}
