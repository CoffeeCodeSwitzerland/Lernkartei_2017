package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * 
 * @author Joel Häberli
 *
 * 		@WhatIsThis? Gibt Daten für DataModel aus, und kann neue verabrbeiten
 */

public class UserCards {

	public UserCards () {

		// für Später, damit sichergestellt ist, dass die Tabelle "Score"
		// existiert
		Connection c = Database.getConnection();

		try {

			c = DriverManager.getConnection(Database.getDbURL());
			Statement stmt = c.createStatement();

			String sql = "CREATE TABLE IF NOT EXISTS Score 	(PK_Score INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "Kartei TEXT NOT NULL,"
					+ "Score REAL NOT NULL);";
			debug.Debugger.out(sql);
			stmt.executeUpdate(sql);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Fragt die Karteien eines Users ab
	 * 
	 * @return --> Returned eine ArrayList mit Karteien, returnt eine leere,
	 *         wenn keine Kartei vorhanden
	 */

	private static ArrayList<String> listCards = new ArrayList<String>();

	public static ArrayList<String> getCards () {

		Connection c = Database.getConnection();
		listCards.clear();

		try {
			c = DriverManager.getConnection(Database.getDbURL());
			Statement stmt = c.createStatement();
			String Karten = "SELECT Kartei FROM Score";
			ResultSet Cards = stmt.executeQuery(Karten);

			String testEintrag = Cards.getString(Cards.findColumn("Kartei"));

			debug.Debugger.out(testEintrag);

			Cards.afterLast();
			int letzterEintrag = Cards.getRow() - 1;

			for (int i = 1; i < letzterEintrag; i++) {
				listCards.add(Cards.getString(i));
			}
			return listCards;

		}
		catch (Exception e) {
			debug.Debugger.out(e.getMessage());
			listCards.clear();
			return listCards;
		}

	}

	boolean wasSuccessful;

	public boolean addCards (String query) {

		Connection c = Database.getConnection();

		try {
			c = DriverManager.getConnection(Database.getDbURL());
			Statement stmt = c.createStatement();

			debug.Debugger.out(query);

			stmt.executeUpdate(query);

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return wasSuccessful;
	}

}
