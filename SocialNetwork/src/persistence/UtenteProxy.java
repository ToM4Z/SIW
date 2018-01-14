package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import model.Notifica;
import model.Utente;

public class UtenteProxy extends Utente{
	
	DataSource dataSource;
	
	UtenteProxy(DataSource ds){
		
		dataSource = ds;
	}
	
	public Set<Notifica> getNotifiche() { 
		//System.out.println("prendo le notifiche");
		Connection connection = this.dataSource.getConnection();
		Set<Notifica> notifiche = new HashSet<>();
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement("select * from notifica where email_utente = ?");
			statement.setString(1, this.getEmail());
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Notifica notifica = new Notifica();
				notifica.setId(result.getLong("id_notifica"));
				notifica.setUtente(new UtenteDaoJDBC(dataSource).findByPrimaryKey(result.getString("email_utente")));;
				notifica.setContenuto(result.getString("contenuto"));
				notifica.setData(new java.util.Date(result.getDate("data_creazione").getTime()));
				notifica.setVisualizzata(Boolean.getBoolean(result.getString("visualizzata")));

				notifiche.add(notifica);
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
		
		this.setNotifiche(notifiche);
		return super.getNotifiche(); 
	}

}
