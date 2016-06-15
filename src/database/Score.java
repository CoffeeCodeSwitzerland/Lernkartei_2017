package database;

import java.sql.ResultSet;

import debug.Logger;


public class Score extends SQLiteConnector {

	protected static String myTableName  = "Lifes";
	protected static String mySeekAttribute = "Lifecount";
	protected static String myPrimaryKey = "PK_Lvs";
//	private   static String myFKName     = "FK_Door";
//	private   static String myAttributeList = mySeekAttribute;
	private   static String myAttributes = 
									  myPrimaryKey + " INTEGER PRIMARY KEY AUTOINCREMENT,"
									+ " "+ mySeekAttribute + " INTEGER DEFAULT 0";
	/**
	 * 
	 * Fragt den Score einer Kartei ab
	 * 
	 * @param Kartei
	 *            --> Welche Kartei, welche abgefragt werden soll
	 * @return --> Returned einen Double Wert des Scores, returned -1, wenn kein
	 *         Score vorhanden
	 */
	public static void correctCard () {

		Database.setConnection(Database.getDbURL());
		try {
			createTableIfNotExists(Score.myTableName, Score.myAttributes);
			Integer currentLifes = 0;

			ResultSet getCurt = seekInTable( Score.myTableName, Score.mySeekAttribute);
			if (getCurt.next()) {
				currentLifes = getCurt.getInt(Score.mySeekAttribute);
				getCurt.close();
			} else {
				// TODO bei der Verallgemeinerung values[0] = 0 setzen 
				insertSQL ("INSERT INTO Lifes (Lifecount) VALUES (0)");
			}
			getCurt.close();
			updateSQL("UPDATE Lifes SET Lifecount = " + (currentLifes + 1));
		}
		catch (Exception e) {
			debug.Debugger.out("Score.correctCard(): "+e.getMessage());
			Logger.log("Score.correctCard(): "+e.getMessage());
		}
		closeDB();
	}

	private static int getCurrentLifes () {
		Integer currentLifes = 0;
		try {
			createTableIfNotExists(Score.myTableName, Score.myAttributes);
			ResultSet rs = seekInTable( Score.myTableName, Score.mySeekAttribute);
			if(rs.next()){
				currentLifes = rs.getInt(Score.mySeekAttribute);
			}
			rs.close();
		}
		catch (Exception e) {
			debug.Debugger.out("Score.getCurrenLifes(): "+e.getMessage());
			Logger.log("Score.getCurrentLifes(): "+e.getMessage());
		}
		return currentLifes;
	}

	public static int getLifecount () {
		Database.setConnection(Database.getDbURL());
		int currentLifes = getCurrentLifes ();
		float notRounded = currentLifes / 30;
		Integer anzahlLeben = Math.round(notRounded);
		closeDB();
		return anzahlLeben;
	}

	public static void death () {
		Database.setConnection(Database.getDbURL());
		int currentLifes = getCurrentLifes ();
		if (currentLifes >= 30) {
			updateSQL("UPDATE Lifes SET Lifecount = " + (currentLifes -30));
		}	
		closeDB();
	}
	
	public static int getCorrectCards () {
		Database.setConnection(Database.getDbURL());
		int currentLifes = getCurrentLifes ();
		closeDB();
		return currentLifes;
	}
}
