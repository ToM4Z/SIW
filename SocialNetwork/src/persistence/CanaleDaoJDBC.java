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
			String insert = "insert into canale(nome, descrizione, data_creazione, email_admin, image) values (?,?,?,?,?)";
			statement = connection.prepareStatement(insert);
			statement.setString(1, canale.getNome());
			statement.setString(2, canale.getDescrizione());
			statement.setDate(3, new java.sql.Date(canale.getData_creazione().getTime()));
			statement.setString(4, canale.getAdmin().getEmail());
			statement.setString(5, canale.getImage());
			statement.executeUpdate();
			
			updateGruppi(canale, connection);
			// salviamo anche tutti gli utenti del canale ed i gruppi in CASCATA
			updateMembri(canale, connection);
			
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

			String iscrittoCanale = "select * from iscrizione where email_utente = ? and gruppo = ? and canale = ?";
			PreparedStatement statement = connection.prepareStatement(iscrittoCanale);
			statement.setString(1, utente.getEmail());
			statement.setString(2, "home");
			statement.setString(3, canale.getNome());
			ResultSet result = statement.executeQuery();
			if (!result.next()) {
				String iscrivi = "insert into iscrizione (email_utente, gruppo, canale) values (?,?,?)";
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
				canale.setImage("image");
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
			String update = "update canale SET descrizione = ?"+/* and image = ?*/ " WHERE nome = ?";	//anche l'immagine
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, canale.getDescrizione());
			//statement.setString(2, canale.getImage());
			statement.setString(2, canale.getNome());

			statement.executeUpdate();
			this.updateMembri(canale, connection);
			this.updateGruppi(canale, connection);
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
	public void delete(Canale canale) {
		Connection connection = this.dataSource.getConnection();
		try {
			deleteGruppi(canale, connection);
			removeAllUtentiFromCanale(canale, connection);
			
			String delete = "delete FROM canale WHERE nome = ? ";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setString(1, canale.getNome());
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			
			
			
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
	
	@Override
	public void addUserToBlackList(Canale canale, Utente utente) {
		Connection connection = dataSource.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("Select utente from blacklist where canale=? and utente=?");
			statement.setString(1, canale.getNome());
			statement.setString(2, utente.getEmail());
			ResultSet result = statement.executeQuery();
			if(result.next())
				throw new PersistenceException("L'Utente "+utente.getEmail()+" è già nella blacklist del canale "+canale.getNome());
			
			String insert = "insert into blacklist(canale, utente) values (?,?)";
			statement = connection.prepareStatement(insert);
			statement.setString(1, canale.getNome());
			statement.setString(2, utente.getEmail());
			statement.executeUpdate();

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
	
	@Override
	public void removeUserFromBlackList(Canale canale, Utente utente) {
		Connection connection = dataSource.getConnection();
		try {
			String delete = "delete FROM blacklist WHERE canale = ? and nome = ?";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setString(1, canale.getNome());
			statement.setString(2, utente.getEmail());

			statement.executeUpdate();
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
	
	@Override
	public void addUserToChannel(Canale canale, Utente utente) {
		Connection connection = dataSource.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("Select email_utente from iscrizione where canale=? and gruppo='home' and email_utente=?");
			statement.setString(1, canale.getNome());
			statement.setString(2, utente.getEmail());
			ResultSet result = statement.executeQuery();
			if(result.next())
				throw new PersistenceException("L'Utente "+utente.getEmail()+" è già iscritto al canale "+canale.getNome());
			
			statement = connection.prepareStatement("Select utente from blacklist where canale=? and utente=?");
			statement.setString(1, canale.getNome());
			statement.setString(2, utente.getEmail());
			result = statement.executeQuery();
			if(result.next())
				throw new PersistenceException("L'Utente "+utente.getEmail()+" non può iscriversi al canale "+canale.getNome());
			
			String insert = "insert into iscrizione(canale, gruppo, email_utente) values (?,?,?)";
			statement = connection.prepareStatement(insert);
			statement.setString(1, canale.getNome());
			statement.setString(2, "home");
			statement.setString(3, utente.getEmail());
			statement.executeUpdate();

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
	
	@Override
	public void removeUserFromChannel(Canale canale, Utente utente) {
		Connection connection = dataSource.getConnection();
		try {
			String delete = "delete FROM iscrizione WHERE canale = ? and gruppo = 'home' and email_utente = ?";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setString(1, canale.getNome());
			statement.setString(2, utente.getEmail());

			statement.executeUpdate();
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
}
