package database;

import java.sql.ResultSet;

import database.sql.Attribute;
import database.sql.Entity;
import database.sql.SQLHandler;
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
		a = new Attribute("Username","def");
		myAttributes.add(a);
		a = new Attribute("Email");
		myAttributes.add(a);
		a = new Attribute("Password"); //als Passwort wird der gesalzene Hash gespeichert
		myAttributes.add(a);
		a = new Attribute("Salz"); //Hier wird das Salz gespeichert
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
			Integer currentLifes = 0;
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(),"ActualScore",null,null)); 
			setLastResultSet(executeQuery(getLastSQLCommand()));
			//String getCurrent = "SELECT Lifecount FROM Lifes";
			if (getLastResultSet().next()) {
				currentLifes = getLastResultSet().getInt("ActualScore");
				getLastResultSet().close();
			} else {
				getLastResultSet().close();
				myAttributes.seekKeyNamed("ActualScore").setValue(0);
				setLastSQLCommand(SQLHandler.insertIntoTableCommand(getMyTableName(), myAttributes)); 
				//String newEntry = "INSERT INTO Lifes (Lifecount) VALUES (0)";
			}
			myAttributes.seekKeyNamed("ActualScore").setValue(currentLifes + 1);
			Attribute k = new Attribute("Username","def");
			setLastSQLCommand(SQLHandler.updateInTableCommand(getMyTableName(),myAttributes,k)); 
			//String updt = "UPDATE Lifes SET Lifecount = " + (currentLifes + 1);
		}
		catch (Exception e) {
			Logger.out(e.getMessage());
		}
	}

	public int getLifecount () {
		try {
			Integer currentLifes = 0;
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(),"ActualScore",null,null)); 
			setLastResultSet(executeQuery(getLastSQLCommand()));
			//String getCurrent = "SELECT Lifecount FROM Lifes";
			if (getLastResultSet().next()) {
				currentLifes = getLastResultSet().getInt("ActualScore");
			}
			getLastResultSet().close();
			float notRounded = currentLifes / 30;
			anzahlLeben = Math.round(notRounded);
		}
		catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return anzahlLeben;
	}

	public void death () {
		try {
			Integer currentLifes = 0;
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(),"ActualScore",null,null)); 
			setLastResultSet(executeQuery(getLastSQLCommand()));
			if (getLastResultSet().next()) {
				currentLifes = getLastResultSet().getInt("ActualScore");
			}
			if (currentLifes >= 30) {
				myAttributes.seekKeyNamed("ActualScore").setValue(currentLifes - 30);
				Attribute k = new Attribute("Username","def");
				setLastSQLCommand(SQLHandler.updateInTableCommand(getMyTableName(),myAttributes,k)); 
			}	
		}
		catch (Exception e) {
			Logger.out(e.getMessage());
		}
	}
	
	public int getCorrectCards () {
		try {
			currentLifes = 0;
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(),"ActualScore",null,null)); 
			setLastResultSet(executeQuery(getLastSQLCommand()));
			if (getLastResultSet().next()) {
				currentLifes = getLastResultSet().getInt("ActualScore");
			}
		}
		catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return currentLifes;
	}
	
	//Überprüft, ob ein Eintrag bereits vorhanden ist gesteuert mit dem Usernamen. Somit ist der Username einmalig
	public String checkDatabase(Attribute attribut, String nameToCheck) {
		try {
			setLastSQLCommand(SQLHandler.selectCommand(getMyTableName(),"name",attribut.getName(),nameToCheck)); 
			setLastResultSet(executeQuery(getLastSQLCommand()));
			if (getLastResultSet().next()) {
				String nam = getLastResultSet().getString("name");
				return nam;
			}
		} catch (Exception e) {
			Logger.out(e.getMessage());
		}
		return null;
	}
	
//	//ladet den Hash -> Passwort und Salt gehasht
//	public String getHash() {
//		ResultSet rs = null;
//		rs = 
//	}
//	
//	//ladet das Salt
//	public String getSalt() {
//		
//	}
}
