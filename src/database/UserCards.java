package database;

import java.sql.ResultSet;
import java.util.ArrayList;

import debug.Logger;

/**
 * 
 * @author Joel Häberli
 *
 * 		@WhatIsThis? Gibt Daten für DataModel aus, und kann neue verabrbeiten
 */

public class UserCards  extends SQLiteConnector {

	protected static String myTableName  = "Score";
	protected static String mySeekAttribute = "Kartei";
	protected static String myPrimaryKey = "PK_Score";
//	private   static String myFKName     = "FK_Door";
//	private   static String myAttributeList = mySeekAttribute;
	private   static String myAttributes = 
									myPrimaryKey + " INTEGER PRIMARY KEY AUTOINCREMENT,"
									+ "Kartei TEXT NOT NULL,"
									+ "Score  REAL NOT NULL";
	/**
	 *  sicherstellen, dass die Tabelle "Score" existiert
	 */
	public UserCards () {
		Database.setConnection(Database.getDbURL());
		try {
			createTableIfNotExists(UserCards.myTableName, UserCards.myAttributes);
		}
		catch (Exception e) {
			debug.Debugger.out("Score.UserCards(): "+e.getMessage());
			Logger.log("Score.UserCards(): "+e.getMessage());
		}
		closeDB();
	}

	/**
	 * Fragt die Karteien eines Users ab
	 * 
	 * @return --> Returned eine ArrayList mit Karteien, returnt eine leere,
	 *         wenn keine Kartei vorhanden
	 */
	private static ArrayList<String> listCards = new ArrayList<String>();

	public static ArrayList<String> getCards () {

		listCards.clear();

		Database.setConnection(Database.getDbURL());
		try {
			ResultSet Cards = seekInTable( UserCards.myTableName, UserCards.mySeekAttribute);
			
			Cards.getString(Cards.findColumn(UserCards.mySeekAttribute));
			Cards.afterLast();
			
			int letzterEintrag = Cards.getRow() - 1;
			for (int i = 1; i < letzterEintrag; i++) {
				listCards.add(Cards.getString(i));
			}
		}
		catch (Exception e) {
			debug.Debugger.out("Score.getCards(): "+e.getMessage());
			Logger.log("Score.getCards(): "+e.getMessage());
			listCards.clear();
		}
		closeDB();
		return listCards;
	}

	public boolean addCards (String query) {
		// TODO change to call without query, only keys
		boolean wasSuccessful = false;
		Database.setConnection(Database.getDbURL());
		try {
			stmt.executeUpdate(query);
			wasSuccessful = true;
		}
		catch (Exception e) {
			if (query == null) query="{null}";
			debug.Debugger.out("Score.addCards("+query+"): "+e.getMessage());
			Logger.log("Score.addCards("+query+"): "+e.getMessage());
		}
		closeDB();
		return wasSuccessful;
	}
}
