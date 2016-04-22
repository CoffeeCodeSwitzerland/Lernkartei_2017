package SQLite;

import java.sql.*;
import java.util.ArrayList;


public class Backup
{
	/**
	 * Backup von allen Daten welche aus pullFromStock() geholt werden können,
	 * hier mitgeben
	 * 
	 * @param values
	 *            --> ArrayList<String[]> von pullFromStock()
	 */

	public static void BackUp (ArrayList<String[]> values)
	{

		Connection c = null;
		Statement stmt = null;

		String url = "jdbc:sqlite://192.168.3.150:3306/test";
		String username = "prototyp";
		String password = "prototyp";
		String driver = "org.sqlite.JDBC";

		try
		{
			Class.forName(driver);
			c = DriverManager.getConnection(url, username, password);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "CREATE IF NOT EXISTS TABLE Stock " +
					"(PK_Stk INT PRIMARY KEY AUTOINCREMENT," +
					" Backside       TEXT    NOT NULL, " +
					" Frontside      TEXT    NOT NULL, " +
					" Description        TEXT, " + 
					" Set_ID	INTEGER" + ")";

			System.out.println(sql);
			stmt.executeUpdate(sql);
			System.out.println("Successful");

			for (int i = 0; i < values.size(); i++)
			{

				String insert = "INSERT INTO Stock (Backside, Frontside, Description, Set_ID)" +
						"VALUES ('" + values.get(i)[0] + "','" + values.get(i)[1] + "','" + values.get(i)[2] + "','" + values.get(i)[3] + "')";

				stmt.executeUpdate(insert);

			}
			
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

}
