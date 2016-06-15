package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import debug.Logger;

/**
 * @author WISS
 *
 */
public abstract class SQLiteConnector extends SQLHandler {

	protected final static String driver = "org.sqlite.JDBC";
	protected final static String urlBase = "jdbc:sqlite:" + globals.Environment.getDatabasePath();
	protected static Connection c = null;

	public static void setConnection(String dbURL) {
		stmt = null;
		c = null;
		try {
			Class.forName(driver);
			c = DriverManager.getConnection(dbURL);
			stmt = c.createStatement();
		} catch (Exception e) {
			if (dbURL == null)
				dbURL = "{null}";
			Logger.log("SQLiteConnetor.setConnection(URL: " + dbURL + "): " + e.getMessage());
			closeDB();
		}
	}
	
	protected static void closeDB() {
		try {
			if (stmt != null)
				stmt.close();
			if (c != null)
				c.close();
		} catch (Exception e2) {
		}
		;
		stmt = null;
	}

	/**
	 * 
	 * 
	 */
	public static boolean replaceOrInsert2Token(String tabName, String kName, String key, 
												String vName,   String value) {
		try {
			// Überprüfen ob bereits ein Token vorhanden ist, wenn ja,
			// überschreiben
			String checkTkn = "SELECT " + kName + " FROM " + tabName + " WHERE " + kName + " = '" + key + "'";
			c.setAutoCommit(false);
			ResultSet rs = stmt.executeQuery(checkTkn);
			if (rs.next()) {
				// mindestens einen Eintrag gefunden:
				// SQLite Statement zum Ersetzen des letzten Tokeneintrags
				// TODO prüfen, dass es nur einer ist (sollte eigentlich, wenn unique)...
				//
				String replace = "UPDATE " + tabName   + " SET " + vName + " = '" + value + "'" 
								           + " WHERE " + kName
						                   + " = '"    + key     + "'";
				c.setAutoCommit(true);
				stmt.executeUpdate(replace);
				debug.Debugger.out(replace + "\n\nErfolgreich Eintrag erneuert!");
			} else {
				// Kein Eintrag gefunden:
				// SQLite Statement zum Erstellen eines neuen Tokens
				String create = "INSERT INTO " + tabName + " ("  + kName + ", " + vName + ") " + "VALUES ('" + key
						                       + "','"   + value + "')";
				stmt.executeUpdate(create);
				c.setAutoCommit(true);
				debug.Debugger.out(create + "\n\nEintrag erstellt!");
			}
			closeDB();
			return true;
		} catch (Exception e) {
			if (key == null) key = "{null}";
			if (value == null) value = "{null}";
			Logger.log("SQLiteConnector.replaceOrInsertToken(" + key + "," + value + ")" + e.getMessage());
			debug.Debugger.out("SQLiteConnector.replaceOrInsertToken(" + key + "," + value + ")" + e.getMessage());
		}
		closeDB();
		return false;
	}

	protected static ResultSet seekSQL(String query) {
		ResultSet result = null;
		try {
			c.setAutoCommit(false);
			result = stmt.executeQuery(query);
			c.setAutoCommit(true);
			debug.Debugger.out("seekSQL:'"+query+"'->OK!");
			return result;
		} catch (Exception e) {
			if (stmt == null) {
				Logger.log("SQLHandler.seekInTable(...): open first!");
				debug.Debugger.out("?!?SQLLiteConnector.seekSQL.1:''->NOT OK!");
			} else {
				// TODO kann im Moment nicht zwischen leer und nicht existent unterscheiden...
				// evtl. nicht von Nöten.
//				if (result == null) {
//					Logger.log("SQLHandler.seekInTable(" + query + ")");
//					Logger.log("SQLHandler.seekInTable(..): " + e.getMessage());
//					debug.Debugger.out("?!?SQLLiteConnector.seekSQL.2:'"+query+"'->NOT OK!");
//				} else {
					debug.Debugger.out("SQLLiteConnector.seekSQL.3:'"+query+"'->TABLE EMPTY!");
//				}
			}
		}
		try {
			c.setAutoCommit(true);
		} catch (Exception e) {};
		return null;
	}

	protected static ResultSet seekInTable(String tabName, String attName, String pkey, String value) {
		return seekSQL( "SELECT " + attName + " FROM " + tabName + " WHERE " + pkey + " = '" + value+"'" );
	}

	protected static ResultSet seekInTable(String tabName, String attName, String pkey, int value) {
		return seekSQL( "SELECT " + attName + " FROM " + tabName + " WHERE " + pkey + " = " + value );
	}

	protected static ResultSet seekInTable (String tabName, String attName, String value) {
		return seekInTable(tabName, attName, tabName, value);
	}

	protected static ResultSet seekInTable (String tabName, String attName, int value) {
		return seekInTable(tabName, attName, tabName, value);
	}

	protected static ResultSet seekInTable(String tabName, String attName) {
		return seekSQL( "SELECT " + attName + " FROM " + tabName );
	}

	public static boolean delEntryIfExists (String tabName, String seekAttr, String setName) {
		boolean worked = false;
		try {
			ResultSet checkExists = seekInTable(tabName, "*", seekAttr, setName);
			if (checkExists.next()) {
				checkExists.close();
			} else {
				checkExists.close();
				deleteSQL (tabName, seekAttr, setName);
				worked = true;
			}
		}
		catch (Exception e) {
			if (setName==null) setName = "{null}";
			debug.Debugger.out("SQLiteConnector.delEntyIfExists("+setName+"): "+e.getMessage());
			Logger.log("SQLiteConnector.delEntyIfExists("+setName+"): "+e.getMessage());
		}
		return worked;
	}
}
