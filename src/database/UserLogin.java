package database;

import java.sql.*;

import debug.Logger;


public class UserLogin {

	// Variabeln Datenbank Verbindung

	private static String	username	= "prototyp";
	private static String	password	= "prototyp";

	private static String	mysqlURL	= "jdbc:mariadb://192.168.3.150:3306/userdb";
	private static String	mysqlDriver	= "com.mysql.jdbc.Driver";

	/**
	 * Fügt der Datenbank einen neuen User hinzu
	 * 
	 * @param values
	 *            --> String Array mit 3 Werten. Username, E-Mail, Passwort
	 * @param teacher
	 *            --> Boolean: true = Lehrer, false = Schüler
	 */
	public static Connection getConnection() {
		try {
			Class.forName(mysqlDriver);
			return DriverManager.getConnection(mysqlURL, username, password);
		} catch (Exception e) {
			Logger.log("UserLogin.getConnection(): "+e.getMessage());
		}
		return null;
	}


	public static void newUser (String[] values, boolean teacher) {

		Connection c = UserLogin.getConnection();

		try {
			Statement stmt = c.createStatement();

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

			debug.Debugger.out("Opened database successfully");

			String sql1 = "CREATE TABLE IF NOT EXISTS Teachers " +
					"(PK_Teachers INT PRIMARY KEY AUTO_INCREMENT," +
					" Username TEXT NOT NULL," +
					" Email TEXT NOT NULL," +
					" Password TEXT NOT NULL" + ")";

			debug.Debugger.out(sql1);
			stmt.executeUpdate(sql1);
			debug.Debugger.out("Lehrertabelle erstellt!");

			String sql2 = "CREATE TABLE IF NOT EXISTS Students " +
					"(PK_Students INT PRIMARY KEY AUTO_INCREMENT," +
					" Username TEXT NOT NULL," +
					" Email TEXT NOT NULL," +
					" Password TEXT NOT NULL" + ")";

			debug.Debugger.out(sql2);
			stmt.executeUpdate(sql2);
			debug.Debugger.out("Schülertabelle erstellt!");

			if (teacher) {

				String newTeach = "INSERT INTO Teachers (Username, Email, Password) VALUES (" + teachValues + ")";
				stmt.executeUpdate(newTeach);
				debug.Debugger.out(newTeach);
				debug.Debugger.out("Teacher sucessfully added!");

			}
			else if (!teacher) {

				String newStud = "INSERT INTO Students (Username, Email, Password) VALUES (" + studValues + ")";
				debug.Debugger.out(newStud);
				stmt.executeUpdate(newStud);
				debug.Debugger.out("Student sucessfully added!");

			}

			stmt.close();
			c.close();
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

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
		boolean noTeacher = true;
		boolean noStudent = true;

		// Überprüfen ob ein Lehrer mit dem Username oder Email vorhanden ist

		Connection c = UserLogin.getConnection();

		try {
			Statement stmt = c.createStatement();

			String sql1 = "CREATE TABLE IF NOT EXISTS Teachers " +
					"(PK_Lehrer INT PRIMARY KEY AUTO_INCREMENT," +
					" Username TEXT NOT NULL," +
					" Email TEXT NOT NULL," +
					" Password TEXT NOT NULL" + ")";

			debug.Debugger.out(sql1);
			stmt.executeUpdate(sql1);
			debug.Debugger.out("Lehrertabelle erstellt!");

			String sql2 = "CREATE TABLE IF NOT EXISTS Students " +
					"(PK_Lehrer INT PRIMARY KEY AUTO_INCREMENT," +
					" Username TEXT NOT NULL," +
					" Email TEXT NOT NULL," +
					" Password TEXT NOT NULL" + ")";

			debug.Debugger.out(sql2);
			stmt.executeUpdate(sql2);
			debug.Debugger.out("Schülertabelle erstellt!");

			c.setAutoCommit(false);
			ResultSet rsUsern = stmt
					.executeQuery("SELECT Username FROM Teachers WHERE Username = " + "'" + values[0] + "'");
			ResultSet rsEmail = stmt.executeQuery("SELECT Email FROM Teachers WHERE Email = " + "'" + values[1] + "'");

			if (rsUsern.next() || rsEmail.next()) {

				noTeacher = false;

			}

			rsUsern.close();
			rsEmail.close();
			stmt.close();
			c.close();
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		// Überprüfen ob ein Schüler mit dem Username oder Email vorhanden ist

		try {
			c.setAutoCommit(false);
			Statement stmt = c.createStatement();

			ResultSet rsUsern = stmt
					.executeQuery("SELECT Username FROM Students WHERE Username = " + "'" + values[0] + "'");
			ResultSet rsEmail = stmt.executeQuery("SELECT Email FROM Students WHERE Email = " + "'" + values[1] + "'");

			if (rsUsern.next() || rsEmail.next()) {

				noStudent = false;

			}

			rsUsern.close();
			rsEmail.close();
			stmt.close();
			c.close();
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
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
	
	public static boolean changeUsername(String newName, String oldName) {
		
		Connection c = UserLogin.getConnection();

		try {
			Statement stmt = c.createStatement();
			
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
			return false;
		}
	}

	/**
	 * Löscht den angegebenen User
	 * 
	 * @param delName
	 *            --> String mit Username
	 * 
	 */

	public static void delUser (String delName) {

		Connection c = UserLogin.getConnection();

		try {
			Statement stmt = c.createStatement();

			String delStudent = "DELETE FROM Students WHERE Username = " + delName;
			String delTeacher = "DELETE FROM Teachers WHERE Username = " + delName;

			stmt.executeUpdate(delStudent);
			stmt.executeUpdate(delTeacher);

			debug.Debugger.out("Successfully deleted User: " + delName);

			stmt.close();
			c.close();
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

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

		Connection c = UserLogin.getConnection();

		try {
			Statement stmt = c.createStatement();
			c.setAutoCommit(false);

			ResultSet rs = stmt.executeQuery("SELECT Password FROM Students WHERE Username = '" + userData[0] + "'");

			while (rs.next()) {

				password = rs.getString("Password");

			}

			stmt.close();
			c.close();
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		if (userData[1].equals(password)) {

			loggedIn = true;

		}

		return loggedIn;
	}	
	
}

