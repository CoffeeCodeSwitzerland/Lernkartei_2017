package database;

import java.sql.ResultSet;

import debug.Logger;

public class Config extends SQLiteConnector {

	// Connection information: URL & Driver
	private final static String configURL = urlBase + "config.db";
	private static boolean tableExists = false;

	protected static String myTableName  = "config";
	protected static String mySeekAttribute = "Key";
	protected static String myPrimaryKey = "PK_Cfg";
//	private   static String myAttributeList = mySeekAttribute+", Value";
	private   static String myAttributes = 
								myPrimaryKey + " INTEGER PRIMARY KEY AUTOINCREMENT," + 
								mySeekAttribute + " TEXT NOT NULL," + 
								"Value TEXT NOT NULL";
	
	
	// TODO check if key OR value is null!
	// TODO fix set value
	
	/**
	 * Neuer Eintrag in der Datenbank config erstellen
	 * 
	 * @param key
	 *            --> Welche Art von Wert mitgeliefert werden soll
	 * @param value
	 *            --> Der absolute Wert welcher gesetzt wird
	 */
	public static void setValue(String key, String value)
	{
		setConnection(configURL);
		try {
//			debug.Debugger.out("Config.setValue("+key+","+value+")");
			if (!tableExists)
				tableExists = createTableIfNotExists(myTableName, myAttributes);
			replaceOrInsert2Token(myTableName, mySeekAttribute, key, "Value", value);
		} catch (Exception e) {
			if (key==null) key="{null}";
			if (value==null) value="{null}";
			Logger.log("Config.setConnection("+key+","+value+")"+e.getMessage());
			debug.Debugger.out("Config.setConnection("+key+","+value+")"+e.getMessage());
		}
		closeDB();
	}

	/**
	 * Methode welche den Wert eines bestimmten Key's zurückliefert
	 * 
	 * @param key --> Der Key, von welchem der Wert zurückgeliefert werden soll
	 * @return --> Retourniert den Wert mit dem Key von oben
	 */
	protected static String getKeyValueFromTable(	String tabName, String valueName, String keyName,  
												String key) {
		try {
//			ResultSet tbl = seekInTable(myTableName, "*");
//
//			// TODO check auf 'config' tabelle fehlt (testet im Moment nur, ob eine Tabelle da)
//			if (!tbl.next()) {
//				debug.Debugger.out("Table '"+tabName+"' not existent, no Values are generated yet!");
//				return null;
//			}
			ResultSet rs = seekInTable(tabName, valueName, keyName, key);
			if (rs.next()) {
				return rs.getString(valueName);
			} else {
				debug.Debugger.out("Config.getKeyValueFromTable: No Values for Key '"+key+"' exist!");
			}
		} catch (Exception e) {
			if (keyName==null) keyName="{null}";
			Logger.log("Config.getKeyValueFromTable("+keyName+")"+e.getMessage());
			debug.Debugger.out("Config.getKeyValueFromTable("+keyName+")"+e.getMessage());
		}
		return null;
	}

	/**
	 * Methode die den Wert eines bestimmten Key's zurückliefert
	 * 
	 * @param key --> Der Key, von welchem der Wert zurückgeliefert werden soll
	 * @return --> Retourniert den Wert mit dem Key von oben
	 */
	public static String getValue(String key) {
		setConnection(configURL);
		String value = null;
		try {
//			debug.Debugger.out("Config.getValue("+key+")");
			if (!tableExists)
				tableExists = createTableIfNotExists(myTableName, myAttributes);
			c.setAutoCommit(false);
			value = getKeyValueFromTable(Config.myTableName,"Value",Config.mySeekAttribute,key);
//			if (value == null) {
//				debug.Debugger.out("Config.getValue("+key+"): null!");
//			} else {
//				debug.Debugger.out("Config.getValue("+key+"):"+value);
//			}
		} catch (Exception e) {
			if (key==null) key="{null}";
			Logger.log("Config.getValue("+key+")"+e.getMessage());
			debug.Debugger.out("Config.getValue("+key+")"+e.getMessage());
		}
		closeDB();
		return value;
	}
}
