package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.DBConnection;
import models.Tweet;
import models.Utente;

public class Dao {
	
	
	
	public Dao() {
		super();
	}

	//Registrazione dell'utente
	public boolean inserisciUtente(Utente utente) {
		
		String query = "INSERT INTO utente(nome, cognome, nickname, email, password)"
					+ "VALUES (?, ?, ?, ?, ?)";
		
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query)){
			stmt.setString(1, utente.getNome());
			stmt.setString(2, utente.getCognome());
			stmt.setString(3, utente.getNickname());
			stmt.setString(4, utente.getEmail());
			stmt.setString(5, utente.getPassword());
			stmt.toString();
			stmt.executeUpdate();
			return true;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Utente cercaUtentePerNicknameEmailId(String nicknameOrEmailId) {
		Utente utente = null;
		//Inizialmente recupero l'utente senza seguiti e follower
		String query = "SELECT * "
					+ "FROM utente "
					+ "WHERE nickname = ? OR email = ? OR id_utente = ?";

		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			stmt.setString(1, nicknameOrEmailId);
			stmt.setString(2, nicknameOrEmailId);
			stmt.setString(3, nicknameOrEmailId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String nicknameRS = rs.getString("nickname");
				String email = rs.getString("email");
				String password = rs.getString("password");
				utente = new Utente(nome, cognome, nicknameRS, email, password);
			}else{
				return null;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		//Recupero i seguiti del follower
		String querySeguiti = "SELECT * "
				+ "FROM utente u JOIN follows f "
				+ "ON u.id_utente = f.id_followed "
				+ "WHERE u.nickname = ?";
		
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(querySeguiti);){
			stmt.setString(1, nicknameOrEmailId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Utente seguito = new Utente(rs.getString("nome"), rs.getString("cognome"), rs.getString("nickname"), rs.getString("email"), rs.getString("password"));
				if(seguito != null) {
					utente.getFollowed().add(seguito);
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		//Recupero i follower
		String queryFollower = "SELECT * "
							+ "FROM utente u JOIN follows f "
							+ "ON u.id_utente = f.id_follower "
							+ "WHERE u.nickname = ?";
		
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(queryFollower);){
			stmt.setString(1, nicknameOrEmailId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Utente follower = new Utente(rs.getString("nome"), rs.getString("cognome"), rs.getString("nickname"), rs.getString("email"), rs.getString("password"));
				if(follower != null) {
					utente.getFollowers().add(follower);
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		//Recupero i tweet
		String queryTweets = "SELECT * "
							+ "FROM tweet t JOIN utente u "
							+ "ON t.id_utente = u.id_utente "
							+ "WHERE u.nickname = ?";
		
		try(Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(queryTweets);){
					stmt.setString(1, nicknameOrEmailId);
					ResultSet rs = stmt.executeQuery();
					while(rs.next()) {
						Tweet tweet = new Tweet(cercaUtentePerNicknameEmailId(rs.getString("id_utente")), rs.getString("titolo"), rs.getString("descrizione"));
						utente.getTweets().add(tweet);
						
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
		
		return utente;
	}
	
	

	public List<Utente> cercaTuttiUtenti(){ 
		List<Utente> utenti = new ArrayList<Utente>(); 
		String query = "SELECT * "
					+ "FROM utente";
		
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet rs = stmt.executeQuery()){
			while(rs.next()) {
				//Richiamo la funzione cercaUtente per recuperare i follower e i seguiti di ogni utente
				Utente utente = cercaUtentePerNicknameEmailId(rs.getString("nickname"));
				utenti.add(utente);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return utenti;
	}
	
	public boolean eliminaUtente(String nickname) {
		String query = "DELETE * "
					+ "FROM utenti "
					+ "WHERE nickname = ?";
		
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);){
			stmt.setString(1, nickname);
			stmt.executeUpdate();
			if(cercaUtentePerNicknameEmailId(nickname) == null) {
				return true;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean rimuoviTweet(long idTweet) {
		String query = "DELETE * "
					+ "FROM tweet t "
					+ "WHERE t.id_tweet = ?";
		
		try(Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);){
					stmt.setLong(1, idTweet);
					stmt.executeUpdate();
					if(cercaTweet(idTweet) == null) {
						return true;
					}
		
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private Tweet cercaTweet(long idTweet) {
		String query = "SELECT *"
					+ "FROM tweet t "
					+ "WHERE t.id_tweet = ?";
		
		try(Connection conn = DBConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(query);){
				stmt.setLong(1, idTweet);
				ResultSet rs = stmt.executeQuery();
				if(rs.next()) {
					Tweet t = new Tweet(cercaUtentePerNicknameEmailId(rs.getString("id_utente")), rs.getString("titolo"), rs.getString("descrizione"));
							t.setIdTweet(rs.getInt("id_tweet"));
					return t;
				}
		
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
	
