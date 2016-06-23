package database;

import java.util.ArrayList;

import database.sql.Attribute;
import database.sql.Entity;
import database.sql.ForeignKey;
import database.sql.KeyAttribute;
import database.sql.SQLHandler;
import debug.Logger;

public class CardEntity extends Entity {

	/**
	 * @param myNewTableName
	 */
	public CardEntity(String tabName) {
		super(tabName,"PK_"+tabName);
		// set table attributes
		Attribute a = new Attribute("Frontside");
		myAttributes.add(a);
		a = new Attribute("Backside");
		myAttributes.add(a);
		ForeignKey f = new ForeignKey("PK_STACK");
		myAttributes.add(f);
		KeyAttribute k = new KeyAttribute("Priority",0,"1");
		myAttributes.add(k);
		a = new Attribute("Color");
		myAttributes.add(a);
		a = new Attribute("Description");
		myAttributes.add(a);
		a = new Attribute("Date");
		myAttributes.add(a);
		createTableIfNotExists();
	}

//	protected static String myFKName     = "Set_ID";	
//	private   static String mySeekAttribute = "Priority";
//				myPrimaryKey + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//				" Frontside     	TEXT    NOT NULL, " +
//				" Backside      	TEXT    NOT NULL, " +
//				" "+myFKName+"    	INTEGER NOT NULL, " +
//				" "+mySeekAttribute+"	INTEGER DEFAULT 1," +
//				" Description   	TEXT    		, " +
//				" Datum				TEXT";
		
//	private static String pushSql = "CREATE TABLE IF NOT EXISTS Stock " + "(PK_Stk INTEGER PRIMARY KEY AUTOINCREMENT,"
//			+ " Frontside       TEXT    NOT NULL, " + " Backside      TEXT    NOT NULL, "
//			+ " Set_ID    		INTEGER NOT NULL, " + " Priority	    INTEGER DEFAULT 1,"
//			+ " Description    TEXT    		, " + " Color			TEXT    		 )";
//
//	public static String getDbURL() {
//		return dbURL;
//	}
//
	/**
	 * Keine neue Instanz Database erstellen, sondern nur die Methode benutzen
	 * 
	 * @param values
	 *            --> Array mit 5 Werten: 1. Vorderseite, 2. Rückseite, 3. PK_Stack
	 *            4. Priorität (1-5), 5. Color
	 * @  deprecated
	 */
	public boolean pushToStock (String[] values) {
		try {
			String id = values[4];
			setLastSQLCommand(SQLHandler.selectCommand("STACK","PK_STACK","name",id)); 
			setLastResultSet(executeQuery(getLastSQLCommand()));
			debug.Debugger.out(getLastSQLCommand());
			// "SELECT PK_Kategorie FROM Kategorie WHERE Kategorie = '" + values[2] + "'"
			String setID;
			if (getLastResultSet().next()) {
				setID = Integer.toString(getLastResultSet().getInt("PK_Stack"));
				getLastResultSet().close();
			} else {
				getLastResultSet().close();
				Logger.out("...1. no Stack in database for '"+id+"'!",getMyTableName());
				return false;
			}
			values[4] = setID;
			for (int i=0, j=0; i< values.length; i++) {
				j=i;
				if (i>=2) j++;
				myAttributes.get(j).setValue(values[i]);
			}
			setLastSQLCommand(SQLHandler.insertIntoTableCommand(getMyTableName(), myAttributes)); 
			return (executeCommand(getLastSQLCommand())>0)?true:false;

//			String attributeList = myAttributes.getCommaSeparatedList(false);
//			myAttributes.seekKeyNamed("Frontside").setValue(value[0]);
//			myAttributes.seekKeyNamed("Backside").setValue(value[1]);
//			myAttributes.seekKeyNamed("Priority").setValue(value[3]);
//			myAttributes.seekKeyNamed("Color").setValue(value[4]);
//			int inserts = insertIntoTable(attributeList, values);
//			String insert = "INSERT INTO Stock (Frontside, Backside, Set_ID, Priority, Color)" + "VALUES ('" + values[0]
//					+ "','" + values[1] + "'," + setID + ", " + values[3] + ", '" + values[4] + "')";
//			if (inserts > 0 ) return true;
//			else 
//				Logger.out("no inserts could be performed!",this.getMyTableName());
		} catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return false;
	}

	/**
	 * Keine neue Instanz Database erstellen, sondern nur die Methode benutzen
	 * 
	 * @return Retourniert eine ArrayList mit Arrays mit 7 Werten: PK, Vorder-,
	 *         Rückseite, Description, Set_ID, Priorität, Farbe
	 */

	public ArrayList<String[]> pullFromStock (String whichSet) {

		ArrayList<String[]> results = new ArrayList<String[]>();

		try {
			setLastSQLCommand(SQLHandler.selectCommand("STACK","PK_STACK","name",whichSet)); 
			setLastResultSet(executeQuery(getLastSQLCommand()));
			//setLastResultSet(seekInTable("STACK", whichSet));
			//ResultSet s = stmt.executeQuery("SELECT PK_Kategorie FROM Kategorie WHERE Kategorie = '" + whichSet + "'");
			String ID_SET="0";
			if (getLastResultSet().next()) {
				ID_SET = Integer.toString(getLastResultSet().getInt("PK_STACK"));
				getLastResultSet().close();
			} else {
				Logger.out("...2. no Stack's in database for "+whichSet+"!",getMyTableName());
				getLastResultSet().close();
				return null;
			}
			
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(),null,"PK_STACK",ID_SET)); 
			setLastResultSet(executeQuery(getLastSQLCommand()));
			//ResultSet rs = stmt.executeQuery("SELECT * FROM Card WHERE Set_ID = '" + IDwhichSet + "'");
			while (getLastResultSet().next()) {
				String[] set = new String[7];
				set[0] = Integer.toString(getLastResultSet().getInt("PK_CARD"));
				set[1] = getLastResultSet().getString("Frontside");
				set[2] = getLastResultSet().getString("Backside");
				set[3] = getLastResultSet().getString("Description");
				set[4] = Integer.toString(getLastResultSet().getInt("PK_STACK"));
				set[5] = Integer.toString(getLastResultSet().getInt("Priority"));
				set[6] = getLastResultSet().getString("Color");
				results.add(set);
			}
			getLastResultSet().close();
		}
		catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return results;
	}

	public boolean delEntry(String id) {
		setLastSQLCommand(SQLHandler.deleteEntryCommand(getMyTableName(), "PK_CARD", id)); 
		debug.Debugger.out(getLastSQLCommand());
		return (executeCommand(getLastSQLCommand())>=0)?true:false;
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

	public boolean editEntry(String id, String frontside, String backside) {

		try {
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(),null,"PK_CARD",id)); 
			setLastResultSet(executeQuery(getLastSQLCommand()));
			debug.Debugger.out(getLastSQLCommand());
			//String sel = "SELECT * FROM Stock WHERE PK_Stk = " + id;
			if (!getLastResultSet().next()) {
				getLastResultSet().close();
				return false;
			}
			else {
				getLastResultSet().close();
			}

			//String del = "UPDATE Stock SET Frontside = '" + frontside + "', Backside = '" + backside
			//		+ "' WHERE PK_Stk = " + id;
			return true;
		}
		catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return false;
	}

	/**
	 * Erhöht die Priorität um 1, Legt die Karte nach hinten, bei 5, bleibt sie
	 * 
	 * @param PK_ID
	 *            --> PK_Stock ID der Karte, welche erhöht wird
	 */

	public void upPrio (Integer PK_ID) {

		Integer oldPrio = null;
		String newPrio = "";
		try {
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(),"Priority","PK_CARD", PK_ID.toString())); 
			setLastResultSet(executeQuery(getLastSQLCommand()));
			debug.Debugger.out(getLastSQLCommand());
			//String sel = "SELECT * FROM Stock WHERE PK_Stk = " + id;
			//.executeQuery("SELECT Priority FROM Stock WHERE PK_Stk = " + PK_ID.toString());

			if (!getLastResultSet().next()) {
			// Frage die Aktuelle Priorität ab

			// Überprüft ob vorhanden oder nicht
				oldPrio = getLastResultSet().getInt("PK_CARD");
				getLastResultSet().close();
			}
			else {
				debug.Debugger.out("No Card with PK_CARD='"+PK_ID.toString()+"' exists.");
				getLastResultSet().close();
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
			Attribute k = new Attribute("Priority",newPrio);
			setLastSQLCommand(SQLHandler.updateInTableCommand(getMyTableName(),myAttributes,k)); 
			executeCommand(getLastSQLCommand());
			debug.Debugger.out(getLastSQLCommand());
			// "UPDATE Stock SET Priority = " + newPrio + " WHERE PK_Stk = " + PK_ID;
		}
		catch (Exception e) {
			Logger.out(e.getMessage());
		}
	}

	/**
	 * Bei nicht wissen der Karte, wird die Prio zurückgesetzt --> Sprich auf 0
	 * gesetzt
	 * 
	 * @param karte
	 *            --> Welche Karte reseted wird
	 */
	public void resetPrio (Integer PK_ID) {
		try {
			// Setzt die Priorität zurück auf 
			myAttributes.seekKeyNamed("Priority").setValue(1);
			Attribute k = new Attribute("PK_Stack",PK_ID);
			setLastSQLCommand(SQLHandler.updateInTableCommand(getMyTableName(),myAttributes,k));
			executeCommand(getLastSQLCommand());
			debug.Debugger.out(getLastSQLCommand());
			// "UPDATE Stock SET Priority = 1 WHERE PK_Stk = " + PK_ID;
		}
		catch (Exception e) {
			Logger.out(e.getMessage());
		}
	}
	
	/**
	 * Liefert die Priorität der Karte mit mitgegebener ID mit 
	 * 
	 * @param ID_Card --> ID der Karte, von welcher die Priorität gebraaucht wird
	 * @return --> Gibt die Kartenpriorität als Integer zurück
	 */
	public int getPriority (String ID_Card) {
		int prio = 0;
		try {
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(),"Priority","PK_CARD", ID_Card)); 
			setLastResultSet(executeQuery(getLastSQLCommand()));
			debug.Debugger.out(getLastSQLCommand());
			// "SELECT Priority FROM Stock WHERE PK_Stk = " + ID_Card;
			if (getLastResultSet().next()) {
				prio = getLastResultSet().getInt("Priority");
			} else {
				debug.Debugger.out("No such Cards exists with PK_CARD ("+ID_Card+")!");
			}
		}
		catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return prio;
	}
	
	/**
	 * Liefert den Maximalen und den bisher erreichten Score eines Stacks zurück
	 *  
	 * @param whichSet --> Score von welchem Stack geliefert werden soll
	 * @return --> Retourniert diesen gewünschten Score
	 */
	public Double[] getScore (String whichSet) {

		Double maxPoints = 0.0;
		Double reachedPoints = 0.0;
		Double[] score = new Double[2];
		try {
			// Alle Prioritäten aus Tabelle hlen, welche als Set das mitgegebene
			// haben.
			//setLastSQLCommand(SQLHandler.selectCommand("STACK","Priority","PK_STACK", ID_Card)); 
			//setLastResultSet(executeQuery(getLastSQLCommand()));

			setLastSQLCommand("SELECT Priority FROM CARD WHERE PK_STACK = (SELECT PK_STACK FROM STACK"
								+ " WHERE name = '" + whichSet + "')");
			setLastResultSet(executeQuery(getLastSQLCommand()));
			debug.Debugger.out(getLastSQLCommand());
			// Durch loopen und die Maximale sowie die Erreichte Punktzahl
			// speichern

			if (getLastResultSet().next()) {
				maxPoints += 4.0;
				reachedPoints += getLastResultSet().getInt("Priority") - 1.0;
				while (getLastResultSet().next()) {
					maxPoints += 4.0;
					reachedPoints += getLastResultSet().getInt("Priority") - 1.0;
				}
			} else {
				return null;
			}
		}
		catch (Exception e) {
			Logger.out(e.getMessage());
		}
		// Erreichte Punktzahl zurückgeben
		score[0] = maxPoints;
		score[1] = reachedPoints;
		return score;
	}

	public String[] getFrontAndBackside(String Stack, int kartenID) {

		ArrayList<String[]> cards = pullFromStock(Stack);

		String vorderseite = cards.get(kartenID)[1];
		String rückseite = cards.get(kartenID)[2];

		String[] VorderUndRückseite = { vorderseite, rückseite };

		return VorderUndRückseite;
	}
}
