package database;

import Quizlet.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;


public class ImpExpQuizletTest {
	
	// Statische Variabeln
	
	static final Quizlet q = new Quizlet("3RhaPk5H9C");
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) throws MalformedURLException, IOException {
		
		// Variabeldeklaration
		
		String[] set;
		ArrayList<String> terms = null;
		ImpExpQuizlet ie = new ImpExpQuizlet();

		// Tabelle erstellen

		ImpTable newTable = new ImpTable("Quizlet");
		ImpAttributes attr = new ImpAttributes("QuizletID", "INTEGER", false);
		ImpAttributes attr2 = new ImpAttributes("Term", "TEXT", false);
		ImpAttributes attr3 = new ImpAttributes("Definition", "TEXT", false);
		newTable.addAttrs(attr);
		newTable.addAttrs(attr2);
		newTable.addAttrs(attr3);
		newTable.generate();
		System.out.println("GENERATED");
		
		// Scannt Benutzereingaben für Suche
		
		System.out.println("Geben sie einen Suchbegriff ein:");
		String search = scan.nextLine();
		ArrayList<String> result = q.SearchSet(search);

		for (String s : result) {
			System.out.println(s);
		}
		
		// Nächste Eingabe, Auswahl Quizlet Set

		System.out.println("Choose a set!");

		String temp = scan.nextLine();

		try {
			int setNr = Integer.parseInt(temp);

			set = result.get(setNr).split(q.separator);

			terms = q.GetSet(set[0]);

			for (String s : terms) {
				System.out.println(s);
			}
			
		} catch (Exception e) {
			
			System.out.println("Failed... why? Fuck u, that's why!");
			
		}
		
		// Import Quizlet set
		
		ie.ImportQuizlet(newTable, terms);
		System.out.println("Import successfull!");
		
		// Export in eine ArrayList mit Separator ":::"
		
		System.out.println("\nAusgabe ihrer Liste\n");
		
		for (String s : ie.ExportQuizlet(newTable)) {
			
			System.out.println(s);
			
		}
		
	}
}
