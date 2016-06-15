package database;

import java.sql.ResultSet;
import java.util.ArrayList;

import debug.Logger;
import globals.Globals;


public class Doors extends SQLiteConnector {

	protected static String myTableName  = "Doors";
	protected static String mySeekAttribute = "Doorname";
	protected static String myPrimaryKey = "PK_Doors";
	private   static String myAttributeList = mySeekAttribute;
	private   static final String myAttributes = 
									  myPrimaryKey + " INTEGER PRIMARY KEY AUTOINCREMENT,"
									+ mySeekAttribute + " TEXT NOT NULL";
	/**
	 * Methode, zum Erstellen einer neuen Türe
	 * 
	 * @param eingabe
	 *            --> String Name der Tür
	 * 
	 * @return --> True, wenn Eintrag eingefügt, false, wenn Eintrag bereits
	 *         vorhanden
	 */
	public static boolean newDoor (String eingabe) {

		boolean worked = false;
		Database.setConnection(Database.getDbURL());
		try {
			createTableIfNotExists(Doors.myTableName, Doors.myAttributes);
			// Überprüft, ob bereits ein Eintrag mit dem Selben Namen enthalten
			// ist
			ResultSet checkName = seekInTable(	Doors.myTableName, Doors.mySeekAttribute,
												Doors.mySeekAttribute, eingabe);
			if (!checkName.next()) {
				checkName.close();

				// Einfügen des Datensatzes in Doors
				String [] values = eingabe.split(Globals.SEPARATOR);
				insertIntoTable (Switcher.myTableName, myAttributeList, values);
				worked = true;
			}
			else {
				worked = false;
			}
		}
		catch (Exception e) {
			if (eingabe==null) eingabe = "{null}";
			debug.Debugger.out("Switcher.newDoor("+eingabe+"): "+e.getMessage());
			Logger.log("Switcher.newDoor("+eingabe+"): "+e.getMessage());
		}
		closeDB();
		return worked;
	}

	/**
	 * 
	 * Methode, welche alle Türen in einer Liste ausgibt
	 * 
	 * @return --> Retourniert die Liste mit allen Türennamen
	 */
	public static ArrayList<String> getDoors () {

		ArrayList<String> data = new ArrayList<String>();
		Database.setConnection(Database.getDbURL());
		try {
			ResultSet tbl = seekInTable("sqlite_master", "tbl_name", "type='table' AND tbl_name", Doors.myTableName);
			// TODO ?? ResultSet tbl = stmt.executeQuery("SELECT *name* FROM sqlite_master WHERE type='table' AND name='Doors'");

			if (tbl.next()) {
				ResultSet rs = seekInTable(Doors.myTableName, Doors.mySeekAttribute);
				while (rs.next()) {
					String name = rs.getString(Doors.mySeekAttribute);
					data.add(name);
				}
				rs.close();
			}
			else {
				debug.Debugger.out("Switcher.getDoors(): Table Doors is not created yet.");
			}
		}
		catch (Exception e) {
			debug.Debugger.out("Switcher.getDoors(): "+e.getMessage());
			Logger.log("Switcher.getDoors(): "+e.getMessage());
		}
		closeDB();
		return data;
	}

	/**
	 * Gibt boolean zurück, obs funktioniert hat oder nicht
	 * 
	 * @param delName
	 *            --> Name, welcher gelöscht werden soll
	 * @return --> True, Gelöscht / false, nicht Gelöscht / vorhanden
	 */

	public static boolean delDoor (String delName) {

		boolean worked = false;
		ArrayList<String> setsToDel = new ArrayList<String>();
		
		Database.setConnection(Database.getDbURL());
		try {
			ResultSet del = seekInTable(	Doors.myTableName, "*",
											Doors.mySeekAttribute, delName);
			if (del.next()) {
				Integer doorID = del.getInt("PK_Doors");
				del.close();

				ResultSet getStacks = seekInTable(	Stack.myTableName, "*",
													Stack.myForeignKey, Integer.toString(doorID));
				while (getStacks.next()) {
					setsToDel.add(getStacks.getString(Stack.mySeekAttribute));
				}
				getStacks.close();
				for (String s : setsToDel) {
					Stack.delStack(s);
				}
				deleteSQL (Doors.myTableName, Doors.mySeekAttribute, "'"+delName+"'");
				deleteSQL (Stack.myTableName, Stack.myForeignKey, "'"+doorID+"'");
				worked = true;
			}
			else {
				del.close();
				worked = false;
			}
		}
		catch (Exception e) {
			if (delName==null) delName = "{null}";
			debug.Debugger.out("Switcher.delDoor("+delName+"): "+e.getMessage());
			Logger.log("Switcher.delDoor("+delName+"): "+e.getMessage());
		}
		closeDB();
		return worked;
	}
	
	public static boolean update(String oldName, String newName) {
		
		boolean worked = true;
		Database.setConnection(Database.getDbURL());
		try {
			ResultSet checkDoor = seekInTable(	Doors.myTableName, "*",
												Doors.mySeekAttribute, oldName);
			if (checkDoor.next()) {
				updateInTable(	Doors.myTableName, 	Doors.mySeekAttribute, newName, 
													Doors.mySeekAttribute, oldName);
				worked = true;
				checkDoor.close();
			} else {
				worked = false;
				checkDoor.close();
			}
		}
		catch (Exception e) {
			if (oldName==null) oldName = "{null}";
			if (newName==null) newName = "{null}";
			debug.Debugger.out("Switcher.update("+oldName+","+newName+"): "+e.getMessage());
			Logger.log("Switcher.update("+oldName+","+newName+"): "+e.getMessage());
		}
		closeDB();
		return worked;
	}
}
