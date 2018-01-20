package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Utente;

public class UtenteCredenziali extends Utente{
	private DataSource dataSource;

	public UtenteCredenziali(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public String getPassword(){						
		Connection connection = dataSource.getConnection();
		try {
			PreparedStatement statement= connection.prepareStatement("select password from utente where email = ?");
			statement.setString(1, getEmail());
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				return result.getString("password");
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
		return null;
	}

}
