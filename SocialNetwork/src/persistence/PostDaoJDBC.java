package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import model.Post;
import persistence.dao.PostDao;

public class PostDaoJDBC implements PostDao {
	
	DataSource dataSource;
	
	PostDaoJDBC(DataSource ds){
		
		dataSource = ds;
	}
	
	@Override
	public void save(Post post) {
		Connection connection = dataSource.getConnection();
		try {
			Long id = IdBroker.getId(connection);
			post.setId(id);
			post.setDataCreazione(Calendar.getInstance().getTime());

			String insert = "insert into post(id_post, id_utente, contenuto, "
					+ "canale, gruppo, data_creazione) values (?,?,?,?,?,?)";

			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setLong(1, post.getId());
			statement.setString(2, post.getCreatore().getNome());
			statement.setString(3, post.getContenuto());
			statement.setString(4, post.getCanale().getNome());
			statement.setString(5, post.getGruppo().getNome());
			statement.setDate(6, new java.sql.Date(post.getDataCreazione().getTime()));
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
			statement = connection.prepareStatement("select * from post where id = ?");
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				post = new Post();
				post.setId(result.getLong("id"));
				post.setCreatore(new UtenteDaoJDBC(dataSource).findByPrimaryKey(result.getLong("id_utente")));;
				post.setContenuto(result.getString("contenuto"));
				post.setCanale(new CanaleDaoJDBC(dataSource).findByPrimaryKey(result.getString("canale")));
				post.setGruppo(new GruppoDaoJDBC(dataSource).findByPrimaryKey(result.getString("gruppo"), post.getCanale().getNome()));
				post.setDataCreazione(new java.util.Date(result.getDate("data_creazione").getTime()));
				
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
				Post post = new Post();
				post.setId(result.getLong("id"));
				post.setCreatore(new UtenteDaoJDBC(dataSource).findByPrimaryKey(result.getLong("id_utente")));;
				post.setContenuto(result.getString("contenuto"));
				post.setCanale(new CanaleDaoJDBC(dataSource).findByPrimaryKey(result.getString("canale")));
				post.setGruppo(new GruppoDaoJDBC(dataSource).findByPrimaryKey(result.getString("gruppo"), post.getCanale().getNome()));
				post.setDataCreazione(new java.util.Date(result.getDate("data_creazione").getTime()));

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

	@Override
	public void update(Post post) {
		
		Connection connection = this.dataSource.getConnection();
		try {
			String update = "update post SET contenuto = ? WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, post.getContenuto());
			statement.setLong(2, post.getId());

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
	public void delete(Post post) {
		// TODO Auto-generated method stub
		
	}

}
