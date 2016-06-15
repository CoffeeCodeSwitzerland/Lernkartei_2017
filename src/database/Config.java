package database;

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
			createTableIfNotExists(Config.myTableName, Config.myAttributes);
			replaceOrInsert2Token(Config.myTableName, Config.mySeekAttribute, key, "Value", value);
		} catch (Exception e) {
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

	public static String getKeyValueFromTable(	String tabName, String valueName, String keyName,  
												String key) {
		try {
			ResultSet tbl = seekInTable("sqlite_master", "tbl_name", "type='table' AND tbl_name", tabName);

			// TODO check auf 'config' tabelle fehlt (testet im Moment nur, ob eine Tabelle da)
			if (!tbl.next()) {
				debug.Debugger.out("Table '"+tabName+"' not existent, no Values are generated yet!");
				return null;
			}
		
			ResultSet rs = seekInTable(tabName, valueName, keyName, key);
			if (rs.next()) {
				return rs.getString(valueName);
			} else {
				debug.Debugger.out("No Values with this Key exist!");
			}
		} catch (Exception e) {
			if (keyName==null) keyName="{null}";
			Logger.log("Config.getKeyValueFromTable("+keyName+")"+e.getMessage());
		}
		return null;
	}

	public static String getValue(String key) {

		String value = null;
		Database.setConnection(configURL);
		try {
			c.setAutoCommit(false);
			value = getKeyValueFromTable(Config.myTableName,"Value",Config.mySeekAttribute,key);
			if (value == null) {
				debug.Debugger.out("Config.getValue("+key+"): null!");
			} else {
				debug.Debugger.out("Config.getValue("+key+"):"+value);
			}
		} catch (Exception e) {
			if (key==null) key="{null}";
			Logger.log("Config.getValue("+key+")"+e.getMessage());
		}
		closeDB();
		return value;
	}
}
