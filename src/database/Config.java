package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import debug.Logger;


public class Config {

	// Connection information: URL & Driver

	private final static String driver = "org.sqlite.JDBC";

	private final static String urlBase	= "jdbc:sqlite:" + globals.Environment.getDatabasePath();

	public static String getUrlbase() {
		return urlBase;
	}

	private final static String configURL	= urlBase + "config.db";

	public static String getConfigURL() {
		return configURL;
	}

	public static String getDriver() {
		return driver;
	}

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

		Connection c = null;
		Statement stmt = null;

		try {

			Class.forName(driver);
			c = DriverManager.getConnection(Config.getConfigURL());
			stmt = c.createStatement();

			// Tabelle erstellen

			String newTbl = "CREATE TABLE IF NOT EXISTS config ("
					+ "PK_Cfg INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "Key TEXT NOT NULL,"
					+ "Value TEXT NOT NULL)";

			debug.Debugger.out(newTbl + "\n\nTable generated!");
			stmt.executeUpdate(newTbl);

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

			stmt.close();
			c.close();

		}
		catch (Exception e) {
			Logger.log(e.getMessage());
		}

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
			c = DriverManager.getConnection(Config.getConfigURL());
			stmt = c.createStatement();
			c.setAutoCommit(false);

			String getTbl = "SELECT tbl_name FROM sqlite_master WHERE type='table'";
			ResultSet tbl = stmt.executeQuery(getTbl);

			if (!tbl.next()) {
				debug.Debugger.out("Table not existent, no Values are generated yet!");
				tbl.close();
				stmt.close();
				c.close();
				return value;
			}

			String getValue = "SELECT Value FROM config WHERE Key = '" + key + "'";
			ResultSet rs = stmt.executeQuery(getValue);

			if (rs.next()) {
				value = rs.getString("Value");
				rs.close();
				stmt.close();
				c.close();
				return value;
			}
			else {
				debug.Debugger.out("No Values with this Key exist!");
				rs.close();
				stmt.close();
				c.close();
				return value;
			}

		}
		catch (Exception e) {
			Logger.log(e.getMessage());
		}

		return value;

	}

}
