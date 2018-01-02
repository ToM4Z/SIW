package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import java.util.Set;

import model.Commento;
import model.Post;

public class PostProxy extends Post{
	
	DataSource dataSource;
	
	public PostProxy(DataSource ds){		
		dataSource = ds;
	}
	
	public Set<Commento> getCommenti() { 
		
		Connection connection = this.dataSource.getConnection();
		Set<Commento> commenti = new HashSet<>();
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement("select * from commento where id_post = ?");
			statement.setLong(1, this.getId());
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Commento commento = new Commento();
				commento.setId(result.getLong("id"));
				commento.setCreatore(new UtenteDaoJDBC(dataSource).findByPrimaryKey(result.getString("email_admin")));;
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
		
		this.setCommenti(commenti);
		return super.getCommenti(); 
	}

	
	
}
