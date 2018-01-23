package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UtilDao {

	private DataSource dataSource;

	public UtilDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void dropDatabase() {
		Connection connection = dataSource.getConnection();
		try {
			String drop = "drop SEQUENCE if EXISTS sequenza_id;" 
					+ "drop table if exists commento;"
					+ "drop table if exists post;"
					+ "drop table if exists messaggio;"
					+ "drop table if exists gestione_gruppo;"
					+ "drop table if exists utenti_attesa;"
					+ "drop table if exists blacklist;"		
					+ "drop table if exists iscrizione;"
					+ "drop table if exists gruppo;"
					+ "drop table if exists canale;"
					+ "drop table if exists notifica;"
					+ "drop table if exists utente;"
										
					;
			PreparedStatement statement = connection.prepareStatement(drop);
			statement.executeUpdate();
			
			System.out.println("Drop database: Success");
			
		} catch (SQLException e) {
			System.out.println("Drop database: Failed");
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}

	public void createDatabase() {
		Connection connection = dataSource.getConnection();
		try {
			String create = "create SEQUENCE sequenza_id;"
					+ "create table utente (email varchar(255) primary key, nome varchar(255), cognome varchar(255), "
						+ "username varchar(255), \"password\" varchar(255), data_nascita date, data_iscrizione date);"
					
					+ "create table canale (nome varchar(255) primary key, descrizione text, "
						+ "data_creazione date, email_admin varchar(255) REFERENCES utente(email));"
					
					+ "create table gruppo (nome varchar(255), data_creazione date, "
						+ "canale varchar(255) REFERENCES canale(nome), PRIMARY KEY (nome,canale));"
					
					+ "create table gestione_gruppo (email_utente varchar(255) REFERENCES utente(email)," 
						+ " gruppo varchar(255), canale varchar(255), "
						+ "FOREIGN KEY(gruppo,canale) REFERENCES gruppo(nome,canale), PRIMARY KEY(email_utente,gruppo,canale));"
											
					+ "create table iscrizione (email_utente varchar(255) REFERENCES utente(email),"
						+ " gruppo varchar(255), canale varchar(255), FOREIGN KEY(gruppo,canale) REFERENCES gruppo(nome,canale), "
						+ "PRIMARY KEY(email_utente,gruppo,canale));"
						
					+ "create table utenti_attesa (email_utente varchar(255) REFERENCES utente(email),"
						+ " gruppo varchar(255), canale varchar(255), FOREIGN KEY(gruppo,canale) REFERENCES gruppo(nome,canale), "
						+ "PRIMARY KEY(email_utente,gruppo,canale));"
					
					+ "create table post (id_post bigint primary key, email_utente varchar(255) REFERENCES utente(email),"
						+"contenuto text, canale varchar(255), gruppo varchar(255), FOREIGN KEY(gruppo,canale) REFERENCES gruppo(nome,canale),"
						+" data_creazione timestamp);"
					
					+"create table commento (id_commento bigint primary key, id_post bigint REFERENCES post(id_post),"
                    	+ "email_utente varchar(255) REFERENCES utente(email), contenuto text, data_creazione timestamp);"
					
                    +"create table messaggio ( id_messaggio bigint primary key, email_mittente varchar(255) REFERENCES utente(email),"
                    	+"contenuto text, canale varchar(255), gruppo varchar(255), FOREIGN KEY(gruppo, canale) REFERENCES gruppo(nome, canale),"
                    	+"data_creazione timestamp);"
                	
                	+ "create table blacklist (canale varchar(255) REFERENCES canale(nome), utente varchar(255) REFERENCES utente(email),"
                		+ "PRIMARY KEY (canale,utente));"
                	
                	+ "create table notifica ( id_notifica bigint primary key, email_utente varchar(255) REFERENCES utente(email), "
                		+"contenuto text); "
					;
			PreparedStatement statement = connection.prepareStatement(create);
			statement.executeUpdate();
			
			System.out.println("Create database: Success");

		} catch (SQLException e) {
			System.out.println("Create database: Failed");
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}

	public void resetDatabase() {
		Connection connection = dataSource.getConnection();
		try {
			PreparedStatement statement;
			
			statement = connection.prepareStatement("delete FROM gestione_gruppo");
			statement.executeUpdate();
						
			statement = connection.prepareStatement("delete FROM iscrizione");
			statement.executeUpdate();
			
			statement = connection.prepareStatement("delete FROM utenti_attesa");
			statement.executeUpdate();
			
			statement = connection.prepareStatement("delete FROM gruppo");
			statement.executeUpdate();
			
			statement = connection.prepareStatement("delete FROM canale");
			statement.executeUpdate();
			
			statement = connection.prepareStatement("delete FROM utente");
			statement.executeUpdate();
			
			statement = connection.prepareStatement("delete FROM post");
			statement.executeUpdate();
			
			statement = connection.prepareStatement("delete FROM commento");
			statement.executeUpdate();	
			
			statement = connection.prepareStatement("delete FROM blacklist");
			statement.executeUpdate();	
			
			statement = connection.prepareStatement("delete FROM notifica");
			statement.executeUpdate();	
			
			System.out.println("Reset database: Success");
			
		} catch (SQLException e) {
			System.out.println("Reset database: Failed");
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}
}
