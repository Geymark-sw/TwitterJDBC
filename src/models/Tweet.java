package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Tweet {
	
	private Utente utente;
	private String titolo;
	private String descrizione;
	private LocalDateTime istanteCaricamento;
	private List<Utente> likes = new ArrayList<Utente>();
	
	
	public Tweet(Utente utente, String descrizione) {
		super();
		this.utente = utente;
		this.descrizione = descrizione;
	}


	public Utente getUtente() {
		return this.utente;
	}


	public void setId_utente(Utente utente) {
		this.utente = utente;
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


	public String getTitolo() {
		return titolo;
	}


	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}


	public LocalDateTime getIstanteCaricamento() {
		return istanteCaricamento;
	}


	public void setIstanteCaricamento(LocalDateTime istanteCaricamento) {
		this.istanteCaricamento = istanteCaricamento;
	}


	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	
	

}
