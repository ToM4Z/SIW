package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.Gruppo;
import model.Utente;
import persistence.dao.GruppoDao;
import persistence.dao.UtenteDao;

public class GruppoDaoJDBC implements GruppoDao {

	private DataSource dataSource;

	public GruppoDaoJDBC(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void save(Gruppo gruppo) {
		Connection connection = dataSource.getConnection();

		try {
			//CONTROLLO SE ESISTE UN ALTRO GRUPPO IN QUESTO CANALE CON LO STESSO NOME
			PreparedStatement statement = connection.prepareStatement("Select nome from gruppo where nome=? and canale=?");
			statement.setString(1, gruppo.getNome());
			statement.setString(2, gruppo.getCanale().getNome());
			ResultSet result = statement.executeQuery();
			if(result.next())
				throw new PersistenceException("Gruppo "+gruppo.getNome()+" già presente nel canale "+gruppo.getCanale().getNome());
			
			//INSERISCO IL GRUPPO
			String insert = "insert into gruppo(nome,data_creazione,canale) values (?,?,?)";
			statement = connection.prepareStatement(insert);
			statement.setString(1, gruppo.getNome());
			statement.setDate(2, new java.sql.Date(gruppo.getData_creazione().getTime()));
			statement.setString(3, gruppo.getCanale().getNome());
			statement.executeUpdate();

			// salviamo anche tutti gli utenti del gruppo in CASCATA
			this.updateMembri(gruppo, connection);
		} catch (SQLException e) {
			if (connection != null)
				try {
					connection.rollback();
				} catch (SQLException excep) {
					throw new PersistenceException(e.getMessage());
				}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}

	}

	private void updateMembri(Gruppo gruppo, Connection connection) throws SQLException {

		UtenteDao utenteDao = new UtenteDaoJDBC(dataSource);
		for (Utente utente : gruppo.getMembri()) {
			if (utenteDao.findByPrimaryKey(utente.getId_utente()) == null) {
				utenteDao.save(utente);
			}

			String iscritto = "select * from iscrizione where id_utente = ? AND gruppo = ? and canale = ?";
			PreparedStatement statement = connection.prepareStatement(iscritto);
			statement.setLong(1, utente.getId_utente());
			statement.setString(2, gruppo.getNome());
			statement.setString(3, gruppo.getCanale().getNome());
			ResultSet result = statement.executeQuery();
			
			if (!result.next()) {
				String iscrivi = "insert into iscrizione (id_utente, gruppo, canale) values (?,?,?)";
				statement = connection.prepareStatement(iscrivi);
				statement.setLong(1, utente.getId_utente());
				statement.setString(2, gruppo.getNome());
				statement.setString(3, gruppo.getCanale().getNome());
				statement.executeUpdate();
			}
		}
	}

	private void removeAllUsersFromGroup(Gruppo gruppo, Connection connection) throws SQLException {
		String delete = "delete from iscrizione WHERE gruppo = ?, canale = ?";
		PreparedStatement statement = connection.prepareStatement(delete);
		statement.setString(1, gruppo.getNome());
		statement.setString(2, gruppo.getCanale().getNome());
		statement.executeUpdate();
	}

	// implementato con lazy load
	@Override
	public Gruppo findByPrimaryKey(String nome,String canale) {
		Connection connection = this.dataSource.getConnection();
		Gruppo gruppo = null;
		try {
			PreparedStatement statement;
			String query = "select * from gruppo where nome = ? and canale = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, nome);
			statement.setString(2, canale);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				gruppo = new GruppoProxy(dataSource);
				gruppo.setNome(nome);
				gruppo.setCanale(new CanaleDaoJDBC(dataSource).findByPrimaryKey(canale));
				gruppo.setData_creazione(result.getDate("data_creazione"));
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
		return gruppo;
	}

	@Override
	public List<Gruppo> findAll() {

		Connection connection = this.dataSource.getConnection();
		List<Gruppo> gruppi = new LinkedList<>();
		try {
			PreparedStatement statement = connection.prepareStatement("select nome,canale from gruppo");
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Gruppo gruppo = findByPrimaryKey(result.getString("nome"),result.getString("canale"));
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
		return gruppi;
	}

	@Override
	public void update(Gruppo gruppo) {
		Connection connection = this.dataSource.getConnection();
		try {
			this.updateMembri(gruppo, connection);
			//connection.commit();
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
	public void delete(Gruppo gruppo) {
		Connection connection = this.dataSource.getConnection();
		try {
			String delete = "delete FROM gruppo WHERE nome = ? and canale = ?";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setString(1, gruppo.getNome());
			statement.setString(2, gruppo.getCanale().getNome());

			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			this.removeAllUsersFromGroup(gruppo, connection);

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
