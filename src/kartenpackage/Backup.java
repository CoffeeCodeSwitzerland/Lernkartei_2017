package kartenpackage;

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

		String url = "jdbc:mariadb://192.168.3.150:3306/backup";
		String username = "prototyp";
		String password = "prototyp";
		String driver = "com.mysql.jdbc.Driver";

		try
		{
			Class.forName(driver);
			c = DriverManager.getConnection(url, username, password);
			stmt = c.createStatement();
			
			String usedb = "USE " + "backup";
			stmt.executeQuery(usedb);
			
			String delTbl = "DROP TABLE Stock";
			stmt.executeUpdate(delTbl);
			
			System.out.println("Opened database successfully");
			
			String sql = "CREATE TABLE IF NOT EXISTS Stock " +
					"(PK_Stk INT PRIMARY KEY AUTO_INCREMENT," +
					" Backside TEXT NOT NULL," +
					" Frontside TEXT NOT NULL," +
					" Description TEXT," + 
					" Set_ID INTEGER" + ")";

			System.out.println(sql);
			stmt.executeUpdate(sql);
			System.out.println("Successful");

			for (int i = 0; i < values.size(); i++)
			{

				String insert = "INSERT INTO Stock (Backside, Frontside, Description)" +
						"VALUES ('" + values.get(i)[1] + "','" + values.get(i)[2] + "','" + values.get(i)[3] + "')";

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
