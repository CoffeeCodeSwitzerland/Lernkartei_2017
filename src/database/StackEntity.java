package database;

import java.util.ArrayList;

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
	public StackEntity(String tabName) {
		super(tabName, "PK_" + tabName);
		// set table attributes
		Attribute a = new Attribute("Name");
		myAttributes.add(a);
		a = new Attribute("Description");
		myAttributes.add(a);
		ForeignKey f = new ForeignKey("PK_DOOR");
		myAttributes.add(f);
		f = new ForeignKey("PK_USER");
		myAttributes.add(f);
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
	public int newStack(String eingabe, String fk_door) {
		int FK_ID = 0;
		int errorMsg = 0;
		// Überprüft, ob die Tür exisitert oder nicht
		// Do "SELECT PK_Doors FROM Doors WHERE Doorname = '" + fk_door + "'"
		setLastSQLCommand(SQLHandler.selectCommand("Door", "PK_Door", "name", fk_door));
		setLastResultSet(executeQuery(getLastSQLCommand()));
		try {
			if (getLastResultSet().next()) {
				FK_ID = getLastResultSet().getInt("PK_Door");
			} else {
				getLastResultSet().close();
				return -1;
			}
			getLastResultSet().close();
			// Do "SELECT * FROM Kategorie WHERE Kategorie = '" + eingabe + "'"
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(), null, "name", eingabe));
			setLastResultSet(executeQuery(getLastSQLCommand()));
			if (getLastResultSet().next()) { // existiert schon
				getLastResultSet().close();
				return -2;
			}
			getLastResultSet().close();
			// Erstellt die neue Kategorie als Eintrag in der Datenbank mit
			// einem Fremdkey für die Tür
			// Do "INSERT INTO Kategorie (Kategorie, FK_Door)" + "VALUES ('" +
			// eingabe + "', " + FK_ID + ")";
			myAttributes.seekKeyNamed("PK_DOOR").setValue(FK_ID);
			myAttributes.seekKeyNamed("PK_USER")
					.setValue(1 /* TODO add user ID */);
			myAttributes.seekKeyNamed("name").setValue(eingabe);
			setLastSQLCommand(SQLHandler.insertIntoTableCommand(getMyTableName(), myAttributes));
			return executeCommand(getLastSQLCommand());
		} catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return errorMsg;
	}

	public ArrayList<String> getKategorien(String doorname) {
		int FK_ID = 0;
		ArrayList<String> datensatz = new ArrayList<String>();
		// Überprüft, ob die Tür exisitert oder nicht
		setLastSQLCommand(SQLHandler.selectCommand("Door", "PK_Door", "name", doorname));
		setLastResultSet(executeQuery(getLastSQLCommand()));
		try {
			if (getLastResultSet().next()) {
				FK_ID = getLastResultSet().getInt("PK_Door");
				getLastResultSet().close();
			} else {
				FK_ID = 0;
			}
			getLastResultSet().close();
			// Do "SELECT * FROM Kategorie WHERE FK_Door = " + FK_ID + ";"
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(), null, "PK_DOOR", FK_ID));
			setLastResultSet(executeQuery(getLastSQLCommand()));
			debug.Debugger.out(getLastSQLCommand());
			while (getLastResultSet().next()) {
				datensatz.add(getLastResultSet().getString("name"));
				debug.Debugger.out("ADD:" + getLastResultSet().getString("name"));
			}
			getLastResultSet().close();
		} catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return datensatz;
	}

	/**
	 * Löscht den gewählten Eintrag
	 * 
	 * @param category
	 *            --> Name der zu löschenden Kategorie
	 */
	public boolean delStack(String category) {
		boolean worked = false;
		// Abfragen, ob zu löschende Kategorie vorhanden ist oder nicht.
		// Wenn ja, wird gelöscht
		// Do "SELECT Kategorie FROM Kategorie WHERE"+ " Kategorie = '" + category + "';"
		setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(), "name", "name", category));
		setLastResultSet(executeQuery(getLastSQLCommand()));
		try {
			if (getLastResultSet().next()) {
				int setID = getLastResultSet().getInt("PK_STACK");
				// Do "DELETE FROM Stack WHERE PK_STACK = " + setID + ";"
				setLastSQLCommand(SQLHandler.deleteEntryCommand("Card", "PK_STACK", setID));
				executeCommand(getLastSQLCommand());
				// Do "DELETE FROM Kategorie WHERE Kategorie = '" + category + "';"
				setLastSQLCommand(SQLHandler.deleteEntryCommand(getMyTableName(), "PK_STACK", setID));
				executeCommand(getLastSQLCommand());
				worked = true;
			}
			getLastResultSet().close();
		} catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return worked;
	}

	public ArrayList<String> getStacknames() {
		ArrayList<String> stacks = new ArrayList<String>();
		// Do "SELECT Kategorie FROM Kategorie"
		setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(), null));
		setLastResultSet(executeQuery(getLastSQLCommand()));
		try {
			if (getLastResultSet().isAfterLast()) {
				stacks = null;
			} else {
				while (getLastResultSet().next()) {
					getLastResultSet().getRow();
					stacks.add(getLastResultSet().getString("name"));
				}
			}
			getLastResultSet().close();
		} catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return stacks;
	}

	public int getStackID(String KategorieName) {
		int ID = 0;
		try {
			// Do "SELECT PK_Kategorie FROM Kategorie WHERE Kategorie'" + KategorieName + "'")
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(), "PK_STOCK", "name", KategorieName));
			setLastResultSet(executeQuery(getLastSQLCommand()));
			ID = Integer.parseInt(getLastResultSet().getString(getLastResultSet().getInt(1)));
			getLastResultSet().close();
		} catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return ID;
	}

	public boolean possible(String boxName) {
		try {
			// Do "SELECT * FROM Kategorie WHERE Kategorie = '" + boxName + "';"
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(), null, "name", boxName));
			setLastResultSet(executeQuery(getLastSQLCommand()));
			if (getLastResultSet().next()) {
				getLastResultSet().close();
				return false;
			} else {
				getLastResultSet().close();
				return true;
			}
		} catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return false;
	}

	public boolean update(String oldName, String newName) {
		boolean worked = true;
		try {
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(), null, "name", oldName));
			setLastResultSet(executeQuery(getLastSQLCommand()));
			// Do "SELECT * FROM Kategorie WHERE Kategorie = '" + oldName + "';"
			if (getLastResultSet().next()) {
				Attribute k = new Attribute("name", oldName);
				// Do "UPDATE Kategorie SET Kategorie = '" + newName + "' WHERE Kategorie = '" + oldName + "';";
				setLastSQLCommand(SQLHandler.updateInTableCommand(getMyTableName(), myAttributes, k));
				executeCommand(getLastSQLCommand());
				worked = true;
				getLastResultSet().close();
			} else {
				worked = false;
				getLastResultSet().close();
			}
		} catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return worked;
	}
}
