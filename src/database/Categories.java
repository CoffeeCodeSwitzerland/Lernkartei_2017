package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class Categories {

	// Connectioninformationen URL & Driver

	private static String	windowsUser	= debug.Environment.getUserName();
	private static String	url			= "jdbc:sqlite:" + debug.Environment.getUserPath() + "\\" + windowsUser + ".db";
	private static String	driver		= "org.sqlite.JDBC";

	/**
	 * 
	 * Methode, zum Einf�gen einer neuen Kategorie
	 *
	 * @param eingabe
	 *            --> String Kategorie
	 * @param fk_door
	 *            --> String Doorname, zu welcher die Kategorie geh�rt
	 */

	public static int newKategorie (String eingabe, String fk_door) {

		Connection c = null;
		Statement stmt = null;
		Integer FK_ID = 0;
		Integer errorMsg = -9;

		try {
			
			// Datenbankverbindung erstellen
			
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			
			// Tabelle Kategorie erstellen, sofern sie nicht existiert

			String sql = "CREATE TABLE IF NOT EXISTS Kategorie "
					+ "(PK_Kategorie INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " Kategorie TEXT NOT NULL,"
					+ " FK_Door INTEGER NOT NULL)";
			
			stmt.executeUpdate(sql);
			c.setAutoCommit(false);
			
			// SELECT Befehl, welcher die ID der T�r abruft, in welcher die Kategorie erstellt wird

			ResultSet id = stmt.executeQuery("SELECT PK_Doors FROM Doors WHERE Doorname = '" + fk_door + "'");
			
			// �berpr�ft, ob die T�r exisitert oder nicht
			
			if (id.next()) {
				FK_ID = id.getInt("PK_Doors");
			}
			else {
				errorMsg = -1;
			}

			id.close();
			
			// �berpr�ft, ob die Kategorie bereits existiert

			ResultSet check = stmt.executeQuery("SELECT * FROM Kategorie WHERE Kategorie = '" + eingabe + "'");
			if (check.next()) {
				check.close();
				errorMsg = -2;
			}
			else {
				check.close();
			}

			c.setAutoCommit(true);
			
			// Erstellt die neue Kategorie als Eintrag in der Datenbank mit einem Fremdkey f�r die T�r

			String insert = "INSERT INTO Kategorie (Kategorie, FK_Door)" +
					"VALUES ('" + eingabe + "', " + FK_ID + ")";

			stmt.executeUpdate(insert);
			stmt.close();
			c.close();
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return errorMsg;

	}

	public static ArrayList<String> getKategorien (String doorname) {

		Connection c = null;
		Statement stmt = null;
		Integer FK_ID = 0;

		ArrayList<String> datensatz = new ArrayList<String>();
		
		try {
			
			// Verbindung erstellen
			
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			
			// Tabelle generieren, falls nicht vorhanden

			String sql = "CREATE TABLE IF NOT EXISTS Kategorie "
					+ "(PK_Kategorie INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " Kategorie TEXT NOT NULL,"
					+ " FK_Door INTEGER NOT NULL)";
			
			stmt.executeUpdate(sql);
			c.setAutoCommit(false);
			
			// Abfrage, ob die T�r bereits existiert

			ResultSet id = stmt.executeQuery("SELECT PK_Doors FROM Doors WHERE Doorname = '" + doorname + "'");

			if (id.next()) {
				FK_ID = id.getInt("PK_Doors");
			}
			else {
				debug.Debugger.out("Keine Kategorien mit dieser T�r vorhanden");
				FK_ID = 0;
			}
			
			id.close();
			
			// Alle Sets ausgeben, welche in dieser T�r enthalten sind
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM Kategorie WHERE FK_Door = " + FK_ID);
			
			// Daten werden in die Liste geschrieben
			
			while (rs.next()) {

				String data;

				String Kategorie = rs.getString("Kategorie");

				data = Kategorie;

				datensatz.add(data);
			}

			rs.close();
			stmt.close();
			c.close();
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return datensatz;

	}

	/**
	 * L�scht den gew�hlten Eintrag
	 * 
	 * @param category
	 *            --> Name der zu l�schenden Kategorie
	 */

	public static boolean delKategorie (String category) {

		Connection c = null;
		Statement stmt = null;
		boolean worked = false;

		try {
			
			// Verbindung erstellen
			
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			
			// Tabelle generieren wenn nicht vorhanden

			String sql = "CREATE TABLE IF NOT EXISTS Kategorie "
					+ "(PK_Kategorie INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " Kategorie TEXT NOT NULL,"
					+ " FK_Door INTEGER NOT NULL)";
			stmt.executeUpdate(sql);

			c.setAutoCommit(false);
			
			// Abfragen, ob zu l�schende Kategorie vorhanden ist oder nicht. Wenn ja, wird gel�scht
			
			ResultSet del = stmt.executeQuery("SELECT Kategorie FROM Kategorie WHERE Kategorie = '" + category + "'");

			if (del.next()) {

				del.close();
				c.setAutoCommit(true);
				String delDoor = "DELETE FROM Kategorie WHERE Kategorie = '" + category + "'";
				stmt.executeUpdate(delDoor);
				stmt.close();
				c.close();
				worked = true;

			}
			else {

				worked = false;
				del.close();
				stmt.close();
				c.close();

			}

		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return worked;

	}

}
