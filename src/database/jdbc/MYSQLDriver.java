package database.jdbc;

import java.sql.DriverManager;

import debug.Logger;

/**
 * Abstract class MYSQL Driver
 * 
 * @author hugo-lucca
 */
final public class MYSQLDriver extends DBDriver {

	private final static String sqlDriver = "com.mysql.jdbc.Driver"; 
	private final String urlBase   = "jdbc:mariadb://192.168.3.150:3306/userdb";
	private String	username;
	private String	password;

	public MYSQLDriver (String dbName) {
		super(sqlDriver);
		setDbURL(urlBase + dbName);
		username	= "root";
		password	= "root";
	}
	
	public MYSQLDriver (String dbName, String newUser, String newPassword) {
		super(sqlDriver);
		setDbURL(urlBase + dbName);
		username	= newUser;
		password	= newPassword;
	}
	
	public void setConnection(String dbURL) {
		try {
			Class.forName(sqlDriver);
			this.setConnection(DriverManager.getConnection(urlBase, username, password));
			
		} catch (Exception e) {
			if (dbURL == null) dbURL = "{null}";
			Logger.out("MYSQLDriver.setConnection(URL: " + dbURL + "): " + e.getMessage());
			closeDB();
		}
	}

	/* (non-Javadoc)
	 * @see database.jdbc.DBDriver#executeCommand(java.lang.String)
	 */
	@Override
	public int executeCommand(String SQLcommand) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see database.jdbc.DBDriver#executeQuery(java.lang.String)
	 */
	@Override
	public boolean executeQuery(String query) {
		// TODO Auto-generated method stub
		return false;
	}
}
