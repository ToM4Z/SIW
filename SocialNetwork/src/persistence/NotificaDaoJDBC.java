package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.Notifica;
import model.Utente;
import persistence.dao.NotificaDao;

public class NotificaDaoJDBC implements NotificaDao{
	
	DataSource dataSource;
	
	NotificaDaoJDBC(DataSource ds){
		
		dataSource = ds;
	}

	@Override
	public void save(Notifica notifica) {

		Connection connection = dataSource.getConnection();
		try {
			Long id = IdBroker.getId(connection);
			notifica.setId(id);

			String insert = "insert into notifica(id_notifica, email_utente, contenuto) values (?,?,?)";

			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setLong(1, notifica.getId());
			statement.setString(2, notifica.getUtente().getEmail());
			statement.setString(3, notifica.getContenuto());
			
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
	public Notifica findByPrimaryKey(Long id) {
		
		Connection connection = this.dataSource.getConnection();
		Notifica notifica = null;
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement("select * from notifica where id_notifica = ?");
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				notifica = new Notifica();
				notifica.setId(result.getLong("id_notifica"));
				notifica.setUtente(new UtenteDaoJDBC(dataSource).findByPrimaryKey(result.getString("email_utente")));;
				notifica.setContenuto(result.getString("contenuto"));
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
		return notifica;
	}

	@Override
	public List<Notifica> getNotificheUtente(Utente utente) {
		
		Connection connection = this.dataSource.getConnection();
		List<Notifica> allNotifiche = new LinkedList<>();
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement("select id_notifica,contenuto from notifica where email_utente = ?");
			statement.setString(1, utente.getEmail());
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Notifica notifica = new Notifica();
				notifica.setId(result.getLong("id_notifica"));
				notifica.setUtente(utente);
				notifica.setContenuto(result.getString("contenuto"));
				
				allNotifiche.add(notifica);
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
		return allNotifiche;
	}

	@Override
	public void update(Notifica notifica) {
		
		Connection connection = this.dataSource.getConnection();
		try {
			String update = "update notifica SET email_utente=?, contenuto=? WHERE id_notifica = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, notifica.getUtente().getEmail());
			statement.setString(2, notifica.getContenuto());
			statement.setLong(3, notifica.getId());
			
			statement.executeUpdate();
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
	public void delete(Notifica notifica) {
		
		Connection connection = this.dataSource.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("select id_notifica from notifica where id_notifica = ?");
			statement.setLong(1, notifica.getId());
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				String delete = "delete FROM notifica WHERE id_notifica = ?";
				statement = connection.prepareStatement(delete);
				statement.setLong(1, notifica.getId());
	
				connection.setAutoCommit(false);
				connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
	
				statement.executeUpdate();
				connection.commit();
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
	public List<Notifica> getAfter(Notifica notifica) {
		Connection connection = this.dataSource.getConnection();
		List<Notifica> allNotifiche = new LinkedList<>();
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement("select id_notifica,contenuto from notifica where email_utente = ?"
													+ " and id_notifica > ?");
			statement.setString(1, notifica.getUtente().getEmail());
			statement.setLong(2, notifica.getId());
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Notifica n = new Notifica();
				n.setId(result.getLong("id_notifica"));
				n.setUtente(notifica.getUtente());
				n.setContenuto(result.getString("contenuto"));
				
				allNotifiche.add(n);
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
		return allNotifiche;
	}

}
