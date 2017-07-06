package serverdb.sql;
import serverdb.sql.*;
import globals.Globals.*;


import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;

import serverdb.sql.Insert;

public abstract class Query 
{	
	String queryType = "";
	static String user_DB = globals.Globals.user_DB;
	static String mysqlpath = globals.Globals.mysqlpath;
	static String mysqluser = globals.Globals.mysqluser;
	static String mysqlpasswort = globals.Globals.mysqlpaswort;
	
	Query()
	{
		
	}
	
	
	public static void registerUser(ArrayList<String> attr)
	{
		Value user = new Value(attr);
		
		Insert i = new Insert(user_DB,user.getAttributes());
	}
}
