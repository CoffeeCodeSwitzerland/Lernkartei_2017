package database;

import java.sql.ResultSet;
import java.util.ArrayList;

import database.sql.Attribute;
import database.sql.Entity;
import database.sql.ForeignKey;
import debug.Logger;


public class StackEntity extends Entity {

	// Connectioninformationen URL & Driver

	/**
	 * @param tabName
	 */
	public StackEntity(String tabName) {
		super(tabName,"PK_"+tabName);
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

	public int newStack (String eingabe, String fk_door) {

		Integer FK_ID = 0;
		Integer errorMsg = 0;
		try {
			// Tabelle Kategorie erstellen, sofern sie nicht existiert
			String sql = "CREATE TABLE IF NOT EXISTS Kategorie "
					+ "(PK_Kategorie INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " Kategorie TEXT NOT NULL,"
					+ " FK_Door INTEGER NOT NULL)";
			stmt.executeUpdate(sql);
			// SELECT Befehl, welcher die ID der Tür abruft, in welcher die
			// Kategorie erstellt wird
			ResultSet id = stmt.executeQuery("SELECT PK_Doors FROM Doors WHERE Doorname = '" + fk_door + "'");
			// Überprüft, ob die Tür exisitert oder nicht

			if (id.next()) {
				FK_ID = id.getInt("PK_Doors");
				id.close();
			}
			else {
				return -1;
			}
			ResultSet check = stmt.executeQuery("SELECT * FROM Kategorie WHERE Kategorie = '" + eingabe + "'");
			if (check.next()) {
				check.close();
				return -2;
			}
			else {
				check.close();
			}
			// Erstellt die neue Kategorie als Eintrag in der Datenbank mit
			// einem Fremdkey für die Tür
			String insert = "INSERT INTO Kategorie (Kategorie, FK_Door)" +
					"VALUES ('" + eingabe + "', " + FK_ID + ")";
			stmt.executeUpdate(insert);
		}
		catch (Exception e) {
			Logger.out("Database.newStack(): " + e.getMessage());
		}
		return errorMsg;

	}

	public ArrayList<String> getKategorien (String doorname) {

		Integer FK_ID = 0;

		ArrayList<String> datensatz = new ArrayList<String>();

		String sql = "CREATE TABLE IF NOT EXISTS Kategorie "
				+ "(PK_Kategorie INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " Kategorie TEXT NOT NULL,"
				+ " FK_Door INTEGER NOT NULL);";

		try {
			// Tabelle generieren, falls nicht vorhanden
			stmt.executeUpdate(sql);
			// Abfrage, ob die Tür bereits existiert
			sql = "SELECT PK_Doors FROM Doors WHERE Doorname = '" + doorname + "';";
			ResultSet id = stmt.executeQuery(sql);
			if (id.next()) {
				FK_ID = id.getInt("PK_Doors");
			}
			else {
				FK_ID = 0;
			}
			id.close();
			// Alle Sets ausgeben, welche in dieser Tür enthalten sind
			sql = "SELECT * FROM Kategorie WHERE FK_Door = " + FK_ID + ";";
			ResultSet rs = stmt.executeQuery(sql);
			// Daten werden in die Liste geschrieben
			while (rs.next()) {
				datensatz.add(rs.getString("Kategorie"));
			}
			rs.close();
		}
		catch (Exception e) {
			Logger.out("Database.getKategorie("+doorname+"): " + e.getMessage());
		}
		return datensatz;
	}

	/**
	 * Löscht den gewählten Eintrag
	 * 
	 * @param category
	 *            --> Name der zu löschenden Kategorie
	 */
	public boolean delStack (String category) {

		boolean worked = false;
		String sql = null;
		try {
			// Tabelle generieren wenn nicht vorhanden
			sql = "CREATE TABLE IF NOT EXISTS Kategorie "
					+ "(PK_Kategorie INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " Kategorie TEXT NOT NULL,"
					+ " FK_Door INTEGER NOT NULL);";
			stmt.executeUpdate(sql);
			// Abfragen, ob zu löschende Kategorie vorhanden ist oder nicht.
			// Wenn ja, wird gelöscht
			sql = "SELECT Kategorie FROM Kategorie WHERE"+ " Kategorie = '" + category + "';";
			ResultSet del = stmt.executeQuery(sql);
			Integer setID  = del.getInt("Kategorie");
			boolean contin = del.next();
			del.close();
			if (contin) {
				try {
					sql = "DELETE FROM Stock WHERE Set_ID = " + setID + ";";
					stmt.executeUpdate(sql);
				} catch (Exception e) {
					Logger.out("Stack.delStack(" + category +"): " + e.getMessage());
				}
				sql = "DELETE FROM Kategorie WHERE Kategorie = '" + category + "';";
				stmt.executeUpdate(sql);
				worked = true;
			}
			else {
				worked = false;
			}
		}
		catch (Exception e) {
			Logger.out("Stack.delStack(" + category +"): " + e.getMessage());
		}
		return worked;
	}

	public ArrayList<String> getStacknames () {
		ArrayList<String> Stacks = new ArrayList<String>();

		try {
			// Tabelle generieren, falls nicht vorhanden
			String sql = "CREATE TABLE IF NOT EXISTS Kategorie "
					+ "(PK_Kategorie INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " Kategorie TEXT NOT NULL,"
					+ " FK_Door INTEGER NOT NULL)";

			stmt.executeUpdate(sql);
			ResultSet StackSet = stmt.executeQuery("SELECT Kategorie FROM Kategorie");
			if (StackSet.isAfterLast()) {
				Stacks = null;
			}
			else {

				while (StackSet.next()) {
					StackSet.getRow();
					Stacks.add(StackSet.getString("Kategorie"));
				}
			}
			StackSet.close();
		}
		catch (Exception e) {
			Logger.out("Database.getStackNames(): " + e.getMessage());
		}
		return Stacks;
	}

	public int getStackID (String KategorieName) {
		int ID = 0;
		try {
			String sql = "CREATE TABLE IF NOT EXISTS Kategorie "
					+ "(PK_Kategorie INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " Kategorie TEXT NOT NULL,"
					+ " FK_Door INTEGER NOT NULL)";
			stmt.executeUpdate(sql);
			ResultSet StackSet = stmt
					.executeQuery("SELECT PK_Kategorie FROM Kategorie WHERE Kategorie = '" + KategorieName + "'");
			ID = Integer.parseInt(StackSet.getString(StackSet.getInt(1)));
			StackSet.close();
		}
		catch (Exception e) {
			Logger.out("Database.getStackID(): " + e.getMessage());
		}
		return ID;
	}

	public boolean possible (String boxName) {
		try {
			String sql = "SELECT * FROM Kategorie WHERE Kategorie = '" + boxName + "';";
			ResultSet checkPossible = stmt.executeQuery(sql);
			if (checkPossible.next()) {
				checkPossible.close();
				return false;
			}
			else {
				checkPossible.close();
				return true;
			}

		}
		catch (Exception e) {
			Logger.out("Database.possible(): " + e.getMessage());
		}
		return false;
	}

	public boolean update (String oldName, String newName) {
		boolean worked = true;
		try {
			ResultSet checkStack = stmt.executeQuery("SELECT * FROM Kategorie WHERE Kategorie = '" + oldName + "';");
			if (checkStack.next()) {
				String updateStack = "UPDATE Kategorie SET Kategorie = '" + newName + "' WHERE Kategorie = '" + oldName + "';";
				stmt.executeUpdate(updateStack);
				worked = true;
				checkStack.close();
			}
			else {
				worked = false;
				checkStack.close();
			}
		}
		catch (Exception e) {
			Logger.out("Database.update(): " + e.getMessage());
		}
		return worked;
	}
}
