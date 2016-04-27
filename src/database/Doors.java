package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class Doors {

	// URL und Driver

	private static String	url		= "jdbc:sqlite:test.db";
	private static String	driver	= "org.sqlite.JDBC";

	/**
	 * Methode, zum Erstellen einer neuen Türe
	 * 
	 * @param eingabe
	 *            --> String Name der Tür
	 *
	 */

	public static void newDoor (String eingabe) {

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			String sql = "CREATE TABLE IF NOT EXISTS Doors " +
					"(PK_Doors INTEGER PRIMARY KEY AUTOINCREMENT," +
					" Doorname TEXT NOT NULL)";

			System.out.println(sql);
			stmt.executeUpdate(sql);

			String insert = "INSERT INTO Doors (Doorname)" +
					"VALUES ('" + eingabe + "')";

			stmt.executeUpdate(insert);
			stmt.close();
			c.close();

			System.out.println("Successfull!");
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

	/**
	 * 
	 * Methode, welche alle Türen in einer Liste ausgibt
	 * 
	 * @return
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

			} else {
				
				System.out.println("Table Doors is not created yet.");
				
			}
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return data;

	}

	public static void delDoor (String delName) {

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			String delDoor = "DELETE FROM Doors WHERE Doorname = " + delName;
			stmt.executeUpdate(delDoor);

			System.out.println("Successfully deleted Door: " + delName);

			stmt.close();
			c.close();
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

}
