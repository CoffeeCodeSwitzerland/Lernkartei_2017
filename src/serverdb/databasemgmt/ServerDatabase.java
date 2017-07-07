package serverdb.databasemgmt;

import serverdb.sql.*;

/**
 * 
 * @author Frithjof Hoppe
 *
 */
public class ServerDatabase 
{
	String dbname = "";
	String dbpath = "";
	String dbuser = "";
	String dbpassword = "";
	
	ServerDatabase()
	{
		
	}
	

	ServerDatabase(String dbname, String dbpath,String dbuser, String dbpasswort)
	{
		this.dbname = dbname;
		this.dbpath = dbpath;
		this.dbuser = dbuser;
		this.dbpassword = dbpath;
	}
	
	public boolean createDB()
	{
		boolean result = false;
		
		globals.Globals.mysqlpassword = dbpassword;
		globals.Globals.mysqluser = dbuser;
		globals.Globals.mysqlpath = dbpath;
		globals.Globals.mysqldb = dbname;
		
		Database database = new Database(dbname);
		QueryExecuter qe = new QueryExecuter(database.create());
		boolean success = qe.executeQueryWithoutResult();
		
		return result;
	}
}
