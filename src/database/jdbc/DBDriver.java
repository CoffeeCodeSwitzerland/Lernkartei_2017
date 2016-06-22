package database.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import debug.Logger;

/**
 * Abstract class DB Driver
 * 
 * @author hugo-lucca
 */
public abstract class DBDriver {
	
	protected Connection connection;
	protected Statement stmt;

	protected DBDriver () {
		connection = null;
		stmt = null;
	}
	
	/**
	 * Executes a query (for Select only)
	 * @param query
	 * @return the result set or null for errors
	 */
	protected ResultSet executeQuery (String query) {
		try {
			if (stmt != null && query != null) {
				connection.setAutoCommit(false);
				ResultSet resultSet = stmt.executeQuery(query);
				connection.setAutoCommit(true);
				return resultSet; // returns a RestultSet, is never null
			} else {
				Logger.out("invalid SQL query!", query);
			}
		} catch (Exception e) {
			Logger.out(e.getMessage(), query);
		}
		closeDB();
		return null;  // null for errors
	}
	
	protected int executeCommand (String SQLcommand) {
		//main.debug.Debugger.out("execute SQL CMD...");
		try {
			if (stmt != null) {
				if (SQLcommand != null) {
					return stmt.executeUpdate(SQLcommand); // for CREATE, UPDATE, DELETE, INSERT
					// returns 1, 2 or 0
				} else {
					Logger.out("invalid SQL command!", SQLcommand);
				}
			} else {
				Logger.out("statement is null!", SQLcommand);
			}
		} catch (Exception e) {
			Logger.out(e.getMessage(), SQLcommand);
		}
		closeDB();
		return -1; // -1 for errors
	}
	
	protected void closeDB() {
		//main.debug.Debugger.out("close connection...");
		try {
			if (stmt != null)
				stmt.close();
			if (connection != null) {
				connection.setAutoCommit(true);
				connection.close();
			}
		} catch (Exception e) { };
		stmt = null;
		connection = null;
	}
}
