package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import sqlite.Attributes;
import sqlite.Table;

public class UserDatabase {

	// Variabeldeklaration

	private boolean teachPossible = true;
	private boolean pwdCorr = true;
	private boolean studPossible = true;
	private Table teacher;
	private Table student;
	private String selTable;
	private String selUsern;
	private Connection c = null;
	private Statement stmt = null;
	private String separator = ":::";
	private String passwd = "";
	private String attributes = "";
	private String insValues = "";
	private final ArrayList<String> values = new ArrayList<String>();

	// Konstruktor, erstellt Tabellen in der Datenbank namens: test.db

	public UserDatabase() {

		// Erstellen der Tabellen, Attribute (Spaltenname, Datentyp, NotNull)

		Attributes usern = new Attributes("Username", "TEXT", true);
		Attributes email = new Attributes("E-Mail", "TEXT", true);
		Attributes passwd = new Attributes("Password", "TEXT", true);

		teacher = new Table("Lehrer");
		teacher.addAttrs(usern);
		teacher.addAttrs(email);
		teacher.addAttrs(passwd);

		student = new Table("Lernender");
		student.addAttrs(usern);
		student.addAttrs(email);
		student.addAttrs(passwd);

	}

	public boolean checkPossible(String values) {

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			c.setAutoCommit(false);

			stmt = c.createStatement();

			// Abfrage, ob ein Lehrer besteht

			ResultSet rsTeacher = stmt.executeQuery("SELECT * FROM " + teacher.getName());

			if (!rsTeacher.next()) {

				while (rsTeacher.next()) {

					String[] splited = values.split(separator);

					for (int i = 0; i < splited.length - 1; i++) {

						if (splited[i].toUpperCase()
								.equals(rsTeacher.getString(teacher.Attrs.get(i).getName()).toUpperCase())) {

							teachPossible = false;
							rsTeacher.close();

						}

					}

				}

			} else {

				teachPossible = true;

			}

			rsTeacher.close();

			// Abfrage, ob ein Schüler besteht

			ResultSet rsStudent = stmt.executeQuery("SELECT * FROM " + student.getName());

			if (!rsStudent.next()) {

				while (rsStudent.next()) {

					String[] splited = values.split(separator);

					for (int i = 0; i < splited.length - 1; i++) {

						if (splited[i].equals(rsStudent.getString(student.Attrs.get(i).getName()))) {

							studPossible = false;
							rsStudent.close();

						}

					}

				}

			} else {

				studPossible = true;

			}

			rsStudent.close();

			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		if (!teachPossible | !studPossible) {

			return false;

		} else {

			return true;

		}

	}

	public void insert(String valuee, boolean person) {

		if (person) {

			for (int i = 0; i < valuee.split(separator).length; i++) {

				values.add(valuee.split(separator)[i]);

			}

			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:test.db");
				c.setAutoCommit(false);
				stmt = c.createStatement();

				// Attributes

				for (int j = 0; j < teacher.Attrs.size(); j++) {

					if (j < teacher.Attrs.size() - 1) {

						attributes += teacher.Attrs.get(j).getName() + ",";

					} else {

						attributes += teacher.Attrs.get(j).getName();

					}

				}

				// Values

				for (int k = 0; k < values.size(); k++) {

					if (k < values.size() - 1) {

						if (teacher.Attrs.get(k).getDatatype() == "INTEGER") {

							insValues += values.get(k) + ",";

						} else {

							insValues += "'" + values.get(k) + "'" + ",";

						}

					} else {

						if (teacher.Attrs.get(k).getDatatype() == "INTEGER") {

							insValues += values.get(k);

						} else {

							insValues += "'" + values.get(k) + "'";

						}

					}

				}

				String sql = "INSERT INTO " + teacher.getName() + "(" + attributes + ")" + " VALUES (" + insValues
						+ ");";

				stmt.executeUpdate(sql);

				stmt.close();
				c.commit();
				c.close();
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}

		} else {
			
			for (int i = 0; i < valuee.split(separator).length; i++) {

				values.add(valuee.split(separator)[i]);

			}

			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:test.db");
				c.setAutoCommit(false);
				stmt = c.createStatement();

				// Attributes

				for (int j = 0; j < student.Attrs.size(); j++) {

					if (j < student.Attrs.size() - 1) {

						attributes += student.Attrs.get(j).getName() + ",";

					} else {

						attributes += student.Attrs.get(j).getName();

					}

				}

				// Values

				for (int k = 0; k < values.size(); k++) {

					if (k < values.size() - 1) {

						if (student.Attrs.get(k).getDatatype() == "INTEGER") {

							insValues += values.get(k) + ",";

						} else {

							insValues += "'" + values.get(k) + "'" + ",";

						}

					} else {

						if (student.Attrs.get(k).getDatatype() == "INTEGER") {

							insValues += values.get(k);

						} else {

							insValues += "'" + values.get(k) + "'";

						}

					}

				}

				String sql = "INSERT INTO " + student.getName() + "(" + attributes + ")" + " VALUES (" + insValues
						+ ");";

				stmt.executeUpdate(sql);

				stmt.close();
				c.commit();
				c.close();
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
			
		}

	}

	public boolean delUser(String values, boolean table) {
		
		if (table) {
			
			selTable = "Lehrer";
			
		} else {
			
			selTable = "Lernender";
			
		}
		
		if (!checkPossible(values)) {
			
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:test.db");
				c.setAutoCommit(false);
				stmt = c.createStatement();

				String sql1 = "SELECT Password FROM " + selTable + " WHERE UPPER(Username) = UPPER(" + values.split(separator)[1] + ")";

				ResultSet rsPwd = stmt.executeQuery(sql1);
				
				while (rsPwd.next()) {
					
					passwd = rsPwd.getString("Password");
					
				}
				
				rsPwd.close();
				stmt.close();
				c.commit();
				c.close();
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}

			if (values.split(separator)[2].equals(passwd)) {
				
				try {
					Class.forName("org.sqlite.JDBC");
					c = DriverManager.getConnection("jdbc:sqlite:test.db");
					c.setAutoCommit(false);
					stmt = c.createStatement();

					String sql = "DELETE FROM " + selTable + " WHERE Username = " + selUsern;

					stmt.executeUpdate(sql);

					stmt.close();
					c.commit();
					c.close();
				} catch (Exception e) {
					System.err.println(e.getClass().getName() + ": " + e.getMessage());
					System.exit(0);
				}				
				
			} else {
				
				pwdCorr = false; 
				
			}

			return pwdCorr;
			
		} else {
			
			return false;
			
		}

	}

}
