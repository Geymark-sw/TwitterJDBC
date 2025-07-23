package Main;

import java.util.Scanner;

import dao.Dao;
import models.Utente;

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
							+ "3. Esci");
			try {
				scelta = Integer.parseInt(input.nextLine());
				if(scelta < 0 || scelta > 3) {
					System.out.println("Hai inserito un valore non valido, riprova.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Hai inserito un valore non valido, riprova.");
			}
			
		}while(scelta < 0 || scelta > 3);
		
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
		
	}

	private static void registrati() {
		System.out.println("Inserisci il nome: ");
		String nome = input.nextLine();
		System.out.println("Inserisci il cognome: ");
		String cognome = input.nextLine();
		System.out.println("Inserisci il nickname: ");
		String nickname = input.nextLine();
		System.out.println("Inserisci l'email: ");
		
		String email = input.nextLine();
		while(!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$") || //email già esitente) {
			System.out.println("Email non valida, inseriscine una valida\nemail: ");
			email = input.nextLine();
		}
			
		System.out.println();
		
		
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
