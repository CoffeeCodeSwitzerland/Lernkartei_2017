package database;

import java.sql.ResultSet;

import debug.Logger;


public class UserLogin extends MYSQLConnector {

	protected static String myTableName  = "Users";
	protected static String mySeekAttribute = "Username";
	protected static String myPrimaryKey = "PK_Usr";
	private   static String myAttributeList = mySeekAttribute + ", Email, Password, Rule";
	private   static String myAttributes = 
									myPrimaryKey + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
									mySeekAttribute +" TEXT NOT NULL," +
									" Email 	TEXT NOT NULL," +
									" Password 	TEXT NOT NULL," +
									" Rule 		TEXT NOT NULL";
	/**
	 * Fügt der Datenbank einen neuen User hinzu
	 * 
	 * @param values
	 *            --> String Array mit 3 Werten. Username, E-Mail, Passwort
	 * @param teacher
	 *            --> Boolean: true = Lehrer, false = Schüler
	 */
	public static void newUser (String[] values, boolean teacher) {

		Database.setConnection(Database.getDbURL());
//		String usedb = "USE " + "userdb";
//		stmt.executeQuery(usedb);

		try {

			// TODO Werte anders setzen!!!
//			String studValues = "";
//			for (int i = 0; i < values.length; i++) {
//				if (i < values.length - 1) {
//					studValues += "'" + values[i] + "',";
//				}
//				else {
//					studValues += "'" + values[i] + "'";
//				}
//			}
//			String teachValues = "";
//			for (int i = 0; i < values.length; i++) {
//				if (i < values.length - 1) {
//					teachValues += "'" + values[i] + "',";
//				}
//				else {
//					teachValues += "'" + values[i] + "'";
//				}
//			}

			createTableIfNotExists(UserLogin.myTableName, UserLogin.myAttributes);

			if (teacher) {
				// TODO set teacher rule
				insertIntoTable (UserLogin.myTableName, UserLogin.myAttributeList, values);
			} else {
				// TODO set student rule
				insertIntoTable (UserLogin.myTableName, UserLogin.myAttributeList, values);
			}
		}
		catch (Exception e) {
			debug.Debugger.out("UserLogin.newUser(): "+e.getMessage());
			Logger.log("UserLogin.newUser(): "+e.getMessage());
		}
		closeDB();
	}

	/**
	 * Überprüft ob ein Wert bereits vorhanden ist
	 * 
	 * @param values
	 *            --> String Array mit 2 Werten, Username und Email
	 * @return --> Retourniert true, wenn möglich, sonst false
	 */

	public static boolean checkPossible (String[] values) {

		boolean possible = true;

		// Überprüfen ob ein Lehrer mit dem Username oder Email vorhanden ist
		Database.setConnection(Database.getDbURL());
		try {
			createTableIfNotExists(UserLogin.myTableName, UserLogin.myAttributes);

			ResultSet rsUsern = seekInTable( 	UserLogin.myTableName, UserLogin.mySeekAttribute,
					 							UserLogin.mySeekAttribute, values[0]);
			ResultSet rsEmail = seekInTable( 	UserLogin.myTableName, "EMail",
					 							"EMail", values[1]);
			if (rsUsern.next() || rsEmail.next()) {
				possible = false;
			}
			rsUsern.close();
			rsEmail.close();
		}
		catch (Exception e) {
			debug.Debugger.out("UserLogin.checkPossible(): "+e.getMessage());
			Logger.log("UserLogin.checkPossible(): "+e.getMessage());
		}
		// Wenn keine Lehrer und keine Schüler mit passendem Username oder Email
		// vorhanden ist, ist es möglich
		closeDB();
		return possible;
	}
	
	/**
	 * Wird gebraucht bei der Änderung des Usernamens
	 * 
	 * @param newName -> der gewünschte neue Name
	 * @return true -> Konnte ändern | false -> Konnte nicht ändern
	 */
	
	public static boolean changeUsername(String newName, String oldName) {
		
		Database.setConnection(Database.getDbURL());
		try {
			ResultSet rsT = seekInTable( 	UserLogin.myTableName, "*",
											UserLogin.mySeekAttribute, newName);
			if (rsT == null)
			{
				updateInTable (	UserLogin.myTableName, UserLogin.mySeekAttribute, newName, 
								UserLogin.mySeekAttribute, oldName);
				closeDB();
				return true;
			}
		} catch (Exception e)
		{
			debug.Debugger.out("UserLogin.changeUserName(): "+e.getMessage());
			Logger.log("UserLogin.changeUserName(): "+e.getMessage());
		}
		closeDB();
		return false;
	}

	/**
	 * Löscht den angegebenen User
	 * 
	 * @param delName
	 *            --> String mit Username
	 * 
	 */

	public static void delUser (String delName) {
		Database.setConnection(Database.getDbURL());
		deleteSQL (UserLogin.myTableName, UserLogin.mySeekAttribute, delName);
		closeDB();
	}

	/**
	 * Überprüft die Login Daten
	 * 
	 * @param userData
	 *            --> Benötigt ein String Array mit Username, Password
	 * @return --> True = Korrekt, False = Inkorrekt
	 */

	public static boolean loginUser (String[] userData) {

		boolean loggedIn = false;
		String password = null;

		Database.setConnection(Database.getDbURL());
		try {
			ResultSet rs = seekInTable( UserLogin.myTableName, "Password",
										UserLogin.mySeekAttribute, userData[0]);
			while (rs.next()) {
				password = rs.getString("Password");
			}
		}
		catch (Exception e) {
			debug.Debugger.out("UserLogin.loginUser(): "+e.getMessage());
			Logger.log("UserLogin.loginUser(): "+e.getMessage());
		}
		if (userData[1].equals(password)) {
			loggedIn = true;
		}
		closeDB();
		return loggedIn;
	}	
}

