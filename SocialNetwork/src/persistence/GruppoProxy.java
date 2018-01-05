package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import model.Gruppo;
import model.Post;
import model.Utente;

public class GruppoProxy extends Gruppo {
	
	DataSource dataSource;
	
	public GruppoProxy(DataSource ds){		
		dataSource = ds;
	}
	
	public Set<Utente> getMembri() { 
		Set<Utente> utenti = new HashSet<>();
		Connection connection = this.dataSource.getConnection();
		
		try {
			PreparedStatement statement;
			String query = "select * from utente where email IN (select email_utente from iscrizione where gruppo = ?)";
			statement = connection.prepareStatement(query);
			statement.setString(1, this.getNome());
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Utente utente = new Utente();
				utente.setEmail(result.getString("email"));
				utente.setNome(result.getString("nome"));
				utente.setCognome(result.getString("cognome"));
				utente.setUsername(result.getString("username"));
				utente.setDataDiNascita(new java.util.Date(result.getDate("data_nascita").getTime()));
				utente.setDataIscrizione(new java.util.Date(result.getDate("data_iscrizione").getTime()));
				utenti.add(utente);
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
		this.setMembri(utenti);
		return super.getMembri(); 
	}
	
	public Set<Utente> getAdmins(){
		
		Set<Utente> admins = new HashSet<>();
		Connection connection = this.dataSource.getConnection();
		
		try {
			PreparedStatement statement;
			String query = "select * from utente where email IN (select email_utente from gestione_gruppo where gruppo = ? and canale = ?)";
			statement = connection.prepareStatement(query);
			statement.setString(1, this.getNome());
			statement.setString(2, this.getCanale().getNome());
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Utente utente = new Utente();
				utente.setEmail(result.getString("email"));
				utente.setNome(result.getString("nome"));
				utente.setCognome(result.getString("cognome"));
				utente.setUsername(result.getString("username"));
				utente.setDataDiNascita(new java.util.Date(result.getDate("data_nascita").getTime()));
				utente.setDataIscrizione(new java.util.Date(result.getDate("data_iscrizione").getTime()));
				admins.add(utente);
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
		this.setAdmins(admins);
		return super.getAdmins(); 
	}
	
	public Set<Post> getPost(){
		
		Connection connection = this.dataSource.getConnection();
		Set<Post> allPost = new HashSet<>();
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement("select * from post where gruppo = ? and canale = ?");
			statement.setString(1, this.getNome());
			statement.setString(2, this.getCanale().getNome());
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Post post = new PostProxy(dataSource);
				post.setId(result.getLong("id_post"));
				post.setCreatore(new UtenteDaoJDBC(dataSource).findByPrimaryKey(result.getString("email_utente")));;
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

}
