package model;

public class Notifica {
	
	private Long id;
	private Utente utente;
	private String contenuto;
	
	public Notifica() {}
	
	public Notifica (Utente u, String c) {		
		utente = u;
		contenuto = c;
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

}
