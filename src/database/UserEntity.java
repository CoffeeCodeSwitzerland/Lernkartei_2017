package database;

import database.jdbc.DBDriver;
import database.sql.Attribute;
import database.sql.Entity;
import database.sql.SQLHandler;

public class UserEntity extends Entity {

	// URL und Driver

	/**
	 * @param tabName
	 */
	public UserEntity(DBDriver dbDriver, String tabName) {
		super(dbDriver, tabName, "PK_"+tabName, false);
		// set table attributes
		Attribute a = new Attribute("ActualScore", 0);
		myAttributes.addUnique(a);
		a = new Attribute("Username", "def");
		myAttributes.addUnique(a);
		a = new Attribute("Email");
		myAttributes.addUnique(a);
		a = new Attribute("Password"); // als Passwort wird der gesalzene Hash
										// gespeichert
		myAttributes.addUnique(a);
		a = new Attribute("Salz"); // Hier wird das Salz gespeichert
		myAttributes.addUnique(a);
		a = new Attribute("HighScore", 0);
		myAttributes.addUnique(a);
		a = new Attribute("UserType", 0);
		myAttributes.addUnique(a);
		createTableIfNotExists();
	}

	// private static String url = "jdbc:sqlite:" +
	// globals.Environment.getDatabasePath()
	// + globals.Globals.db_name + ".db";
	// private static String driver = "org.sqlite.JDBC";
	//
	private static Integer anzahlLeben;
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
	public void correctCard() {
		Integer currentLifes = 0;
		myDBDriver.executeQuery(SQLHandler.selectCommand(getMyTableName(), "ActualScore", null, null));
		// String getCurrent = "SELECT Lifecount FROM Lifes";
		if (myDBDriver.isThereAResult()) {
			currentLifes = myDBDriver.getResultPIntValueOf("ActualScore");
		} else {
			myAttributes.getAttributeNamedAs("ActualScore").setValue(0);
			// "INSERT INTO Lifes (Lifecount) VALUES (0)";
			myDBDriver.executeCommand(SQLHandler.insertIntoTableCommand(getMyTableName(), myAttributes));
		}
		myAttributes.getAttributeNamedAs("ActualScore").setValue(currentLifes + 1);
		Attribute k = new Attribute("Username", "def");
		// "UPDATE Lifes SET Lifecount = " + (currentLifes+1);
		myDBDriver.executeCommand(SQLHandler.updateInTableCommand(getMyTableName(), myAttributes, k));
	}

	public int getLifecount() {
		Integer currentLifes = 0;
		myDBDriver.executeQuery(SQLHandler.selectCommand(getMyTableName(), "ActualScore", null, null));
		// String getCurrent = "SELECT Lifecount FROM Lifes";
		if (myDBDriver.isThereAResult()) {
			currentLifes = myDBDriver.getResultPIntValueOf("ActualScore");
		}
		float notRounded = currentLifes / 30;
		anzahlLeben = Math.round(notRounded);
		return anzahlLeben;
	}

	public void death() {
		Integer currentLifes = 0;
		myDBDriver.executeQuery(SQLHandler.selectCommand(getMyTableName(), "ActualScore", null, null));
		if (myDBDriver.isThereAResult()) {
			currentLifes = myDBDriver.getResultPIntValueOf("ActualScore");
		}
		if (currentLifes >= 30) {
			myAttributes.getAttributeNamedAs("ActualScore").setValue(currentLifes - 30);
			Attribute k = new Attribute("Username", "def");
			myDBDriver.executeCommand(SQLHandler.updateInTableCommand(getMyTableName(), myAttributes, k));
		}
	}

	public int getCorrectCards() {
		currentLifes = 0;
		myDBDriver.executeQuery(SQLHandler.selectCommand(getMyTableName(), "ActualScore", null, null));
		if (myDBDriver.isThereAResult()) {
			currentLifes = myDBDriver.getResultPIntValueOf("ActualScore");
		}
		return currentLifes;
	}

	// Überprüft, ob ein Eintrag bereits vorhanden ist gesteuert mit dem
	// Usernamen. Somit ist der Username einmalig
	public boolean checkDatabase(String attribut, String nameToCheck) {
		myDBDriver.executeQuery(SQLHandler.selectCommand(getMyTableName(), attribut, attribut, nameToCheck));
		if (myDBDriver.isThereAResult()) {
			return true;
		}
		return false;
	}

	/**
	 * insertIntoTableCommand(String tabName, AttributeList attributes, String
	 * att1, String val1, String att2, String val2)
	 * 
	 * @param attribute
	 *            Welche Eigenschaft
	 * @param keyName
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	public boolean setData(String attribute, String newValue) {
		return myDBDriver.executeQuery(SQLHandler.insertIntoTableCommand(getMyTableName(), myAttributes, attribute, newValue, null, null));
	}

	public String loadData(String attribute, String username) {
		myDBDriver.executeQuery(SQLHandler.selectCommand(getMyTableName(), attribute, attribute, username));
		if (myDBDriver.isThereAResult())
			return myDBDriver.getResultValueOf(attribute);
		return "leer";
	}
}
