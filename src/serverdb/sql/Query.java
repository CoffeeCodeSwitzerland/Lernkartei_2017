package serverdb.sql;
import serverdb.sql.*;

import java.util.ArrayList;
import java.util.List;
import serverdb.sql.Insert;

public abstract class Query 
{
	final static String userdb ="usersb";
	
	String queryType = "";
	
	Query()
	{
		
	}
	
	
	public static void registerUser(ArrayList<String> attr)
	{
		Value user = new Value(attr);
		
		Insert i = new Insert(userdb,user.getAttributes());
	}
}
