package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class Doors {

	// URL und Driver

	private static String	windowsUser	= debug.Environment.getUserName();
	private static String	url			= "jdbc:sqlite:" + windowsUser + ".db";
	private static String	driver		= "org.sqlite.JDBC";

	/**
	 * Methode, zum Erstellen einer neuen Türe
	 * 
	 * @param eingabe
	 *            --> String Name der Tür
	 * 
	 * @return --> True, wenn Eintrag eingefügt, false, wenn Eintrag bereits
	 *         vorhanden
	 *
	 */

	public static boolean newDoor (String eingabe) {

		Connection c = null;
		Statement stmt = null;
		boolean worked = false;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			String sql = "CREATE TABLE IF NOT EXISTS Doors " +
					"(PK_Doors INTEGER PRIMARY KEY AUTOINCREMENT," +
					" Doorname TEXT NOT NULL)";

			debug.Debugger.out(sql);
			stmt.executeUpdate(sql);

			// Überprüft, ob bereits ein Eintrag mit dem Selben Namen enthalten
			// ist

			c.setAutoCommit(false);

			ResultSet checkName = stmt
					.executeQuery("SELECT Doorname FROM Doors WHERE Doorname = " + "'" + eingabe + "'");

			if (!checkName.next()) {

				checkName.close();
				c.setAutoCommit(true);

				// Einfügen des Datensatzes in Doors

				String insert = "INSERT INTO Doors (Doorname)" +
						"VALUES ('" + eingabe + "')";

				stmt.executeUpdate(insert);
				stmt.close();
				c.close();
				
				worked = true;

			}
			else {

				worked = false;
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

	/**
	 * 
	 * Methode, welche alle Türen in einer Liste ausgibt
	 * 
	 * @return --> Retourniert die Liste mit allen Türennamen
	 */

	public static ArrayList<String> getDoors () {

		Connection c = null;
		Statement stmt = null;

		ArrayList<String> data = new ArrayList<String>();

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			c.setAutoCommit(false);
			stmt = c.createStatement();

			ResultSet tbl = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Doors'");

			if (tbl.next()) {

				ResultSet rs = stmt.executeQuery("SELECT Doorname FROM Doors");

				while (rs.next()) {

					String name = "";
					name = rs.getString("Doorname");
					data.add(name);

				}

				rs.close();
				stmt.close();
				c.close();

			}
			else {

				debug.Debugger.out("Table Doors is not created yet.");
				stmt.close();
				c.close();

			}
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return data;

	}

	/**
	 * Gibt boolean zurück, obs funktioniert hat oder nicht
	 * 
	 * @param delName
	 *            --> Name, welcher gelöscht werden soll
	 * @return --> True, Gelöscht / false, nicht Gelöscht / vorhanden
	 */

	public static boolean delDoor (String delName) {

		Connection c = null;
		Statement stmt = null;
		boolean worked = false;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			c.setAutoCommit(false);

			ResultSet del = stmt.executeQuery("SELECT Doorname FROM Doors WHERE Doorname = '" + delName + "'");

			if (del.next()) {

				del.close();
				c.setAutoCommit(true);
				String delDoor = "DELETE FROM Doors WHERE Doorname = '" + delName + "'";
				stmt.executeUpdate(delDoor);

				debug.Debugger.out("Successfully deleted Door: " + delName);

				stmt.close();
				c.close();
				worked = true;

			}
			else {

				worked = false;
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
