package database;

import java.sql.*;


public class UserDatabase {

	/**
	 * Fügt der Datenbank einen neuen User hinzu
	 * 
	 * @param values
	 *            --> String Array mit 3 Werten. Username, E-Mail, Passwort
	 * @param teacher
	 *            --> Boolean: true = Lehrer, false = Schüler
	 */

	public static void newUser (String[] values, boolean teacher) {

		Connection c = null;
		Statement stmt = null;

		String url = "jdbc:mariadb://192.168.3.150:3306/userdb";
		String username = "prototyp";
		String password = "prototyp";
		String driver = "com.mysql.jdbc.Driver";

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url, username, password);
			stmt = c.createStatement();

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

			System.out.println("Opened database successfully");

			String sql1 = "CREATE TABLE IF NOT EXISTS Teachers " +
					"(PK_Teachers INT PRIMARY KEY AUTO_INCREMENT," +
					" Username TEXT NOT NULL," +
					" Email TEXT NOT NULL," +
					" Password TEXT NOT NULL" + ")";

			System.out.println(sql1);
			stmt.executeUpdate(sql1);
			System.out.println("Lehrertabelle erstellt!");

			String sql2 = "CREATE TABLE IF NOT EXISTS Students " +
					"(PK_Students INT PRIMARY KEY AUTO_INCREMENT," +
					" Username TEXT NOT NULL," +
					" Email TEXT NOT NULL," +
					" Password TEXT NOT NULL" + ")";

			System.out.println(sql2);
			stmt.executeUpdate(sql2);
			System.out.println("Schülertabelle erstellt!");

			if (teacher) {

				String newTeach = "INSERT INTO Teachers (Username, Email, Password) VALUES (" + teachValues + ")";
				stmt.executeUpdate(newTeach);
				System.out.println(newTeach);
				System.out.println("Teacher sucessfully added!");

			}
			else if (!teacher) {

				String newStud = "INSERT INTO Students (Username, Email, Password) VALUES (" + studValues + ")";
				System.out.println(newStud);
				stmt.executeUpdate(newStud);
				System.out.println("Student sucessfully added!");

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

		Connection c = null;
		Statement stmt = null;

		String url = "jdbc:mariadb://192.168.3.150:3306/userdb";
		String username = "prototyp";
		String password = "prototyp";
		String driver = "com.mysql.jdbc.Driver";

		try {
			Class.forName(driver);
			c = DriverManager.getConnection(url, username, password);
			stmt = c.createStatement();
			
			String sql1 = "CREATE TABLE IF NOT EXISTS Teachers " +
					"(PK_Lehrer INT PRIMARY KEY AUTO_INCREMENT," +
					" Username TEXT NOT NULL," +
					" Email TEXT NOT NULL," +
					" Password TEXT NOT NULL" + ")";

			System.out.println(sql1);
			stmt.executeUpdate(sql1);
			System.out.println("Lehrertabelle erstellt!");

			String sql2 = "CREATE TABLE IF NOT EXISTS Students " +
					"(PK_Lehrer INT PRIMARY KEY AUTO_INCREMENT," +
					" Username TEXT NOT NULL," +
					" Email TEXT NOT NULL," +
					" Password TEXT NOT NULL" + ")";

			System.out.println(sql2);
			stmt.executeUpdate(sql2);
			System.out.println("Schülertabelle erstellt!");

			c.setAutoCommit(false);
			ResultSet rsUsern = stmt.executeQuery("SELECT Username FROM Teachers WHERE Username = " + "'" + values[0] + "'");
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
			Class.forName(driver);
			c = DriverManager.getConnection(url, username, password);
			c.setAutoCommit(false);
			stmt = c.createStatement();

			ResultSet rsUsern = stmt.executeQuery("SELECT Username FROM Students WHERE Username = " + "'" + values[0] + "'");
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
	 * Löscht einen User wenn Username / Passwort korrekt
	 * 
	 * @param values
	 *            --> String Array mit Username , Passwort
	 * @return --> True wenn gelöscht, false wenn nicht vorhanden oder Passwort
	 *         nicht korrekt
	 */

	public static boolean delUser (String[] values) {

		boolean deleted = false;
		
		// To be continued
		
		return deleted;

	}

}
