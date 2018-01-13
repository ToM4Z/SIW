package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import model.Canale;
import model.Post;
import model.Utente;
import persistence.dao.UtenteDao;

class UtenteDaoJDBC implements UtenteDao {
	private DataSource dataSource;

	public UtenteDaoJDBC(DataSource ds) {
		dataSource = ds;
	}

	@Override
	public void save(Utente utente) {
		Connection connection = dataSource.getConnection();
		try {
			utente.setDataIscrizione(Calendar.getInstance().getTime());

			String insert = "insert into utente(email, nome, cognome, username, "
					+ "data_nascita, data_iscrizione) values (?,?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, utente.getEmail());
			statement.setString(2, utente.getNome());
			statement.setString(3, utente.getCognome());
			statement.setString(4, utente.getUsername());
			statement.setDate(5, new java.sql.Date(utente.getDataDiNascita().getTime()));
			statement.setDate(6, new java.sql.Date(utente.getDataIscrizione().getTime()));
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
	public Utente findByPrimaryKey(String email) {
		Connection connection = this.dataSource.getConnection();
		Utente utente = null;
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement("select * from utente where email = ?");
			statement.setString(1, email);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				utente = new UtenteProxy(dataSource);
				utente.setEmail(result.getString("email"));
				utente.setNome(result.getString("nome"));
				utente.setCognome(result.getString("cognome"));
				utente.setUsername(result.getString("username"));
				utente.setDataDiNascita(new java.util.Date(result.getDate("data_nascita").getTime()));
				utente.setDataIscrizione(new java.util.Date(result.getDate("data_iscrizione").getTime()));
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
		return utente;
	}

	@Override
	public List<Utente> findAll() {
		Connection connection = this.dataSource.getConnection();
		List<Utente> utenti = new LinkedList<>();
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement("select * from utente");
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Utente utente = new UtenteProxy(dataSource);
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
		return utenti;
	}

	@Override
	public void update(Utente utente) {

		Connection connection = this.dataSource.getConnection();
		try {
			String update = "update utente SET username = ?"/* and image = ?*/ +" WHERE email_utente = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, utente.getUsername());
			//statement.setString(2, utente.getImage());
			statement.setString(2, utente.getEmail());

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
	public void delete(Utente utente) {
		Connection connection = this.dataSource.getConnection();
		try {
			PreparedStatement statement;

			statement = connection.prepareStatement("delete from utente where email = ?");
			statement.setString(1, utente.getEmail());
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

	@Override
	public void setPassword(Utente user, String password) {
		Connection connection = this.dataSource.getConnection();
		try {
			String update = "update utente SET \"password\" = ? WHERE email=?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, password);
			statement.setString(2, user.getEmail());
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
	public UtenteCredenziali findByPrimaryKeyCredential(String email) {
		Utente user = findByPrimaryKey(email);
		UtenteCredenziali userCred = null;
		if (user != null) {
			userCred = new UtenteCredenziali(dataSource);
			userCred.setEmail(user.getEmail());
			userCred.setNome(user.getNome());
			userCred.setCognome(user.getCognome());
			userCred.setUsername(user.getUsername());
			userCred.setDataDiNascita(user.getDataDiNascita());
			userCred.setDataIscrizione(user.getDataIscrizione());
		}
		return userCred;
	}
	
	public List<Post> getPostsOfMyGroups(Utente utente){
		
		Connection connection = this.dataSource.getConnection();
		List<Post> allPost = new LinkedList<>();
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement("select * from post where gruppo in (select gruppo from iscrizione where email_utente = ?)");
			statement.setString(1, utente.getEmail());
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
			
			//System.out.println("from db"+allPost.size());
			
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
	
	public List<Canale> getMyChannels(Utente utente){
	
		Connection connection = this.dataSource.getConnection();
		List<Canale> canali = new LinkedList<>();
		try {
			PreparedStatement statement = connection.prepareStatement("select * from canale where nome in" 
					+"(select canale from iscrizione where email_utente = ?)");
			statement.setString(1, utente.getEmail());
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				Canale canale = new CanaleProxy(dataSource);
				canale.setNome(result.getString("nome"));
				canale.setDescrizione(result.getString("descrizione"));
				canale.setData_creazione(result.getDate("data_creazione"));
				canale.setAdmin(new UtenteDaoJDBC(dataSource).findByPrimaryKey(result.getString("email_admin")));
				canali.add(canale);
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
		return canali;
	}

}
