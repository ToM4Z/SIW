package model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Canale {
	
	private String nome;
	private String descrizione;
	private Date data_creazione;
	private Long admin;
	private Set<Utente> membri;
	private Set<Gruppo> gruppi;
	//private Image image;

	public Canale() {
		membri = new HashSet<>();
		gruppi = new HashSet<>();
	}
	
	public Canale(String n, String d, Date dc, Long ad) {
		nome=n;
		descrizione=d;
		data_creazione=dc;
		admin=ad;
		membri = new HashSet<>();
		gruppi = new HashSet<>();
	}
	
	public Date getData_creazione() {
		return data_creazione;
	}

	public void setData_creazione(Date data_creazione) {
		this.data_creazione = data_creazione;
	}

	public Set<Gruppo> getGruppi() {
		return gruppi;
	}

	public void setGruppi(Set<Gruppo> gruppi) {
		this.gruppi = gruppi;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public Long getAdmin() {
		return admin;
	}
	
	public void setAdmin(Long admin) {
		this.admin = admin;
	}
		
	public Set<Utente> getMembri() {
		return membri;
	}
	
	public void setMembri(Set<Utente> membri) {
		this.membri = membri;
	}
	
	public void addMembro(Utente u){		
		this.getMembri().add(u);   //importante usare getMembri() per il proxy
	}
	
	public void removeMembro(Utente u){
		
		this.getMembri().remove(u);
	}
	
	public void addGruppo(Gruppo g){
		
		this.getGruppi().add(g);
	}
	
	public void removeGruppo(Gruppo g){
		
		this.getGruppi().remove(g);
	}
}
