package utils;

import java.util.*;

import dao.Dao;
import models.Utente;

public class FunzioniUtils {
	
	private static Dao dao = new Dao();
	
	public static boolean emailExists(String email){
		List<Utente> utenti = dao.cercaTuttiUtenti();
		
		for(Utente u : utenti) {
			if(u.getEmail().equalsIgnoreCase(email)) {
				return true;
			}
			
		}
		return false;
	}
	
	public static boolean nicknameExists(String nickname) {
		List<Utente> utenti = dao.cercaTuttiUtenti();
		for(Utente u : utenti) {
			if(u.getNickname().equalsIgnoreCase(nickname)) {
				return true;
			}
		}
		return false;
	}
	
	//Da riga 34 a riga 75 sono funzioni per ricercare un nickname
	///////////////////Calcola la distanza di Levenshtein tra due stringhe///////////////////
    public static int levenshtein(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(
                        dp[i - 1][j - 1] + (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1),
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1)
                    );
                }
            }
        }

        return dp[s1.length()][s2.length()];
    }

    // Calcola la similarità tra due stringhe in percentuale (da 0.0 a 1.0)
    public static double similarita(String s1, String s2) {
        int maxLen = Math.max(s1.length(), s2.length());
        if (maxLen == 0) return 1.0;
        return 1.0 - ((double) levenshtein(s1.toLowerCase(), s2.toLowerCase()) / maxLen);
    }

    // Cerca i Nomi simili nella lista
    public static List<Utente> trovaNomiSimili(String input, List<Utente> listaUtenti, double soglia) {
        List<Utente> risultati = new ArrayList<>();
        for (Utente utente : listaUtenti) {
            double sim = similarita(input, utente.getNome());
            if (sim >= soglia) {
                risultati.add(utente);
            }
        }
        return risultati;
    }
    
 // Cerca i Cognomi simili nella lista
    public static List<Utente> trovaCognomiSimili(String input, List<Utente> listaUtenti, double soglia) {
        List<Utente> risultati = new ArrayList<>();
        for (Utente utente : listaUtenti) {
            double sim = similarita(input, utente.getCognome());
            if (sim >= soglia) {
                risultati.add(utente);
            }
        }
        return risultati;
    }
    
 // Cerca i nickname simili nella lista
    public static List<Utente> trovaNicknameSimili(String input, List<Utente> listaUtenti, double soglia) {
        List<Utente> risultati = new ArrayList<>();
        for (Utente utente : listaUtenti) {
            double sim = similarita(input, utente.getNickname());
            if (sim >= soglia) {
                risultati.add(utente);
            }
        }
        return risultati;
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////
///

    
    public static List<String> getNicknames(){
    	List<String> nicknames = new ArrayList<String>();
    	
    	for(Utente u : dao.cercaTuttiUtenti()) {
    		nicknames.add(u.getNickname());
    	}
    	return nicknames;
    }
    
    public static List<String> getNomiECognomi(){
    	List<String> nomiECognomi = new ArrayList<String>();
    	
    	for(Utente u : dao.cercaTuttiUtenti()) {
    		nomiECognomi.add(u.getNome() + " " + u.getCognome());
    	}
    	
    	return nomiECognomi;
    }
    
    public static List<String> getNomi(){
    	List<String> nomi = new ArrayList<String>();
    	for(Utente u : dao.cercaTuttiUtenti()) {
    		nomi.add(u.getNome());
    	}
    	return nomi;
    }
    
    public static List<String> getCognomi(){
    	List<String> cognomi = new ArrayList<String>();
    	for(Utente u : dao.cercaTuttiUtenti()) {
    		cognomi.add(u.getCognome());
    	}
    	return cognomi;
    }
    
    
    public static void stampaUtenti(List<Utente> utenti) {
        System.out.printf("%-15s %-15s %-15s%n", "Nome", "Cognome", "Nickname");
        System.out.println("--------------------------------------------------");

        for (Utente u : utenti) {
            System.out.printf("%-15s %-15s %-15s%n",
                u.getNome(),
                u.getCognome(),
                u.getNickname());
        }
    }
    
    
    public static List<Utente> accorpaUtentiDaRicerca(List<Utente> utentiNomiSimili, List<Utente> utentiCognomiSimili, List<Utente> utentiNicknameSimili) {
    	List<Utente> utentiAccorpati = new ArrayList<Utente>();
    	List<String> nicknames = new ArrayList<String>();
    	
    	//Aggiungo tutti gli utenti con nomi simili
    	for(Utente u : utentiNomiSimili) {
    		if(!nicknames.contains(u.getNickname())) {
    			nicknames.add(u.getNickname());
    			utentiAccorpati.add(u);
    		}
    	}
    	
    	//Aggiungo tutti gli utenti con cognomi simili ma escludendo quelli con nickname uguale
    	for(Utente u : utentiCognomiSimili) {
    		if(!nicknames.contains(u.getNickname())) {
    			nicknames.add(u.getNickname());
    			utentiAccorpati.add(u);
    		}
    	}
    	
    	//Aggiungo tutti gli utenti con cognomi simili ma escludendo quelli con nickname uguale
    	for(Utente u : utentiNicknameSimili) {
    		if(!nicknames.contains(u.getNickname())) {
    			nicknames.add(u.getNickname());
    			utentiAccorpati.add(u);
    		}
    	}
    	
    	return utentiAccorpati;
    	
    }
    
}

