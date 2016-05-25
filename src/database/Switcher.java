package database;

import java.sql.*;

public class Switcher {

	public static boolean newSwitch (String setName) {

		Connection c = Database.getConnection();
		boolean worked = false;

		try {
			c = DriverManager.getConnection(Database.getDbURL());
			Statement stmt = c.createStatement();
			
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

		Connection c = Database.getConnection();
		boolean worked = false;

		try {
			c = DriverManager.getConnection(Database.getDbURL());
			Statement stmt = c.createStatement();
			
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

		Connection c = Database.getConnection();
		boolean checked = false;

		try {
			c = DriverManager.getConnection(Database.getDbURL());
			Statement stmt = c.createStatement();

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
