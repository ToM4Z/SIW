package model;

import java.util.Date;

public class Utente {
	
	private Long id_utente;
	private String nome;
	private String cognome;
	private String username;
	private Date dataDiNascita;
	private Date dataIscrizione;
	//private Image image;
	
	public Utente(){}
	
	public Utente(String n, String c,String un,Date di, Date ddn){
		nome = n;
		cognome = c;
		username = un;
		dataDiNascita = ddn;
		dataIscrizione = di;
	}
	
	public Long getId_utente() {
		return id_utente;
	}

	public void setId_utente(Long id) {
		this.id_utente = id;
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
	
}
