package persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.Gruppo;
import model.Messaggio;
import model.Post;
import model.Utente;
import persistence.dao.GruppoDao;
import persistence.dao.MessaggioDao;
import persistence.dao.PostDao;
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
			if(!result.next()){				
				//INSERISCO IL GRUPPO
				String insert = "insert into gruppo(nome, data_creazione, canale) values (?,?,?)";
				statement = connection.prepareStatement(insert);
				statement.setString(1, gruppo.getNome());
				statement.setDate(2, new Date(gruppo.getData_creazione().getTime()));
				statement.setString(3, gruppo.getCanale().getNome());
				statement.executeUpdate();
			}
			// salviamo anche tutti gli utenti del gruppo in CASCATA
			this.updateMembri(gruppo, connection);
			this.updateAdmins(gruppo, connection);
			this.updateUtentiInAttesa(gruppo, connection);
			this.updateChat(gruppo, connection);
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
			if (utenteDao.findByPrimaryKey(utente.getEmail()) == null) {
				utenteDao.save(utente);
			}

			String iscritto = "select * from iscrizione where email_utente = ? AND gruppo = ? and canale = ?";
			PreparedStatement statement = connection.prepareStatement(iscritto);
			statement.setString(1, utente.getEmail());
			statement.setString(2, gruppo.getNome());
			statement.setString(3, gruppo.getCanale().getNome());
			ResultSet result = statement.executeQuery();
			
			if (!result.next()) {
				String iscrivi = "insert into iscrizione (email_utente, gruppo, canale) values (?,?,?)";
				statement = connection.prepareStatement(iscrivi);
				statement.setString(1, utente.getEmail());
				statement.setString(2, gruppo.getNome());
				statement.setString(3, gruppo.getCanale().getNome());
				statement.executeUpdate();
			}
		}
	}
	
	private void updateUtentiInAttesa(Gruppo gruppo, Connection connection) throws SQLException{
		
		UtenteDao utenteDao = new UtenteDaoJDBC(dataSource);
		for (Utente utente : gruppo.getUtentiInAttesa()) {
			if (utenteDao.findByPrimaryKey(utente.getEmail()) == null) {
				utenteDao.save(utente);
			}

			String iscritto = "select * from utenti_attesa where email_utente = ? AND gruppo = ? and canale = ?";
			PreparedStatement statement = connection.prepareStatement(iscritto);
			statement.setString(1, utente.getEmail());
			statement.setString(2, gruppo.getNome());
			statement.setString(3, gruppo.getCanale().getNome());
			ResultSet result = statement.executeQuery();
			
			if (!result.next()) {
				String iscrivi = "insert into utenti_attesa (email_utente, gruppo, canale) values (?,?,?)";
				statement = connection.prepareStatement(iscrivi);
				statement.setString(1, utente.getEmail());
				statement.setString(2, gruppo.getNome());
				statement.setString(3, gruppo.getCanale().getNome());
				statement.executeUpdate();
			}
		}
	}
	
	private void updateAdmins(Gruppo gruppo, Connection connection) throws SQLException {

		UtenteDao utenteDao = new UtenteDaoJDBC(dataSource);
		for (Utente utente : gruppo.getAdmins()) {
			if (utenteDao.findByPrimaryKey(utente.getEmail()) == null) {
				utenteDao.save(utente);
			}

			String admin = "select * from gestione_gruppo where email_utente = ? AND gruppo = ? and canale = ?";
			PreparedStatement statement = connection.prepareStatement(admin);
			statement.setString(1, utente.getEmail());
			statement.setString(2, gruppo.getNome());
			statement.setString(3, gruppo.getCanale().getNome());
			ResultSet result = statement.executeQuery();
			
			if (!result.next()) {
				String setAdmin = "insert into gestione_gruppo (email_utente, gruppo, canale) values (?,?,?)";
				statement = connection.prepareStatement(setAdmin);
				statement.setString(1, utente.getEmail());
				statement.setString(2, gruppo.getNome());
				statement.setString(3, gruppo.getCanale().getNome());
				statement.executeUpdate();
			}
		}
	}
	
	
	
	private void updateChat(Gruppo gruppo, Connection connection) throws SQLException {

		MessaggioDao messaggioDao = new MessaggioDaoJDBC(dataSource);
		for (Messaggio messaggio : gruppo.getChat()) {
			if (messaggioDao.findByPrimaryKey(messaggio.getId()) == null) {
				messaggioDao.save(messaggio);
			}

			String mex = "select * from messaggio where id_messaggio = ? AND gruppo = ? and canale = ?";
			PreparedStatement statement = connection.prepareStatement(mex);
			statement.setLong(1, messaggio.getId());
			statement.setString(2, gruppo.getNome());
			statement.setString(3, gruppo.getCanale().getNome());
			ResultSet result = statement.executeQuery();
			
			if (!result.next()) {
				String setMex = "insert into messaggio (email_mittente, gruppo, canale) values (?,?,?)";
				statement = connection.prepareStatement(setMex);
				statement.setLong(1, messaggio.getId());
				statement.setString(2, gruppo.getNome());
				statement.setString(3, gruppo.getCanale().getNome());
				statement.executeUpdate();
			}
		}
	}
	
	

	private void removeAllUsersFromGroup(Gruppo gruppo, Connection connection) throws SQLException {
		String delete = "delete from iscrizione WHERE gruppo = ? and canale = ?";
		PreparedStatement statement = connection.prepareStatement(delete);
		statement.setString(1, gruppo.getNome());
		statement.setString(2, gruppo.getCanale().getNome());
		statement.executeUpdate();
	}
	
	private void removeAdmins(Gruppo gruppo, Connection connection) throws SQLException {
		
		for (Utente u : gruppo.getAdmins()) {
			String delete = "delete from gestione_gruppo WHERE gruppo = ? and canale = ? and email_utente = ?";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setString(1, gruppo.getNome());
			statement.setString(2, gruppo.getCanale().getNome());
			statement.setString(3, u.getEmail());
			statement.executeUpdate();
		}
	}
	
	private void removeUtentiInAttesa(Gruppo gruppo, Connection connection) throws SQLException {
		
		for (Utente u : gruppo.getUtentiInAttesa()) {
			String delete = "delete from utenti_attesa WHERE gruppo = ? and canale = ? and email_utente = ?";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setString(1, gruppo.getNome());
			statement.setString(2, gruppo.getCanale().getNome());
			statement.setString(3, u.getEmail());
			statement.executeUpdate();
		}
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
				gruppo.setCanale(DatabaseManager.getInstance().getDaoFactory().getCanaleDAO().findByPrimaryKey(canale));
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
			this.updateAdmins(gruppo, connection);
			this.updateUtentiInAttesa(gruppo, connection);
			this.updateChat(gruppo, connection);
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
			
			this.deleteAllPosts(gruppo, connection);
			this.deleteChat(gruppo, connection);
			this.removeAllUsersFromGroup(gruppo, connection);
			this.removeAdmins(gruppo, connection);
			this.removeUtentiInAttesa(gruppo, connection);
			
			String delete = "delete FROM gruppo WHERE nome = ? and canale = ?";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setString(1, gruppo.getNome());
			statement.setString(2, gruppo.getCanale().getNome());

			connection.setAutoCommit(false);
			
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			
			
			//System.out.println(gruppo.getNome());
			//System.out.println(gruppo.getCanale().getNome());

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
	
	private void deleteAllPosts(Gruppo gruppo, Connection connection) throws SQLException{
		
		PostDao postDao = new PostDaoJDBC(dataSource);
		for (Post post : gruppo.getPost()) {
			postDao.delete(post);
		}
	}
	
	private void deleteChat(Gruppo gruppo, Connection connection) throws SQLException{
		
		MessaggioDao mexDao = new MessaggioDaoJDBC(dataSource);
		
		for (Messaggio mex : gruppo.getChat()) {
			
			mexDao.delete(mex);
		}
	}
	
	@Override
	public void addUserToGroup(Gruppo gruppo, Utente utente) {
		Connection connection = dataSource.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("Select email_utente from iscrizione where canale=? and gruppo=? and email_utente=?");
			statement.setString(1, gruppo.getCanale().getNome());
			statement.setString(2, gruppo.getNome());
			statement.setString(3, utente.getEmail());
			ResultSet result = statement.executeQuery();
			if(!result.next()) {
			
				String insert = "insert into iscrizione(canale, gruppo, email_utente) values (?,?,?)";
				statement = connection.prepareStatement(insert);
				statement.setString(1, gruppo.getCanale().getNome());
				statement.setString(2, gruppo.getNome());
				statement.setString(3, utente.getEmail());
				statement.executeUpdate();
			}

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
	public void removeUserFromGroup(Gruppo gruppo, Utente utente) {
		Connection connection = dataSource.getConnection();
		try {
			String delete = "delete FROM iscrizione WHERE canale = ? and gruppo = ? and email_utente = ?";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setString(1, gruppo.getCanale().getNome());
			statement.setString(2, gruppo.getNome());
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
	public void addUserToAdmin(Gruppo gruppo, Utente utente) {
		Connection connection = dataSource.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("Select email_utente from gestione_gruppo where canale=? and gruppo=? and email_utente=?");
			statement.setString(1, gruppo.getCanale().getNome());
			statement.setString(2, gruppo.getNome());
			statement.setString(3, utente.getEmail());
			ResultSet result = statement.executeQuery();
			if(!result.next()) {
			
				String insert = "insert into gestione_gruppo(canale, gruppo, email_utente) values (?,?,?)";
				statement = connection.prepareStatement(insert);
				statement.setString(1, gruppo.getCanale().getNome());
				statement.setString(2, gruppo.getNome());
				statement.setString(3, utente.getEmail());
				statement.executeUpdate();
			}

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
	public void removeUserFromAdmin(Gruppo gruppo, Utente utente) {
		Connection connection = dataSource.getConnection();
		try {
			String delete = "delete FROM gestione_gruppo WHERE canale = ? and gruppo = ? and email_utente = ?";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setString(1, gruppo.getCanale().getNome());
			statement.setString(2, gruppo.getNome());
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
	public void addUserToAttesa(Gruppo gruppo, Utente utente) {
		Connection connection = dataSource.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("Select email_utente from utenti_attesa where canale=? and gruppo=? and email_utente=?");
			statement.setString(1, gruppo.getCanale().getNome());
			statement.setString(2, gruppo.getNome());
			statement.setString(3, utente.getEmail());
			ResultSet result = statement.executeQuery();
			if(!result.next()) {
				
				String insert = "insert into utenti_attesa(canale, gruppo, email_utente) values (?,?,?)";
				statement = connection.prepareStatement(insert);
				statement.setString(1, gruppo.getCanale().getNome());
				statement.setString(2, gruppo.getNome());
				statement.setString(3, utente.getEmail());
				statement.executeUpdate();
			}
			//System.out.println("aggiunto in attesa nel db al gruppo "+gruppo.getNome());

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
	public void removeUserFromAttesa(Gruppo gruppo, Utente utente) {
		Connection connection = dataSource.getConnection();
		try {
			String delete = "delete FROM utenti_attesa WHERE canale = ? and gruppo = ? and email_utente = ?";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setString(1, gruppo.getCanale().getNome());
			statement.setString(2, gruppo.getNome());
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
	
		

}
