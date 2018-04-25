package database.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import debug.Logger;

/**
 * Abstract class MYSQL Driver
 * 
 * @author hugo-lucca
 */
final public class MYSQLDriver extends DBDriver {

	private final static String sqlDriver = "com.mysql.jdbc.Driver"; 
	private final String urlBase   = "jdbc:mysql://192.168.2.2:3306/wisslearncards";
	private String	username;
	private String	password;

	public MYSQLDriver () {
		super(sqlDriver);
		setDbURL(urlBase);
		username	= "wisslearncards";
		password	= "wisslearncards";
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
		 try {
			 	Class.forName("com.mysql.jdbc.Driver");  
	            Connection conn = DriverManager.getConnection(urlBase, username, password);
	            Statement stmt = conn.createStatement();
	           // ResultSet rs;
	            if(stmt.execute("CREATE TABLE test (ID INT AUTO_INCREMENT,Name TEXT,PRIMARY KEY(ID))"))
	            {
	            	 System.out.println("Tabelle wurde erstellt");
	            }
	          /*  while ( rs.next() ) {
	                String lastName = rs.getString("Lname");
	                System.out.println(lastName);
	            }*/
	           
	            conn.close();
	        } catch (Exception e) {
	            System.err.println("Got an exception! ");
	            System.err.println(e.getMessage());
	        }
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
