package model;

import java.util.Date;

public class Messaggio {
	
	private Long id;
	private Gruppo gruppo;
	private Utente mittente;
	private Date data;
	private String contenuto;
	private String image;
	
	public Messaggio(){}
	
	public Messaggio(Utente m, Gruppo g, String c){
		
		mittente = m;
		gruppo = g;
		contenuto = c;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Gruppo getGruppo() {
		return gruppo;
	}

	public void setGruppo(Gruppo gruppo) {
		this.gruppo = gruppo;
	}

	public Utente getMittente() {
		return mittente;
	}

	public void setMittente(Utente mittente) {
		this.mittente = mittente;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
	

}
