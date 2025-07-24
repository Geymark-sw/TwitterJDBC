package Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.Dao;
import models.Utente;
import utils.FunzioniUtils;

public class Main {
	
	private static Scanner input = new Scanner(System.in);
	private static Dao dao = new Dao();
	private static Utente utente = null;
	
	public static void main(String [] args) {
		
		
		
		int scelta = -1;
		System.out.println("Benvenuto in Twitter!");
		do {
			System.out.println("1. Accedi\n"
							+ "2. Registrati\n"
							+ "3. Esci da Twitter");
			try {
				scelta = Integer.parseInt(input.nextLine());
				if(scelta < 0 || scelta > 3) {
					System.out.println("Hai inserito un valore non valido, riprova.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Hai inserito un valore non valido, riprova.");
			}
			
		}while(scelta < 0 || scelta > 3);
		
		//Primo menù
		switch(scelta) {
		
		case 1: 
			accedi();
			break;
		
		case 2:
			registrati();
			break;
			
		case 3:
			return;
		
		}
		
		//Menù principale
		do {
			System.out.println("1. Cerca una persona\n"
							+ "2. Mostra profilo\n"
							+ "3. Mostra feed\n"
							+ "4. Metti Like\n"
							+ "5. Rimuovi Like"
							+ "0. Esci da Twitter");
			try {
				scelta = Integer.parseInt(input.nextLine());
				if(scelta < 0 || scelta > 5) {
					System.out.println("Hai inserito un valore non valido, riprova.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Hai inserito un valore non valido, riprova.");
			}
			
		}while(scelta < 0 || scelta > 3);
		
		
		switch(scelta) {
		//cerca utente
		case 1:
			System.out.println("Inserisci nome / cognome / nickname dell'utente da cercare: ");
			String daCercare = input.nextLine().replaceAll("\\s+", "");
			List<Utente> utentiNomiSimili = FunzioniUtils.trovaNomiSimili(daCercare, dao.cercaTuttiUtenti(), 0.65); //ricerca per nome
			List<Utente> utentiCognomiSimili = FunzioniUtils.trovaCognomiSimili(daCercare, dao.cercaTuttiUtenti(), 0.65); //ricerca per cognome con tolleranza del 65%
			List<Utente> utentiNicknamesSimili = FunzioniUtils.trovaNicknameSimili(daCercare, dao.cercaTuttiUtenti(), 0.65); //ricerca per cognome
			
			List<Utente> utentiAccorpati = FunzioniUtils.accorpaUtentiDaRicerca(utentiNomiSimili, utentiCognomiSimili, utentiNicknamesSimili);
			
			FunzioniUtils.stampaUtenti(utentiAccorpati);
			//Vuoi visualizzare il profilo di un utente specifico?
			//visualizza profilo utente selezionato
				//(Quindi stampa utente(nome, cognome, nickname, numero follower, numero seguiti))
				//Stampa tweets(nickname_utente, titolo, descrizione, numeroLikes, istante caricamento)
				//Vuoi mettere Like?
				//se no torna menu principale
			
			
			break;
		case 2:
			//Visualizza profilo utente
				//Stampa utente
				//Stampa numero follower
				//Stampa numero seguiti
				//Stampa i tweet in forma tabellare
				//Sotto menu in profilo utente
					//Visualizza follower
					//Visualizza seguiti
					//Torna al menu principale
						//Vuoi visualizzare un profilo specifico
						//riprendi da riga 88
						
			
			break;
		}
		
	}

	private static void registrati() {
		System.out.println("Inserisci il nome: ");
		String nome = input.nextLine().replaceAll("\\s+", "");//Rimuove tutti gli spazi contenuti nella string
		System.out.println("Inserisci il cognome: ");
		String cognome = input.nextLine().replaceAll("\\s+", "");
		
		System.out.println("Inserisci il nickname: ");
		String nickname = input.nextLine().replaceAll("\\s+", "").toLowerCase();
		while(FunzioniUtils.nicknameExists(nickname)) {
			System.out.println("Nickname già esistente, inseriscine un altro\nnickname: ");
			nickname = input.nextLine();
		}
		
		System.out.println("Inserisci l'email: ");
		String email = input.nextLine().replaceAll("\\s+", "").toLowerCase();
		while(!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$") || FunzioniUtils.emailExists(email)){ //email già esitente) 
			System.out.println("Email non validao già esistente, inseriscine una valida\nemail: ");
			email = input.nextLine();
		}
		
			
		System.out.println("Inserisci la password: ");
		String password = input.nextLine();
		
		utente = new Utente(nome, cognome, nickname, email, password);
		System.out.println("Registrazione completata con successo!\n"
				+ "Benvenuto in Twitter" + utente.getNome());
		
		
	}

	private static void accedi() {
		
		System.out.println("Puoi effettuare l'accesso sia per email che per nickname");
		System.out.println("Inserisci l'email o il nickname: ");
		String user = input.nextLine();
		System.out.println("Inserisci la password: ");
		String password = input.nextLine();
		
		if (dao.cercaUtentePerNickname(user) != null) {
			utente = dao.cercaUtentePerNickname(user);
			if(!utente.getPassword().equals(password)) {
				utente = null;
				System.out.println("Errore, le credenziali sono errate");
			}
			System.out.println("Accesso effettuato, bentornato " + utente.getNome());
			
		}
		
	}

}
