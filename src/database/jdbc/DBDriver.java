package database.jdbc;

import java.sql.Connection;
import java.sql.Statement;

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
