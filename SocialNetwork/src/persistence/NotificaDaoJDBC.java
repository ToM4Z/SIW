package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
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
			notifica.setData(Calendar.getInstance().getTime());

			String insert = "insert into notifica(id_notifica, email_utente, contenuto, "
					+ "data_creazione, visualizzata) values (?,?,?,?,?)";

			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setLong(1, notifica.getId());
			statement.setString(2, notifica.getUtente().getEmail());
			statement.setString(3, notifica.getContenuto());
			statement.setDate(4, new java.sql.Date(notifica.getData().getTime()));
			statement.setString(5, Boolean.toString(notifica.isVisualizzata()));
			
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
				notifica.setData(new java.util.Date(result.getDate("data_creazione").getTime()));
				notifica.setVisualizzata(Boolean.getBoolean(result.getString("visualizzata")));
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
			statement = connection.prepareStatement("select * from notifica where email_utente = ?");
			statement.setString(1, utente.getEmail());
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Notifica notifica = new Notifica();
				notifica.setId(result.getLong("id_notifica"));
				notifica.setUtente(new UtenteDaoJDBC(dataSource).findByPrimaryKey(result.getString("email_utente")));;
				notifica.setContenuto(result.getString("contenuto"));
				notifica.setData(new java.util.Date(result.getDate("data_creazione").getTime()));
				notifica.setVisualizzata(Boolean.getBoolean(result.getString("visualizzata")));
				
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
			String update = "update notifica SET visualizzata = ? WHERE id_notifica = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, Boolean.toString(notifica.isVisualizzata()));
			statement.setLong(2, notifica.getId());

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
			String delete = "delete FROM notifica WHERE id_notifica = ?";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setLong(1, notifica.getId());

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
	
	

}
