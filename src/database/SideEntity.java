package database;

import java.sql.ResultSet;
import java.util.ArrayList;

import debug.Logger;

public class SideEntity extends SQLiteConnector {

	protected static String myTableName  =  "Stock";
	private   static String myPrimaryKey = "PK_Stk";
	protected static String myFKName     = "Set_ID";	
	private   static String mySeekAttribute = "Priority";
//	private   static String myAttributeList = myFKName+", Frontside, Backside, Priority, Datum";
//	private   static String myAttributes = 
//				myPrimaryKey + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//				" Frontside     	TEXT    NOT NULL, " +
//				" Backside      	TEXT    NOT NULL, " +
//				" "+myFKName+"    	INTEGER NOT NULL, " +
//				" "+mySeekAttribute+"	INTEGER DEFAULT 1," +
//				" Description   	TEXT    		, " +
//				" Datum				TEXT";
		
	private static String pushSql = "CREATE TABLE IF NOT EXISTS Stock " + "(PK_Stk INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ " Frontside       TEXT    NOT NULL, " + " Backside      TEXT    NOT NULL, "
			+ " Set_ID    		INTEGER NOT NULL, " + " Priority	    INTEGER DEFAULT 1,"
			+ " Description    TEXT    		, " + " Color			TEXT    		 )";

	public static String getDbURL() {
		return dbURL;
	}

	/**
	 * Keine neue Instanz Database erstellen, sondern nur die Methode benutzen
	 * 
	 * @param values
	 *            --> Array mit 5 Werten: 1. Vorderseite, 2. Rückseite, 3.
	 *            Set_ID, 4. Priorität (1-5), 5. Color
	 */
	public static boolean pushToStock (String[] values) {
		SideEntity.setConnection(dbURL);
		try {
			stmt.executeUpdate(pushSql);

			c.setAutoCommit(false);
			ResultSet selectSet = stmt
					.executeQuery("SELECT PK_Kategorie FROM Kategorie WHERE Kategorie = '" + values[2] + "'");
			c.setAutoCommit(true);
			
			String setID;
			if (selectSet.next()) {
				setID = Integer.toString(selectSet.getInt("PK_Kategorie"));
				selectSet.close();
			} else {
				selectSet.close();
				stmt.close();
				c.close();
				return false;
			}

			String insert = "INSERT INTO Stock (Frontside, Backside, Set_ID, Priority, Color)" + "VALUES ('" + values[0]
					+ "','" + values[1] + "'," + setID + ", " + values[3] + ", '" + values[4] + "')";

			stmt.executeUpdate(insert);
			closeDB();
			return true;
		} catch (Exception e) {
			debug.Debugger.out("Database.pushToStock(...): "+e.getMessage());
			Logger.log("Database.pushToStock(...): "+e.getMessage());
		}
		closeDB();
		return false;

	}

	/**
	 * Keine neue Instanz Database erstellen, sondern nur die Methode benutzen
	 * 
	 * @return Retourniert eine ArrayList mit Arrays mit 7 Werten: PK, Vorder-,
	 *         Rückseite, Description, Set_ID, Priorität, Farbe
	 */

	public static ArrayList<String[]> pullFromStock (String whichSet) {

		ArrayList<String[]> results = new ArrayList<String[]>();

		SideEntity.setConnection(dbURL);
		try {

			stmt.executeUpdate(pushSql);

			c.setAutoCommit(false);
			String IDwhichSet = "";
			ResultSet s = stmt.executeQuery("SELECT PK_Kategorie FROM Kategorie WHERE Kategorie = '" + whichSet + "'");
			c.setAutoCommit(true);

			if (s.next()) {
				IDwhichSet = Integer.toString(s.getInt("PK_Kategorie"));
			} else {
				debug.Debugger.out("No Kategorie: " + whichSet + "in Table Kategorie");
				closeDB();
				return null;
			}

			s.close();
			c.setAutoCommit(false);
			ResultSet rs = stmt.executeQuery("SELECT * FROM Stock WHERE Set_ID = '" + IDwhichSet + "'");
			c.setAutoCommit(true);

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
			closeDB();
		}
		catch (Exception e) {
			if (whichSet==null) whichSet="{null}";
			debug.Debugger.out("Database.pullFromStock("+whichSet+"): "+e.getMessage());
			Logger.log("Database.pullFromStock("+whichSet+"): "+e.getMessage());
			closeDB();
		}
		return results;
	}

	public static boolean delEntry(String id) {

		boolean deleted = false;

		SideEntity.setConnection(dbURL);
		try {
			String del = "DELETE FROM Stock WHERE PK_Stk = " + id;
			stmt.executeUpdate(del);
			deleted = true;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		closeDB();
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

	public static boolean editEntry(String id, String frontside, String backside) {

		SideEntity.setConnection(dbURL);
		try {
			c.setAutoCommit(false);
			String sel = "SELECT * FROM Stock WHERE PK_Stk = " + id;
			ResultSet rs = stmt.executeQuery(sel);
			c.setAutoCommit(true);

			if (!rs.next()) {
				c.commit();
				closeDB();
				return false;
			}
			else {
				rs.close();
			}
			String del = "UPDATE Stock SET Frontside = '" + frontside + "', Backside = '" + backside
					+ "' WHERE PK_Stk = " + id;
			debug.Debugger.out(del);
			stmt.executeUpdate(del);
			closeDB();
			return true;
		}
		catch (Exception e) {
			debug.Debugger.out("Database.editEntry("+id+"): "+e.getMessage());
			Logger.log("Database.editEntry("+id+"): "+e.getMessage());
		}
		closeDB();
		return false;
	}

	/**
	 * Erhöht die Priorität um 1, Legt die Karte nach hinten, bei 5, bleibt sie
	 * 
	 * @param PK_ID
	 *            --> PK_Stock ID der Karte, welche erhöht wird
	 */

	public static void upPrio (Integer PK_ID) {

		SideEntity.setConnection(dbURL);
		Integer oldPrio = null;
		String newPrio = "";
		try {
			stmt.executeUpdate(pushSql);

			// Frage die Aktuelle Priorität ab
			c.setAutoCommit(false);
			ResultSet actualPrio = stmt.executeQuery("SELECT Priority FROM Stock WHERE PK_Stk = " + PK_ID.toString());
			c.setAutoCommit(true);

			// Überprüft ob vorhanden oder nicht
			if (actualPrio.next()) {
				oldPrio = actualPrio.getInt(SideEntity.mySeekAttribute);
				actualPrio.close();
			}
			else {
				debug.Debugger.out("No Card with "+SideEntity.myPrimaryKey+"='"+PK_ID.toString()+"' exists.");
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

			String updatePrio = "UPDATE Stock SET Priority = " + newPrio + " WHERE PK_Stk = " + PK_ID;
			stmt.executeUpdate(updatePrio);
		}
		catch (Exception e) {
			debug.Debugger.out("Database.upPrio("+PK_ID+"): "+e.getMessage());
			Logger.log("Database.upPrio("+PK_ID+"): "+e.getMessage());
		}
		closeDB();
	}

	/**
	 * Bei nicht wissen der Karte, wird die Prio zurückgesetzt --> Sprich auf 0
	 * gesetzt
	 * 
	 * @param karte
	 *            --> Welche Karte reseted wird
	 */
	public static void resetPrio (Integer PK_ID) {
		SideEntity.setConnection(dbURL);
		try {
			// Setzt die Priorität zurück auf 1

			String updatePrio = "UPDATE Stock SET Priority = 1 WHERE PK_Stk = " + PK_ID;
			stmt.executeUpdate(updatePrio);
		}
		catch (Exception e) {
			debug.Debugger.out("Database.resetPrio("+PK_ID+"): "+e.getMessage());
			Logger.log("Database.resetPrio("+PK_ID+"): "+e.getMessage());
		}
		closeDB();
	}
	
	/**
	 * Liefert die Priorität der Karte mit mitgegebener ID mit 
	 * 
	 * @param ID_Card --> ID der Karte, von welcher die Priorität gebraaucht wird
	 * @return --> Gibt die Kartenpriorität als Integer zurück
	 */
	public static int getPriority (String ID_Card) {
		SideEntity.setConnection(dbURL);
		int prio = 0;
		try {
			String getPrio = "SELECT Priority FROM Stock WHERE PK_Stk = " + ID_Card;
			c.setAutoCommit(false);
			ResultSet rsPrio = stmt.executeQuery(getPrio);
			c.setAutoCommit(true);

			if (rsPrio.next()) {
				prio = rsPrio.getInt("Priority");
			} else {
				debug.Debugger.out("No such Cards exists in stock @ ID ("+ID_Card+")!");
			}
		}
		catch (Exception e) {
			if (ID_Card==null) ID_Card="{null}";
			debug.Debugger.out("Database.getPriority("+ID_Card+"): "+e.getMessage());
			Logger.log("Database.getPriority("+ID_Card+"): "+e.getMessage());
		}
		closeDB();
		return prio;
	}
	
	/**
	 * Liefert den Maximalen und den bisher erreichten Score eines Stacks zurück
	 *  
	 * @param whichSet --> Score von welchem Stack geliefert werden soll
	 * @return --> Retourniert diesen gewünschten Score
	 */
	public static Double[] getScore (String whichSet) {

		SideEntity.setConnection(dbURL);
		Double maxPoints = 0.0;
		Double reachedPoints = 0.0;
		Double[] score = new Double[2];
		try {
			// Alle Prioritäten aus Tabelle hlen, welche als Set das mitgegebene
			// haben.

			String getScore = "SELECT Priority FROM Stock WHERE Set_ID = (SELECT PK_Kategorie FROM Kategorie"
					+ " WHERE Kategorie = '" + whichSet + "')";
			c.setAutoCommit(false);
			ResultSet scrs = stmt.executeQuery(getScore);
			c.setAutoCommit(true);
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
				closeDB();
				return null;
			}
		}
		catch (Exception e) {
			if (whichSet==null) whichSet="{null}";
			debug.Debugger.out("Database.getScore("+whichSet+"): "+e.getMessage());
			Logger.log("Database.getScore("+whichSet+"): "+e.getMessage());
		}
		// Erreichte Punktzahl zurückgeben
		score[0] = maxPoints;
		score[1] = reachedPoints;
		closeDB();
		return score;
		
		
	}

	public static String[] getFrontAndBackside(String Stack, int kartenID) {
		
		pullFromStock(Stack);
		
		
		String vorderseite = "Hallo";		
		String rückseite = "Hello";
		
		
		String[] VorderUndRückseite = {vorderseite, rückseite};
		
		return VorderUndRückseite;
	}
}
