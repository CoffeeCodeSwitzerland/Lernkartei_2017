package database.jdbc;

import java.sql.DriverManager;
import java.sql.ResultSet;

import debug.Logger;

/**
 * @author WISS
 *
 */
public abstract class SQLiteDriver extends DBDriver {

	private final static String sqlDriver = "org.sqlite.JDBC";
	protected static String urlBase = "jdbc:sqlite:" + globals.Environment.getDatabasePath();
	private String dbURL;

	public SQLiteDriver () {
		dbURL = globals.Globals.db_name + ".db";
	}
	
	public void setDriver (String newDBName) {
		if (newDBName != null) {
			if (newDBName.endsWith(".db")) {
				dbURL = newDBName;
			} else {
				dbURL = newDBName + ".db";
			}
		}
	}

	/**
	 * 
	 */
	public int setConnection() {
		try {
			if (stmt == null || connection == null) {
				//main.debug.Debugger.out("set connection..."+sqlDriver);
				Class.forName(sqlDriver);
				//main.debug.Debugger.out("set connection..."+urlBase+dbURL);
				connection = DriverManager.getConnection(urlBase+dbURL);
				if (connection != null) {
					//main.debug.Debugger.out("set statement...");
					stmt = connection.createStatement();
				} else {
					stmt = null;
					Logger.out("connection is null (no Driver found... add JDBC-JAR)!");
				}
					
				return 1;
			}
			return 0;
		} catch (Exception e) {
			Logger.out("connection error (JDBC-JAR?)..."+e.getMessage()+e.getCause());
			closeDB();
		}
		return -1;
	}
	/**
	 * Executes a query (for Select only)
	 * @param query
	 * @return the result set or null for errors
	 */
	protected ResultSet executeQuery (String query) {
		try {
			if (stmt == null) {
				Logger.out("connection is close for query "+query);
				//this.setConnection();
				return null;
			}
			if (query != null) {
				connection.setAutoCommit(false);
				ResultSet resultSet = stmt.executeQuery(query);
				connection.setAutoCommit(true);
				return resultSet; // returns a RestultSet, is never null
			} else {
				Logger.out("invalid SQL query {null}!");
			}
		} catch (Exception e) {
			Logger.out(e.getMessage(), query);
		}
		closeDB();
		return null;  // null for errors
	}
	
	public int executeCommand (String SQLcommand) {
		//main.debug.Debugger.out("execute SQL CMD...");
		try {
			if (stmt == null) {
				Logger.out("connection is close for command "+SQLcommand);
				//this.setConnection();
				return -1;
			}
			else {
				if (SQLcommand != null) {
					return stmt.executeUpdate(SQLcommand); // for CREATE, UPDATE, DELETE, INSERT
					// returns 1, 2 or 0
				} else {
					Logger.out("invalid SQL command!", SQLcommand);
				}
			}
		} catch (Exception e) {
			Logger.out(e.getMessage(), SQLcommand);
		}
		closeDB();
		return -1; // -1 for errors
	}
	
}
