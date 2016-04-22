package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import sqlite.Attributes;
import sqlite.Table;

public class ImpCSV {

	String Tbl_Name = "CSV";
	String attrs = "";
	String values = "";

	private Connection c = null;
	private Statement stmt = null;
	private ArrayList<String> exportList = new ArrayList<String>();
	public Table csvTable;
	private static final String csv_separator = ",";

	public ImpCSV(List<String[]> list) {

		csvTable = new Table(Tbl_Name);

		Attributes attr = new Attributes("Frontside", "TEXT", true);
		Attributes attr2 = new Attributes("Backside", "TEXT", true);
		csvTable.addAttrs(attr);
		csvTable.addAttrs(attr2);
		csvTable.generate();

		System.out.println("GENERATED");

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();

			// Attributes im Insert

			for (int i = 0; i < csvTable.Attrs.size(); i++) {

				if (i != csvTable.Attrs.size() - 1) {

					attrs += csvTable.Attrs.get(i).getName() + ",";

				} else {

					attrs += csvTable.Attrs.get(i).getName();

				}

			}

			// Values im Insert
			
			mainLoop:
			for (int i = 0; i < list.size(); i++) {

				values = "";

				for (int j = 0; j < list.get(i).length; j++) {

					if (list.get(i).length > csvTable.Attrs.size()) {
						
						continue mainLoop;

					} else {

						if (j < list.get(i).length - 1) {

							values += "'" + list.get(i)[j] + "',";

						} else {

							values += "'" + list.get(i)[j] + "'";

						}
					}
				}

				String sql = "INSERT INTO " + Tbl_Name + "(" + attrs + ") VALUES (" + values + ")";
				stmt.executeUpdate(sql);

			}

			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

	
	public ArrayList<String> ExportCSV (Table table) {		
		
		try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:test.db");
		      c.setAutoCommit(false);

		      stmt = c.createStatement();
		      ResultSet rs = stmt.executeQuery("SELECT * FROM " + table.getName());
		      while ( rs.next() ) {
		    	  
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
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }

		return exportList;
		
	}
	
}
