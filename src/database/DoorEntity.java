package database;

import java.util.ArrayList;

import database.sql.Attribute;
import database.sql.Entity;
import database.sql.SQLHandler;
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
			
			setLastSQLCommand(SQLHandler.selectCommand(	this.getMyTableName(),"name","name",eingabe)); 
			setLastResultSet(executeQuery(getLastSQLCommand()));
			// executeQuery("SELECT Doorname FROM Doors WHERE Doorname = " + "'" + eingabe + "'");
			if (!getLastResultSet().next()) {
				getLastResultSet().close();
				// Einfügen des Datensatzes in Doors
				myAttributes.seekKeyNamed("name").setValue(eingabe);
				setLastSQLCommand(SQLHandler.insertIntoTableCommand(getMyTableName(), myAttributes)); 
				//String insert = "INSERT INTO Doors (Doorname)" +
				//		"VALUES ('" + eingabe + "')";
				worked = (this.executeCommand(getLastSQLCommand()) >= 0) ? true:false;
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
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(), null)); 
			setLastResultSet(executeQuery(getLastSQLCommand()));
			while (getLastResultSet().next()) {
				String name = "";
				name = getLastResultSet().getString("name");
				data.add(name);
			}
			getLastResultSet().close();
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
			setLastSQLCommand(SQLHandler.selectCommand(	this.getMyTableName(),"PK_DOOR","name", delName)); 
			setLastResultSet(executeQuery(getLastSQLCommand()));
			if (getLastResultSet().next()) {
				int doorID = getLastResultSet().getInt("PK_DOOR");
				getLastResultSet().close();

				setLastSQLCommand(SQLHandler.selectCommand(	"STACK",null,"PK_DOOR",doorID)); 
				setLastResultSet(executeQuery(getLastSQLCommand()));

				//ResultSet getStacks = stmt.executeQuery("SELECT * FROM Kategorie WHERE PK_Door = " + doorID);
				
				while (getLastResultSet().next()) {
					setsToDel.add(getLastResultSet().getString("name"));
				}
				getLastResultSet().close();
				for (String s : setsToDel) {
					LKDatabase.myStacks.delStack(s);
				}
				
				
				setLastSQLCommand(SQLHandler.deleteEntryCommand(getMyTableName(), "name", delName)); 
				setLastResultSet(executeQuery(getLastSQLCommand()));
				//String delDoor = "DELETE FROM Doors WHERE Doorname = '" + delName + "'";
				setLastSQLCommand(SQLHandler.deleteEntryCommand("STACK", "PK_DOOR", doorID)); 
				setLastResultSet(executeQuery(getLastSQLCommand()));
				//String delSets = "DELETE FROM Kategorie WHERE FK_Door = " + doorID;
// TODO Delete all cards of those stacks...
//				setLastSQLCommand(SQLHandler.deleteEntryCommand("STACK", "PK_DOOR", doorID)); 
//				setLastResultSet(executeQuery(getLastSQLCommand()));
				//String delSets = "DELETE FROM Kategorie WHERE FK_Door = " + doorID;
				worked = true;
			}
			else {
				getLastResultSet().close();
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
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(),"name","name",oldName)); 
			setLastResultSet(executeQuery(getLastSQLCommand()));
			//ResultSet checkDoor = stmt.executeQuery("SELECT * FROM Doors WHERE Doorname = '" + oldName + "';");
			if (getLastResultSet().next()) {
				Attribute k = new Attribute("name",oldName);
				setLastSQLCommand(SQLHandler.updateInTableCommand(getMyTableName(),myAttributes,k)); 
				setLastResultSet(executeQuery(getLastSQLCommand()));
				//String updateDoor = "UPDATE Doors SET Doorname = '" + newName + "' WHERE Doorname = '" + oldName + "';";
				//stmt.executeUpdate(updateDoor);
				worked = true;
			} else {
				worked = false;
			}
			getLastResultSet().close();
		}
		catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return worked;
	}
}
