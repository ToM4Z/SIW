package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import model.Commento;
import model.Post;
import persistence.dao.CommentoDao;

public class CommentoDaoJDBC implements CommentoDao {
	
	private DataSource dataSource;
	
	CommentoDaoJDBC(DataSource ds){
		
		dataSource = ds;
	}

	@Override
	public void save(Commento commento) {
		
		Connection connection = dataSource.getConnection();
		try {
			Long id = IdBroker.getId(connection);
			commento.setId(id);
			commento.setDataCreazione(Calendar.getInstance().getTime());

			String insert = "insert into commento(id_commento, id_post, id_utente, "
					+ "contenuto, data_creazione) values (?,?,?,?,?)";

			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setLong(1, commento.getId());
			statement.setLong(2, commento.getPost().getId());
			statement.setLong(3, commento.getCreatore().getId_utente());
			statement.setString(4, commento.getContenuto());
			statement.setDate(5, new java.sql.Date(commento.getDataCreazione().getTime()));
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
	public Commento findByPrimaryKey(Long id) {
		Connection connection = this.dataSource.getConnection();
		Commento commento = null;
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement("select * from commento where id_commento = ?");
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				commento = new Commento();
				commento.setId(result.getLong("id_commento"));
				commento.setCreatore(new UtenteDaoJDBC(dataSource).findByPrimaryKey(result.getLong("id_utente")));;
				commento.setContenuto(result.getString("contenuto"));
				commento.setPost(new PostDaoJDBC(dataSource).findByPrimaryKey(result.getLong("post")));
				commento.setDataCreazione(new java.util.Date(result.getDate("data_creazione").getTime()));
				
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
		return commento;
	}
	

	@Override
	public List<Commento> findAll() {
		
		Connection connection = this.dataSource.getConnection();
		List<Commento> commenti = new LinkedList<>();
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement("select * from commento");
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Commento commento = new Commento();
				commento.setId(result.getLong("id_commento"));
				commento.setCreatore(new UtenteDaoJDBC(dataSource).findByPrimaryKey(result.getLong("id_utente")));;
				commento.setContenuto(result.getString("contenuto"));
				commento.setPost(new PostDaoJDBC(dataSource).findByPrimaryKey(result.getLong("post")));
				commento.setDataCreazione(new java.util.Date(result.getDate("data_creazione").getTime()));

				commenti.add(commento);
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
		return commenti;
	}

	@Override
	public void update(Commento commento) {
		
		Connection connection = this.dataSource.getConnection();
		try {
			String update = "update commento SET contenuto = ? WHERE id_commento = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, commento.getContenuto());
			statement.setLong(2, commento.getId());

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
	public void delete(Commento commento) {
		
		Connection connection = this.dataSource.getConnection();
		try {
			PreparedStatement statement;
			
			statement = connection.prepareStatement("delete from commento where id_commento = ?");
			statement.setLong(1, commento.getId());
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
