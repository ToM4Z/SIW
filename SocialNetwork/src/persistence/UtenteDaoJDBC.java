package persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
			statement.setDate(5, new Date(utente.getDataDiNascita().getTime()));
			statement.setDate(6, new Date(utente.getDataIscrizione().getTime()));
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
			statement = connection.prepareStatement("select username from utente where email = ?");
			statement.setString(1, email);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				utente = new UtenteProxy(dataSource);
				utente.setEmail(email);
				utente.setUsername(result.getString("username"));
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
			statement = connection.prepareStatement("select email,username from utente ORDER BY username");
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Utente utente = new UtenteProxy(dataSource);
				utente.setEmail(result.getString("email"));
				utente.setUsername(result.getString("username"));

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
			String update = "update utente SET username = ? WHERE email_utente = ?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, utente.getUsername());
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
			userCred.setUsername(user.getUsername());
		}
		return userCred;
	}
	
	public List<Post> getPostsOfMyGroups(Utente utente){
		
		Connection connection = this.dataSource.getConnection();
		List<Post> allPost = new LinkedList<>();
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement("select * from post as p where gruppo in "
													+ "(select gruppo from iscrizione as i where "
													+ "email_utente = ? and i.canale = p.canale)"
													+ "ORDER BY id_post DESC");
			statement.setString(1, utente.getEmail());
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Post post = new PostProxy(dataSource);
				post.setId(result.getLong("id_post"));
				post.setCreatore(new UtenteDaoJDBC(dataSource).findByPrimaryKey(result.getString("email_utente")));;
				post.setContenuto(result.getString("contenuto"));
				post.setCanale(new CanaleDaoJDBC(dataSource).findByPrimaryKey(result.getString("canale")));
				post.setGruppo(new GruppoDaoJDBC(dataSource).findByPrimaryKey(result.getString("gruppo"), post.getCanale().getNome()));
				post.setDataCreazione(new Timestamp(result.getTimestamp("data_creazione").getTime()));
				post.setNumLikes(result.getInt("numlikes"));
				post.setNumDislikes(result.getInt("numdislikes"));
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
	
	public List<Canale> getMyChannels(Utente utente){
	
		Connection connection = this.dataSource.getConnection();
		List<Canale> canali = new LinkedList<>();
		try {
			PreparedStatement statement = connection.prepareStatement("select * from canale where nome in" 
					+"(select canale from iscrizione where email_utente = ?) ORDER BY nome");
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
