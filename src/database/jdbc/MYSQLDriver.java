package database.jdbc;

import java.sql.DriverManager;

import debug.Logger;

/**
 * Abstract class MYSQL Driver
 * 
 * @author hugo-lucca
 */
public abstract class MYSQLDriver extends DBDriver {

	private static final String	sqlDriver	= "com.mysql.jdbc.Driver";
	private static final String	dbDriverURL	= "jdbc:mariadb://192.168.3.150:3306/userdb";
	private String	username;
	private String	password;

	MYSQLDriver () {
		username	= "root";
		password	= "root";
	}
	
	MYSQLDriver (String newUser, String newPassword) {
		username	= newUser;
		password	= newPassword;
	}
	
	public void setConnection(String dbURL) {
		try {
			Class.forName(sqlDriver);
			connection = DriverManager.getConnection(dbDriverURL, username, password);
			stmt = connection.createStatement();
		} catch (Exception e) {
			if (dbURL == null) dbURL = "{null}";
			Logger.out("MYSQLDriver.setConnection(URL: " + dbURL + "): " + e.getMessage());
			closeDB();
		}
	}
}
