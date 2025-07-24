package models;

import java.util.ArrayList;
import java.util.List;

public class Utente {
	
	private String nome;
	private String cognome;
	private String nickname;
	private String email;
	private String password;
	private List<Utente> followers = new ArrayList<Utente>();
	private List<Utente> followed = new ArrayList<Utente>();
	private List<Tweet> tweets = new ArrayList<Tweet>();
	
	public Utente(String nome, String cognome, String nickname, String email, String password) {
		this.nome = nome;
		this.cognome = cognome;
		this.nickname = nickname;
		setEmail(email);
		this.password = password;
	}

	public String getNome() {
		return this.nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCognome() {
		return this.cognome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public String getNickname() {
		return this.nickname;
	}
	
	//Controllare che il nickname non sia già presente nel database escludendo l'utente che ha intenzione di cambiare il nickname
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	//Controllare che l'email non sia già presente nel database escludendo l'utente che ha intenzione che
	public void setEmail(String email) {
		if(email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
			this.email = email;
		}
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public List<Utente> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Utente> followers) {
		this.followers = followers;
	}

	public List<Utente> getFollowed() {
		return followed;
	}

	public void setFollowed(List<Utente> followed) {
		this.followed = followed;
	}

	public List<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}
	
	
	
}
