package database;

import java.sql.*;
import java.util.ArrayList;


public class Database {

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

			String sql = "CREATE TABLE IF NOT EXISTS Stock " +
					"(PK_Stk INTEGER PRIMARY KEY AUTOINCREMENT," +
					" Frontside       TEXT    NOT NULL, " +
					" Backside      TEXT    NOT NULL, " +
					" Set_ID    		INTEGER NOT NULL, " +
					" Priority	    INTEGER DEFAULT 1," +
					" Description    TEXT    		, " +
					" Color			TEXT    		 )";

			debug.Debugger.out(sql);
			stmt.executeUpdate(sql);

			String setID;
			c.setAutoCommit(false);

			ResultSet selectSet = stmt.executeQuery("SELECT PK_Kategorie FROM Kategorie WHERE Kategorie = '"
					+ values[2] + "'");

			if (selectSet.next()) {
				setID = Integer.toString(selectSet.getInt("PK_Kategorie"));
				selectSet.close();
			}
			else {
				selectSet.close();
				stmt.close();
				c.close();
				return false;
			}

			c.setAutoCommit(true);

			String insert = "INSERT INTO Stock (Frontside, Backside, Set_ID, Priority, Color)" +
					"VALUES ('" + values[0] + "','" + values[1] + "'," + setID + ", " + values[3] + ", '"
					+ values[4] + "')";

			debug.Debugger.out(insert);
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

			String sql = "CREATE TABLE IF NOT EXISTS Stock " +
					"(PK_Stk INTEGER PRIMARY KEY AUTOINCREMENT," +
					" Frontside       TEXT    NOT NULL, " +
					" Backside      TEXT    NOT NULL, " +
					" Set_ID    		INTEGER NOT NULL, " +
					" Priority	    INTEGER DEFAULT 1," +
					" Description    TEXT    		, " +
					" Color			TEXT    		 )";

			debug.Debugger.out(sql);
			stmt.executeUpdate(sql);

			c.setAutoCommit(false);

			String IDwhichSet = "";
			ResultSet s = stmt.executeQuery("SELECT PK_Kategorie FROM Kategorie WHERE Kategorie = '" + whichSet + "'");

			if (s.next()) {
				IDwhichSet = Integer.toString(s.getInt("PK_Kategorie"));
			}
			else {
				debug.Debugger.out("No Kategorie: " + whichSet + "in Table Kategorie");
				stmt.close();
				c.close();
				return null;
			}

			s.close();

			ResultSet rs = stmt.executeQuery("SELECT * FROM Stock WHERE Set_ID = '" + IDwhichSet + "'");

			while (rs.next()) {

				String[] set = new String[7];
				set[0] = Integer.toString(rs.getInt("PK_Stk"));
				set[1] = rs.getString("Frontside");
				set[2] = rs.getString("Backside");
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

	/**
	 * Überschreibt Werte in der Datenbank um Karten zu editieren.
	 * 
	 * @param id
	 *            --> Welche Karte mit ID soll geändert werden
	 * @param frontside
	 *            --> Welcher Text als Vorderseite
	 * @param backside
	 *            --> Welcher Text als Rückseite
	 * @return --> True: Funktionierte, False: Nicht geklappt
	 */

	public static boolean editEntry (String id, String frontside, String backside) {

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			c.setAutoCommit(false);
			stmt = c.createStatement();

			String sel = "SELECT * FROM Stock WHERE PK_Stk = " + id;
			ResultSet rs = stmt.executeQuery(sel);

			if (!rs.next()) {
				rs.close();
				stmt.close();
				c.commit();
				return false;
			}
			else {
				rs.close();
			}

			c.setAutoCommit(true);
			String del = "UPDATE Stock SET Frontside = '" + frontside
					+ "', Backside = '" + backside
					+ "' WHERE PK_Stk = " + id;

			debug.Debugger.out(del);
			stmt.executeUpdate(del);

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
				debug.Debugger.out("No Card with this ID exists.");
				actualPrio.close();
			}

			// Wenn Aktuelle Priorität = 5, bleibt die neue bei 5, sonst wird
			// sie um 1 erhöht

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
