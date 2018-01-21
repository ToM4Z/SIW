package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import model.Utente;

public class UtenteProxy extends Utente{
	
	private DataSource dataSource;
	
	UtenteProxy(DataSource ds){		
		dataSource = ds;
	}
	
	@Override
	public String getNome() { 
		if(super.getNome()!=null)
			return super.getNome();
		String nome="";
		Connection connection = dataSource.getConnection();
		try {
			String query = "select nome from utente where email = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, getEmail());
			ResultSet result = statement.executeQuery();
			result.next();
			nome=result.getString(1);
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}	
		setNome(nome);
		return nome; 
	}
	
	@Override
	public String getCognome() { 
		if(super.getCognome()!=null)
			return super.getCognome();
		String cognome="";
		Connection connection = dataSource.getConnection();
		try {
			String query = "select cognome from utente where email = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, getEmail());
			ResultSet result = statement.executeQuery();
			result.next();
			cognome=result.getString(1);
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}	
		setCognome(cognome);
		return cognome; 
	}
	
	@Override
	public Date getDataDiNascita() { 
		if(super.getDataDiNascita()!=null)
			return super.getDataDiNascita();
		Date data = null;
		Connection connection = dataSource.getConnection();
		try {
			String query = "select data_nascita from utente where email = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, getEmail());
			ResultSet result = statement.executeQuery();
			result.next();
			data = new java.util.Date(result.getDate("data_nascita").getTime());
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}	
		setDataDiNascita(data);
		return data; 
	}

	@Override
	public Date getDataIscrizione() { 
		if(super.getDataIscrizione()!=null)
			return super.getDataIscrizione();
		Date data = null;
		Connection connection = dataSource.getConnection();
		try {
			String query = "select data_iscrizione from utente where email = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, getEmail());
			ResultSet result = statement.executeQuery();
			result.next();
			data = new java.util.Date(result.getDate("data_iscrizione").getTime());
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}	
		setDataIscrizione(data);
		return data; 
	}
}
