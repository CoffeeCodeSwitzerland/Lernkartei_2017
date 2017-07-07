package serverdb.sql;
import serverdb.sql.*;
import globals.Globals.*;


import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;

import serverdb.sql.Insert;

public  class Query 
{	
	String queryType = "";
	static String user_Table = globals.Globals.user_Table;
	static String mysqldb = globals.Globals.mysqldb;
	static String mysqlpath = globals.Globals.mysqlpath;
	static String mysqluser = globals.Globals.mysqluser;
	static String mysqlpasswort = globals.Globals.mysqlpaswort;
	
	Query()
	{
		
	}
	
	
	public static void registerUser(ArrayList<String> attr)
	{
		Value user = new Value(attr);
		Attribute attribute = new Attribute(user_Table,mysqldb);
		Insert i = new Insert(mysqldb,user.getValues(),attribute.getAllAttributes());
		QueryExecuter qe = new QueryExecuter(i.createCommand());
	}
}
