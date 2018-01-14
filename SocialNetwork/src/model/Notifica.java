package model;

import java.util.Date;

public class Notifica {
	
	private Long id;
	private Utente utente;
	private String contenuto;
	private boolean visualizzata;
	Date data;
	
	public Notifica() {}
	
	public Notifica (Utente u, String c) {
		
		utente = u;
		contenuto = c;
		visualizzata = false;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public boolean isVisualizzata() {
		return visualizzata;
	}

	public void setVisualizzata(boolean visualizzata) {
		this.visualizzata = visualizzata;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
