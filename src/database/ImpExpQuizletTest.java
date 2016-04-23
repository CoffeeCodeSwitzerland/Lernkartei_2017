package database;
import quizlet.*;
import sqlite.Attributes;
import sqlite.Table;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;


public class ImpExpQuizletTest {
	
	// ---------
	// Test Main
	// ---------
	
	// Statische Variabeln
	
	static final Quizlet q = new Quizlet("3RhaPk5H9C");
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) throws MalformedURLException, IOException {
		
		// Variabeldeklaration
		
		String[] set;
		ArrayList<String> terms = null;
		ImpExpQuizlet ie = new ImpExpQuizlet();

		// Tabelle erstellen

		Table newTable = new Table("Quizlet");
		Attributes attr = new Attributes("QuizletID", "INTEGER", false);
		Attributes attr2 = new Attributes("Term", "TEXT", false);
		Attributes attr3 = new Attributes("Definition", "TEXT", false);
		newTable.addAttrs(attr);
		newTable.addAttrs(attr2);
		newTable.addAttrs(attr3);
		newTable.generate();
		System.out.println("GENERATED");
		
		// Scannt Benutzereingaben für Suche
		
		System.out.println("Geben sie einen Suchbegriff ein:");
		String search = scan.nextLine();
		ArrayList<String> result = q.searchSet(search);

		for (String s : result) {
			System.out.println(s);
		}
		
		// Nächste Eingabe, Auswahl Quizlet Set

		System.out.println("Choose a set!");

		String temp = scan.nextLine();

		try {
			int setNr = Integer.parseInt(temp);

			set = result.get(setNr).split(q.separator);

			terms = q.getSet(set[0]);

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
