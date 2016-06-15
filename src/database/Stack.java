package database;

import java.sql.ResultSet;
import java.util.ArrayList;

import debug.Logger;

public class Stack extends SQLiteConnector {

	protected static String  myTableName  = "Kategorie";
	protected static String  mySeekAttribute = "Name";
	protected static boolean mySeekAttributeIsNum	= false;
	protected static String  myPrimaryKey = "PK_"+myTableName;
	protected static String  myForeignKey = "FK_"+Doors.myTableName;
	private   static String  myAttributeList = myForeignKey + ", " + mySeekAttribute;
	private   static String  myAttributes = 
									  myPrimaryKey + " INTEGER PRIMARY KEY AUTOINCREMENT,"
									+ " "+ mySeekAttribute + " TEXT NOT NULL,"
									+ " "+ myForeignKey + " INTEGER NOT NULL";
	/**
	 * 
	 * Methode, zum Einfügen einer neuen Kategorie
	 *
	 * @param eingabe
	 *            --> String Kategorie
	 * @param fk_door
	 *            --> String Doorname, zu welcher die Kategorie gehört
	 */
	public static int newStack (String eingabe, String fk_door) {


		Integer errorMsg = 0;

		Database.setConnection(Database.getDbURL());
		try {
			// Tabelle Kategorie erstellen, sofern sie nicht existiert
			createTableIfNotExists(Stack.myTableName, Stack.myAttributes);

			// SELECT Befehl, welcher die ID der Tür abruft, in welcher die
			// Kategorie erstellt wird
			Integer FK_ID = 0;
			ResultSet id = seekInTable(Doors.myTableName, Doors.myPrimaryKey, Doors.mySeekAttribute, fk_door);
			// Überprüft, ob die Tür exisitert oder nicht
			if (id.next()) {
				FK_ID = id.getInt(Doors.myPrimaryKey);
			}
			else {
				errorMsg = -1;
			}
			id.close();
			
			ResultSet check = seekInTable(Stack.myTableName, "*", Stack.mySeekAttribute, eingabe);
			if (check.next()) {
				errorMsg = -2;
			}
			check.close();

			// Erstellt die neue Kategorie als Eintrag in der Datenbank mit
			// einem Fremdkey für die Tür
			String[] values = eingabe.split(globals.Globals.SEPARATOR);
			insertIntoTable (Stack.myTableName, Stack.myAttributeList, Integer.toString(FK_ID), values);
		}
		catch (Exception e) {
			if (eingabe == null) eingabe = "{null}";
			debug.Debugger.out("Stack.newStack("+eingabe+"):"+e.getMessage());
			Logger.log("Stack.newStack("+eingabe+"):"+e.getMessage());
		}
		closeDB();
		return errorMsg;
	}

	public static ArrayList<String> getKategorien (String doorname) {

		final ArrayList<String> datensatz = new ArrayList<String>();

		Database.setConnection(Database.getDbURL());
		try {
			// Tabelle Kategorie erstellen, sofern sie nicht existiert
			createTableIfNotExists(Stack.myTableName, Stack.myAttributes);

			// Abfrage, ob die Tür bereits existiert
			// ID der Tür abrufen, in der die Kategorie erstellt wird
			Integer FK_ID = 0;
			ResultSet id = seekInTable(Doors.myTableName, Doors.myPrimaryKey, Doors.mySeekAttribute, doorname);
			if (id.next()) {
				FK_ID = id.getInt(Doors.myPrimaryKey);
			}
			else {
				if (doorname == null) doorname="{null}";
				debug.Debugger.out("Keine Kategorien mit Tür "+doorname+" vorhanden");
				FK_ID = 0;
				// TODO evtl. datensatz = null setzen?
			}
			id.close();

			// Alle Sets ausgeben, welche in dieser Tür enthalten sind
			ResultSet rs = seekInTable(Stack.myTableName, "*", Stack.myForeignKey, Integer.toString(FK_ID));
			while (rs.next()) {
				// Daten werden in die Liste geschrieben
				datensatz.add(rs.getString(Stack.mySeekAttribute));
			}
			rs.close();
		}
		catch (Exception e) {
			if (doorname == null) doorname="{null}";
			debug.Debugger.out("Stack.getKategorien("+doorname+"):"+e.getMessage());
			Logger.log("Stack.getKategorien("+doorname+"):"+e.getMessage());
		}
		closeDB();
		return datensatz;
	}

	/**
	 * Löscht den gewählten Eintrag
	 * 
	 * @param category
	 *            --> Name der zu löschenden Kategorie
	 */
	public static boolean delStack (String category) {

		boolean worked = false;

		Database.setConnection(Database.getDbURL());
		try {
			// Tabelle Kategorie erstellen, sofern sie nicht existiert
			createTableIfNotExists(Stack.myTableName, Stack.myAttributes);
			

			String substring = 	 "(SELECT "+Stack.myPrimaryKey+" FROM "+Stack.myTableName
								+" WHERE "+Stack.mySeekAttribute+" = '" + category + "')";
			deleteSQL (Database.myTableName, Database.myFKName, substring);
			
			deleteSQL (Stack.myTableName, Stack.mySeekAttribute, "'"+category+"'");

			worked = true;
		}
		catch (Exception e) {
			if (category == null) category="{null}";
			debug.Debugger.out("Stack.delStack("+category+"):"+e.getMessage());
			Logger.log("Stack.getStack("+category+"):"+e.getMessage());
		}
		closeDB();
		return worked;
	}

	public static ArrayList<String> getStacknames () {

		ArrayList<String> Stacks = new ArrayList<String>();

		Database.setConnection(Database.getDbURL());
		try {
			// Tabelle Kategorie erstellen, sofern sie nicht existiert
			createTableIfNotExists(Stack.myTableName, Stack.myAttributes);

			ResultSet StackSet = seekInTable(Stack.myTableName, Stack.mySeekAttribute);
			if (StackSet.isAfterLast()) {
				Stacks = null;
			}
			else {
				while (StackSet.next()) {
					StackSet.getRow();
					Stacks.add(StackSet.getString(Stack.mySeekAttribute));
				}
			}
			StackSet.close();
		}
		catch (Exception e) {
			debug.Debugger.out("Stack.getStacknames():"+e.getMessage());
			Logger.log("Stack.getStacknames():"+e.getMessage());
		}
		closeDB();
		return Stacks;
	}

	public int getStackID (String KategorieName) {

		int ID = 0;
		Database.setConnection(Database.getDbURL());
		try {
			// Tabelle Kategorie erstellen, sofern sie nicht existiert
			createTableIfNotExists(Stack.myTableName, Stack.myAttributes);
			ResultSet StackSet = seekInTable(	Stack.myTableName, Stack.myPrimaryKey, 
												Stack.mySeekAttribute, KategorieName);
			// TODO  test if set not empty?
			ID = Integer.parseInt(StackSet.getString(StackSet.getInt(1)));
			StackSet.close();
		}
		catch (Exception e) {
			if (KategorieName == null) KategorieName="{null}";
			debug.Debugger.out("Stack.getStackID("+KategorieName+"):"+e.getMessage());
			Logger.log("Stack.getStackID("+KategorieName+"):"+e.getMessage());
		}
		closeDB();
		return ID;
	}

	public static boolean possible (String boxName) {

		boolean worked = false;
		
		Database.setConnection(Database.getDbURL());
		try {
			ResultSet checkPossible = seekInTable(	Stack.myTableName, "*", 
													Stack.mySeekAttribute, boxName);
			if (checkPossible.next()) worked = true;
			checkPossible.close();
		}
		catch (Exception e) {
			if (boxName == null) boxName="{null}";
			debug.Debugger.out("Stack.possible("+boxName+"):"+e.getMessage());
			Logger.log("Stack.possible("+boxName+"):"+e.getMessage());
		}
		closeDB();
		return worked;
	}

	public static boolean update (String oldName, String newName) {

		boolean worked = false;

		Database.setConnection(Database.getDbURL());
		try {
			ResultSet checkStack = seekInTable(	Stack.myTableName, "*", 
													Stack.mySeekAttribute, oldName);
			if (checkStack.next()) {
				updateInTable(	Stack.myTableName, Stack.mySeekAttribute, newName, 
													Stack.mySeekAttribute, oldName);
				worked = true;
			}
			checkStack.close();
		}
		catch (Exception e) {
			if (oldName == null) oldName="{null}";
			if (newName == null) newName="{null}";
			debug.Debugger.out("Stack.update("+oldName+"'"+newName+"):"+e.getMessage());
			Logger.log("Stack.update("+oldName+"'"+newName+"):"+e.getMessage());
		}
		closeDB();
		return worked;
	}
}
