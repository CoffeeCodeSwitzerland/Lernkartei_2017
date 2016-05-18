package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class Categories {

	// Connectioninformationen URL & Driver

	private static String	url			= "jdbc:sqlite:" +  debug.Environment.getDatabasePath()
										 + controls.Globals.db_name + ".db";
	private static String	driver		= "org.sqlite.JDBC";

	/**
	 * 
	 * Methode, zum Einfügen einer neuen Kategorie
	 *
	 * @param eingabe
	 *            --> String Kategorie
	 * @param fk_door
	 *            --> String Doorname, zu welcher die Kategorie gehört
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
			
			// SELECT Befehl, welcher die ID der Tür abruft, in welcher die Kategorie erstellt wird

			ResultSet id = stmt.executeQuery("SELECT PK_Doors FROM Doors WHERE Doorname = '" + fk_door + "'");
			
			// Überprüft, ob die Tür exisitert oder nicht
			
			if (id.next()) {
				FK_ID = id.getInt("PK_Doors");
			}
			else {
				errorMsg = -1;
			}

			id.close();
			
			// Überprüft, ob die Kategorie bereits existiert

			ResultSet check = stmt.executeQuery("SELECT * FROM Kategorie WHERE Kategorie = '" + eingabe + "'");
			if (check.next()) {
				check.close();
				errorMsg = -2;
			}
			else {
				check.close();
			}

			c.setAutoCommit(true);
			
			// Erstellt die neue Kategorie als Eintrag in der Datenbank mit einem Fremdkey für die Tür

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
			
			// Abfrage, ob die Tür bereits existiert

			ResultSet id = stmt.executeQuery("SELECT PK_Doors FROM Doors WHERE Doorname = '" + doorname + "'");

			if (id.next()) {
				FK_ID = id.getInt("PK_Doors");
			}
			else {
				debug.Debugger.out("Keine Kategorien mit dieser Tür vorhanden");
				FK_ID = 0;
			}
			
			id.close();
			
			// Alle Sets ausgeben, welche in dieser Tür enthalten sind
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM Kategorie WHERE FK_Door = " + FK_ID);
			
			// Daten werden in die Liste geschrieben
			
			while (rs.next()) {
				datensatz.add(rs.getString("Kategorie"));
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
	 * Löscht den gewählten Eintrag
	 * 
	 * @param category
	 *            --> Name der zu löschenden Kategorie
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
			
			// Abfragen, ob zu löschende Kategorie vorhanden ist oder nicht. Wenn ja, wird gelöscht
			
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
	
	public ArrayList<String> getStacknames() {
		
		Connection c = null;
		Statement stmt = null;
		
		ArrayList<String> Stacks = new ArrayList<String>();
		
		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			
			// Tabelle generieren, falls nicht vorhanden

			String sql = "CREATE TABLE IF NOT EXISTS Kategorie "
					+ "(PK_Kategorie INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " Kategorie TEXT NOT NULL,"
					+ " FK_Door INTEGER NOT NULL)";
			
			stmt.executeUpdate(sql);
			
			ResultSet StackSet = stmt.executeQuery("SELECT Kategorie FROM Kategorie");
			if (StackSet.isAfterLast())
			{
				Stacks = null;
			} else {
				//TODO : Erster Datensatz einlesen und danach in while schleife schreiben
				while (StackSet.next())
				{
						StackSet.getRow();
						Stacks.add(StackSet.getString("Kategorie"));
					}
				}
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return Stacks;
	}
	
	public int getStackID(String KategorieName) {		
		int ID = 0;
		
		Connection c = null;
		Statement stmt = null;
		
		try {
			
			
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			String sql = "CREATE TABLE IF NOT EXISTS Kategorie "
					+ "(PK_Kategorie INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " Kategorie TEXT NOT NULL,"
					+ " FK_Door INTEGER NOT NULL)";
			
			stmt.executeUpdate(sql);
			c.setAutoCommit(false);

			ResultSet StackSet = stmt.executeQuery("SELECT PK_Kategorie FROM Kategorie WHERE Kategorie = '" + KategorieName + "'");
			
			ID = Integer.parseInt(StackSet.getString(StackSet.getInt(1)));
			
		} catch (Exception e) {
			ID = 0;
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		return ID;
	}
	
	

}
