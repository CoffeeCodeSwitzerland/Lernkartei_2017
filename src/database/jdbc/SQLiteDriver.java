package database.jdbc;

import debug.Logger;


/**
 * @author WISS
 *
 */
final public class SQLiteDriver extends DBDriver {

	private String urlBase = "jdbc:sqlite:" + globals.Environment.getDatabasePath();

	public SQLiteDriver(String dbName) {
		super("org.sqlite.JDBC");
		setDbURL(urlBase + dbName);
	}

	/**
	 * To execute a SQL query (like SELECT)
	 * 
	 * @param query
	 * @return the result set or null for errors
	 */
	public boolean executeQuery(String query) {
		if (query != null) {
			if (query.startsWith("SELECT")) {
				return doQuery(query);
			} else {
				Logger.out("invalid SQL query {null}!");
			}
		} else {
			Logger.out("query is {null}!");
		}
		return false; // null for errors
	}

	/**
	 * To execute a SQL command (except a query like SELECT)
	 * 
	 * @param SQLcommand
	 * @return 0, 1,2 or -1 on error
	 */
	public int executeCommand(String SQLcommand) {
		if (SQLcommand != null) {
			if (!SQLcommand.startsWith("SELECT")) {
				return doCommand(SQLcommand);  // for CREATE, UPDATE, DELETE, INSERT
			} else {
				Logger.out("Invalid SQL command to execute", SQLcommand);
			}
		} else {
			Logger.out("Command is null");
		}
		return -1;
	}
}
