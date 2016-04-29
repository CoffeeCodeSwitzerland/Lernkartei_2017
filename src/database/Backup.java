package database;

import java.sql.*;
import java.util.ArrayList;


public class Backup {
	// Connectioninformationen URL & Driver

	private static String	url			= "jdbc:mariadb://192.168.3.150:3306/backup";
	private static String	username	= "prototyp";
	private static String	password	= "prototyp";
	private static String	driver		= "com.mysql.jdbc.Driver";

	/**
	 * Backup von allen Daten welche aus pullFromStock() geholt werden können,
	 * hier mitgeben
	 * 
	 * @param values
	 *            --> ArrayList<String[]> von pullFromStock()
	 */

	public static void BackUp (ArrayList<String[]> values) {
		Connection c = null;
		Statement stmt = null;

		try {
			// Verbindungsaufbau zur Datenbank

			Class.forName(driver);
			c = DriverManager.getConnection(url, username, password);
			stmt = c.createStatement();

			// Tabelle wählen

			String usedb = "USE " + "backup";
			stmt.executeQuery(usedb);

			// Tabelle löschen wenn sie existiert, sodass anschliessend die
			// komplette Tabelle neu
			// erfasst wird

			String delTbl = "DROP TABLE IF EXISTS Stock";
			stmt.executeUpdate(delTbl);

			// Erstellt die Backup Tabelle in der Datenbank

			String sql = "CREATE TABLE IF NOT EXISTS Stock " +
					"(PK_Stk INT PRIMARY KEY AUTO_INCREMENT," +
					" Backside TEXT NOT NULL," +
					" Frontside TEXT NOT NULL," +
					" Description TEXT," +
					" Set_ID INTEGER," +
					" Priority INT NOT NULL," +
					" Color TEXT NOT NULL" + ")";

			stmt.executeUpdate(sql);

			// Insert Statement, welches die Backup Tabelle mit den Backup füllt

			for (int i = 0; i < values.size(); i++) {
				String insert = "INSERT INTO Stock (Backside, Frontside, Description, Set_ID, Priority, Color)" +
						"VALUES ('" + values.get(i)[1] + "','"
						+ values.get(i)[2] + "','"
						+ values.get(i)[3] + "','"
						+ values.get(i)[4] + "','"
						+ values.get(i)[5] + "','"
						+ values.get(i)[6] + "')";

				stmt.executeUpdate(insert);
			}

			stmt.close();
			c.close();

		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		System.out.println("Backup erfolgreich erstellt!");

	}

}
