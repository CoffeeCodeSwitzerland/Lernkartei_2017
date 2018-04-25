package database;

import java.util.ArrayList;

import database.jdbc.DBDriver;
import database.sql.Attribute;
import database.sql.Entity;
import database.sql.SQLHandler;

public class DoorEntity extends Entity {

	DoorEntity(DBDriver dbDrive, String tabName) {
		super(dbDrive, tabName, "PK_" + tabName, false);
		// set table attributes
		Attribute a = new Attribute("Name");
		myAttributes.addUnique(a);
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
	public boolean newDoor(String eingabe) {
		boolean worked = false;
		// Do "SELECT Doorname FROM Doors WHERE Doorname = " + "'" + eingabe +
		// "'"
		myDBDriver.executeQuery(SQLHandler.selectCommand(this.getMyTableName(), "name", "name", eingabe));
		// Überprüft, ob bereits ein Eintrag mit dem Selben Namen enthalten ist
		if (!myDBDriver.isThereAResult()) {
			// Einfügen des Datensatzes in Doors
			myAttributes.getAttributeNamedAs("name").setValue(eingabe);
			// Do "INSERT INTO Doors (Doorname)" + "VALUES ('" + eingabe + "')";
			worked = (myDBDriver.executeCommand(SQLHandler.insertIntoTableCommand(getMyTableName(), myAttributes)) >= 0) ? true : false;
		}
		return worked; 
	}

	/**
	 * 
	 * Methode, welche alle Türen in einer Liste ausgibt
	 * 
	 * @return --> Retourniert die Liste mit allen Türennamen
	 */
	public ArrayList<String> getDoors() {
		Attribute key = new Attribute("name", null);
		return getDataList(key);
	}

	/**
	 * Gibt boolean zurück, obs funktioniert hat oder nicht
	 * 
	 * @param delName
	 *            --> Name, welcher gelöscht werden soll
	 * @return --> True, Gelöscht / false, nicht Gelöscht / vorhanden
	 */
	public boolean delDoor(String delName) {
		boolean worked = false;
		myDBDriver.executeQuery(SQLHandler.selectCommand(getMyTableName(), "PK_DOOR", "name", delName));
		if (myDBDriver.isThereAResult()) {
			int doorID = myDBDriver.getResultPIntValueOf("PK_DOOR");

			// Do "SELECT * FROM Kategorie WHERE PK_Door = " + doorID
			myDBDriver.executeQuery(SQLHandler.selectCommand("STACK", null, "PK_DOOR", doorID));
			ArrayList<String> setsToDel = new ArrayList<String>();
			while (myDBDriver.isThereAResult()) {
				setsToDel.add(myDBDriver.getResultValueOf("name"));
			}
			for (String s : setsToDel) {
				LKDatabase.myStacks.delStack(s, delName);
			}
			myDBDriver.executeCommand(SQLHandler.deleteEntryCommand(getMyTableName(), "PK_DOOR", doorID));
			// Do "DELETE FROM Doors WHERE Doorname = '" + delName + "'";
			myDBDriver.executeCommand(SQLHandler.deleteEntryCommand("STACK", "PK_DOOR", doorID));

			// TODO Delete all cards of those stacks...
			// Do "DELETE FROM Kategorie WHERE FK_Door = " + doorID;
			// setLastSQLCommand(SQLHandler.deleteEntryCommand("STACK",
			// "PK_DOOR", doorID));
			// setLastResultSet(executeQuery(getLastSQLCommand()));
			// String delSets = "DELETE FROM Kategorie WHERE FK_Door = " +
			// doorID;
			worked = true;
		} else {
			worked = false;
		}
		return worked;
	}

	public boolean update(String oldName, String newName) {
		boolean worked = true;
		// Do "SELECT * FROM Doors WHERE Doorname = '" + oldName + "';"
		myDBDriver.executeQuery(SQLHandler.selectCommand(getMyTableName(), "name", "name", oldName));
		if (myDBDriver.isThereAResult()) {
			Attribute k = new Attribute("name", oldName);
			// Do "UPDATE Doors SET Doorname = '" + newName + "' WHERE
			// Doorname = '" + oldName + "';"
			myAttributes.getAttributeNamedAs("name").setValue(newName);
			myDBDriver.executeCommand(SQLHandler.updateInTableCommand(getMyTableName(), myAttributes, k));
			worked = true;
		} else {
			worked = false;
		}
		return worked;
	}
}
