package model;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Canale {
	
	private String nome;
	private String descrizione;
	private Date data_creazione;
	private Utente admin;
	private Set<Utente> membri;
	private List<Gruppo> gruppi;
	private List<Utente> blacklist;

	public Canale() {
		membri = new HashSet<>();
		gruppi = new LinkedList<>();
		blacklist = new LinkedList<>();
	}
	
	public Canale(String n, String d, Date dc, Utente ad) {
		nome=n;
		descrizione=d;
		data_creazione=dc;
		admin=ad;
		membri = new HashSet<>();
		gruppi = new LinkedList<>();
		blacklist = new LinkedList<>();
	}
	
	public List<Utente> getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(List<Utente> blacklist) {
		this.blacklist = blacklist;
	}

	public Date getData_creazione() {
		return data_creazione;
	}

	public void setData_creazione(Date data_creazione) {
		this.data_creazione = data_creazione;
	}

	public List<Gruppo> getGruppi() {
		return gruppi;
	}

	public void setGruppi(List<Gruppo> gruppi) {
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
	
	public Utente getAdmin() {
		return admin;
	}
	
	public void setAdmin(Utente admin) {
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
