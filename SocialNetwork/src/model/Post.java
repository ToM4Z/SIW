package model;

import java.util.Date;
import java.util.Set;

public class Post {
	
	private Utente creatore;
	private String contenuto;
	private Long id;
	private Set<Commento> commenti;
	private Gruppo gruppo;
	private Canale canale;
	private Date dataCreazione;
	

	public Post(){}
	
	public Post(Utente cr, String co, Canale c, Gruppo g, Date data){
		
		creatore = cr;
		contenuto = co;
		canale = c;
		gruppo = g;
		dataCreazione = data;
	}

	public Utente getCreatore() {
		return creatore;
	}

	public void setCreatore(Utente creatore) {
		this.creatore = creatore;
	}

	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Commento> getCommenti() {
		return commenti;
	}

	public void setCommenti(Set<Commento> commenti) {
		this.commenti = commenti;
	}
	
	public void addCommento (Commento c){
		
		this.getCommenti().add(c);
	}
	
	public void removeCommento (Commento c){
		
		this.getCommenti().remove(c);
	}
	
	public Gruppo getGruppo() {
		return gruppo;
	}

	public void setGruppo(Gruppo gruppo) {
		this.gruppo = gruppo;
	}

	public Canale getCanale() {
		return canale;
	}

	public void setCanale(Canale canale) {
		this.canale = canale;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
	
	

}
