package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class KtgDatabase
{
	/**
	 * Methode, zum Einfügen einer neuen Kategorie 
	 * 
	 * @param eingabe --> String Kategorie
	 *
	 */

	public static void Eingabe (String eingabe)
	{

		Connection c = null;
		Statement stmt = null;

		String url = "jdbc:sqlite:test.db";
		String driver = "org.sqlite.JDBC";

		try
		{
			Class.forName(driver);
			c = DriverManager.getConnection(url);
			stmt = c.createStatement();
			
			System.out.println("Opened database successfully");
			
			String sql = "CREATE TABLE IF NOT EXISTS Kategorie " +
					"(PK_Kategorie INTEGER PRIMARY KEY AUTOINCREMENT," +
					" Kategorie TEXT NOT NULL)";

			System.out.println(sql);
			stmt.executeUpdate(sql);
			System.out.println("Successful");

			String insert = "INSERT INTO Kategorie (Kategorie)" +
							"VALUES ('" + eingabe + "')";

			stmt.executeUpdate(insert);
			
			System.out.println("Successful");

			stmt.close();
			c.close();
		}
		catch (Exception e)
		{
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}
public static ArrayList<String[]> Ausgabe() {
		
		Connection c = null;
		Statement stmt = null;
		
		ArrayList<String[]> datensatz = new ArrayList<String[]>();
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Kategorie;");

			while (rs.next()) {
				
				String[] data = new String[2];
				
				String ID = Integer.toString(rs.getInt("PK_Kategorie"));
				String Kategorie = rs.getString("Kategorie");
				
				data[0] = ID;
				data[1] = Kategorie;
				
				datensatz.add(data);
			}

			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		
		return datensatz;
		
	}
	
	
	

}
