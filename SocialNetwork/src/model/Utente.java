package model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Utente {
	
	private String email;
	private String nome;
	private String cognome;
	private String username;
	private Date dataDiNascita;
	private Date dataIscrizione;
	private String image;
	private Set<Notifica> notifiche; 
	
	
	public Utente(){
		
		notifiche = new HashSet<>();
	}
	
	public Utente(String e, String n, String c,String un,Date di, Date ddn){
		
		email = e;
		nome = n;
		cognome = c;
		username = un;
		dataDiNascita = ddn;
		dataIscrizione = di;
		notifiche = new HashSet<>();
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDataDiNascita() {
		return dataDiNascita;
	}

	public void setDataDiNascita(Date dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDataIscrizione() {
		return dataIscrizione;
	}

	public void setDataIscrizione(Date dataIscrizione) {
		this.dataIscrizione = dataIscrizione;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Set<Notifica> getNotifiche() {
		return notifiche;
	}

	public void setNotifiche(Set<Notifica> notifiche) {
		this.notifiche = notifiche;
	}
	
	
	
}
