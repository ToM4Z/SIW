package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import model.Messaggio;
import persistence.dao.MessaggioDao;

public class MessaggioDaoJDBC implements MessaggioDao{
	
	private DataSource dataSource;
	
	public MessaggioDaoJDBC(DataSource ds){		
		dataSource = ds;
	}

	@Override
	public void save(Messaggio messaggio) {
		Connection connection = dataSource.getConnection();
		try {
			Long id = IdBroker.getId(connection);
			messaggio.setId(id);
			messaggio.setData(Calendar.getInstance().getTime());

			String insert = "insert into messaggio(id_messaggio, email_mittente, contenuto, "
					+ "canale, gruppo, data_creazione, image) values (?,?,?,?,?,?,?)";
			
			System.out.println(messaggio.getGruppo().getNome());
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setLong(1, messaggio.getId());
			statement.setString(2, messaggio.getMittente().getEmail());
			statement.setString(3, messaggio.getContenuto());
			statement.setString(4, messaggio.getGruppo().getCanale().getNome());
			statement.setString(5, messaggio.getGruppo().getNome());
			statement.setDate(6, new java.sql.Date(messaggio.getData().getTime()));
			statement.setString(7, messaggio.getImage());
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
	public Messaggio findByPrimaryKey(Long id) {
		
		Connection connection = this.dataSource.getConnection();
		Messaggio messaggio = null;
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement("select * from messaggio where id_messaggio = ?");
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				messaggio = new Messaggio();
				messaggio.setId(result.getLong("id_messaggio"));
				messaggio.setMittente(new UtenteDaoJDBC(dataSource).findByPrimaryKey(result.getString("email_mittente")));;
				messaggio.setContenuto(result.getString("contenuto"));
				messaggio.setGruppo(new GruppoDaoJDBC(dataSource).findByPrimaryKey(result.getString("gruppo"), result.getString("canale")));
				messaggio.setData(new java.util.Date(result.getDate("data_creazione").getTime()));
				messaggio.setImage(result.getString("image"));
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
		return messaggio;
	}

	@Override
	public List<Messaggio> findAll() {
		
		Connection connection = this.dataSource.getConnection();
		List<Messaggio> allMessaggi = new LinkedList<>();
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement("select * from messaggio");
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Messaggio messaggio = new Messaggio();
				messaggio.setId(result.getLong("id_messaggio"));
				messaggio.setMittente(new UtenteDaoJDBC(dataSource).findByPrimaryKey(result.getString("email_mittente")));;
				messaggio.setContenuto(result.getString("contenuto"));
				messaggio.setGruppo(new GruppoDaoJDBC(dataSource).findByPrimaryKey(result.getString("gruppo"), result.getString("canale")));
				messaggio.setData(new java.util.Date(result.getDate("data_creazione").getTime()));
				messaggio.setImage(result.getString("image"));

				allMessaggi.add(messaggio);
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
		return allMessaggi;
	}

	@Override
	public void update(Messaggio messaggio) {
		
		Connection connection = this.dataSource.getConnection();
		try {
			String update = "update messaggio SET contenuto = ? and image = ? WHERE id_messaggio = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, messaggio.getContenuto());
			statement.setString(2, messaggio.getImage());
			statement.setLong(3, messaggio.getId());

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
	public void delete(Messaggio messaggio) {
		
		Connection connection = this.dataSource.getConnection();
		try {
			String delete = "delete FROM messaggio WHERE id_messaggio = ?";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setLong(1, messaggio.getId());

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
	public List<Messaggio> getAfter(Messaggio m) {
		Connection connection = this.dataSource.getConnection();
		List<Messaggio> allMessaggi = new LinkedList<>();
		try {
			String query = "select id_messaggio,email_mittente,contenuto,"
							+ "data_creazione,image from messaggio where gruppo = ? and canale = ? and id_messaggio > ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, m.getGruppo().getNome());
			statement.setString(2, m.getGruppo().getCanale().getNome());
			statement.setLong(3,m.getId());
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Messaggio messaggio = new Messaggio();
				messaggio.setId(result.getLong("id_messaggio"));
				messaggio.setMittente(new UtenteDaoJDBC(dataSource).findByPrimaryKey(result.getString("email_mittente")));;
				messaggio.setContenuto(result.getString("contenuto"));
				messaggio.setGruppo(m.getGruppo());
				messaggio.setData(new java.util.Date(result.getDate("data_creazione").getTime()));
				messaggio.setImage(result.getString("image"));

				allMessaggi.add(messaggio);
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
		return allMessaggi;
	}
	
	@Override
	public List<Messaggio> getOther50(Messaggio m) {
		Connection connection = this.dataSource.getConnection();
		List<Messaggio> allMessaggi = new LinkedList<>();
		try {
			String query = "select id_messaggio,email_mittente,contenuto,"
							+ "data_creazione,image from messaggio where gruppo = ? and canale = ?"
							+ ( m.getId() != null ? " and id_messaggio < ?" : "" )
							+ " LIMIT 50";
			
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, m.getGruppo().getNome());
			statement.setString(2, m.getGruppo().getCanale().getNome());
			if(m.getId() != null)
				statement.setLong(3,m.getId());
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Messaggio messaggio = new Messaggio();
				messaggio.setId(result.getLong("id_messaggio"));
				messaggio.setMittente(new UtenteDaoJDBC(dataSource).findByPrimaryKey(result.getString("email_mittente")));;
				messaggio.setContenuto(result.getString("contenuto"));
				messaggio.setGruppo(m.getGruppo());
				//messaggio.setGruppo(new GruppoDaoJDBC(dataSource).findByPrimaryKey(result.getString("gruppo"), result.getString("canale")));
				messaggio.setData(new java.util.Date(result.getDate("data_creazione").getTime()));
				messaggio.setImage(result.getString("image"));

				allMessaggi.add(messaggio);
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
		return allMessaggi;
	}
}
