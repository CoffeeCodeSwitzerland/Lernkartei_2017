package database;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JTextField;

import kartenpackage.EditCard;

public class Database
{
	private static String DB_Name = "Kartei";
	public static ResultSet rs;
	public static String insert;
	
	/**
	 *  Keine neue Instanz Database erstellen, sondern nur die Methode benutzen
	 *  @param values --> Array mit 3 Werten: 1. Vorderseite, 2. Rückseite, 3. Beschreibung
	 */
	
	public static void pushToStock (String[] values) {
		
		Connection c = null;
		Statement stmt = null;
		
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:" + DB_Name);

	      stmt = c.createStatement();
	      String sql = "CREATE TABLE IF NOT EXISTS Stock " +
	                   "(PK_Stk INTEGER PRIMARY KEY AUTOINCREMENT," +
	                   " Backside       TEXT    NOT NULL, " + 
	                   " Frontside      TEXT    NOT NULL, " + 
	                   " Description    TEXT    NOT NULL, " + 
	                   " Color			TEXT    NOT NULL  )"; 
	      
	      System.out.println(sql);
	      stmt.executeUpdate(sql);
	      
	      insert = 	"INSERT INTO Stock (Backside, Frontside, Description, Color)" + 
	    		  		  	"VALUES ('" + values[0] + "','" + values[1] + "','" + values[2] + "', '" + values[3] + "')";	
	      
	      System.out.println(insert);
	      stmt.executeUpdate(insert);
	      
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
		
	}
	
	/**
	 *  Keine neue Instanz Database erstellen, sondern nur die Methode benutzen
	 *  @return Retourniert eine ArrayList mit Arrays mit 4 Werten: PK, Vorder-, Rückseite, Beschreibung
	 */
	
	public static ArrayList<String[]> pullFromStock () {
		
		ArrayList<String[]> results = new ArrayList<String[]>();
		Connection c = null;
	    Statement stmt = null;
	    
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:" + DB_Name);
	      c.setAutoCommit(false);

	      stmt = c.createStatement();
	      rs = stmt.executeQuery("SELECT * FROM Stock;");
	      
	    while ( rs.next() ) {
	         
	        String[] set = new String[5];
	        set[0] = Integer.toString(rs.getInt("PK_Stk"));
	        set[1] = rs.getString("Backside");
	        set[2] = rs.getString("Frontside");
	        set[3] = rs.getString("Description");
	        set[4] = rs.getString("Color");
	        
	        results.add(set);          
	    }
	    
	      rs.close();
	      stmt.close();
	      c.close();
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    
	    return results;
		
	}
	
	public static void delEntry (String id) {
		
		Connection c = null;
	    Statement stmt = null;
	    
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:" + DB_Name);
	      stmt = c.createStatement();
	      
	      String del = "DELETE FROM Stock WHERE PK_Stk = " + id;
	      
	      stmt.executeUpdate(del);
	      stmt.close();
	      c.close();
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
		
	}
	
	public static ArrayList<String> getEdited () {
		
		ArrayList<String> swalues = new ArrayList<String>();
		
		for (JTextField s : EditCard.addJFs) {			
			swalues.add(s.getText());		
		}
		
		return swalues;
		
	}
	
	public static void delStock () {
		
		Connection c = null;
	    Statement stmt = null;
	    
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:" + DB_Name);
	      stmt = c.createStatement();
	      
	      String del = "DROP TABLE Stock";
	      
	      stmt.executeUpdate(del);
	      stmt.close();
	      c.close();
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
		
	}
	
}
