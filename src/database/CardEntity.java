package database;

import java.util.ArrayList;

import database.jdbc.DBDriver;
import database.sql.Attribute;
import database.sql.Entity;
import database.sql.ForeignKey;
import database.sql.KeyAttribute;
import database.sql.SQLHandler;
import debug.Logger;

public class CardEntity extends Entity {

	/**
	 * @param myNewTableName
	 */
	public CardEntity(DBDriver dbDriver, String tabName) {
		super(dbDriver, tabName, "PK_" + tabName, false);
		// set table attributes
		ForeignKey f = new ForeignKey("PK_STACK");
		myAttributes.addUnique(f);
		Attribute a = new Attribute("Frontside");
		myAttributes.addUnique(a);
		a = new Attribute("Backside");
		myAttributes.addUnique(a);
		KeyAttribute k = new KeyAttribute("Priority", 0, "1");
		myAttributes.addUnique(k);
		a = new Attribute("Color");
		myAttributes.addUnique(a);
		a = new Attribute("Link");
		myAttributes.addUnique(a);
		a = new Attribute("Description");
		myAttributes.addUnique(a);
		a = new Attribute("Date");
		myAttributes.addUnique(a);
		createTableIfNotExists();
	}

	/**
	 * Keine neue Instanz Database erstellen, sondern nur die Methode benutzen
	 * 
	 * @param values
	 *            --> Array mit 5 Werten: 0. PK_STACK 1. Vorderseite, 2.
	 *            Rückseite, 3. Priorität (1-5), 4. Color, 5... @ deprecated
	 */
	public boolean pushToStock(String[] values) {
		String setID;
		String id = values[0];
		// Do "SELECT PK_STACK FROM STACK WHERE name = '" + id + "'"
		myDBDriver.executeQuery(SQLHandler.selectCommand("STACK", "PK_STACK", "name", id));
		if (myDBDriver.isThereAResult()) {
			setID = myDBDriver.getResultValueOf("PK_Stack");
		} else {
			Logger.out("...1. no Stack in database for '" + id + "'!", getMyTableName());
			return false;
		}
		myAttributes.getAttributeNamedAs("PK_STACK").setValue(setID);
		myAttributes.setValuesAfterPrimaryKey(values);
		return (myDBDriver.executeCommand(SQLHandler.insertIntoTableCommand(getMyTableName(), myAttributes)) > 0) ? true : false;
	}

	/**
	 * Keine neue Instanz Database erstellen, sondern nur die Methode benutzen
	 * 
	 * @return Retourniert eine ArrayList mit Arrays mit 7 Werten: PK, Vorder-,
	 *         Rückseite, Description, Set_ID, Priorität, Farbe
	 */

	public ArrayList<String[]> pullFromStock(String whichSet) {

		ArrayList<String[]> results = new ArrayList<String[]>();
		int ID_SET = 0;
		// Do "SELECT PK_Kategorie FROM Kategorie WHERE Kategorie = '" +
		// whichSet + "'"
		myDBDriver.executeQuery(SQLHandler.selectCommand("STACK", "PK_STACK", "name", whichSet));
		if (myDBDriver.isThereAResult()) {
			ID_SET = myDBDriver.getResultPIntValueOf("PK_STACK");
		} else {
			Logger.out("...2. no Stack's in database for " + whichSet + "!", getMyTableName());
			return null;
		}
		// Do "SELECT * FROM Card WHERE Set_ID = '" + IDwhichSet + "'"
		myDBDriver.executeQuery(SQLHandler.selectCommand(getMyTableName(), null, "PK_STACK", ID_SET));
		while (myDBDriver.isThereAResult()) {
			String[] set = new String[7];
			set[0] = myDBDriver.getResultValueOf("PK_CARD");
			set[1] = myDBDriver.getResultValueOf("Frontside");
			set[2] = myDBDriver.getResultValueOf("Backside");
			set[3] = myDBDriver.getResultValueOf("Description");
			set[4] = myDBDriver.getResultValueOf("PK_STACK");
			set[5] = myDBDriver.getResultValueOf("Priority");
			set[6] = myDBDriver.getResultValueOf("Color");
			results.add(set);
		}
		return results;
	}

	public boolean delEntry(String id) {
		return (myDBDriver.executeCommand(SQLHandler.deleteEntryCommand(getMyTableName(), "PK_CARD", id)) >= 0) ? true : false;
	}

	/**
	 * Überschreibt Werte in der Datenbank um Karten zu editieren.
	 * 
	 * @param id
	 *            --> Welche Karte mit ID soll geändert werden
	 * @param frontside
	 *            --> Welcher Text als Vorderseite
	 * @param backside
	 *            --> Welcher Text als Rückseite
	 * @return --> True: Funktionierte, False: Nicht geklappt
	 */

	public boolean editEntry(String idname, String frontside, String backside) {
		int PK_ID = 0;
		String pkStack = "";
		// Do "SELECT * FROM Card WHERE PK_Card = " + idnema
		myDBDriver.executeQuery(SQLHandler.selectCommand(getMyTableName(), null, "PK_CARD", idname));
		if (!myDBDriver.isThereAResult()) {
			return false;
		} else {
			PK_ID = myDBDriver.getResultPIntValueOf("PK_CARD");
			pkStack = myDBDriver.getResultValueOf("PK_STACK");
		}
		// Do "UPDATE Card SET Frontside = '" + frontside + "', Backside =
		// '" + backside
		// + "' WHERE PK_Card = " + id;
		myAttributes.getAttributeNamedAs("Frontside").setValue(frontside);
		myAttributes.getAttributeNamedAs("Backside").setValue(backside);
		myAttributes.getAttributeNamedAs("PK_STACK").setValue(pkStack);
		Attribute key = new Attribute("PK_CARD", PK_ID);
		return (myDBDriver.executeCommand(SQLHandler.updateInTableCommand(getMyTableName(), myAttributes, key)) >= 0) ? true : false;
	}

	/**
	 * Erhöht die Priorität um 1, Legt die Karte nach hinten, bei 5, bleibt sie
	 * 
	 * @param PK_ID
	 *            --> PK_Stock ID der Karte, welche erhöht wird
	 */
	public void upPrio(Integer PK_ID) {

		int oldPrio = 0;
		int newPrio = 0;
		// Do "SELECT * FROM Card WHERE PK_Card = " + id;
		myDBDriver.executeQuery(SQLHandler.selectCommand(getMyTableName(), null, "PK_CARD", PK_ID.toString()));
		/* Changed from negation to approval because like this it doesn't change the value of each card to the same value.
		 * Philippe Krüttli - 01.06.2017 */
		if (myDBDriver.isThereAResult()) { // Frage die Aktuelle Priorität ab
			oldPrio = myDBDriver.getResultPIntValueOf("PK_CARD");
		} else {
			debug.Debugger.out("No Card with PK_CARD='" + PK_ID.toString() + "' exists.");
		}
		// Wenn Aktuelle Priorität = 5, bleibt die neue bei 5, sonst wird
		// sie um 1 erhöht
		if (oldPrio == 5) {
			newPrio = 5;
		} else {
			newPrio = oldPrio + 1;
		}
		// Schreibt die Neue Priorität in die Datenbank
		Attribute k = new Attribute("Priority", newPrio);
		// Do "UPDATE Card SET Priority = " + newPrio + " WHERE PK_Cadr = "
		// + PK_ID
		myDBDriver.executeCommand(SQLHandler.updateInTableCommand(getMyTableName(), myAttributes, k));
	}

	/**
	 * Bei nicht wissen der Karte, wird die Prio zurückgesetzt --> Sprich auf 0
	 * gesetzt
	 * 
	 * @param karte
	 *            --> Welche Karte reseted wird
	 */
	public void resetPrio(Integer PK_ID) {
		// Setzt die Priorität zurück auf 1
		myAttributes.getAttributeNamedAs("Priority").setValue(1);
		Attribute k = new Attribute("PK_Stack", PK_ID);
		// Do "UPDATE Stock SET Priority = 1 WHERE PK_Stk = " + PK_ID
		myDBDriver.executeCommand(SQLHandler.updateInTableCommand(getMyTableName(), myAttributes, k));
	}

	/**
	 * Liefert die Priorität der Karte mit mitgegebener ID mit
	 * 
	 * @param ID_Card
	 *            --> ID der Karte, von welcher die Priorität gebraaucht wird
	 * @return --> Gibt die Kartenpriorität als Integer zurück
	 */
	public int getPriority(String ID_Card) {
		int prio = 0;
		// Do "SELECT Priority FROM Stock WHERE PK_Stk = " + ID_Card
		myDBDriver.executeQuery(SQLHandler.selectCommand(getMyTableName(), "Priority", "PK_CARD", ID_Card));
		if (myDBDriver.isThereAResult()) {
			prio = myDBDriver.getResultPIntValueOf("Priority");
		} else {
			debug.Debugger.out("No such Cards exists with PK_CARD (" + ID_Card + ")!");
		}
		return prio;
	}

	/**
	 * Liefert den Maximalen und den bisher erreichten Score eines Stacks zurück
	 * 
	 * @param whichSet
	 *            --> Score von welchem Stack geliefert werden soll
	 * @return --> Retourniert diesen gewünschten Score
	 */
	public Double[] getScore(String whichSet) {

		Double maxPoints = 0.0;
		Double reachedPoints = 0.0;
		Double[] score = new Double[2];
		// Alle Prioritäten aus Tabelle hlen, welche als Set das mitgegebene
		// haben.
		// setLastSQLCommand(SQLHandler.selectCommand("STACK","Priority","PK_STACK",
		// ID_Card));
		// setLastResultSet(executeQuery(getLastSQLCommand()));

		myDBDriver.executeQuery("SELECT Priority FROM CARD WHERE PK_STACK = (SELECT PK_STACK FROM STACK" + " WHERE name = '"
				+ whichSet + "')");

		// Durch loopen und die Maximale sowie die Erreichte Punktzahl
		// speichern
		if (myDBDriver.isThereAResult()) {
			maxPoints += 4.0;
			reachedPoints += myDBDriver.getResultPIntValueOf("Priority") - 1.0;
			while (myDBDriver.isThereAResult()) {
				maxPoints += 4.0;
				reachedPoints += myDBDriver.getResultPIntValueOf("Priority") - 1.0;
			}
		} else {
			return null;
		}
		// Erreichte Punktzahl zurückgeben
		score[0] = maxPoints;
		score[1] = reachedPoints;
		return score;
	}

	public String[] getFrontAndBackside(String Stack, int kartenID) {

		ArrayList<String[]> cards = pullFromStock(Stack);

		String vorderseite = cards.get(kartenID)[1];
		String rückseite = cards.get(kartenID)[2];

		String[] VorderUndRückseite = { vorderseite, rückseite };

		return VorderUndRückseite;
	}
}
