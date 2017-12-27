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
					+ "drop table if exists gestione_gruppo;"
					+ "drop table if exists iscrizione;"
					+ "drop table if exists gruppo;"
					+ "drop table if exists canale;"
					+ "drop table if exists utente;"
					+ "drop table if exists post;"
					+ "drop table if exists commento;"
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
					+ "create table utente (id_utente bigint primary key, nome varchar(255), cognome varchar(255), "
						+ "username varchar(255), \"password\" varchar(255), data_nascita date, data_iscrizione date);"
					
					+ "create table canale (nome varchar(255) primary key, descrizione text, "
						+ "data_creazione date, id_admin bigint REFERENCES utente(id_utente));"
					
					+ "create table gruppo (nome varchar(255), data_creazione date, "
						+ "canale varchar(255) REFERENCES canale(nome), PRIMARY KEY (nome,canale));"
					
					+ "create table gestione_gruppo (id_utente bigint REFERENCES utente(id_utente)," 
						+ " gruppo varchar(255), canale varchar(255), "
						+ "FOREIGN KEY(gruppo,canale) REFERENCES gruppo(nome,canale), PRIMARY KEY(id_utente,gruppo,canale));"
											
					+ "create table iscrizione (id_utente bigint REFERENCES utente(id_utente),"
						+ " gruppo varchar(255), canale varchar(255), FOREIGN KEY(gruppo,canale) REFERENCES gruppo(nome,canale), "
						+ "PRIMARY KEY(id_utente,gruppo,canale));"
					
					+ "create table post (id_post bigint primary key, id_utente bigint REFERENCES utente(id_utente),"
						+"contenuto text, canale varchar(255), gruppo varchar(255), FOREIGN KEY(gruppo,canale) REFERENCES gruppo(nome,canale),"
						+" data_creazione date);"
					
					+"create table commento (id_commento bigint primary key, id_post bigint REFERENCES post(id_post),"
                    	+ "id_utente bigint REFERENCES utente(id_utente), contenuto text, data_creazione date)"
					
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
