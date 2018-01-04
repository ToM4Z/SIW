package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import model.Canale;
import model.Gruppo;
import model.Utente;
import persistence.dao.CanaleDao;
import persistence.dao.GruppoDao;
import persistence.dao.UtenteDao;

public class CanaleDaoJDBC implements CanaleDao {

	DataSource dataSource;

	CanaleDaoJDBC(DataSource d) {
		dataSource = d;
	}

	@Override
	public void save(Canale canale) {
		Connection connection = dataSource.getConnection();
		try {
			//CONTROLLO SE ESISTE UN ALTRO CANALE CON LO STESSO NOME
			PreparedStatement statement = connection.prepareStatement("Select nome from canale where nome=?");
			statement.setString(1, canale.getNome());
			ResultSet result = statement.executeQuery();
			if(result.next())
				throw new PersistenceException("Canale "+canale.getNome()+" già esistente");
			
			//INSERISCO IL CANALE
			String insert = "insert into canale(nome, descrizione, data_creazione, email_admin) values (?,?,?,?)";
			statement = connection.prepareStatement(insert);
			statement.setString(1, canale.getNome());
			statement.setString(2, canale.getDescrizione());
			statement.setDate(3, new java.sql.Date(canale.getData_creazione().getTime()));
			statement.setString(4, canale.getAdmin().getEmail());
			statement.executeUpdate();

			// salviamo anche tutti gli utenti del canale ed i gruppi in CASCATA
			updateMembri(canale, connection);
			updateGruppi(canale, connection);
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException excep) {
					throw new PersistenceException(e.getMessage());
				}
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}

	private void updateMembri(Canale canale, Connection connection) throws SQLException {

		UtenteDao utenteDao = new UtenteDaoJDBC(dataSource);
		for (Utente utente : canale.getMembri()) {
			if (utenteDao.findByPrimaryKey(utente.getEmail()) == null) {
				utenteDao.save(utente);
			}

			String iscrittoCanale = "select * from iscrizione where email_utente = ? AND gruppo = ? AND canale = ?";
			PreparedStatement statement = connection.prepareStatement(iscrittoCanale);
			statement.setString(1, utente.getEmail());
			statement.setString(2, "home");
			statement.setString(3, canale.getNome());
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				String iscrivi = "insert into iscrizione (id_utente, gruppo, canale) values (?,?,?)";
				statement = connection.prepareStatement(iscrivi);
				statement.setString(1, utente.getEmail());
				statement.setString(2, "home");
				statement.setString(3, canale.getNome());
				statement.executeUpdate();
			}
		}
	}

	private void removeAllUtentiFromCanale(Canale canale, Connection connection) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("delete from iscrizione where gruppo=? and canale=?");
		statement.setString(1, "home");
		statement.setString(2, canale.getNome());
		statement.executeUpdate();
	}

	private void updateGruppi(Canale canale, Connection connection) throws SQLException {

		GruppoDao gruppoDao = new GruppoDaoJDBC(dataSource);
		for (Gruppo gruppo : canale.getGruppi()) {
			if (gruppoDao.findByPrimaryKey(gruppo.getNome(),canale.getNome()) == null) {
				gruppoDao.save(gruppo);
			}
			
			/*String gruppoCanale = "select nome from gruppo where nome=? AND canale=?";
			PreparedStatement statementGruppoCanale = connection.prepareStatement(gruppoCanale);
			statementGruppoCanale.setString(1, gruppo.getNome());
			statementGruppoCanale.setString(2, canale.getNome());
			ResultSet result = statementGruppoCanale.executeQuery();
			if (!result.next()) {
				String iscrivi = "insert into gruppo (nome_gruppo, nome_canale) values (?,?,?)";
				PreparedStatement statementIscrivi = connection.prepareStatement(iscrivi);
				Long id = IdBroker.getId(connection);
				statementIscrivi.setLong(1, id);
				statementIscrivi.setString(2, gruppo.getNome());
				statementIscrivi.setString(3, canale.getNome());
				statementIscrivi.executeUpdate();
			}*/
		}
	}

	private void deleteGruppi(Canale canale, Connection connection) throws SQLException {
		GruppoDao gruppoDao = new GruppoDaoJDBC(dataSource);
		for (Gruppo gruppo : canale.getGruppi()) {
			gruppoDao.delete(gruppo);
		}
	}

	// implementato con lazy load (proxy)
	@Override
	public Canale findByPrimaryKey(String nome) {
		Connection connection = this.dataSource.getConnection();
		Canale canale = null;
		try {
			PreparedStatement statement = connection.prepareStatement("select * from canale where nome = ?");
			statement.setString(1, nome);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				canale = new CanaleProxy(dataSource);
				canale.setNome(result.getString("nome"));
				canale.setDescrizione(result.getString("descrizione"));
				canale.setData_creazione(result.getDate("data_creazione"));
				canale.setAdmin(new UtenteDaoJDBC(dataSource).findByPrimaryKey(result.getString("email_admin")));
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
		return canale;
	}
	

	@Override
	public List<Canale> findAll() {

		Connection connection = this.dataSource.getConnection();
		List<Canale> canali = new LinkedList<>();
		try {
			PreparedStatement statement = connection.prepareStatement("select nome from canale");
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				canali.add(findByPrimaryKey(result.getString("nome")));
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
		return canali;
	}

	@Override
	public void update(Canale canale) {
		Connection connection = this.dataSource.getConnection();
		try {
			String update = "update canale SET descrizione = ? WHERE nome = ?";	//anche l'immagine
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, canale.getDescrizione());
			statement.setString(2, canale.getNome());

			statement.executeUpdate();
			this.updateMembri(canale, connection);
			this.updateGruppi(canale, connection);
			connection.commit();
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException excep) {
					throw new PersistenceException(e.getMessage());
				}
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}

	}

	@Override
	public void delete(Canale canale) {
		Connection connection = this.dataSource.getConnection();
		try {
			String delete = "delete FROM canale WHERE nome = ? ";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setString(1, canale.getNome());
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			
			deleteGruppi(canale, connection);
			removeAllUtentiFromCanale(canale, connection);
			statement.executeUpdate();
			
			connection.commit();
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
