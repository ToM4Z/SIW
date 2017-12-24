package persistence;

import persistence.dao.*;

public abstract class DAOFactory {

	public static final int HSQLDB = 1;
	public static final int POSTGRESQL = 2;

	public static DAOFactory getDAOFactory(int whichFactory) {
		switch (whichFactory) {
		case HSQLDB:
			return null;
		case POSTGRESQL:
			return new PostgresDAOFactory();
		default:
			return null;
		}
	}

	public abstract CanaleDao getCanaleDAO();

	public abstract GruppoDao getGruppoDAO();

	public abstract UtenteDao getUtenteDAO();

	public abstract persistence.UtilDao getUtilDAO();
	
	// public abstract PostDao getPostDAO();

	// public abstract CommentoDao getCommentoDAO();

}
