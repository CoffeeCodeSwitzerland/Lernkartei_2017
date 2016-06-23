package database;

import java.sql.ResultSet;

import database.sql.Attribute;
import database.sql.Entity;
import debug.Logger;


public class UserEntity extends Entity {

	// URL und Driver

/**
	 * @param tabName
	 */
	public UserEntity(String tabName) {
		super(tabName, tabName+"_PK");
		// set table attributes
		Attribute a = new Attribute("ActualScore",0);
		myAttributes.add(a);
		a = new Attribute("Username");
		myAttributes.add(a);
		a = new Attribute("Email");
		myAttributes.add(a);
		a = new Attribute("Password");
		myAttributes.add(a);
		a = new Attribute("HighScore",0);
		myAttributes.add(a);
		a = new Attribute("UserType",0);
		myAttributes.add(a);
		createTableIfNotExists();
	}


	//	private static String	url		= "jdbc:sqlite:" + globals.Environment.getDatabasePath()
//			+ globals.Globals.db_name + ".db";
//	private static String	driver	= "org.sqlite.JDBC";
//
	private static Integer	anzahlLeben;
	private static Integer currentLifes;

	/**
	 * 
	 * Fragt den Score einer Kartei ab
	 * 
	 * @param Kartei
	 *            --> Welche Kartei, welche abgefragt werden soll
	 * @return --> Returned einen Double Wert des Scores, returned -1, wenn kein
	 *         Score vorhanden
	 */
	public void correctCard () {
		try {
			String sql = "CREATE TABLE IF NOT EXISTS Lifes " +
					"(PK_Lvs INTEGER PRIMARY KEY AUTOINCREMENT," +
					" Lifecount INTEGER DEFAULT 0);";
			stmt.executeUpdate(sql);
			Integer currentLifes = 0;
			String getCurrent = "SELECT Lifecount FROM Lifes";
			ResultSet getCurt = stmt.executeQuery(getCurrent);
			if (getCurt.next()) {
				currentLifes = getCurt.getInt("Lifecount");
				getCurt.close();
			} else {
				String newEntry = "INSERT INTO Lifes (Lifecount) VALUES (0)";
				stmt.executeUpdate(newEntry);
			}
			getCurt.close();
			String updt = "UPDATE Lifes SET Lifecount = " + (currentLifes + 1);
			stmt.executeUpdate(updt);
		}
		catch (Exception e) {
			Logger.out("Database.correctCard(): " + e.getMessage());
		}
	}

	public int getLifecount () {
		try {
			String sql = "CREATE TABLE IF NOT EXISTS Lifes " +
					"(PK_Lvs INTEGER PRIMARY KEY AUTOINCREMENT," +
					" Lifecount INTEGER DEFAULT 0);";
			stmt.executeUpdate(sql);
			Integer currentLifes = 0;
			String getCurrent = "SELECT Lifecount FROM Lifes";
			ResultSet rs = stmt.executeQuery(getCurrent);
			if(rs.next()){
				currentLifes = rs.getInt("Lifecount");
			}
			float notRounded = currentLifes / 30;
			anzahlLeben = Math.round(notRounded);
		}
		catch (Exception e) {
			Logger.out("Database.getLifecount(): " + e.getMessage());
		}
		return anzahlLeben;
	}

	public void death () {
		try {
			String sql = "CREATE TABLE IF NOT EXISTS Lifes " +
					"(PK_Lvs INTEGER PRIMARY KEY AUTOINCREMENT," +
					" Lifecount INTEGER DEFAULT 0);";
			stmt.executeUpdate(sql);
			Integer currentLifes = 0;
			String getCurrent = "SELECT Lifecount FROM Lifes";
			ResultSet rs = stmt.executeQuery(getCurrent);
			if(rs.next()){
				currentLifes = rs.getInt("Lifecount");
			}
			if (currentLifes >= 30) {
				String updt = "UPDATE Lifes SET Lifecount = " + (currentLifes - 30);
				stmt.executeUpdate(updt);
			}	
		}
		catch (Exception e) {
			Logger.out("Database.death(): " + e.getMessage());
		}
	}
	
	public int getCorrectCards () {
		try {
			String sql = "CREATE TABLE IF NOT EXISTS Lifes " +
					"(PK_Lvs INTEGER PRIMARY KEY AUTOINCREMENT," +
					" Lifecount INTEGER DEFAULT 0);";
			stmt.executeUpdate(sql);
			currentLifes = 0;
			String getCurrent = "SELECT Lifecount FROM Lifes";
			ResultSet rs = stmt.executeQuery(getCurrent);
			if(rs.next()){
				currentLifes = rs.getInt("Lifecount");
			}
		}
		catch (Exception e) {
			Logger.out("Database.getCorrectCards(): " + e.getMessage());
		}
		return currentLifes;
	}
}
