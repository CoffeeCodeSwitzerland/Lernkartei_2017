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

	public static void newKategorie (String eingabe, String fk_door) {

		Connection c = null;
		Statement stmt = null;
		Integer FK_ID = 0;

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
			System.out.println("Successful");

			c.setAutoCommit(false);
			
			ResultSet id = stmt.executeQuery("SELECT PK_Doors FROM Doors WHERE Doorname = '" + fk_door + "'");
			
			if (id.next()) {			
			FK_ID = id.getInt("PK_Doors");
			} else {
				System.out.println("Keine Tür mit dem Namen vorhanden");
				FK_ID = 0;
			}

			c.setAutoCommit(true);

			String insert = "INSERT INTO Kategorie (Kategorie, FK_Door)" +
					"VALUES ('" + eingabe + "', " + FK_ID + ")";

			stmt.executeUpdate(insert);

			System.out.println("Successful");
			
			id.close();
			stmt.close();
			c.close();
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

	public static ArrayList<String[]> getKategorien (String doorname) {

		Connection c = null;
		Statement stmt = null;
		Integer FK_ID = 0;

		ArrayList<String[]> datensatz = new ArrayList<String[]>();

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			c.setAutoCommit(false);
			stmt = c.createStatement();
			
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

				String[] data = new String[2];

				String ID = Integer.toString(rs.getInt("PK_Kategorie"));
				String Kategorie = rs.getString("Kategorie");

				data[0] = ID;
				data[1] = Kategorie;

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

	public static void delKategorie (int delID) {
		
				
		
	}

}
