package database;
import java.sql.*;
import java.util.ArrayList;

import sqlite.Table;
public class ImpExpQuizlet {
	
	// Name der Datenbank
	String DB_Name = "test";
		
	// Name des Treibers
	String driver = "org.sqlite.JDBC";
	
	// Private Variabeln
	
	private Connection c = null;
	private Statement stmt = null;
	private String values = "";
	private String sql = "";
	private String exportSeparator = ":::";
	private String infos = "";
	private ArrayList<String> exportTerms = new ArrayList<String>();
	
	/**
	 * Methode, welche Quizlet Daten in die Tabelle schreibt
	 *  
	 * @param newTable --> Tabelle, in welche Quizlet importiert werden soll
	 * @param terms --> ArrayListe mit String (QuizletID:::Vorderseite:::Rückseite)
	 *
	 */
	
	public void ImportQuizlet(Table newTable, ArrayList<String> terms) {
		
		// Abfrage ob Bilder enthalten sind
		
		if (terms.get(0).split(":::")[4].equals("true")) {

			System.out.println("Pictures not supported.");

		} else {
			
			// Erste Zeile der Liste mit allg. Informationen
			
			infos += newTable.getName();
			
			// Einfügen in Tabelle Quizlet mit INSERT
			
			try {
				Class.forName(driver);
				c = DriverManager.getConnection("jdbc:sqlite:" + DB_Name + ".db");
				c.setAutoCommit(false);
				stmt = c.createStatement();
				
				// For Schleife, welche durch die Liste loopt, und sie in die DB schreibt
				
				for (int j = 1; j < terms.size(); j++)

				{
					values = "";

					for (int i = 0; i < terms.size(); i++) {

						values += "'" + terms.get(j).split(":::")[i] + "'";

						if (i != 2) {

							values += ",";

						}

					}

					sql = "INSERT INTO " + newTable.getName() + "(" + newTable.Attrs.get(0).getName() + "," 
					+ newTable.Attrs.get(1).getName() + "," 
					+ newTable.Attrs.get(2).getName() + ")" 
					+ "VALUES (" + values + ");";
					
					System.out.println(sql);
					
					stmt.executeUpdate(sql);

				}

				stmt.close();
				c.commit();
				c.close();
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
		}
		
	}
	
	/**
	 * Exportiert Tabelleninhalten im selben Format wie QuizletImport
	 *  	
	 * @param table --> Tabelle welche exportiert werden soll
	 * @return --> retourniert eine ArrayList mit Separator exportSeparator
	 * 
	 */
	
	// Methode, welche Tabelle für Quizlet exportiert
	
	public ArrayList<String> ExportQuizlet(Table table) {
		
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName(driver);
	      c = DriverManager.getConnection("jdbc:sqlite:" + DB_Name + ".db");
	      c.setAutoCommit(false); 

	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM " + table.getName() + ";" );
	      
	      exportTerms.add(infos);
	      
	      while ( rs.next() ) {
	    	  
	    	  Integer ins1 = rs.getInt("QuizletID");
	    	  String term = rs.getString("Term");
	    	  String def = rs.getString("Definition");
	    	  
	    	  String e = ins1 + exportSeparator + term + exportSeparator + def;
	    	  
	    	  exportTerms.add(e);
	    	  
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    
	    return exportTerms;
		
	}
	
}
