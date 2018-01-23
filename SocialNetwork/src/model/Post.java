package model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Post {
	
	private Utente creatore;
	private String contenuto;
	private Long id;
	private Set<Commento> commenti;
	private Gruppo gruppo;
	private Canale canale;
	private Date dataCreazione;	
	private Set<String> like;
	private Set<String> dislike;

	public Post(){
		commenti = new HashSet<>();
		like = new HashSet<>();
		dislike = new HashSet<>();
	}
	
	public Post(Utente cr, String co, Canale c, Gruppo g, Date data){
		
		creatore = cr;
		contenuto = co;
		canale = c;
		gruppo = g;
		dataCreazione = data;
		commenti = new HashSet<>();
		like = new HashSet<>();
		dislike = new HashSet<>();
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

	public Set<String> getLike() {
		return like;
	}

	public void setLike(Set<String> like) {
		this.like = like;
	}

	public Set<String> getDislike() {
		return dislike;
	}

	public void setDislike(Set<String> dislike) {
		this.dislike = dislike;
	}
}
