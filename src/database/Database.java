package database;

import java.sql.*;
import java.util.ArrayList;

import debug.Logger;

public class Database extends SQLiteConnector {

	private static final String dbURL = urlBase + globals.Globals.db_name + ".db";

	protected static String myTableName  =  "Stock";
	private   static String myPrimaryKey = "PK_Stk";
	protected static String myFKName     = "Set_ID";	
	private   static String mySeekAttribute = "Priority";
	private   static String myAttributeList = myFKName+", Frontside, Backside, Priority, Datum";
	private   static String myAttributes = 
				myPrimaryKey + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				" Frontside     	TEXT    NOT NULL, " +
				" Backside      	TEXT    NOT NULL, " +
				" "+myFKName+"    	INTEGER NOT NULL, " +
				" "+mySeekAttribute+"	INTEGER DEFAULT 1," +
				" Description   	TEXT    		, " +
				" Datum				TEXT";
		
	public static String getDbURL() {
		return dbURL;
	}

	/**
	 * 
	 * @param values
	 *            --> Array mit 5 Werten: 1. Vorderseite, 2. Rückseite, 3.
	 *            Set_ID, 4. Priorität (1-5), 5. Color
	 */
	public static boolean pushToStock (String[] values) {
		Database.setConnection(dbURL);
		try {
			createTableIfNotExists ("Stock", myAttributes);
			ResultSet selectSet = seekInTable (Stack.myTableName, Stack.myPrimaryKey, Stack.mySeekAttribute, values[2]);
			String setID = "";
			if (selectSet.next()) {
				setID = Integer.toString(selectSet.getInt(Stack.myPrimaryKey));
				selectSet.close();
			}
			else {
				selectSet.close();
				closeDB();
				return false;
			}
			values[2] = values[3]; // TODO dieser murgs muss weg (siehe Aufruf)
			values[3] = values[4];
			values[4] = null;
			insertIntoTable (Database.myTableName, Database.myAttributeList, setID, values);
			closeDB();
			return true;
		} catch (Exception e) {
			debug.Debugger.out("Database.pushToStock(...): "+e.getMessage());
			Logger.log("Database.pushToStock(...): "+e.getMessage());
		}
		closeDB();
		return false; // TODO evtl. false? ... auch weiter unten ein Problem
	}

	/**
	 * Keine neue Instanz Database erstellen, sondern nur die Methode benutzen
	 * 
	 * @return Retourniert eine ArrayList mit Arrays mit 7 Werten: PK, Vorder-,
	 *         Rückseite, Description, Set_ID, Priorität, Farbe
	 */

	public static ArrayList<String[]> pullFromStock (String whichSet) {

		ArrayList<String[]> results = new ArrayList<String[]>();

		Database.setConnection(dbURL);
		try {
			createTableIfNotExists (Database.myTableName, myAttributes);
			String IDwhichSet = "";
			ResultSet selectSet = seekInTable (Stack.myTableName, Stack.myPrimaryKey, Stack.mySeekAttribute, whichSet);
			if (selectSet.next()) {
				IDwhichSet = Integer.toString(selectSet.getInt(Stack.myPrimaryKey));
			}
			else {
				debug.Debugger.out("No Primary Key '" + whichSet + "' in Table '"+Stack.myTableName+"'");
				selectSet.close();
				closeDB();
				return null;
			}
			selectSet.close();
			ResultSet rs = seekInTable(Database.myTableName, "*", Database.myFKName, IDwhichSet);
			while (rs.next()) {
				String[] set = new String[7];
				set[0] = Integer.toString(rs.getInt(Database.myPrimaryKey));
				set[1] = rs.getString("Frontside");
				set[2] = rs.getString("Backside");
				set[3] = rs.getString("Description");
				set[4] = Integer.toString(rs.getInt(Database.myFKName));
				set[5] = Integer.toString(rs.getInt(Database.mySeekAttribute));
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

	public static boolean delEntry (String id) {
		Database.setConnection(dbURL);
		Boolean deleted = deleteSQL (Database.myTableName, Stack.myPrimaryKey, Integer.decode(id));
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
	public static boolean editEntry (String id, String frontside, String backside) {

		Database.setConnection(dbURL);
		try {
			ResultSet rs = seekInTable(Database.myTableName, "*", Database.myPrimaryKey, id);
			if (!rs.next()) {
				c.commit();
				closeDB();
				return false;
			}
			else {
				rs.close();
			}
			updateInTable(Database.myTableName, "Frontside", frontside, "Backside", backside, Database.myPrimaryKey, id);
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

		Database.setConnection(dbURL);
		Integer oldPrio = null;
		String newPrio = "";
		try {
			// Frage die Aktuelle Priorität ab
			ResultSet actualPrio = seekInTable(	Database.myTableName, Database.mySeekAttribute,
												Database.myPrimaryKey, PK_ID.toString());
			// Überprüft ob vorhanden oder nicht
			if (actualPrio.next()) {
				oldPrio = actualPrio.getInt(Database.mySeekAttribute);
				actualPrio.close();
			}
			else {
				debug.Debugger.out("No Card with "+Database.myPrimaryKey+"='"+PK_ID.toString()+"' exists.");
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

			updateInTable (	Database.myTableName, Database.mySeekAttribute, newPrio, 
							Database.myPrimaryKey, PK_ID.toString());
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
		Database.setConnection(dbURL);
		try {
			// Setzt die Priorität zurück auf 1
			updateInTable (	Database.myTableName, Database.mySeekAttribute, "1", 
							Database.myPrimaryKey, PK_ID.toString());
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
		Database.setConnection(dbURL);
		int prio = 0;
		try {
			// Frage die Aktuelle Priorität ab
			ResultSet rsPrio = seekInTable(	Database.myTableName, Database.mySeekAttribute,
											Database.myPrimaryKey, ID_Card);
			if (rsPrio.next()) {
				prio = rsPrio.getInt(Database.mySeekAttribute);
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

		Database.setConnection(dbURL);
		Double maxPoints = 0.0;
		Double reachedPoints = 0.0;
		Double[] score = new Double[2];
		try {
			// Alle Prioritäten aus Tabelle holen, welche als Set das mitgegebene haben.
//			String seekAttribute = "Priority";
//			String katAttribute = "PK_Kategorie";
//			String katTable = "Kategorie";
//			String katName  = "Kategorie"; // TODO ??? = PK_Kategorie

			String subSelect = "(SELECT " + Stack.myPrimaryKey + " FROM " + Stack.myTableName
							 + " WHERE "+ Stack.mySeekAttribute +" = '" + whichSet + "')";
			ResultSet scrs = seekInTable (	Database.myTableName, Database.mySeekAttribute, 
											Database.myFKName, subSelect);

			// Durch loopen und die Maximale sowie die Erreichte Punktzahl
			// speichern
			if (scrs.next()) {
				maxPoints += 4.0;
				reachedPoints += scrs.getInt(Database.mySeekAttribute) - 1.0;
				while (scrs.next()) {
					maxPoints += 4.0;
					reachedPoints += scrs.getInt(Database.mySeekAttribute) - 1.0;
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
}
