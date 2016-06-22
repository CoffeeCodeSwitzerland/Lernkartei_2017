package database.jdbc;

import java.sql.DriverManager;

import debug.Logger;

/**
 * @author WISS
 *
 */
public abstract class SQLiteDriver extends DBDriver {

	private final static String sqlDriver = "org.sqlite.JDBC";
	protected String urlBase = "jdbc:sqlite:" + globals.Environment.getDatabasePath();
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
}
