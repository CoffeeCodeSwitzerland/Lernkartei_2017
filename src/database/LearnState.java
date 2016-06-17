package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import debug.Logger;

public class LearnState extends SQLiteConnector {

	// Varibeln Connection

//	private static String	url		= "jdbc:sqlite:" + globals.Environment.getDatabasePath()
//													 + globals.Globals.db_name + ".db";
//	
//	private static String	driver	= "org.sqlite.JDBC";
//
	public static boolean newSwitch (String setName) {

		Connection c = null;
		Statement stmt = null;
		boolean worked = false;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(dbURL);
			stmt = c.createStatement();
			
			String create = "CREATE TABLE IF NOT EXISTS Switcher ("
							+ "PK_Swt INTEGER PRIMARY KEY AUTOINCREMENT,"
							+ "SwitchStack TEXT NOT NULL);";
			stmt.executeUpdate(create);
			
			c.setAutoCommit(false);			
			ResultSet checkExists = stmt.executeQuery("SELECT * FROM Switcher WHERE SwitchStack = '" + setName + "';");
			c.setAutoCommit(true);			
			
			if (checkExists.next()) {
				checkExists.close();
			} else {
				checkExists.close();
				String insertSwitch = "INSERT INTO Switcher (SwitchStack) VALUES ('" + setName + "');";
				stmt.executeUpdate(insertSwitch);
				worked = true;
			}
		}
		catch (Exception e) {
			Logger.log("Database.newSwitch(): " + e.getMessage());
		}
		closeDB();
		return worked;
	}

	public static boolean delSwitch (String setName) {

		Connection c = null;
		Statement stmt = null;
		boolean worked = false;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(dbURL);
			stmt = c.createStatement();
			
			String create = "CREATE TABLE IF NOT EXISTS TABLE Switcher ("
					+ "PK_Swt INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "SwitchStack TEXT NOT NULL);";
			stmt.executeUpdate(create);
			
			c.setAutoCommit(false);			
			ResultSet checkExists = stmt.executeQuery("SELECT * FROM Switcher WHERE SwitchStack = '" + setName + "';");
			c.setAutoCommit(true);	
			
			if (checkExists.next()) {
				checkExists.close();
			} else {
				checkExists.close();
				String updateSwitch = "DELETE FROM Switcher WHERE SwitchStack = '" + setName + "'";
				stmt.executeUpdate(updateSwitch);
				worked = true;
			}
		}
		catch (Exception e) {
			Logger.log("Database.delSwitch(): " + e.getMessage());
		}
		closeDB();
		return worked;
	}

	public static boolean checkSwitched (String setName) {

		Connection c = null;
		Statement stmt = null;
		boolean checked = false;

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(dbURL);
			stmt = c.createStatement();

			String create = "CREATE TABLE IF NOT EXISTS TABLE Switcher ("
					+ "PK_Swt INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "SwitchStack TEXT NOT NULL);";
			stmt.executeUpdate(create);
			
			c.setAutoCommit(false);			
			ResultSet checkExists = stmt.executeQuery("SELECT * FROM Switcher WHERE SwitchStack = '" + setName + "';");
			c.setAutoCommit(true);			
			
			if (checkExists.next()) {
				checkExists.close();
				checked = true;
			} else {
				checkExists.close();
				checked = false;
			}
		}
		catch (Exception e) {
			Logger.log("Database.checkSwitched(): " + e.getMessage());
		}
		closeDB();
		return checked;
	}
}
