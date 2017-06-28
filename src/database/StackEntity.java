package database;

import java.util.ArrayList;

import database.jdbc.DBDriver;
import database.sql.Attribute;
import database.sql.Entity;
import database.sql.ForeignKey;
import database.sql.SQLHandler;
import debug.Logger;

public class StackEntity extends Entity {

	// Connectioninformationen URL & Driver

	/**
	 * @param tabName
	 */
	public StackEntity(DBDriver dbDriver, String tabName) {
		super(dbDriver, tabName, "PK_" + tabName, false);
		// set table attributes
		Attribute a = new Attribute("Name");
		myAttributes.addUnique(a);
		a = new Attribute("Description");
		myAttributes.addUnique(a);
		ForeignKey f = new ForeignKey("PK_DOOR");
		myAttributes.addUnique(f);
		f = new ForeignKey("PK_USER");
		myAttributes.addUnique(f);
		createTableIfNotExists();
	}

	/**
	 * 
	 * Methode, zum Einfügen einer neuen Kategorie
	 *
	 * @param eingabe
	 *            --> String Kategorie
	 * @param fk_door
	 *            --> String Doorname, zu welcher die Kategorie gehört
	 */
	public int newStack(String eingabe, String doorName) {
		int FK_ID = LKDatabase.myDoors.getEntityID("name", doorName);
		// Überprüft, ob die Tür exisitert oder nicht
		// "SELECT * FROM STACK WHERE name = '" + eingabe + "'" +"AND PK_DOOR =
		// "+FK_ID
		myDBDriver.executeQuery(SQLHandler.selectCommand(getMyTableName(), null, "name", eingabe, "PK_DOOR", FK_ID));
		debug.Debugger.out("NEW-STACK1:"+myDBDriver.getLastSQLCommand());
		if (myDBDriver.isThereAResult()) { // existiert schon
			debug.Debugger.out("alread exists");
			return -2;
		}
		// Erstellt die neue Kategorie als Eintrag in der Datenbank mit
		// einem Fremdkey für die Tür
		// Do "INSERT INTO Kategorie (Kategorie, FK_Door)" + "VALUES ('" +
		// eingabe + "', " + FK_ID + ")";
		myAttributes.getAttributeNamedAs("PK_DOOR").setValue(FK_ID);
		myAttributes.getAttributeNamedAs("PK_USER")
				.setValue(1 /* TODO add user ID */);
		myAttributes.getAttributeNamedAs("name").setValue(eingabe);
		return myDBDriver.executeCommand(SQLHandler.insertIntoTableCommand(getMyTableName(), myAttributes));
	}

	public ArrayList<String> getKategorien(String doorName) {
		int FK_ID = LKDatabase.myDoors.getEntityID("name", doorName);
		debug.Debugger.out("STACK1: ("+FK_ID+")");
		ArrayList<String> datensatz = new ArrayList<String>();
		// Do "SELECT * FROM Stack WHERE FK_Door = " + FK_ID + ";"
		myDBDriver.executeQuery(SQLHandler.selectCommand(getMyTableName(), null, "PK_DOOR", FK_ID));
		debug.Debugger.out("STACK3: '" + myDBDriver.getLastSQLCommand() +"'");
		while (myDBDriver.isThereAResult()) {
			String res = myDBDriver.getResultValueOf("name");
			datensatz.add(res);
			debug.Debugger.out("ADD:" +	res);
		}
		return datensatz;
	}

	/**
	 * Löscht den gewählten Eintrag
	 * 
	 * @param category
	 *            --> Name der zu löschenden Kategorie
	 */
	public boolean delStack(String category, String doorName) {
		boolean worked = false;
		int FK_ID = LKDatabase.myDoors.getEntityID("name", doorName);
		// Überprüft, ob die Tür exisitert oder nicht
		// Abfragen, ob zu löschende Kategorie vorhanden ist oder nicht.
		// Wenn ja, wird gelöscht
		// Do "SELECT * FROM STACK WHERE "+ " name = '" + category + "'" +
		// "AND PK_DOOR =" +FK_ID
		myDBDriver.executeQuery(SQLHandler.selectCommand(getMyTableName(), null, "name", category, "PK_DOOR", FK_ID));
		debug.Debugger.out("DEL-STACK1:" + myDBDriver.getLastSQLCommand());
		if (myDBDriver.isThereAResult()) {
			int setID = myDBDriver.getResultPIntValueOf("PK_STACK");
			// Do "DELETE FROM Stack WHERE PK_STACK = " + setID
			myDBDriver.executeCommand(SQLHandler.deleteEntryCommand("Card", "PK_STACK", setID));
			// debug.Debugger.out("DEL-STACK2:"+getLastSQLCommand());
			// Do "DELETE FROM DOOR WHERE name = '" + category + "'"
			myDBDriver.executeCommand(SQLHandler.deleteEntryCommand(getMyTableName(), "PK_STACK", setID));
			// debug.Debugger.out("DEL-STACK3:"+getLastSQLCommand());
			worked = true;
		}
		return worked;
	}

	public ArrayList<String> getStacknames(String doorName) {
		ArrayList<String> stacks = new ArrayList<String>();
		int FK_ID = LKDatabase.myDoors.getEntityID("name", doorName);
		// Do "SELECT Kategorie FROM Kategorie"
		String cmd;
		if (FK_ID > 0) {
			cmd = SQLHandler.selectCommand(getMyTableName(), null, "PK_DOOR", FK_ID);
		} else {
			cmd = SQLHandler.selectCommand(getMyTableName(), null);
		}
		myDBDriver.executeQuery(cmd);
		try {
			if (myDBDriver.getLastResultSet().isAfterLast()) {
				stacks = null;
			} else {
				while (myDBDriver.isThereAResult()) {
					myDBDriver.getLastResultSet().getRow();
					stacks.add(myDBDriver.getResultValueOf("name"));
				}
			}
		} catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return stacks;
	}

	public boolean possible(String boxName) {
		// Do "SELECT * FROM STACK WHERE name = '" + boxName + "';"
		myDBDriver.executeQuery(SQLHandler.selectCommand(getMyTableName(), null, "name", boxName));
		if (!myDBDriver.isThereAResult()) {
			return true;
		}
		return false;
	}

	public boolean update(String oldName, String newName, String doorName) {
		boolean worked = true;
		int FK_ID = LKDatabase.myDoors.getEntityID("name", doorName);
		// Do "SELECT * FROM Stack WHERE name = '" + oldName + "' AND
		// PK_DOOR ="+doorID
		myDBDriver.executeQuery(SQLHandler.selectCommand(getMyTableName(), null, "name", oldName, "PK_DOOR", FK_ID));
		if (myDBDriver.isThereAResult()) {
			Attribute k = new Attribute("name", oldName);
			// Do "UPDATE Stack SET name = '" + newName + "' WHERE name = '"
			// + oldName + "';";
			System.out.println("$$$ SQL: "+SQLHandler.updateInTableCommand(getMyTableName(), myAttributes, k));
			myAttributes.getAttributeNamedAs("name").setValue(newName);
			myAttributes.getAttributeNamedAs("PK_DOOR").setValue(FK_ID);
			myAttributes.getAttributeNamedAs("PK_USER").setValue(1);
			System.out.println("$$$ SQL: "+SQLHandler.updateInTableCommand(getMyTableName(), myAttributes, k));
			myDBDriver.executeCommand(SQLHandler.updateInTableCommand(getMyTableName(), myAttributes, k));
			worked = true;
		} else {
			worked = false;
		}
		return worked;
	}
}
