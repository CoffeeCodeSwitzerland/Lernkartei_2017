package database;

import java.sql.*;

public class Switcher {

	// Varibeln Connection

	private static String	url		= "jdbc:sqlite:" + globals.Environment.getDatabasePath()
													 + globals.Globals.db_name + ".db";
	
	private static String	driver	= "org.sqlite.JDBC";

	public static boolean newSwitch (String setName) {

		Connection c = null;
		Statement stmt = null;
		boolean worked = false;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			
			String create = "CREATE TABLE IF NOT EXISTS Switcher ("
							+ "PK_Swt INTEGER PRIMARY KEY AUTOINCREMENT,"
							+ "SwitchStack TEXT NOT NULL);";
			stmt.executeUpdate(create);
			
			c.setAutoCommit(false);			
			ResultSet checkExists = stmt.executeQuery("SELECT * FROM Switcher WHERE SwitchStack = '" + setName + "';");
			
			if (checkExists.next()) {
				checkExists.close();
			} else {
				checkExists.close();
				c.setAutoCommit(true);	
				String insertSwitch = "INSERT INTO Switcher (SwitchStack) VALUES ('" + setName + "');";
				stmt.executeUpdate(insertSwitch);
				worked = true;
			}
			
			stmt.close();
			c.close();
		}
		catch (Exception e) {
			debug.Debugger.out(e.getMessage());
		}
		
		return worked;
	}

	public static boolean delSwitch (String setName) {

		Connection c = null;
		Statement stmt = null;
		boolean worked = false;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			
			String create = "CREATE TABLE IF NOT EXISTS TABLE Switcher ("
					+ "PK_Swt INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "SwitchStack TEXT NOT NULL);";
			stmt.executeUpdate(create);
			
			c.setAutoCommit(false);			
			ResultSet checkExists = stmt.executeQuery("SELECT * FROM Switcher WHERE SwitchStack = '" + setName + "';");
			
			if (checkExists.next()) {
				checkExists.close();
			} else {
				checkExists.close();
				c.setAutoCommit(true);	
				String updateSwitch = "DELETE FROM Switcher WHERE SwitchStack = '" + setName + "'";
				stmt.executeUpdate(updateSwitch);
				worked = true;
			}
			
			stmt.close();
			c.close();

		}
		catch (Exception e) {
			debug.Debugger.out(e.getMessage());
		}
		
		return worked;

	}

	public static boolean checkSwitched (String setName) {

		Connection c = null;
		Statement stmt = null;
		boolean checked = false;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();

			String create = "CREATE TABLE IF NOT EXISTS TABLE Switcher ("
					+ "PK_Swt INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "SwitchStack TEXT NOT NULL);";
			stmt.executeUpdate(create);
			
			c.setAutoCommit(false);			
			ResultSet checkExists = stmt.executeQuery("SELECT * FROM Switcher WHERE SwitchStack = '" + setName + "';");
			
			if (checkExists.next()) {
				checkExists.close();
				checked = true;
			} else {
				checkExists.close();
				checked = false;
			}
			
			stmt.close();
			c.close();

		}
		catch (Exception e) {
			debug.Debugger.out(e.getMessage());
		}
		
		return checked;

	}

}
