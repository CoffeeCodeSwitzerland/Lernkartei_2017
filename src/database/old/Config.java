package database.old;

import java.sql.ResultSet;

import debug.Logger;

public class Config extends SQLiteConnector {

	// Connection information: URL & Driver
	private final static String configURL = urlBase + "config.db";

	protected static String myTableName  = "config";
	protected static String mySeekAttribute = "Key";
	protected static String myPrimaryKey = "PK_Cfg";
//	private   static String myAttributeList = mySeekAttribute+", Value";
	private   static String myAttributes = 
								myPrimaryKey + " INTEGER PRIMARY KEY AUTOINCREMENT," + 
								mySeekAttribute + " TEXT NOT NULL," + 
								"Value TEXT NOT NULL";
	/**
	 * Neuer Eintrag in der Datenbank config erstellen
	 * 
	 * @param key
	 *            --> Welche Art von Wert mitgeliefert werden soll
	 * @param value
	 *            --> Der absolute Wert welcher gesetzt wird
	 */
	public static void setValue(String key, String value) {

		Database.setConnection(configURL);
		try {
//			String attr = "PK_Cfg INTEGER PRIMARY KEY AUTOINCREMENT,"
//							+ "Key TEXT NOT NULL,"
//							+ "Value TEXT NOT NULL";
			createTableIfNotExists(Config.myTableName, Config.myAttributes);

			// Überprüfen ob bereits ein Token vorhanden ist, wenn ja,
			// überschreiben

			String checkTkn = "SELECT Key FROM config WHERE Key = '" + key + "'";
			c.setAutoCommit(false);
			ResultSet rs = stmt.executeQuery(checkTkn);
			c.setAutoCommit(true);
			
			if (rs.next()) {
				// SQLite Statement zum Ersetzen des letzten Tokeneintrags

				String replace = "UPDATE config SET Value = '" + value + "' "
						+ "WHERE Key = '" + key + "'";

				stmt.executeUpdate(replace);
				debug.Debugger.out(replace + "\n\nErfolgreich Eintrag erneuert!");
			}
			else {
				// SQLite Statement zum Erstellen eines neuen Tokens

				String create = "INSERT INTO config (Key, Value) "
						+ "VALUES ('" + key + "','" + value + "')";

				stmt.executeUpdate(create);
				debug.Debugger.out(create + "\n\nEintrag erstellt!");
			}
		}
		catch (Exception e) {
			if (key==null) key="{null}";
			if (value==null) value="{null}";
			Logger.log("Config.setConnection("+key+","+value+")"+e.getMessage());
		}
		closeDB();
	}

	/**
	 * Methode welche den Wert eines bestimmten Key's zurückliefert
	 * 
	 * @param key
	 *            --> Der Key, von welchem der Wert zurückgeliefert werden soll
	 * @return --> Retourniert den Wert mit dem Key von oben
	 */

	public static String getValue (String key) {

		String value = null;

		Database.setConnection(configURL);
		try {
			String getTbl = "SELECT tbl_name FROM sqlite_master WHERE type='table' AND tbl_name = 'config';";
			
			c.setAutoCommit(false);
			ResultSet tbl = stmt.executeQuery(getTbl);
			c.setAutoCommit(true);
			
			if (!tbl.next()) {
				debug.Debugger.out("Config.getValue("+key+"): No table 'config'");
				closeDB();
				return value;
			}

			String getValue = "SELECT Value FROM config WHERE Key = '" + key + "'";
			
			c.setAutoCommit(false);
			ResultSet rs = stmt.executeQuery(getValue);
			c.setAutoCommit(true);

			if (rs.next()) {
				value = rs.getString("Value");
				closeDB();
				return value;
			}
			else {
				debug.Debugger.out("Config.getValue(" + key + "): No Values with this Key exist!");
				closeDB();
				return value;
			}

		}
		catch (Exception e) {
			debug.Debugger.out("Config.getValue(): " + e.getMessage());
			Logger.log("Config.getValue(): " + e.getMessage());
		}
		closeDB();
		return value;
	}
}
