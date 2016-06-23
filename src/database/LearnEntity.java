package database;

import java.sql.ResultSet;

import database.sql.Attribute;
import database.sql.Entity;
import database.sql.ForeignKey;
import debug.Logger;

public class LearnEntity extends Entity {

	// Varibeln Connection

/**
	 * @param tabName
	 */
	public LearnEntity(String tabName) {
		super(tabName,"PK_"+tabName);
		// set table attributes
		Attribute a = new Attribute("WasCorrect",0);
		myAttributes.add(a);
		a = new Attribute("Date");
		myAttributes.add(a);
		ForeignKey f = new ForeignKey("PK_CARD");
		myAttributes.add(f);
		f = new ForeignKey("PK_USER");
		myAttributes.add(f);
		createTableIfNotExists();
	}

	//	private static String	url		= "jdbc:sqlite:" + globals.Environment.getDatabasePath()
//													 + globals.Globals.db_name + ".db";
//	
//	private static String	driver	= "org.sqlite.JDBC";
//
	public boolean newSwitch (String setName) {

		boolean worked = false;

		try {
			String create = "CREATE TABLE IF NOT EXISTS Switcher ("
							+ "PK_Swt INTEGER PRIMARY KEY AUTOINCREMENT,"
							+ "SwitchStack TEXT NOT NULL);";
			stmt.executeUpdate(create);
			
			ResultSet checkExists = stmt.executeQuery("SELECT * FROM Switcher WHERE SwitchStack = '" + setName + "';");
			
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

	public boolean delSwitch (String setName) {

		boolean worked = false;
		try {
			String create = "CREATE TABLE IF NOT EXISTS TABLE Switcher ("
					+ "PK_Swt INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "SwitchStack TEXT NOT NULL);";
			stmt.executeUpdate(create);
			ResultSet checkExists = stmt.executeQuery("SELECT * FROM Switcher WHERE SwitchStack = '" + setName + "';");
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
		return worked;
	}

	public boolean checkSwitched (String setName) {

		boolean checked = false;
		try {
			String create = "CREATE TABLE IF NOT EXISTS TABLE Switcher ("
					+ "PK_Swt INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "SwitchStack TEXT NOT NULL);";
			stmt.executeUpdate(create);
			ResultSet checkExists = stmt.executeQuery("SELECT * FROM Switcher WHERE SwitchStack = '" + setName + "';");
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
