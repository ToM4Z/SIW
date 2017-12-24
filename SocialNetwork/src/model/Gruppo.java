package model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Gruppo {

	private String nome;
	private Set<Utente> admins;
	private Set<Utente> membri;
	// private Set<Post> post;
	private Date data_creazione;
	private Canale canale;
	
	public Gruppo() {
		admins = new HashSet<>();
		membri = new HashSet<>();
	}
	
	public Gruppo(String nome,Date dc, Canale canale) {
		this.nome=nome;
		data_creazione=dc;
		this.canale=canale;
		admins = new HashSet<>();
		membri = new HashSet<>();
	}

	public void addMembro(Utente u) {
		this.getMembri().add(u);
	}

	public void removeMembro(Utente u) {
		this.getMembri().remove(u);
	}

	public void addAdmin(Utente u) {
		this.getAdmins().add(u);
	}

	public void removeAdmin(Utente u) {
		this.getAdmins().remove(u);
	}

	public Canale getCanale() {
		return canale;
	}

	public void setCanale(Canale canale) {
		this.canale = canale;
	}

	public Date getData_creazione() {
		return data_creazione;
	}

	public void setData_creazione(Date data_creazione) {
		this.data_creazione = data_creazione;
	}

	public Set<Utente> getMembri() {
		return membri;
	}

	public void setMembri(Set<Utente> membri) {
		this.membri = membri;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Utente> getAdmins() {
		return admins;
	}

	public void setAdmins(Set<Utente> admin) {
		this.admins = admin;
	}

	// public Set<Post> getPost() {
	// return post;
	// }
	//
	// public void setPost(Set<Post> post) {
	// this.post = post;
	// }
}
