package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import model.Utente;
import persistence.dao.UtenteDao;

public class UtenteDaoJDBC implements UtenteDao {
	private DataSource dataSource;

	UtenteDaoJDBC(DataSource ds) {
		dataSource = ds;
	}

	@Override
	public void save(Utente utente) {
		Connection connection = dataSource.getConnection();
		try {
			utente.setDataIscrizione(Calendar.getInstance().getTime());

			String insert = "insert into utente(email, nome, cognome, username, "
					+ "\"password\", data_nascita, data_iscrizione) values (?,?,?,?,?,?,?)";

			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, utente.getEmail());
			statement.setString(2, utente.getNome());
			statement.setString(3, utente.getCognome());
			statement.setString(4, utente.getUsername());
			statement.setString(5, "ciao"); // password ??
			statement.setDate(6, new java.sql.Date(utente.getDataDiNascita().getTime()));
			statement.setDate(7, new java.sql.Date(utente.getDataIscrizione().getTime()));
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}

	}

	@Override
	public Utente findByPrimaryKey(String email) {
		Connection connection = this.dataSource.getConnection();
		Utente utente = null;
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement("select * from utente where email = ?");
			statement.setString(1, email);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				utente = new Utente();
				utente.setEmail(result.getString("email"));
				utente.setNome(result.getString("nome"));
				utente.setCognome(result.getString("cognome"));
				utente.setUsername(result.getString("username"));
				utente.setDataDiNascita(new java.util.Date(result.getDate("data_nascita").getTime()));
				utente.setDataIscrizione(new java.util.Date(result.getDate("data_iscrizione").getTime()));
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
		return utente;
	}

	@Override
	public List<Utente> findAll() {
		Connection connection = this.dataSource.getConnection();
		List<Utente> utenti = new LinkedList<>();
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement("select * from utente");
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
		return utenti;
	}

	@Override
	public void update(Utente utente) {
		Connection connection = this.dataSource.getConnection();
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement("select * from utente where email = ?");
			statement.setString(1, utente.getEmail());
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				utente = new Utente();
				utente.setEmail(result.getString("email"));
				utente.setNome(result.getString("nome"));
				utente.setCognome(result.getString("cognome"));
				utente.setUsername(result.getString("username"));
				utente.setDataDiNascita(new java.util.Date(result.getDate("data_nascita").getTime()));
				utente.setDataIscrizione(new java.util.Date(result.getDate("data_iscrizione").getTime()));
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
	}

	@Override
	public void delete(Utente utente) {
		Connection connection = this.dataSource.getConnection();
		try {
			PreparedStatement statement;
			
			statement = connection.prepareStatement("delete from utente where email = ?");
			statement.setString(1, utente.getEmail());
			statement.executeQuery();
			
		} catch (SQLException e) {
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
