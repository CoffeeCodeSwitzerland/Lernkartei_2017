package serverdb.sql;

import java.util.ArrayList;
import database.jdbc.MYSQLDriver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * 
 * @author Frithjof Hoppe
 *
 */

public class QueryExecuter extends Query
{
	String user_DB = globals.Globals.mysqluser;
	String mysqldriver = globals.Globals.mysqldirver;
	String query = "";
	ArrayList<String> queries = new ArrayList<String>();
	Connection conn;
	Statement stmt;
	ResultSet rs;

	public QueryExecuter(String query)
	{
		this.query = query;
	}

	public QueryExecuter(ArrayList<String> queries)
	{
		this.queries = queries;
	}

	public ArrayList<String> executeAttributeQuery()
	{
		ArrayList<String> result = new ArrayList<String>();
		try
		{

			Class.forName(mysqldriver).newInstance();
			conn = DriverManager.getConnection(mysqlpath, mysqluser, mysqlpasswort);
			stmt = conn.createStatement();

			// System.out.println(rs.get+ " ----");

			if (stmt.execute(query))
			{

				rs = stmt.getResultSet();
				ResultSetMetaData dbmeta = rs.getMetaData();

				while (rs.next())
				{
					if (!rs.isFirst())
					{
						result.add(rs.getString(dbmeta.getColumnName(1)));
						System.out.println("*** " + rs.getString(dbmeta.getColumnName(1)));
					}
				}
			}

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e)
		{
			System.out.println("***Exception 1");
			e.printStackTrace();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			System.out.println("***Exception 2");
			e.printStackTrace();
		}
		return result;
	}

	public boolean executeQueryWithoutResult()
	{
		boolean result = false;

		try
		{
			Class.forName(mysqldriver).newInstance();
			conn = DriverManager.getConnection(mysqlpath, mysqluser, mysqlpasswort);
			stmt = conn.createStatement();
			boolean rs = stmt.execute(query);
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * SUCCESS_NO_INFO says with the value -2 that the query has been
		 * executed successfully, though the number of row affected is unknown
		 */
		if (stmt.SUCCESS_NO_INFO == -2)
		{
			result = true;
		}

		return result;
	}

	public String executeQueryWithSingleResult()
	{
		String result = "";

		try
		{
			Class.forName(mysqldriver).newInstance();
			conn = DriverManager.getConnection(mysqlpath, mysqluser, mysqlpasswort);
			stmt = conn.createStatement();
			boolean rs = stmt.execute(query);
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public boolean executeQueriesWithoutResult()
	{
		boolean result = false;

		for (
			String s : queries
		)
		{
			try
			{
				Class.forName(mysqldriver).newInstance();
				conn = DriverManager.getConnection(mysqlpath, mysqluser, mysqlpasswort);
				stmt = conn.createStatement();
				boolean rs = stmt.execute(s);
			} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	public ArrayList<String[]> executeQueryWithMultipleResult()
	{
		ArrayList<String[]> result = new ArrayList<String[]>();

		return result;
	}
}
