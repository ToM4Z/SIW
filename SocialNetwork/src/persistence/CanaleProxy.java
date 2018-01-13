package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import model.Canale;
import model.Gruppo;
import model.Utente;

public class CanaleProxy extends Canale {
	
	DataSource dataSource;
	
	CanaleProxy(DataSource ds){
		dataSource = ds;
	}
	
	public Set<Utente> getMembri() { 
		Set<Utente> utenti = new HashSet<>();
		Connection connection = dataSource.getConnection();
		try {
			String query = "select * from utente where email IN "
							+ "(select email_utente from iscrizione where canale = ? and gruppo = 'home')";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, getNome());
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Utente utente = new Utente();
				utente.setEmail(result.getString("email"));
				utente.setNome(result.getString("nome"));
				utente.setCognome(result.getString("cognome"));
				utente.setUsername(result.getString("username"));
				utente.setDataDiNascita(new java.util.Date(result.getDate("data_nascita").getTime()));
				utente.setDataIscrizione(new java.util.Date(result.getDate("data_iscrizione").getTime()));
			
				utenti.add(utente);
			}
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}	
		setMembri(utenti);
		return super.getMembri(); 
	}
	
	
	public Set<Gruppo> getGruppi() { 
		Set<Gruppo> gruppi = new HashSet<>();
		Connection connection = dataSource.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("select nome, data_creazione from gruppo where canale = ?");
			statement.setString(1, getNome());
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Gruppo gruppo = new GruppoProxy(dataSource);			
				gruppo.setNome(result.getString("nome"));
				gruppo.setData_creazione(result.getDate("data_creazione"));
				gruppo.setCanale(this);
				gruppi.add(gruppo);
			}
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}	
		setGruppi(gruppi);
		return super.getGruppi(); 
	}
	
	@Override
	public Set<Utente> getBlacklist() {
		Set<Utente> utenti = new HashSet<>();
		Connection connection = dataSource.getConnection();
		try {
			String query = "select * from utente where email IN "
							+ "(select utente from blacklist where canale = ?)";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, getNome());
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Utente utente = new Utente();
				utente.setEmail(result.getString("email"));
				utente.setNome(result.getString("nome"));
				utente.setCognome(result.getString("cognome"));
				utente.setUsername(result.getString("username"));
				utente.setDataDiNascita(new java.util.Date(result.getDate("data_nascita").getTime()));
				utente.setDataIscrizione(new java.util.Date(result.getDate("data_iscrizione").getTime()));
			
				utenti.add(utente);
			}
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}	
		setBlacklist(utenti);
		return super.getBlacklist(); 
	}

}
