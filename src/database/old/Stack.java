package database.old;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import debug.Logger;


public class Stack extends SQLiteConnector {

	// Connectioninformationen URL & Driver

	private static String	url		= "jdbc:sqlite:" + globals.Environment.getDatabasePath()
			+ globals.Globals.db_name + ".db";
	private static String	driver	= "org.sqlite.JDBC";

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

		Connection c = null;
		Statement stmt = null;
		Integer FK_ID = 0;
		Integer errorMsg = 0;

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

			// SELECT Befehl, welcher die ID der Tür abruft, in welcher die
			// Kategorie erstellt wird

			c.setAutoCommit(false);
			ResultSet id = stmt.executeQuery("SELECT PK_Doors FROM Doors WHERE Doorname = '" + fk_door + "'");
			c.setAutoCommit(true);

			// Überprüft, ob die Tür exisitert oder nicht

			if (id.next()) {
				FK_ID = id.getInt("PK_Doors");
				id.close();
			}
			else {
				closeDB();
				return -1;
			}

			c.setAutoCommit(false);
			ResultSet check = stmt.executeQuery("SELECT * FROM Kategorie WHERE Kategorie = '" + eingabe + "'");
			c.setAutoCommit(true);

			if (check.next()) {
				check.close();
				closeDB();
				return -2;
			}
			else {
				check.close();
			}
			// Erstellt die neue Kategorie als Eintrag in der Datenbank mit
			// einem Fremdkey für die Tür

			String insert = "INSERT INTO Kategorie (Kategorie, FK_Door)" +
					"VALUES ('" + eingabe + "', " + FK_ID + ")";
			stmt.executeUpdate(insert);
		}
		catch (Exception e) {
			Logger.log("Database.newStack(): " + e.getMessage());
		}
		closeDB();
		return errorMsg;

	}

	public static ArrayList<String> getKategorien (String doorname) {

		Connection c = null;
		Statement stmt = null;
		Integer FK_ID = 0;

		ArrayList<String> datensatz = new ArrayList<String>();

		String sql = "CREATE TABLE IF NOT EXISTS Kategorie "
				+ "(PK_Kategorie INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " Kategorie TEXT NOT NULL,"
				+ " FK_Door INTEGER NOT NULL);";

		try {

			// Verbindung erstellen

			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			// Tabelle generieren, falls nicht vorhanden

			stmt.executeUpdate(sql);
			debug.Debugger.out("Database.getKategorie("+sql+"): ");

			// Abfrage, ob die Tür bereits existiert
			sql = "SELECT PK_Doors FROM Doors WHERE Doorname = '" + doorname + "';";
			c.setAutoCommit(false);
			ResultSet id = stmt.executeQuery(sql);
			c.setAutoCommit(true);
			debug.Debugger.out("Database.getKategorie("+sql+"): ");

			if (id.next()) {
				FK_ID = id.getInt("PK_Doors");
			}
			else {
				debug.Debugger.out("Keine Kategorien mit dieser Tür vorhanden");
				FK_ID = 0;
			}

			id.close();

			// Alle Sets ausgeben, welche in dieser Tür enthalten sind
			sql = "SELECT * FROM Kategorie WHERE FK_Door = " + FK_ID + ";";
			c.setAutoCommit(false);
			ResultSet rs = stmt.executeQuery(sql);
			c.setAutoCommit(true);

			// Daten werden in die Liste geschrieben

			while (rs.next()) {
				datensatz.add(rs.getString("Kategorie"));
			}

			rs.close();
		}
		catch (Exception e) {
			if (doorname == null) doorname = "{null}";
			debug.Debugger.out("Database.getKategorie("+doorname+"): " + e.getMessage());
			Logger.log("Database.getKategorie("+doorname+"): " + e.getMessage());
		}
		closeDB();
		return datensatz;
	}

	/**
	 * Löscht den gewählten Eintrag
	 * 
	 * @param category
	 *            --> Name der zu löschenden Kategorie
	 */
	public static boolean delStack (String category) {

		Connection c = null;
		Statement stmt = null;
		boolean worked = false;
		String sql = null;
		try {

			// Verbindung erstellen

			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			// Tabelle generieren wenn nicht vorhanden

			sql = "CREATE TABLE IF NOT EXISTS Kategorie "
					+ "(PK_Kategorie INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " Kategorie TEXT NOT NULL,"
					+ " FK_Door INTEGER NOT NULL);";
			stmt.executeUpdate(sql);

			// Abfragen, ob zu löschende Kategorie vorhanden ist oder nicht.
			// Wenn ja, wird gelöscht
			sql = "SELECT Kategorie FROM Kategorie WHERE"+ " Kategorie = '" + category + "';";
			c.setAutoCommit(false);
			ResultSet del = stmt.executeQuery(sql);
			debug.Debugger.out("Stack.delStack(" + sql +"): Size="+del.getFetchSize());
			c.setAutoCommit(true);
			Integer setID  = del.getInt("Kategorie");
			debug.Debugger.out("Stack.delStack(" + category +"): ID="+setID);

			boolean contin = del.next();
			del.close();

			if (contin) {
				try {
					sql = "DELETE FROM Stock WHERE Set_ID = " + setID + ";";
					int ret = stmt.executeUpdate(sql);
					debug.Debugger.out("Stack.delStack(" + sql +"):"+ret);
				} catch (Exception e) {
					debug.Debugger.out("Stack.delStack(" + sql +")...");
					debug.Debugger.out("Stack.delStack(" + category +"): " + e.getMessage());
					Logger.log("Stack.delStack(" + category +"): " + e.getMessage());
				}
				sql = "DELETE FROM Kategorie WHERE Kategorie = '" + category + "';";
				int ret = stmt.executeUpdate(sql);
				debug.Debugger.out("Stack.delStack(" + sql +"):"+ret);

				worked = true;
			}
			else {
				worked = false;
			}
		}
		catch (Exception e) {
			debug.Debugger.out("Stack.delStack(" + sql +")...");
			debug.Debugger.out("Stack.delStack(" + category +"): " + e.getMessage());
			Logger.log("Stack.delStack(" + category +"): " + e.getMessage());
		}
		closeDB();
		return worked;
	}

	public static ArrayList<String> getStacknames () {

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

			c.setAutoCommit(false);
			ResultSet StackSet = stmt.executeQuery("SELECT Kategorie FROM Kategorie");
			c.setAutoCommit(true);
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
		}
		catch (Exception e) {
			Logger.log("Database.getStackNames(): " + e.getMessage());
		}
		closeDB();
		return Stacks;
	}

	public int getStackID (String KategorieName) {
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
			ResultSet StackSet = stmt
					.executeQuery("SELECT PK_Kategorie FROM Kategorie WHERE Kategorie = '" + KategorieName + "'");
			c.setAutoCommit(true);

			ID = Integer.parseInt(StackSet.getString(StackSet.getInt(1)));

			StackSet.close();
		}
		catch (Exception e) {
			Logger.log("Database.getStackID(): " + e.getMessage());
		}
		closeDB();
		return ID;
	}

	public static boolean possible (String boxName) {

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			String sql = "SELECT * FROM Kategorie WHERE Kategorie = '" + boxName + "';";
			c.setAutoCommit(false);
			ResultSet checkPossible = stmt.executeQuery(sql);
			c.setAutoCommit(true);

			if (checkPossible.next()) {
				checkPossible.close();
				closeDB();
				return false;
			}
			else {
				checkPossible.close();
				closeDB();
				return true;
			}

		}
		catch (Exception e) {
			Logger.log("Database.possible(): " + e.getMessage());
		}
		closeDB();
		return false;
	}

	public static boolean update (String oldName, String newName) {

		Connection c = null;
		Statement stmt = null;
		boolean worked = true;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

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
		}
		catch (Exception e) {
			Logger.log("Database.update(): " + e.getMessage());
		}
		closeDB();
		return worked;
	}
}
