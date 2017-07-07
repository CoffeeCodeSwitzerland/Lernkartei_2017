package serverdb.sql;

import java.util.ArrayList;
import database.jdbc.MYSQLDriver;
import com.mysql.*;
import java.sql.*;
public class QueryExecuter extends Query
{
	String user_DB = globals.Globals.mysqluser;
	String mysqldriver = globals.Globals.mysqldirver;
	String query ="";
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	QueryExecuter(String query)
	{
		this.query = query;
	}
	
	public ArrayList<String> executeAttributeQuery()
	{
		ArrayList<String> result = new ArrayList<String>();
		try {
			Class.forName(mysqldriver).newInstance();
			conn = DriverManager.getConnection(mysqlpath,mysqluser,mysqlpasswort);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				 int numColumns = rs.getMetaData().getColumnCount();
				 for(int i = 0; i <= numColumns;i++)
				 {
					 result.add((String) rs.getObject(i));
				 }
			}
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 return result;
	}
	
	public boolean executeQueryWithoutResult(String input)
	{
		boolean result = false;
		
		System.out.println(input +" definitiv query");
		
		try {
			Class.forName(mysqldriver).newInstance();
			conn = DriverManager.getConnection(mysqlpath,mysqluser,mysqlpasswort);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(input);
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*SUCCESS_NO_INFO says with the value -2 that the query has been executed successfully, though the number of row affected is unknown */
		if(stmt.SUCCESS_NO_INFO == -2)
		{
			result = true;
		}
					
		return result;
	}
}
