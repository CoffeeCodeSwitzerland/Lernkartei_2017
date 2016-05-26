package database;

import java.sql.Connection;
import java.sql.DriverManager;

import debug.Logger;

/**
 * @author WISS
 *
 */
public abstract class MYSQLConnector extends SQLHandler {

	private static String	username	= "prototyp";
	private static String	password	= "prototyp";

	private static String	mysqlURL	= "jdbc:mariadb://192.168.3.150:3306/userdb";
	private static String	mysqlDriver	= "com.mysql.jdbc.Driver";
	protected static Connection c = null;

	public static void setConnection(String dbURL) {
		stmt = null;
		c = null;
		try {
			Class.forName(mysqlDriver);
			c = DriverManager.getConnection(mysqlURL, username, password);
			stmt = c.createStatement();
		} catch (Exception e) {
			if (dbURL == null)
				dbURL = "{null}";
			Logger.log("MYSQLConnector.setConnection(URL: " + dbURL + "): " + e.getMessage());
			closeDB(c);
		}
	}
}
