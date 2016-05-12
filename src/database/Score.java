package database;
//z
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import controls.Globals;


public class Score {

	// URL und Driver
	private static String	windowsUser	= debug.Environment.getUserName();
	private static String	url			= "jdbc:sqlite:" + windowsUser + ".db";
	private static String	driver		= "org.sqlite.JDBC";

	/**
	 * 
	 * Fragt den Score einer Kartei ab
	 * 
	 * @param Kartei
	 *            --> Welche Kartei, welche abgefragt werden soll
	 * @return --> Returned einen Double Wert des Scores, returned -1, wenn kein
	 *         Score vorhanden
	 */
	
	public Score() 
	{
		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			String sql = "CREATE TABLE IF NOT EXISTS Score 	(PK_Score INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "Kartei TEXT NOT NULL,"
					+ "Score REAL NOT NULL);";

			debug.Debugger.out(sql);
			stmt.executeUpdate(sql);
			
			System.out.println("Tabelle Score erstellen");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

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

			debug.Debugger.out(sql);
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

			String sql = "INSERT INTO Score (Kartei, Score) VALUES ('" + Kartei + "'," + Score + ")";

			debug.Debugger.out(sql);
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
	 * @param Kartei
	 *            --> Welche Kartei
	 * @param newScore
	 *            --> Neuer Score
	 * @return --> Returned true wenns funktioniert hat, sonst false, wenns kein
	 *         Eintrag hatte
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

			debug.Debugger.out(sql);
			stmt.executeUpdate(sql);
			c.setAutoCommit(false);
			ResultSet rs = stmt.executeQuery("SELECT Score FROM Score WHERE Kartei = '" + Kartei + "';");
			if (!rs.next()) {

				debug.Debugger.out("No Entry with that name in our Database.");
				return false;

			}
			rs.close();
			c.setAutoCommit(true);

			String updt = "UPDATE Score SET Score = '" + newScore + "' WHERE Kartei = " + Kartei;
			debug.Debugger.out(updt);
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
	//getScores gibt alle Pnuktzahlen zu den Kartien zurück. In der ArrayList sind Stringarrays gelistet, welche wie folgt aufgebaut sind:
	// | 0 KarteiName | 1 Punktzahl | (!Achtung! -> Die Punktzahl wird als String übergeben -> Sie sollte am Schluss ein Double sein)
	//Später wenn Online-implementierung dazukommt, kann man als Parameter den Username angeben um die richtige Funktionalität dieser Funktion sicher zu stellen
	//Wenn ein Fehler autritt, wird in catch ein Array in die Liste gespeichert, in welchem zwei Fehler stehen. Im 1. Eintrag eine Message für Entwickler 
	//und im 2. füe den Benutzer, welcher einfacher lesbar ist, da der User nicht weiss was er mit der 1. Anfangen soll.
	
	private static ArrayList<String> allScores = new ArrayList<String>();

	public ArrayList<String> getScores () {

		allScores.clear();

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			
			String all = "SELECT Kartei, Score FROM Score";
			ResultSet rs = stmt.executeQuery(all);

			while (rs.next()) {
				String Kartei = rs.getString(1);
				String Score = Double.toString(rs.getDouble(2));
				// Array und in liste schreiben
				allScores.add(genString(Kartei, Score));
			}

		}
		catch (Exception e) {
			String Error = e.getMessage();
			String allgemeinerFehler = "Es tut uns leid aber wir konnten ihre Karteien nicht finden";

			allScores.add(genString(Error, allgemeinerFehler));
		}

		return allScores;
	}
	
	public void generateTestdata(String Insert) {
		
		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			//TestData in die Score Tabelle einschreiben
			stmt.executeUpdate(Insert);
			System.out.println(Insert);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void dropTestData(String Drop) {
		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			//Tabelle löschen
			stmt.executeUpdate(Drop);
			System.out.println(Drop);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static String genString(String Kartei, String Score)
	{
		return Kartei + Globals.SEPARATOR + Score;
	}

}
