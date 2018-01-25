package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import model.Commento;
import model.Post;

public class PostProxy extends Post{
	
	DataSource dataSource;
	
	public PostProxy(DataSource ds){		
		dataSource = ds;
	}
	
	public List<Commento> getCommenti() { 
		
		Connection connection = this.dataSource.getConnection();
		List<Commento> commenti = new LinkedList<>();
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement("select * from commento where id_post = ? ORDER BY id_commento DESC");
			statement.setLong(1, this.getId());
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Commento commento = new Commento();
				commento.setId(result.getLong("id_commento"));
				commento.setCreatore(new UtenteDaoJDBC(dataSource).findByPrimaryKey(result.getString("email_utente")));;
				commento.setContenuto(result.getString("contenuto"));
				commento.setPost(new PostDaoJDBC(dataSource).findByPrimaryKey(result.getLong("id_post")));
				commento.setDataCreazione(new Date(result.getTimestamp("data_creazione").getTime()));

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
		
		this.setCommenti(commenti);
		return super.getCommenti(); 
	}
	
	public Set<String> getLike(){
		
		Connection connection = this.dataSource.getConnection();
		Set<String> like = new HashSet<>();
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement("select * from likes where id_post = ?");
			statement.setLong(1, this.getId());
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				
				like.add(result.getString("email_utente"));
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
		
		setNumLikes(like.size());
		this.setLike(like);
		return super.getLike();
	}

	public Set<String> getDislike(){
		
		Connection connection = this.dataSource.getConnection();
		Set<String> dislike = new HashSet<>();
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement("select * from dislikes where id_post = ?");
			statement.setLong(1, this.getId());
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				
				dislike.add(result.getString("email_utente"));
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
		
		setNumDislikes(dislike.size());
		this.setDislike(dislike);
		return super.getDislike();
	}
	
}
