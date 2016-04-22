package sqlite;

import java.sql.*;
import java.util.ArrayList;

public class Database
{
	private String DB_Name = "Kartei";
	
	/**
	 *  Keine neue Instanz Database erstellen, sondern nur die Methode benutzen
	 *  @param values --> Array mit 3 Werten: 1. Vorderseite, 2. Rückseite, 3. Beschreibung
	 */
	
	public void pushToStock (String[] values) {
		
		Connection c = null;
		Statement stmt = null;
		
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:" + DB_Name);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      String sql = "CREATE IF NOT EXISTS TABLE Stock " +
	                   "(PK_Stk INT PRIMARY KEY AUTOINCREMENT," +
	                   " Backside       TEXT    NOT NULL, " + 
	                   " Frontside      TEXT    NOT NULL, " + 
	                   " Description        TEXT)"; 
	      
	      System.out.println(sql);
	      stmt.executeUpdate(sql);
	      System.out.println("Successful");
	      
	      String insert = 	"INSERT INTO Stock (Backside, Frontside, Description)" + 
	    		  		  	"VALUES ('" + values[0] + "','" + values[1] + "','" + values[2] + "')";	
	      
	      System.out.println(insert);
	      stmt.executeUpdate(insert);
	      System.out.println("Successful");
	      
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
		
	}
	
	/**
	 *  Keine neue Instanz Database erstellen, sondern nur die Methode benutzen
	 *  @return Retourniert ein Array mit 4 Werten: PK, Vorder-, Rückseite, Beschreibung
	 */
	
	public ArrayList<String[]> pullFromStock () {
		
		ArrayList<String[]> results = new ArrayList<String[]>();
		Connection c = null;
	    Statement stmt = null;
	    
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:" + DB_Name);
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery("SELECT * FROM Stock;");
	      
	    while ( rs.next() ) {
	         
	        String[] set = new String[4];
	        set[0] = Integer.toString(rs.getInt("PK_Stk"));
	        set[1] = rs.getString("Frontside");
	        set[2] = rs.getString("Backside");
	        set[3] = rs.getString("Description");
	        
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
	
}
