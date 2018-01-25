package model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Utente {
	
	private String email;
	private String nome;
	private String cognome;
	private String username;
	private Date dataDiNascita;
	private Date dataIscrizione;
	private List<Notifica> notifiche; 
	
	
	public Utente(){
		
		notifiche = new LinkedList<>();
	}
	
	public Utente(String e, String n, String c,String un,Date di, Date ddn){
		
		email = e;
		nome = n;
		cognome = c;
		username = un;
		dataDiNascita = ddn;
		dataIscrizione = di;
		notifiche = new LinkedList<>();
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

	public List<Notifica> getNotifiche() {
		return notifiche;
	}

	public void setNotifiche(List<Notifica> notifiche) {
		this.notifiche = notifiche;
	}
	
}
