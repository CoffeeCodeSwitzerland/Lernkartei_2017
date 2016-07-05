package database.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;

import debug.Logger;

/**
 * Abstract class DB Driver
 * 
 * @author hugo-lucca
 */
public abstract class DBDriver {

	private String sqlDriver = null;
	private Connection connection;
	private Statement stmt;
	private String dbURL = null;
	private ResultSet lastResultSet;
	private String lastSQLCommand;

	protected DBDriver(String newSqlDriver) {
		sqlDriver = newSqlDriver;
		connection = null;
		stmt = null;
	}

	/**
	 * For the automatic close of the ResultSet
	 */
	private void closeLastResultSet() {
		try {
			if (lastResultSet != null)
				lastResultSet.close();
		} catch (Exception e) {
		}
		lastResultSet = null;
	}

	public void closeDB() {
		// main.debug.Debugger.out("close connection...");
		try {
			if (stmt != null)
				stmt.close();
			if (connection != null) {
				connection.setAutoCommit(true);
				connection.close();
			}
		} catch (Exception e) {
		}
		;
		stmt = null;
		connection = null;
	}

	/**
	 * To open a SQL DB connection
	 * 
	 * @return 1, 0 or -1 on error
	 */
	public int setConnection() {
		if (getDbURL() != null) {
			try {
				if (stmt == null || connection == null) {
					// main.debug.Debugger.out("set connection..."+sqlDriver);
					Class.forName(sqlDriver);
					// main.debug.Debugger.out("set
					// connection..."+urlBase+dbURL);
					this.setConnection(DriverManager.getConnection(this.getDbURL()));
					return 1;
				}
				return 0;
			} catch (Exception e) {
				Logger.out("connection error (JDBC-JAR?)..." + e.getCause());
			}
			closeDB();
			return -1;
		}
		Logger.out("connection name is null");
		return -2;
	}

	/**
	 * To execute a SQL query (like SELECT)
	 * 
	 * @param query
	 * @return the result set or null for errors
	 */
	protected boolean doQuery(String query) {
		if (stmt == null) {
			Logger.out("connection was closed before query " + query);
			setConnection();
		}
		try {
			closeLastResultSet();
			setLastSQLCommand(query);
			connection.setAutoCommit(false);
			lastResultSet = stmt.executeQuery(query); // returns a RestultSet,
														// is never null
			connection.setAutoCommit(true);
			return true;
		} catch (Exception e) {
			Logger.out(query);
		}
		return false; // false for errors
	}

	/**
	 * To execute a SQL command (except a query like SELECT)
	 * 
	 * @param SQLcommand
	 * @return 0, 1,2 or -1 on error
	 */
	protected int doCommand(String SQLcommand) {
		if (stmt == null) {
			Logger.out("connection was closed before command " + SQLcommand);
			setConnection();
		}
		try {
			setLastSQLCommand(SQLcommand);
			// returns 0, 1, 2, ...
			return stmt.executeUpdate(SQLcommand); // for CREATE,
													// UPDATE, DELETE,
													// INSERT
		} catch (SQLTimeoutException e) {
			Logger.out("SQL CMD Timeout Exception: " + e.getCause(), SQLcommand);
		} catch (SQLException e2) {
			Logger.out("SQL CMD Exception: " + e2.getMessage() + "/" + e2.getCause(), SQLcommand);
		} catch (Exception e3) {
			Logger.out("Exception: " + e3.getMessage() + "/" + e3.getCause(), SQLcommand);
		}
		return -1;
	}

	/**
	 * To check if there is a valid result set
	 * 
	 * @return true if ok, false if empty or on errors
	 */
	public boolean isThereAResult() {
		try {
			if (lastResultSet != null) {
				return lastResultSet.next();
			}
			Logger.out("did not have any resultset ready");
		} catch (SQLException e) {
		}
		return false;
	}

	/**
	 * To get a value of specific attribute of the SQL result set
	 * 
	 * @param attr
	 * @return the value or null if not found or on errors
	 */
	public String getResultValueOf(String attr) {
		if (attr == null) {
			Logger.out("may not seek a {null} attribute in the resultset");
			return null;
		}
		try {
			if (lastResultSet != null) {
				return lastResultSet.getString(attr);
			}
			Logger.out("did not have any resultset ready to get an attribute", attr);
		} catch (SQLException e) {
			Logger.out("did not found the attribute in the resulset:"+e.getMessage(), attr);
		}
		return null;
	}

	/**
	 * To get a positive integer value of specific attribute of the SQL result
	 * set
	 * 
	 * @param attr
	 * @return the integer value >= 0 or -1, -2 on errors
	 */
	public int getResultPIntValueOf(String attr) {
		if (attr == null) {
			Logger.out("may not seek a {null} attribute in the resultset");
			return -2;
		}
		try {
			if (lastResultSet != null) {
				return lastResultSet.getInt(attr);
			}
			Logger.out("did not have any resultset ready to get the integer value of the attribute", attr);
		} catch (SQLException e) {
			Logger.out("did not found the integer value of the attribute in the resulset", attr);
		}
		return -1;
	}

	/**
	 * To get the first attribute value of the SQL result set
	 * 
	 * @return the value or null if not found or on errors
	 */
	public String getFirstResultValue() {
		try {
			if (lastResultSet != null) {
				return lastResultSet.getString(1);
			}
			Logger.out("did not have any resultset ready to get the first attribute");
		} catch (SQLException e) {
			Logger.out("did not found the first attribute in the resulset",e.getMessage());
		}
		return null;
	}

	/**
	 * SETTERs and Getters
	 */
	public String getDbURL() {
		return dbURL;
	}

	public void setDbURL(String dbURL) {
		this.dbURL = dbURL;
	}

	public String getLastSQLCommand() {
		return lastSQLCommand;
	}

	public ResultSet getLastResultSet() {
		return lastResultSet;
	}

	public String setLastSQLCommand(String newLastSQLCommand) {
		lastSQLCommand = newLastSQLCommand;
		return lastSQLCommand;
	}

	public void setLastResultSet(ResultSet newLastResultSet) {
		lastResultSet = newLastResultSet;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
		try {
			if (this.connection != null) {
				stmt = this.connection.createStatement();
			} else {
				stmt = null;
				Logger.out("connection is null (no Driver found... add JDBC-JAR)!");
			}
		} catch (Exception e) {
			Logger.out("connection does not work: "+e.getMessage());
		}
	}
	
	/**
	 * ABSTRACT methods
	 */
	public abstract int executeCommand(String SQLcommand);
	public abstract boolean executeQuery(String query);
}
