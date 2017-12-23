package persistence;

import persistence.dao.*;


public abstract class DAOFactory {
	
		public static final int HSQLDB = 1;
		public static final int POSTGRESQL = 2;
		
		
		//ciao thomas
		/**
		 * Depending on the input parameter
		 * this method returns one out of several possible 
		 * implementations of this factory spec 
		 */
		public static DAOFactory getDAOFactory(int whichFactory) {
			switch ( whichFactory ) {
			
			case HSQLDB:
				return null;//new HsqldbDAOFactory();
			case POSTGRESQL:
				return new PostgresDAOFactory();
			default:
				return null;
			}
		}
		// --- Factory specification: concrete factories implementing this spec must provide this methods! ---
		
		/**
		 * Method to obtain a DATA ACCESS OBJECT
		 * for the datatype 'Student'
		 */
		public abstract CanaleDao getCanaleDAO();
		
		public abstract GruppoDao getGruppoDAO();
		
		public abstract UtenteDao getUtenteDAO();
		
		public abstract PostDao getPostDAO();
		
		public abstract CommentoDao getCommentoDAO();

}
