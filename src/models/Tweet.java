package models;

import java.util.List;

public class Tweet {
	
	private long id_utente;
	private String descrizione;
	private List<Utente> likes;
	
	
	public Tweet(long id_utente, String descrizione) {
		super();
		this.id_utente = id_utente;
		this.descrizione = descrizione;
	}


	public long getId_utente() {
		return id_utente;
	}


	public void setId_utente(long id_utente) {
		this.id_utente = id_utente;
	}


	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	//deve ritornare solo il nickname degli utenti e il numero di likes
	public List<Utente> getLikes() {
		return likes; 
	}


	public void setLikes(List<Utente> likes) {
		this.likes = likes;
	}
	
	
	
	

}
