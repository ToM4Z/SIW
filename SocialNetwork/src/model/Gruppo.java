package model;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Gruppo {

	private String nome;
	private Set<Utente> admins;
	private Set<Utente> membri;
	private Set<Utente> utentiInAttesa;
	private List<Post> post;
	private Date data_creazione;
	private Canale canale;
	private List<Messaggio> chat;
	
	public Gruppo() {
		admins = new HashSet<>();
		membri = new HashSet<>();
		utentiInAttesa=new HashSet<>();
		post = new LinkedList<>();
		chat = new LinkedList<>();
	}
	
	public Gruppo(String nome,Date dc, Canale canale) {
		this.nome=nome;
		data_creazione=dc;
		this.canale=canale;
		admins = new HashSet<>();
		membri = new HashSet<>();
		utentiInAttesa=new HashSet<>();
		post = new LinkedList<>();
		chat = new LinkedList<>();
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

	public List<Post> getPost() {
		return post;
	}

	public void setPost(List<Post> post) {
		this.post = post;
	}

	public List<Messaggio> getChat() {
		return chat;
	}

	public void setChat(List<Messaggio> chat) {
		this.chat = chat;
	}

	public Set<Utente> getUtentiInAttesa() {
		return utentiInAttesa;
	}

	public void setUtentiInAttesa(Set<Utente> inAttesa) {
		this.utentiInAttesa = inAttesa;
	}
	
	public void addMessaggio (Messaggio m) {		
		chat.add(m);
	}
	
}
