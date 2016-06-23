package database;

import java.sql.ResultSet;

import database.sql.Attribute;
import database.sql.Entity;
import debug.Logger;


public class UserLogin extends Entity {

	/**
	 * @param tabName
	 */
	public UserLogin(String tabName) {
		super(tabName, tabName+"_PK");
		// set table attributes
		Attribute a = new Attribute("Name");
		myAttributes.add(a);
		Attribute p = new Attribute("Password");
		myAttributes.add(p);
		createTableIfNotExists();
	}

	protected static String myTableName  = "Users";
	protected static String mySeekAttribute = "Username";
	protected static String myPrimaryKey = "PK_Usr";
//	private   static String myAttributeList = mySeekAttribute + ", Email, Password, Rule";
//	private   static String myAttributes = 
//									myPrimaryKey + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
//									mySeekAttribute +" TEXT NOT NULL," +
//									" Email 	TEXT NOT NULL," +
//									" Password 	TEXT NOT NULL," +
//									" Rule 		TEXT NOT NULL";
	/**
	 * Fügt der Datenbank einen neuen User hinzu
	 * 
	 * @param values
	 *            --> String Array mit 3 Werten. Username, E-Mail, Passwort
	 * @param teacher
	 *            --> Boolean: true = Lehrer, false = Schüler
	 */
	public void newUser (String[] values, boolean teacher) {
		try {
			String studValues = "";
			for (int i = 0; i < values.length; i++) {
				if (i < values.length - 1) {
					studValues += "'" + values[i] + "',";
				}
				else {
					studValues += "'" + values[i] + "'";
				}
			}
			String teachValues = "";
			for (int i = 0; i < values.length; i++) {
				if (i < values.length - 1) {
					teachValues += "'" + values[i] + "',";
				}
				else {
					teachValues += "'" + values[i] + "'";
				}
			}
			String usedb = "USE " + "userdb";
			stmt.executeQuery(usedb);
			String sql1 = "CREATE TABLE IF NOT EXISTS Teachers " +
					"(PK_Teachers INT PRIMARY KEY AUTO_INCREMENT," +
					" Username TEXT NOT NULL," +
					" Email TEXT NOT NULL," +
					" Password TEXT NOT NULL" + ")";
			stmt.executeUpdate(sql1);
			String sql2 = "CREATE TABLE IF NOT EXISTS Students " +
					"(PK_Students INT PRIMARY KEY AUTO_INCREMENT," +
					" Username TEXT NOT NULL," +
					" Email TEXT NOT NULL," +
					" Password TEXT NOT NULL" + ")";
			stmt.executeUpdate(sql2);
			if (teacher) {
				String newTeach = "INSERT INTO Teachers (Username, Email, Password) VALUES (" + teachValues + ")";
				stmt.executeUpdate(newTeach);
			}
			else if (!teacher) {
				String newStud = "INSERT INTO Students (Username, Email, Password) VALUES (" + studValues + ")";
				stmt.executeUpdate(newStud);
			}
			stmt.close();
		}
		catch (Exception e) {
			Logger.out("Database.newUser(): " + e.getMessage());
		}
	}

	/**
	 * Überprüft ob ein Wert bereits vorhanden ist
	 * 
	 * @param values
	 *            --> String Array mit 2 Werten, Username und Email
	 * @return --> Retourniert true, wenn möglich, sonst false
	 */

	public boolean checkPossible (String[] values) {

		boolean possible = true;
		boolean noTeacher = true;
		boolean noStudent = true;

		// Überprüfen ob ein Lehrer mit dem Username oder Email vorhanden ist
		try {
			String sql1 = "CREATE TABLE IF NOT EXISTS Teachers " +
					"(PK_Lehrer INT PRIMARY KEY AUTO_INCREMENT," +
					" Username TEXT NOT NULL," +
					" Email TEXT NOT NULL," +
					" Password TEXT NOT NULL" + ")";
			stmt.executeUpdate(sql1);
			String sql2 = "CREATE TABLE IF NOT EXISTS Students " +
					"(PK_Lehrer INT PRIMARY KEY AUTO_INCREMENT," +
					" Username TEXT NOT NULL," +
					" Email TEXT NOT NULL," +
					" Password TEXT NOT NULL" + ")";
			stmt.executeUpdate(sql2);
			ResultSet rsUsern = stmt.executeQuery("SELECT Username FROM Teachers WHERE Username = " + "'" + values[0] + "'");
			ResultSet rsEmail = stmt.executeQuery("SELECT Email FROM Teachers WHERE Email = " + "'" + values[1] + "'");
			if (rsUsern.next() || rsEmail.next()) {
				noTeacher = false;
			}
			rsUsern.close();
			rsEmail.close();
			stmt.close();
		}
		catch (Exception e) {
			Logger.out("UserLogin.checkPossible(): " + e.getMessage());
		}
		// Überprüfen ob ein Schüler mit dem Username oder Email vorhanden ist
		try {
			ResultSet rsUsern = stmt
					.executeQuery("SELECT Username FROM Students WHERE Username = " + "'" + values[0] + "'");
			ResultSet rsEmail = stmt.executeQuery("SELECT Email FROM Students WHERE Email = " + "'" + values[1] + "'");
			if (rsUsern.next() || rsEmail.next()) {
				noStudent = false;
			}
			rsUsern.close();
			rsEmail.close();
			stmt.close();
		}
		catch (Exception e) {
			Logger.out("UserLogin.checkPossible(): " + e.getMessage());
		}
		// Wenn keine Lehrer und keine Schüler mit passendem Username oder Email
		// vorhanden ist, ist es möglich
		if (noTeacher && noStudent) {
			possible = true;
		}
		else {
			possible = false;
		}
		return possible;
	}
	
	/**
	 * Wird gebraucht bei der Änderung des Usernamens
	 * 
	 * @param newName -> der gewünschte neue Name
	 * @return true -> Konnte ändern | false -> Konnte nicht ändern
	 */
	public boolean changeUsername(String newName, String oldName) {
		try
		{
			String checkT = "SELECT * FROM Teachers WHERE Username = " + newName + ";";
			String checkS = "SELECT * FROM Students WHERE Username = " + newName + ";";
			String insertT ="INSERT INTO Teachers (Username) VALUES (" + newName + ") WHERE Username = " + oldName + ";";
			String insertS ="INSERT INTO Students (Username) VALUES (" + newName + ") WHERE Username = " + oldName + ";";
			ResultSet rsT = stmt.executeQuery(checkT);
			ResultSet rsS = stmt.executeQuery(checkS);
			if (rsT == null && rsS == null)
			{
				try
				{
					stmt.executeUpdate(insertT);
				} catch (Exception e)
				{
					stmt.executeUpdate(insertS);
				}
				return true;
			} else {
				return false;
			}
		
		} catch (Exception e)
		{
			Logger.out("UserLogin.changeUsername(): " + e.getMessage());
		}
		return false;
	}

	/**
	 * Löscht den angegebenen User
	 * 
	 * @param delName
	 *            --> String mit Username
	 * 
	 */
	public void delUser (String delName) {
		try {
			String delStudent = "DELETE FROM Students WHERE Username = " + delName;
			String delTeacher = "DELETE FROM Teachers WHERE Username = " + delName;
			stmt.executeUpdate(delStudent);
			stmt.executeUpdate(delTeacher);
			stmt.close();
		}
		catch (Exception e) {
			Logger.out("UserLogin.delUser(): " + e.getMessage());
		}
	}

	/**
	 * Überprüft die Login Daten
	 * 
	 * @param userData
	 *            --> Benötigt ein String Array mit Username, Password
	 * @return --> True = Korrekt, False = Inkorrekt
	 */
	public boolean loginUser (String[] userData) {
		boolean loggedIn = false;
		String password = null;
		try {
			ResultSet rs = stmt.executeQuery("SELECT Password FROM Students WHERE Username = '" + userData[0] + "'");
			while (rs.next()) {
				password = rs.getString("Password");
			}
			stmt.close();
		}
		catch (Exception e) {
			Logger.out("UserLogin.loginUser(): " + e.getMessage());
		}
		if (userData[1].equals(password)) {
			loggedIn = true;
		}
		return loggedIn;
	}	
}

