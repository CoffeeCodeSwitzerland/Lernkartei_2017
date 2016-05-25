package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class Stack {

	/**
	 * 
	 * Methode, zum Einfügen einer neuen Kategorie
	 *
	 * @param eingabe
	 *            --> String Kategorie
	 * @param fk_door
	 *            --> String Doorname, zu welcher die Kategorie gehört
	 */

	public static int newStack (String eingabe, String fk_door) {

		Connection c = Database.getConnection();
		Integer FK_ID = 0;
		Integer errorMsg = 0;

		try {

			// Datenbankverbindung erstellen
			c = DriverManager.getConnection(Database.getDbURL());
			Statement stmt = c.createStatement();

			// Tabelle Kategorie erstellen, sofern sie nicht existiert

			String sql = "CREATE TABLE IF NOT EXISTS Kategorie "
					+ "(PK_Kategorie INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " Kategorie TEXT NOT NULL,"
					+ " FK_Door INTEGER NOT NULL)";

			stmt.executeUpdate(sql);
			c.setAutoCommit(false);

			// SELECT Befehl, welcher die ID der Tür abruft, in welcher die
			// Kategorie erstellt wird

			ResultSet id = stmt.executeQuery("SELECT PK_Doors FROM Doors WHERE Doorname = '" + fk_door + "'");

			// Überprüft, ob die Tür exisitert oder nicht

			if (id.next()) {
				FK_ID = id.getInt("PK_Doors");
				id.close();
			}
			else {
				return -1;
			}

			ResultSet check = stmt.executeQuery("SELECT * FROM Kategorie WHERE Kategorie = '" + eingabe + "'");

			if (check.next()) {
				check.close();
				return -2;
			}
			else {
				check.close();
			}

			c.setAutoCommit(true);

			// Erstellt die neue Kategorie als Eintrag in der Datenbank mit
			// einem Fremdkey für die Tür

			String insert = "INSERT INTO Kategorie (Kategorie, FK_Door)" +
					"VALUES ('" + eingabe + "', " + FK_ID + ")";

			stmt.executeUpdate(insert);

			stmt.close();
			c.close();
		}
		catch (Exception e) {
			debug.Debugger.out(e.getMessage());
		}

		return errorMsg;

	}

	public static ArrayList<String> getKategorien (String doorname) {

		Connection c = Database.getConnection();
		Integer FK_ID = 0;

		ArrayList<String> datensatz = new ArrayList<String>();

		try {
			c = DriverManager.getConnection(Database.getDbURL());
			Statement stmt = c.createStatement();

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
			debug.Debugger.out(e.getMessage());
		}

		return datensatz;

	}

	/**
	 * Löscht den gewählten Eintrag
	 * 
	 * @param category
	 *            --> Name der zu löschenden Kategorie
	 */

	public static boolean delStack (String category) {

		Connection c = Database.getConnection();
		boolean worked = false;

		try {
			c = DriverManager.getConnection(Database.getDbURL());
			Statement stmt = c.createStatement();

			// Tabelle generieren wenn nicht vorhanden

			String sql = "CREATE TABLE IF NOT EXISTS Kategorie "
					+ "(PK_Kategorie INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " Kategorie TEXT NOT NULL,"
					+ " FK_Door INTEGER NOT NULL)";
			
			stmt.executeUpdate(sql);
			
			String delCards = "DELETE FROM Stock WHERE Set_ID = (SELECT PK_Kategorie FROM Kategorie WHERE Kategorie = '" + category + "');";
			stmt.executeUpdate(delCards);
			
			String delStack = "DELETE FROM Kategorie WHERE Kategorie = '" + category + "';";
			stmt.executeUpdate(delStack);

			stmt.close();
			c.close();
			worked = true;
		}
		catch (Exception e) {
			debug.Debugger.out(e.getMessage());
			worked = false;
		}

		return worked;

	}

	public static ArrayList<String> getStacknames () {

		Connection c = Database.getConnection();
		ArrayList<String> Stacks = new ArrayList<String>();

		try {
			c = DriverManager.getConnection(Database.getDbURL());
			Statement stmt = c.createStatement();

			// Tabelle generieren, falls nicht vorhanden

			String sql = "CREATE TABLE IF NOT EXISTS Kategorie "
					+ "(PK_Kategorie INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " Kategorie TEXT NOT NULL,"
					+ " FK_Door INTEGER NOT NULL)";

			stmt.executeUpdate(sql);

			ResultSet StackSet = stmt.executeQuery("SELECT Kategorie FROM Kategorie");
			if (StackSet.isAfterLast()) {
				Stacks = null;
			}
			else {

				while (StackSet.next()) {
					StackSet.getRow();
					Stacks.add(StackSet.getString("Kategorie"));
				}
			}

			StackSet.close();
			stmt.close();
			c.close();

		}
		catch (Exception e) {
			debug.Debugger.out(e.getMessage());
		}
		return Stacks;
	}

	public int getStackID (String KategorieName) {
		int ID = 0;
		Connection c = Database.getConnection();

		try {
			c = DriverManager.getConnection(Database.getDbURL());
			Statement stmt = c.createStatement();

			String sql = "CREATE TABLE IF NOT EXISTS Kategorie "
					+ "(PK_Kategorie INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " Kategorie TEXT NOT NULL,"
					+ " FK_Door INTEGER NOT NULL)";

			stmt.executeUpdate(sql);
			c.setAutoCommit(false);

			ResultSet StackSet = stmt
					.executeQuery("SELECT PK_Kategorie FROM Kategorie WHERE Kategorie = '" + KategorieName + "'");

			ID = Integer.parseInt(StackSet.getString(StackSet.getInt(1)));

			StackSet.close();
			stmt.close();
			c.close();

		}
		catch (Exception e) {
			ID = 0;
			debug.Debugger.out(e.getMessage());
		}

		return ID;
	}

	public static boolean possible (String boxName) {

		Connection c = Database.getConnection();

		try {
			c = DriverManager.getConnection(Database.getDbURL());
			Statement stmt = c.createStatement();

			String sql = "SELECT * FROM Kategorie WHERE Kategorie = '" + boxName + "';";

			c.setAutoCommit(false);
			ResultSet checkPossible = stmt.executeQuery(sql);

			if (checkPossible.next()) {
				checkPossible.close();
				stmt.close();
				c.close();
				return false;
			}
			else {
				checkPossible.close();
				stmt.close();
				c.close();
				return true;
			}

		}
		catch (Exception e) {
			debug.Debugger.out(e.getMessage());
		}

		return true;

	}

	public static boolean update (String oldName, String newName) {

		Connection c = Database.getConnection();
		boolean worked = true;

		try {
			c = DriverManager.getConnection(Database.getDbURL());
			Statement stmt = c.createStatement();

			c.setAutoCommit(false);

			ResultSet checkStack = stmt.executeQuery("SELECT * FROM Kategorie WHERE Kategorie = '" + oldName + "';");

			c.setAutoCommit(true);
			if (checkStack.next()) {
				String updateStack = "UPDATE Kategorie SET Kategorie = '" + newName + "' WHERE Kategorie = '" + oldName + "';";
				stmt.executeUpdate(updateStack);
				worked = true;
				checkStack.close();
			}
			else {
				worked = false;
				checkStack.close();
			}

			stmt.close();
			c.close();
		}
		catch (Exception e) {
			debug.Debugger.out(e.getMessage());
		}

		return worked;

	}

}
