package database;

import java.sql.*;
import java.util.ArrayList;

import debug.Logger;

public class Database {

	// Connection Info.:
	private static String dbURL = Config.getUrlbase() + globals.Globals.db_name + ".db";

	public static String getDbURL() {
		return dbURL;
	}

	public static Connection getConnection() {
		try {
			Class.forName(Config.getDriver());
			return DriverManager.getConnection(dbURL);
		} catch (Exception e) {
			Logger.log("Database.getConnection(): "+e.getMessage());
		}
		return null;
	}


	private static boolean createStockIfNotExists (Statement stmt) {
		String sqlUpdate = 
				"CREATE TABLE IF NOT EXISTS Stock " +
				"(PK_Stk INTEGER PRIMARY KEY AUTOINCREMENT," +
				" Frontside       TEXT    NOT NULL, " +
				" Backside      TEXT    NOT NULL, " +
				" Set_ID    		INTEGER NOT NULL, " +
				" Priority	    INTEGER DEFAULT 1," +
				" Description    TEXT    		, " +
				" Datum			TEXT    		 )";
		try {
			stmt.executeUpdate(sqlUpdate);
			debug.Debugger.out(sqlUpdate);
		} catch (Exception e) {
			Logger.log("Database.createStockIfNotExists(): "+e.getMessage());
			return false;
		}
		return true;
	}

	private static ResultSet seekCategories (Statement stmt, String attribute, String value) {
		String sqlQuery = "SELECT "+attribute+" FROM Kategorie WHERE Kategorie = '"
				+ value + "'";
		debug.Debugger.out(sqlQuery);
		try {
			return stmt.executeQuery(sqlQuery);
		} catch (Exception e) {
			Logger.log("Database.seekCategories(...): "+e.getMessage());
		}
		return null;
	}
	
	/**
	 * Keine neue Instanz Database erstellen, sondern nur die Methode benutzen
	 * 
	 * @param values
	 *            --> Array mit 5 Werten: 1. Vorderseite, 2. Rückseite, 3.
	 *            Set_ID, 4. Priorität (1-5), 5. Datum
	 */

	public static boolean pushToStock (String[] values) {

		Connection c = Database.getConnection();
		try {
			Statement stmt = c.createStatement();
			createStockIfNotExists (stmt);
			c.setAutoCommit(false);
			String attribute = "PK_Kategorie";
			ResultSet selectSet = seekCategories (stmt, attribute, values[2]);
			
			String setID;
			if (selectSet.next()) {
				setID = Integer.toString(selectSet.getInt(attribute));
				selectSet.close();
			}
			else {
				selectSet.close();
				stmt.close();
				c.close();
				return false;
			}
			c.setAutoCommit(true);
			String insertCMD = "INSERT INTO Stock (Frontside, Backside, Set_ID, Priority, Datum)";
			String insert = insertCMD +
					"VALUES ('" + values[0] + "','" + values[1] + "'," + setID + ", " + values[3] + ", '"
					+ values[4] + "')";

			debug.Debugger.out(insert);
			stmt.executeUpdate(insert);
			
			stmt.close();
			c.close();
			return true;
		} catch (Exception e) {
			Logger.log("Database.pushToStock(...): "+e.getMessage());
		}
		return true; // TODO evtl. false? ... auch weiter unten ein Problem
	}

	/**
	 * Keine neue Instanz Database erstellen, sondern nur die Methode benutzen
	 * 
	 * @return Retourniert eine ArrayList mit Arrays mit 7 Werten: PK, Vorder-,
	 *         Rückseite, Description, Set_ID, Priorität, Farbe
	 */

	public static ArrayList<String[]> pullFromStock (String whichSet) {

		ArrayList<String[]> results = new ArrayList<String[]>();

		Connection c = Database.getConnection();
		try {
			Statement stmt = c.createStatement();
			createStockIfNotExists (stmt);
			c.setAutoCommit(false);

			String IDwhichSet = "";
			String attribute = "PK_Kategorie";
			ResultSet selectSet = seekCategories (stmt, attribute, whichSet);


			if (selectSet.next()) {
				IDwhichSet = Integer.toString(selectSet.getInt(attribute));
			}
			else {
				debug.Debugger.out("No Kategorie: " + whichSet + "in Table Kategorie");
				stmt.close();
				c.close();
				return null;
			}

			selectSet.close();

			ResultSet rs = stmt.executeQuery("SELECT * FROM Stock WHERE Set_ID = '" + IDwhichSet + "'");

			while (rs.next()) {

				String[] set = new String[7];
				set[0] = Integer.toString(rs.getInt("PK_Stk"));
				set[1] = rs.getString("Frontside");
				set[2] = rs.getString("Backside");
				set[3] = rs.getString("Description");
				set[4] = Integer.toString(rs.getInt("Set_ID"));
				set[5] = Integer.toString(rs.getInt("Priority"));
				set[6] = rs.getString("Datum");
				results.add(set);

			}

			rs.close();
			stmt.close();
			c.close();

		}
		catch (Exception e) {
			debug.Debugger.out(e.getMessage());
		}

		return results;

	}

	public static boolean delEntry (String id) {

		Connection c = Database.getConnection();
		boolean deleted = false;

		try {
			c = DriverManager.getConnection(dbURL);
			Statement stmt = c.createStatement();

			String del = "DELETE FROM Stock WHERE PK_Stk = " + id;
			stmt.executeUpdate(del);
			deleted = true;

			stmt.close();
			c.close();

		}
		catch (Exception e) {
			debug.Debugger.out(e.getMessage());
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

		Connection c = Database.getConnection();

		try {
			c = DriverManager.getConnection(dbURL);
			c.setAutoCommit(false);
			Statement stmt = c.createStatement();

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
			debug.Debugger.out(e.getMessage());
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

		Connection c = Database.getConnection();
		Integer oldPrio = null;
		String newPrio = "";

		try {
			c = DriverManager.getConnection(dbURL);
			Statement stmt = c.createStatement();
			c.setAutoCommit(false);

			// Frage die Aktuelle Priorität ab

			ResultSet actualPrio = stmt.executeQuery("SELECT Priority FROM Stock WHERE PK_Stk = " + PK_ID.toString());

			// Überprüft ob vorhanden oder nicht

			if (actualPrio.next()) {
				oldPrio = actualPrio.getInt("Priority");
				actualPrio.close();
			}
			else {
				debug.Debugger.out("No Card with this ID exists.");
				actualPrio.close();
			}

			// Wenn Aktuelle Priorität = 5, bleibt die neue bei 5, sonst wird
			// sie um 1 erhöht

			if (oldPrio == 5) {
				newPrio = "5";
			}
			else {
				newPrio = Integer.toString(oldPrio + 1);
			}

			// Schreibt die Neue Priorität in die Datenbank

			c.setAutoCommit(true);
			String updatePrio = "UPDATE Stock SET Priority = " + newPrio + " WHERE PK_Stk = " + PK_ID;
			stmt.executeUpdate(updatePrio);

			stmt.close();
			c.close();

		}
		catch (Exception e) {
			debug.Debugger.out(e.getMessage());
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

		Connection c = Database.getConnection();

		try {
			c = DriverManager.getConnection(dbURL);
			Statement stmt = c.createStatement();

			// Setzt die Priorität zurück auf 1

			String updatePrio = "UPDATE Stock SET Priority = 1 WHERE PK_Stk = " + PK_ID;
			stmt.executeUpdate(updatePrio);

			stmt.close();
			c.close();

		}
		
		catch (Exception e) {
			debug.Debugger.out(e.getMessage());
		}

	}
	
	/**
	 * Liefert die Priorität der Karte mit mitgegebener ID mit 
	 * 
	 * @param ID_Card --> ID der Karte, von welcher die Priorität gebraaucht wird
	 * @return --> Gibt die Kartenpriorität als Integer zurück
	 */
	
	public static int getPriority (String ID_Card) {
		
		Connection c = Database.getConnection();
		int prio = 0;

		try {
			c = DriverManager.getConnection(dbURL);
			Statement stmt = c.createStatement();
			c.setAutoCommit(false);

			String getPrio = "SELECT Priority FROM Stock WHERE PK_Stk = " + ID_Card;
			ResultSet rsPrio = stmt.executeQuery(getPrio);
			
			if (rsPrio.next()) {
				prio = rsPrio.getInt("Priority");
			} else {
				debug.Debugger.out("No such Card exists!");
			}
			
			stmt.close();
			c.close();

		}
		catch (Exception e) {
			debug.Debugger.out(e.getMessage());
		}
		
		return prio;
		
	}
	
	/**
	 * Liefert den Maximalen und den bisher erreichten Score eines Stacks zurück
	 *  
	 * @param whichSet --> Score von welchem Stack geliefert werden soll
	 * @return --> Retourniert diesen gewünschten Score
	 */
	
	public static Double[] getScore (String whichSet) {

		Connection c = Database.getConnection();

		Double maxPoints = 0.0;
		Double reachedPoints = 0.0;
		Double[] score = new Double[2];

		try {
			c = DriverManager.getConnection(dbURL);
			Statement stmt = c.createStatement();
			c.setAutoCommit(false);

			// Alle Prioritäten aus Tabelle hlen, welche als Set das mitgegebene
			// haben.

			String getScore = "SELECT Priority FROM Stock WHERE Set_ID = (SELECT PK_Kategorie FROM Kategorie"
					+ " WHERE Kategorie = '" + whichSet + "')";

			ResultSet scrs = stmt.executeQuery(getScore);

			// Durch loopen und die Maximale sowie die Erreichte Punktzahl
			// speichern

			if (scrs.next()) {
				maxPoints += 4.0;
				reachedPoints += scrs.getInt("Priority") - 1.0;
				while (scrs.next()) {
					maxPoints += 4.0;
					reachedPoints += scrs.getInt("Priority") - 1.0;
				}

			} else {
				
				return null;
				
			}

			stmt.close();
			c.close();

		}
		catch (Exception e) {
			debug.Debugger.out(e.getMessage());
		}

		// Erreichte Punktzahl zurückgeben
		
		score[0] = maxPoints;
		score[1] = reachedPoints;

		return score;

	}

}
