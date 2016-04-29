package database;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JTextField;


public class Database {
	public static ResultSet	rs;
	public static String	insert;

	// Varibeln Connection

	private static String	windowsUser	= debug.Environment.getUserName();
	private static String	url			= "jdbc:sqlite:" + windowsUser + ".db";
	private static String	driver		= "org.sqlite.JDBC";

	/**
	 * Keine neue Instanz Database erstellen, sondern nur die Methode benutzen
	 * 
	 * @param values
	 *            --> Array mit 5 Werten: 1. Vorderseite, 2. Rückseite, 3.
	 *            Set_ID, 4. Priorität (1-5), 5. Color
	 */

	public static boolean pushToStock (String[] values) {

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			String setID;
			c.setAutoCommit(false);
			
			ResultSet selectSet = stmt.executeQuery("SELECT PK_Kategorie FROM Kategorie WHERE Kategorie = '" 
													+ values[2] + "'");
			
			if (selectSet.next()) {
				setID = Integer.toString(selectSet.getInt("PK_Kategorie"));
			} else {
				return false;
			}
			
			c.setAutoCommit(true);

			String sql = "CREATE TABLE IF NOT EXISTS Stock " +
					"(PK_Stk INTEGER PRIMARY KEY AUTOINCREMENT," +
					" Backside       TEXT    NOT NULL, " +
					" Frontside      TEXT    NOT NULL, " +
					" Set_ID    		INTEGER NOT NULL, " +
					" Priority	    INTEGER DEFAULT 1," +
					" Description    TEXT    		, " +
					" Color			TEXT    		 )";

			System.out.println(sql);
			stmt.executeUpdate(sql);

			insert = "INSERT INTO Stock (Backside, Frontside, Set_ID, Priority, Color)" +
					"VALUES ('" + values[0] + "','" + values[1] + "'," + setID + ", " + values[3] + ", '"
					+ values[4] + "')";

			System.out.println(insert);
			stmt.executeUpdate(insert);

			stmt.close();
			c.close();
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		return true;

	}

	/**
	 * Keine neue Instanz Database erstellen, sondern nur die Methode benutzen
	 * 
	 * @return Retourniert eine ArrayList mit Arrays mit 7 Werten: PK, Vorder-,
	 *         Rückseite, Description, Set_ID, Priorität, Farbe
	 */

	public static ArrayList<String[]> pullFromStock (String whichSet) {

		ArrayList<String[]> results = new ArrayList<String[]>();
		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			c.setAutoCommit(false);
			
			ResultSet tbl = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Stock'");
			
			if (!tbl.next()) {
				tbl.close();
				return null;
			} else {
				tbl.close();
			}
			
			String IDwhichSet = "";
			ResultSet s = stmt.executeQuery("SELECT PK_Kategorie FROM Kategorie WHERE Kategorie = '" + whichSet + "'");
			
			if (s.next()) {
				IDwhichSet = Integer.toString(s.getInt("PK_Kategorie"));
			} else {
				return null;
			}
			
			s.close();
			
			rs = stmt.executeQuery("SELECT * FROM Stock WHERE Set_ID = '" + IDwhichSet + "'");

			while (rs.next()) {

				String[] set = new String[7];
				set[0] = Integer.toString(rs.getInt("PK_Stk"));
				set[1] = rs.getString("Backside");
				set[2] = rs.getString("Frontside");
				set[3] = rs.getString("Description");
				set[4] = Integer.toString(rs.getInt("Set_ID"));
				set[5] = Integer.toString(rs.getInt("Priority"));
				set[6] = rs.getString("Color");
				results.add(set);

			}

			rs.close();
			stmt.close();
			c.close();

		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return results;

	}

	public static boolean delEntry (String id) {

		Connection c = null;
		Statement stmt = null;
		boolean deleted = false;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			
			String del = "DELETE FROM Stock WHERE PK_Stk = " + id;
			stmt.executeUpdate(del);
			deleted = true;			
			
			stmt.close();
			c.close();

		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		return deleted;

	}

	public static void delStock () {

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			String del = "DROP TABLE IF EXISTS Stock";

			stmt.executeUpdate(del);
			stmt.close();
			c.close();

		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

	/**
	 * Erhöht die Priorität um 1, Legt die Karte nach hinten, bei 5, bleibt sie
	 * 
	 * @param PK_ID
	 *            --> PK_Stock ID der Karte, welche erhöht wird
	 */

	public static void upPrio (Integer PK_ID) {

		Connection c = null;
		Statement stmt = null;
		String oldPrio = "";
		String newPrio = "";

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			c.setAutoCommit(false);
			
			// Frage die Aktuelle Priorität ab
			
			ResultSet actualPrio = stmt.executeQuery("SELECT Priority FROM Stock WHERE PK_Stk = " + PK_ID.toString());
			
			// Überprüft ob vorhanden oder nicht
			
			if (actualPrio.next()) {
				oldPrio = Integer.toString(actualPrio.getInt("Priority"));
				actualPrio.close();
			}
			else {
				System.out.println("No Card with this ID exists.");
				actualPrio.close();
			}
			
			// Wenn Aktuelle Priorität = 5, bleibt die neue bei 5, sonst wird sie um 1 erhöht
			
			if (oldPrio.equals("5")) {
				newPrio = "5";
			}
			else {
				newPrio = oldPrio + 1;
			}
			
			// Schreibt die Neue Priorität in die Datenbank

			c.setAutoCommit(true);
			String updatePrio = "UPDATE Stock SET Priority = " + newPrio + " WHERE PK_Stk = " + PK_ID;
			stmt.executeUpdate(updatePrio);
			
			stmt.close();
			c.close();

		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

	/**
	 * Bei nicht wissen der Karte, wird die Prio zurückgesetzt --> Sprich auf 0
	 * gesetzt
	 * 
	 * @param karte
	 *            --> Welche Karte reseted wird
	 */

	public static void resetPrio (Integer PK_ID) {
		
		Connection c = null;
		Statement stmt = null;
		String oldPrio = "";
		String newPrio = "";

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			
			// Setzt die Priorität zurück auf 1
			
			String updatePrio = "UPDATE Stock SET Priority = 1 WHERE PK_Stk = " + PK_ID;
			stmt.executeUpdate(updatePrio);
			
			stmt.close();
			c.close();

		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}		
		
	}

}
