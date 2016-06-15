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

	public static boolean replaceOrInsert2Token(String tabName, String kName, String key, String vName, String value) {
		try {
			// Überprüfen ob bereits ein Token vorhanden ist, wenn ja,
			// überschreiben
			String checkTkn = "SELECT " + kName + " FROM " + tabName + " WHERE " + kName + " = '" + key + "'";
			c.setAutoCommit(false);
			ResultSet rs = stmt.executeQuery(checkTkn);
			if (rs.next()) {
				// SQLite Statement zum Ersetzen des letzten Tokeneintrags
				String replace = "UPDATE " + tabName + " SET " + vName + " = '" + value + "'" 
								+ " WHERE " + kName
						+ " = '" + key + "'";
				c.setAutoCommit(true);
				stmt.executeUpdate(replace);
				debug.Debugger.out(replace + "\n\nErfolgreich Eintrag erneuert!");
			} else {
				// SQLite Statement zum Erstellen eines neuen Tokens
				String create = "INSERT INTO " + tabName + " (" + kName + ", " + vName + ") " + "VALUES ('" + key
						+ "','" + value + "')";
				stmt.executeUpdate(create);
				c.setAutoCommit(true);
				debug.Debugger.out(create + "\n\nEintrag erstellt!");
			}
			closeDB();
			return true;
		} catch (Exception e) {
			if (key == null)
				key = "{null}";
			if (value == null)
				value = "{null}";
			Logger.log("SQLiteConnector.replaceOrInsertToken(" + key + "," + value + ")" + e.getMessage());
		}
		closeDB();
		return false;
	}

	protected static ResultSet seekSQL(String query) {
		try {
			c.setAutoCommit(false);
			ResultSet result = stmt.executeQuery(query);
			c.setAutoCommit(true);
			return result;
		} catch (Exception e) {
			if (stmt == null) {
				Logger.log("SQLHandler.seekInTable(...): open first!");
			}
			Logger.log("SQLHandler.seekInTable(" + query + ")");
			Logger.log("SQLHandler.seekInTable(..): " + e.getMessage());
		}
		try {
			c.setAutoCommit(true);
		} catch (Exception e) {};
		return null;
	}

	protected static ResultSet seekInTable(String tabName, String attName, String pkey, String value) {
		return seekSQL( "SELECT " + attName + " FROM " + tabName + " WHERE " + pkey + " = " + value );
	}

	protected static ResultSet seekInTable (String tabName, String attName, String value) {
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
