package database;

import java.sql.*;

import debug.Logger;
import globals.Globals;

public class Switcher  extends SQLiteConnector {

	protected static String myTableName  = "Switcher";
	protected static String mySeekAttribute = "SwitchStack";
	protected static String myPrimaryKey = "PK_Swt";
//	private   static String myFKName     = "FK_Door";
	private   static String myAttributeList = mySeekAttribute;
	private   static String myAttributes = 
									  myPrimaryKey + " INTEGER PRIMARY KEY AUTOINCREMENT,"
									+ " "+ mySeekAttribute + " TEXT NOT NULL";
	
	public static boolean newSwitch (String setName) {

		boolean worked = false;
		Database.setConnection(Database.getDbURL());
		try {
			createTableIfNotExists(Switcher.myTableName, Switcher.myAttributes);

			ResultSet checkExists = seekInTable(Switcher.myTableName, "*",
												Switcher.mySeekAttribute, setName);
			if (checkExists.next()) {
				checkExists.close();
			} else {
				checkExists.close();

				String [] values = setName.split(Globals.SEPARATOR);
				insertIntoTable (Switcher.myTableName, myAttributeList, values);
				worked = true;
			}
		}
		catch (Exception e) {
			if (setName==null) setName = "{null}";
			debug.Debugger.out("Switcher.newSwitch("+setName+"): "+e.getMessage());
			Logger.log("Switcher.newSwitch("+setName+"): "+e.getMessage());
		}
		closeDB();
		return worked;
	}

	public static boolean delSwitch (String setName) {
		boolean worked = false;
		Database.setConnection(Database.getDbURL());
		createTableIfNotExists(Switcher.myTableName, Switcher.myAttributes);
		worked = delEntryIfExists (Switcher.myTableName, Switcher.mySeekAttribute, setName);
		closeDB();
		return worked;
	}

	public static boolean checkSwitched (String setName) {

		boolean checked = false;

		Database.setConnection(Database.getDbURL());
		try {
			createTableIfNotExists(Switcher.myTableName, Switcher.myAttributes);

			ResultSet checkExists = seekInTable(Switcher.myTableName, "*",
												Switcher.mySeekAttribute, setName);
			if (checkExists.next()) {
				checkExists.close();
				checked = true;
			} else {
				checkExists.close();
				checked = false;
			}
		}
		catch (Exception e) {
			if (setName==null) setName = "{null}";
			debug.Debugger.out("Switcher.checkSwitched("+setName+"): "+e.getMessage());
			Logger.log("Switcher.checkSwitched("+setName+"): "+e.getMessage());
		}
		closeDB();
		return checked;
	}
}
