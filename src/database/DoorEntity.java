package database;

import java.sql.ResultSet;
import java.util.ArrayList;

import database.sql.Attribute;
import database.sql.Entity;
import debug.Logger;

public class DoorEntity extends Entity {

//	private static String sqlCreate = "CREATE TABLE IF NOT EXISTS Doors (" +
//										myPrimaryKey+" INTEGER PRIMARY KEY AUTOINCREMENT," +
//										mySeekAttribute+" TEXT NOT NULL)";
	DoorEntity (String tabName) {
		super(tabName, tabName+"_PK");
		// set table attributes
		Attribute a = new Attribute("Name");
		myAttributes.add(a);
		createTableIfNotExists();
	}

	/**
	 * Methode, zum Erstellen einer neuen Türe
	 * 
	 * @param eingabe
	 *            --> String Name der Tür
	 * 
	 * @return --> True, wenn Eintrag eingefügt, false, wenn Eintrag bereits
	 *         vorhanden
	 *
	 */
	public boolean newDoor (String eingabe) {

		boolean worked = false;

		try {

			// Überprüft, ob bereits ein Eintrag mit dem Selben Namen enthalten
			// ist
			
			
			ResultSet checkName =
					executeQuery("SELECT Doorname FROM Doors WHERE Doorname = " + "'" + eingabe + "'");
			
			
			if (!checkName.next()) {

				checkName.close();
				
				// Einfügen des Datensatzes in Doors

				String insert = "INSERT INTO Doors (Doorname)" +
						"VALUES ('" + eingabe + "')";

				executeCommand(insert);
				worked = true;
			}
			else {
				worked = false;
			}
		}
		catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return worked;
	}

	/**
	 * 
	 * Methode, welche alle Türen in einer Liste ausgibt
	 * 
	 * @return --> Retourniert die Liste mit allen Türennamen
	 */

	public ArrayList<String> getDoors () {

		ArrayList<String> data = new ArrayList<String>();

		try {
			ResultSet tbl = stmt.executeQuery("SELECT tbl_name FROM sqlite_master WHERE type='table' AND tbl_name='Doors'");
			
			if (tbl.next()) {
				ResultSet rs = stmt.executeQuery("SELECT Doorname FROM Doors");
				
				while (rs.next()) {
					String name = "";
					name = rs.getString("Doorname");
					data.add(name);
				}
				rs.close();
			}
			else {
				debug.Debugger.out("Table Doors is not created yet.");
			}
		}
		catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return data;
	}

	/**
	 * Gibt boolean zurück, obs funktioniert hat oder nicht
	 * 
	 * @param delName
	 *            --> Name, welcher gelöscht werden soll
	 * @return --> True, Gelöscht / false, nicht Gelöscht / vorhanden
	 */

	public boolean delDoor (String delName) {
		boolean worked = false;
		ArrayList<String> setsToDel = new ArrayList<String>();
		try {
			ResultSet del = stmt.executeQuery("SELECT * FROM Doors WHERE Doorname = '" + delName + "'");
			
			if (del.next()) {
				Integer doorID = del.getInt("PK_Doors");
				del.close();

				ResultSet getStacks = stmt.executeQuery("SELECT * FROM Kategorie WHERE FK_Door = " + doorID);
				
				while (getStacks.next()) {
					setsToDel.add(getStacks.getString("Kategorie"));
				}
				
				getStacks.close();
				for (String s : setsToDel) {
					LKDatabase.myStacks.delStack(s);
				}
				
				String delDoor = "DELETE FROM Doors WHERE Doorname = '" + delName + "'";
				String delSets = "DELETE FROM Kategorie WHERE FK_Door = " + doorID;
				
				stmt.executeUpdate(delDoor);
				stmt.executeUpdate(delSets);
				worked = true;
			}
			else {
				del.close();
				worked = false;
			}
		}
		catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return worked;
	}
	
	public boolean update(String oldName, String newName) {
		boolean worked = true;
		try {
			ResultSet checkDoor = stmt.executeQuery("SELECT * FROM Doors WHERE Doorname = '" + oldName + "';");
			if (checkDoor.next()) {
				String updateDoor = "UPDATE Doors SET Doorname = '" + newName + "' WHERE Doorname = '" + oldName + "';";
				stmt.executeUpdate(updateDoor);
				worked = true;
				checkDoor.close();
			} else {
				worked = false;
				checkDoor.close();
			}
		}
		catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return worked;
	}
}
