package model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Gruppo {

	private String nome;
	private List<Utente> admins;
	private List<Utente> membri;
	private List<Utente> utentiInAttesa;
	private List<Post> post;
	private Date data_creazione;
	private Canale canale;
	private List<Messaggio> chat;
	
	public Gruppo() {
		admins = new LinkedList<>();
		membri = new LinkedList<>();
		utentiInAttesa=new LinkedList<>();
		post = new LinkedList<>();
		chat = new LinkedList<>();
	}
	
	public Gruppo(String nome,Date dc, Canale canale) {
		this.nome=nome;
		data_creazione=dc;
		this.canale=canale;
		admins = new LinkedList<>();
		membri = new LinkedList<>();
		utentiInAttesa=new LinkedList<>();
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

	public List<Utente> getMembri() {
		return membri;
	}

	public void setMembri(List<Utente> membri) {
		this.membri = membri;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Utente> getAdmins() {
		return admins;
	}

	public void setAdmins(List<Utente> admin) {
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

	public List<Utente> getUtentiInAttesa() {
		return utentiInAttesa;
	}

	public void setUtentiInAttesa(List<Utente> inAttesa) {
		this.utentiInAttesa = inAttesa;
	}
	
	public void addMessaggio (Messaggio m) {		
		chat.add(m);
	}
	
}
