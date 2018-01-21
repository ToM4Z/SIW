package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import model.Post;
import persistence.dao.PostDao;

public class PostDaoJDBC implements PostDao {
	
	DataSource dataSource;
	
	public PostDaoJDBC(DataSource ds){		
		dataSource = ds;
	}
	
	@Override
	public void save(Post post) {
		Connection connection = dataSource.getConnection();
		try {
			Long id = IdBroker.getId(connection);
			post.setId(id);
			post.setDataCreazione(Calendar.getInstance().getTime());

			String insert = "insert into post(id_post, email_utente, contenuto, "
					+ "canale, gruppo, data_creazione, image) values (?,?,?,?,?,?,?)";

			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setLong(1, post.getId());
			statement.setString(2, post.getCreatore().getEmail());
			statement.setString(3, post.getContenuto());
			statement.setString(4, post.getCanale().getNome());
			statement.setString(5, post.getGruppo().getNome());
			statement.setTimestamp(6, new Timestamp(post.getDataCreazione().getTime()));
			statement.setString(7, post.getImage());
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
	public Post findByPrimaryKey(Long id) {
		Connection connection = this.dataSource.getConnection();
		Post post = null;
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement("select * from post where id_post = ?");
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				post = new PostProxy(dataSource);
				post.setId(result.getLong("id_post"));
				post.setCreatore(new UtenteDaoJDBC(dataSource).findByPrimaryKey(result.getString("email_utente")));;
				post.setContenuto(result.getString("contenuto"));
				post.setCanale(new CanaleDaoJDBC(dataSource).findByPrimaryKey(result.getString("canale")));
				post.setGruppo(new GruppoDaoJDBC(dataSource).findByPrimaryKey(result.getString("gruppo"), post.getCanale().getNome()));
				post.setDataCreazione(new Date(result.getTimestamp("data_creazione").getTime()));
				post.setImage(result.getString("image"));
				
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
		return post;
	}

	@Override
	public List<Post> findAll() {
		Connection connection = this.dataSource.getConnection();
		List<Post> allPost = new LinkedList<>();
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement("select * from post");
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Post post = new PostProxy(dataSource);
				post.setId(result.getLong("id_post"));
				post.setCreatore(new UtenteDaoJDBC(dataSource).findByPrimaryKey(result.getString("email_utente")));;
				post.setContenuto(result.getString("contenuto"));
				post.setCanale(new CanaleDaoJDBC(dataSource).findByPrimaryKey(result.getString("canale")));
				post.setGruppo(new GruppoDaoJDBC(dataSource).findByPrimaryKey(result.getString("gruppo"), post.getCanale().getNome()));
				post.setDataCreazione(new Date(result.getTimestamp("data_creazione").getTime()));
				post.setImage(result.getString("image"));

				allPost.add(post);
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
		return allPost;
	}
	
	private void removeAllCommentsFromPost(Post post, Connection connection) throws SQLException {
		String delete = "delete from commento WHERE id_post = ?";
		PreparedStatement statement = connection.prepareStatement(delete);
		statement.setLong(1, post.getId());
		statement.executeUpdate();
	}

	@Override
	public void update(Post post) {
		
		Connection connection = this.dataSource.getConnection();
		try {
			String update = "update post SET contenuto = ?"+/* and image = ?*/" WHERE id_post = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, post.getContenuto());
			//statement.setString(2, post.getImage());
			statement.setLong(2, post.getId());

			statement.executeUpdate();
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
	public void delete(Post post) {
		
		Connection connection = this.dataSource.getConnection();
		try {
			String delete = "delete FROM post WHERE id_post = ?";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setLong(1, post.getId());

			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			this.removeAllCommentsFromPost(post, connection);

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
