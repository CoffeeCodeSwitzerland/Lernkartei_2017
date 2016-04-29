package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Score {
	
	// URL und Driver

	private static String windowsUser = System.getProperty("user.name");
	private static String	url		= "jdbc:sqlite:" + windowsUser + ".db";
	private static String	driver	= "org.sqlite.JDBC";
	
	/**
	 * 
	 * Fragt den Score einer Kartei ab
	 * 
	 * @param Kartei 	--> Welche Kartei, welche abgefragt werden soll
	 * @return			--> Returned einen Double Wert des Scores, returned -1, wenn kein Score vorhanden
	 */
	
	public static double getScore (String Kartei) {
		
		Connection c = null;
		Statement stmt = null;
		double score = 0;
		
		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			String sql = "CREATE TABLE IF NOT EXISTS Score 	(PK_Score INTEGER PRIMARY KEY AUTOINCREMENT,"
															+ "Kartei TEXT NOT NULL,"
															+ "Score REAL NOT NULL);";

			System.out.println(sql);
			stmt.executeUpdate(sql);
			
			c.setAutoCommit(false);
			ResultSet rs = stmt.executeQuery("SELECT Score FROM Score WHERE Kartei = '" + Kartei + "';");
			if (!rs.next()) {
				
				return -1;
				
			}
			
			rs.close();
			stmt.close();
			c.close();
			score = rs.getDouble("Score");		

		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		return score;
		
	}
	
	public static void addScore (String Kartei, double Score) {
		
		Connection c = null;
		Statement stmt = null;
		
		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			
			String sql = "INSERT INTO Score (Kartei, Score) VALUES ('" + Kartei + "'," + Score +")";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
	}
	
	/**
	 * 
	 * Sie können den Score einer bestimmten Kartei updaten
	 * 
	 * @param Kartei	--> Welche Kartei
	 * @param newScore	--> Neuer Score
	 * @return			--> Returned true wenns funktioniert hat, sonst false, wenns kein Eintrag hatte
	 */
	
	public static boolean updateScore (String Kartei, double newScore) {
		
		Connection c = null;
		Statement stmt = null;
		
		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			String sql = "CREATE TABLE IF NOT EXISTS Score 	(PK_Score INTEGER PRIMARY KEY AUTOINCREMENT,"
															+ "Kartei TEXT NOT NULL,"
															+ "Score REAL NOT NULL);";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);			
			c.setAutoCommit(false);
			ResultSet rs = stmt.executeQuery("SELECT Score FROM Score WHERE Kartei = '" + Kartei + "';");
			if (!rs.next()) {
				
				System.out.println("No Entry with that name in our Database.");
				return false;
				
			}
			rs.close();
			c.setAutoCommit(true);
			
			String updt = "UPDATE Score SET Score = '" + newScore + "' WHERE Kartei = " + Kartei;
			System.out.println(updt);
			stmt.executeUpdate(updt);
			
			stmt.close();
			c.close();
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		return true;
		
	}
	
}
