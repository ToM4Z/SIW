package model;

import java.util.Date;

public class Commento {
	
	private Long id;
	private String contenuto;
	private Utente creatore;
	private Post post;
	private Date dataCreazione;
	
	public Commento(){}
	
	public Commento(String co, Utente cr){
		
		contenuto = co;
		creatore = cr;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getContenuto() {
		return contenuto;
	}
	
	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}
	
	public Utente getCreatore() {
		return creatore;
	}
	
	public void setCreatore(Utente creatore) {
		this.creatore = creatore;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	
	
	
	

}
