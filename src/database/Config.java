package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import debug.Logger;


public class Config extends SQLiteConnector {

	// Connectioninformationen URL & Driver

	private static String	url			= "jdbc:sqlite:" +  globals.Environment.getDatabasePath()
	 									 + "config.db";
	private static String	driver	= "org.sqlite.JDBC";

	/**
	 * Neuer Eintrag in der Datenbank config erstellen
	 * 
	 * @param key
	 *            --> Welche Art von Wert mitgeliefert werden soll
	 * @param value
	 *            --> Der absolute Wert welcher gesetzt wird
	 */

	public static void setValue (String key, String value) {

		// Verbindung und Aktionen mit der Datenbank

		Database.setConnection(url);
		try {
			// Tabelle erstellen
//			String newTbl = "CREATE TABLE IF NOT EXISTS config ("
//					+ "PK_Cfg INTEGER PRIMARY KEY AUTOINCREMENT,"
//					+ "Key TEXT NOT NULL,"
//					+ "Value TEXT NOT NULL)";
//
//			debug.Debugger.out(newTbl + "\n\nTable generated!");
//			stmt.executeUpdate(newTbl);

			String attr = "PK_Cfg INTEGER PRIMARY KEY AUTOINCREMENT,"
							+ "Key TEXT NOT NULL,"
							+ "Value TEXT NOT NULL";
			createTableIfNotExists("config", attr);

			// Überprüfen ob bereits ein Token vorhanden ist, wenn ja,
			// überschreiben

			String checkTkn = "SELECT Key FROM config WHERE Key = '" + key + "'";
			c.setAutoCommit(false);

			ResultSet rs = stmt.executeQuery(checkTkn);
			if (rs.next()) {
				// SQLite Statement zum Ersetzen des letzten Tokeneintrags

				String replace = "UPDATE config SET Value = '" + value + "' "
						+ "WHERE Key = '" + key + "'";

				c.setAutoCommit(true);
				stmt.executeUpdate(replace);
				debug.Debugger.out(replace + "\n\nErfolgreich Eintrag erneuert!");
			}
			else {
				// SQLite Statement zum Erstellen eines neuen Tokens

				String create = "INSERT INTO config (Key, Value) "
						+ "VALUES ('" + key + "','" + value + "')";

				stmt.executeUpdate(create);
				c.setAutoCommit(true);
				debug.Debugger.out(create + "\n\nEintrag erstellt!");
			}
		}
		catch (Exception e) {
			if (key==null) key="{null}";
			if (value==null) value="{null}";
			Logger.log("Config.setConnection("+key+","+value+")"+e.getMessage());
		}
		closeDB(c);
	}

	/**
	 * Methode welche den Wert eines bestimmten Key's zurückliefert
	 * 
	 * @param key
	 *            --> Der Key, von welchem der Wert zurückgeliefert werden soll
	 * @return --> Retourniert den Wert mit dem Key von oben
	 */

	public static String getValue (String key) {

		Connection c = null;
		Statement stmt = null;
		String value = null;

		try {

			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			c.setAutoCommit(false);

			String getTbl = "SELECT tbl_name FROM sqlite_master WHERE type='table'";
			ResultSet tbl = stmt.executeQuery(getTbl);

			if (!tbl.next()) {
				debug.Debugger.out("Table not existent, no Values are generated yet!");
				stmt.close();
				c.close();
				return value;
			}

			String getValue = "SELECT Value FROM config WHERE Key = '" + key + "'";
			ResultSet rs = stmt.executeQuery(getValue);

			if (rs.next()) {
				value = rs.getString("Value");
				stmt.close();
				c.close();
				return value;
			}
			else {
				debug.Debugger.out("No Values with this Key exist!");
				stmt.close();
				c.close();
				return value;
			}

		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return value;

	}

}
