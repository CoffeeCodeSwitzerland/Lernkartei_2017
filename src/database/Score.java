package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import debug.Logger;


public class Score extends SQLiteConnector {

	// URL und Driver

	private static String	url		= "jdbc:sqlite:" + globals.Environment.getDatabasePath()
			+ globals.Globals.db_name + ".db";
	private static String	driver	= "org.sqlite.JDBC";

	private static Integer	anzahlLeben;
	private static Integer currentLifes;

	/**
	 * 
	 * Fragt den Score einer Kartei ab
	 * 
	 * @param Kartei
	 *            --> Welche Kartei, welche abgefragt werden soll
	 * @return --> Returned einen Double Wert des Scores, returned -1, wenn kein
	 *         Score vorhanden
	 */

	public static void correctCard () {

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			String sql = "CREATE TABLE IF NOT EXISTS Lifes " +
					"(PK_Lvs INTEGER PRIMARY KEY AUTOINCREMENT," +
					" Lifecount INTEGER DEFAULT 0);";

			debug.Debugger.out(sql);
			stmt.executeUpdate(sql);

			Integer currentLifes = 0;
			String getCurrent = "SELECT Lifecount FROM Lifes";

			c.setAutoCommit(false);
			ResultSet getCurt = stmt.executeQuery(getCurrent);
			c.setAutoCommit(true);

			if (getCurt.next()) {
				currentLifes = getCurt.getInt("Lifecount");
				getCurt.close();
			} else {
				String newEntry = "INSERT INTO Lifes (Lifecount) VALUES (0)";
				stmt.executeUpdate(newEntry);
			}
			
			getCurt.close();

			String updt = "UPDATE Lifes SET Lifecount = " + (currentLifes + 1);
			stmt.executeUpdate(updt);
		}
		catch (Exception e) {
			Logger.log("Database.correctCard(): " + e.getMessage());
		}
		closeDB();
	}

	public static int getLifecount () {

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			String sql = "CREATE TABLE IF NOT EXISTS Lifes " +
					"(PK_Lvs INTEGER PRIMARY KEY AUTOINCREMENT," +
					" Lifecount INTEGER DEFAULT 0);";

			debug.Debugger.out(sql);
			stmt.executeUpdate(sql);

			Integer currentLifes = 0;
			String getCurrent = "SELECT Lifecount FROM Lifes";

			c.setAutoCommit(false);
			ResultSet rs = stmt.executeQuery(getCurrent);
			c.setAutoCommit(true);
			
			if(rs.next()){
				currentLifes = rs.getInt("Lifecount");
			}
			float notRounded = currentLifes / 30;
			anzahlLeben = Math.round(notRounded);
		}
		catch (Exception e) {
			Logger.log("Database.getLifecount(): " + e.getMessage());
		}
		closeDB();
		return anzahlLeben;
	}

	public static void death () {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			String sql = "CREATE TABLE IF NOT EXISTS Lifes " +
					"(PK_Lvs INTEGER PRIMARY KEY AUTOINCREMENT," +
					" Lifecount INTEGER DEFAULT 0);";

			debug.Debugger.out(sql);
			stmt.executeUpdate(sql);

			Integer currentLifes = 0;

			String getCurrent = "SELECT Lifecount FROM Lifes";
			
			c.setAutoCommit(false);
			ResultSet rs = stmt.executeQuery(getCurrent);
			c.setAutoCommit(true);
			
			if(rs.next()){
				currentLifes = rs.getInt("Lifecount");
			}
			
			
			if (currentLifes >= 30) {
				String updt = "UPDATE Lifes SET Lifecount = " + (currentLifes - 30);
				stmt.executeUpdate(updt);
			}	
		}
		catch (Exception e) {
			Logger.log("Database.death(): " + e.getMessage());
		}
		closeDB();
	}
	
	public static int getCorrectCards () {

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			String sql = "CREATE TABLE IF NOT EXISTS Lifes " +
					"(PK_Lvs INTEGER PRIMARY KEY AUTOINCREMENT," +
					" Lifecount INTEGER DEFAULT 0);";

			debug.Debugger.out(sql);
			stmt.executeUpdate(sql);

			currentLifes = 0;
			String getCurrent = "SELECT Lifecount FROM Lifes";
			
			c.setAutoCommit(false);
			ResultSet rs = stmt.executeQuery(getCurrent);
			c.setAutoCommit(true);
			
			if(rs.next()){
				currentLifes = rs.getInt("Lifecount");
			}
		}
		catch (Exception e) {
			Logger.log("Database.getCorrectCards(): " + e.getMessage());
		}
		closeDB();
		return currentLifes;
	}
}
