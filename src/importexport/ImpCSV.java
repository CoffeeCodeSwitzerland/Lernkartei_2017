package importexport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import sqlite.Attributes;
import sqlite.Table;


public class ImpCSV {

	// Variabeldeklaration

	// Name der Datenbank
	String						DB_Name			= "test";

	// Name der Tabelle
	String						Tbl_Name		= "CSV_Import";

	// Name des Treibers
	String						driver			= "org.sqlite.JDBC";

	String						attrs			= "";
	String						values			= "";

	private Connection			c				= null;
	private Statement			stmt			= null;
	private ArrayList<String>	exportList		= new ArrayList<String>();
	public Table				csvTable;

	// Falls die CSV Files anders als mit einem Komma getrennt sind, hier der
	// Separator

	private static final String	csv_separator	= ",";

	// Methode, welche CSV files importiert und in die Tabelle mit folgenden
	// Attributen schreibt

	// 1. ID, 2. Vorderseite, 3. Rückseite, 4. Description, 5. Learndate -->
	// null , 6. KategorieID --> null

	/**
	 * Benötigt eine Liste mit Arrays, welche aus dem CSV File erstellt wird
	 * 
	 * @param list
	 *            --> java.util.List, welche mit String[] gefüllt ist
	 */

	public ImpCSV (List<String[]> list) {

		csvTable = new Table(Tbl_Name);

		Attributes attr = new Attributes("Frontside", "TEXT", true);
		Attributes attr2 = new Attributes("Backside", "TEXT", true);
		Attributes attr3 = new Attributes("Description", "TEXT", false);
		Attributes attr4 = new Attributes("LearnDate", "DATE", false);
		Attributes attr5 = new Attributes("CategorieID", "INTEGER", false);

		csvTable.addAttrs(attr);
		csvTable.addAttrs(attr2);
		csvTable.addAttrs(attr3);
		csvTable.addAttrs(attr4);
		csvTable.addAttrs(attr5);
		csvTable.generate();

		System.out.println("GENERATED");

		try {
			Class.forName(driver);
			c = DriverManager.getConnection("jdbc:sqlite:" + DB_Name + ".db");
			c.setAutoCommit(false);
			stmt = c.createStatement();

			// Aus hinzugefügten Attributen wird der Teil mit den Attributen im
			// SQLite Statement generiert

			for (int i = 0; i < csvTable.Attrs.size(); i++) {

				if (i != csvTable.Attrs.size() - 1) {

					attrs += csvTable.Attrs.get(i).getName() + ",";

				}
				else {

					attrs += csvTable.Attrs.get(i).getName();

				}

			}

			// Aus hinzugefügten Values wird der Teil mit den Werten im SQLite
			// Statement generiert

				for (String[] s : list) {
					
					values = "";
					
					if (s.length < csvTable.Attrs.size()) {
						
						for (int j = 0; j < csvTable.Attrs.size(); j++) {
							
							if (j == 0) {
								
								values += "'" + s[j] + "'" + ",";
								
							} else if (j == 1) {
								
								values += "'" + s[j] + "'" + ",";
								
							} else if (j < csvTable.Attrs.size() - 1) {
								
								values += null + ",";
								
							} else {
								
								values += null;
								
							}
							
						}
						
					}
					
					String sql = "INSERT INTO " + Tbl_Name + "(" + attrs + ") VALUES (" + values + ")";
					stmt.executeUpdate(sql);
					
				}

			stmt.close();
			c.commit();
			c.close();
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

	/**
	 * 
	 * @param table
	 *            Benötigt die jeweilige Tabelle, aus welcher Exportiert werden
	 *            soll, zb: Stock
	 * @return Returnt eine ArrayList. Jeder Wert ist ein String welcher mit
	 *         einem Separator getrennt wird
	 * 
	 */

	public ArrayList<String> ExportCSV (Table table) {

		try {
			Class.forName(driver);
			c = DriverManager.getConnection("jdbc:sqlite:" + DB_Name + ".db");
			c.setAutoCommit(false);

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + table.getName());
			while (rs.next()) {

				String entry = "";

				for (int i = 0; i < table.Attrs.size(); i++) {

					entry += rs.getString(table.Attrs.get(i).getName());

					if (i != table.Attrs.size() - 1) {

						entry += csv_separator;

					}

				}

				exportList.add(entry);

			}
			rs.close();
			stmt.close();
			c.close();
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return exportList;

	}

}
