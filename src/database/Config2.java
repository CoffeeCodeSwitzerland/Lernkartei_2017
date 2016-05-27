package database;

import java.sql.ResultSet;

import debug.Logger;


public class Config2 extends SQLiteConnector {

	private static String url = urlBase + "config.db";
	private static String name = "config";

	public static void setValue (String key, String value) {

		Database.setConnection(url);
		// Tabelle erstellen

		String attr = "PK_Cfg INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "Key TEXT NOT NULL,"
				+ "Value TEXT NOT NULL";
		createTableIfNotExists(name, attr);

		replaceOrInsert2Token(name, "key", key, "value", value);

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
		Database.setConnection(url);

		try {
			c.setAutoCommit(false);
			String getTbl = "SELECT tbl_name FROM config.sqlite_master WHERE type='table' "
							+ "AND tbl_name = '" + name + "'";
			ResultSet tbl = stmt.executeQuery(getTbl);

			if (!tbl.next()) {
				debug.Debugger.out("Table '" + name + "' not existent, no Values are generated yet!");
				closeDB();
				return value;
			}
			
			String getValue = "SELECT Value FROM "+name+" WHERE Key = '" + key + "'";
			ResultSet rs = stmt.executeQuery(getValue);

			if (rs.next()) {
				value = rs.getString("Value");
				closeDB();
				return value;
			}
			else {
				debug.Debugger.out("No Values with this Key exist!");
				closeDB();
				return value;
			}

		}
		catch (Exception e) {
			Logger.log("Database.getValue(): " + e.getMessage());
		}
		closeDB();
		return value;

	}

}
