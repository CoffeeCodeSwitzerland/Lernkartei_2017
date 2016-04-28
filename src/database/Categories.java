package database;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class Categories {
	// URL und Driver

	private static String	url		= "jdbc:sqlite:test.db";
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

	public static int newKategorie (String eingabe, String fk_door) {

		Connection c = null;
		Statement stmt = null;
		Integer FK_ID = 0;
		Integer errorMsg = -9;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			System.out.println("Opened database successfully");

			String sql = "CREATE TABLE IF NOT EXISTS Kategorie "
					+ "(PK_Kategorie INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " Kategorie TEXT NOT NULL,"
					+ " FK_Door INTEGER NOT NULL)";

			System.out.println(sql);
			stmt.executeUpdate(sql);
			System.out.println("Table creation successful");

			c.setAutoCommit(false);
			
			ResultSet id = stmt.executeQuery("SELECT PK_Doors FROM Doors WHERE Doorname = '" + fk_door + "'");
			
			if (id.next()) {			
			FK_ID = id.getInt("PK_Doors");
			} else {
				System.out.println("Keine Tür mit dem Namen vorhanden");
				errorMsg = -1;
			}
			
			id.close();
			
			ResultSet check = stmt.executeQuery("SELECT * FROM Kategorie WHERE Kategorie = '" + eingabe + "'");
			if (check.next()) {
				check.close();
				System.out.println("Kategorie mit diesem Namen bereits vorhanden");
				errorMsg = -2;
			} else {
				check.close();
			}

			c.setAutoCommit(true);

			String insert = "INSERT INTO Kategorie (Kategorie, FK_Door)" +
					"VALUES ('" + eingabe + "', " + FK_ID + ")";

			stmt.executeUpdate(insert);

			System.out.println("Insert Successful");
			
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
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			
			String sql = "CREATE TABLE IF NOT EXISTS Kategorie "
					+ "(PK_Kategorie INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " Kategorie TEXT NOT NULL,"
					+ " FK_Door INTEGER NOT NULL)";

			System.out.println(sql);
			stmt.executeUpdate(sql);
			
			System.out.println("Successful");
			c.setAutoCommit(false);
			
			ResultSet id = stmt.executeQuery("SELECT PK_Doors FROM Doors WHERE Doorname = '" + doorname + "'");
			
			if (id.next()) {			
			FK_ID = id.getInt("PK_Doors");
			} else {
				System.out.println("Keine Kategorien mit dieser Tür vorhanden");
				FK_ID = 0; 
			}
			
			id.close();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM Kategorie WHERE FK_Door = " + FK_ID);

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
	 * Löscht den gewählten Eintrag
	 * 
	 * @param category --> Name der zu löschenden Kategorie
	 */
	
	public static boolean delKategorie (String category) {

		Connection c = null;
		Statement stmt = null;
		boolean worked = false;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			
			String sql = "CREATE TABLE IF NOT EXISTS Kategorie "
					+ "(PK_Kategorie INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " Kategorie TEXT NOT NULL,"
					+ " FK_Door INTEGER NOT NULL)";
			
			System.out.println(sql);
			stmt.executeUpdate(sql);
			
			c.setAutoCommit(false);

			ResultSet del = stmt.executeQuery("SELECT Kategorie FROM Kategorie WHERE Kategorie = '" + category + "'");

			if (del.next()) {

				del.close();
				c.setAutoCommit(true);
				String delDoor = "DELETE FROM Kategorie WHERE Kategorie = '" + category + "'";
				stmt.executeUpdate(delDoor);

				System.out.println("Successfully deleted Categorie: " + category);

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
