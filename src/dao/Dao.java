package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import utils.DBConnection;

import models.Utente;

public class Dao {
	
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
			stmt.executeUpdate();
			return true;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
