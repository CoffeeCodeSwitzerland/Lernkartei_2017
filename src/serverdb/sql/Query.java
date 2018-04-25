package serverdb.sql;
import java.util.ArrayList;

/**
 * <h3>Query-Class</h3>
 * -Is responsible for creating MYSQL-queries
 * @author Frithjof Hoppe
 * @version 1.0
 * 
 */

public  class Query 
{	
	String queryType = "";
	static String user_Table = globals.Globals.user_Table;
	static String mysqldb = globals.Globals.mysqldb;
	static String mysqlpath = globals.Globals.mysqlpath;
	static String mysqluser = globals.Globals.mysqluser;
	static String mysqlpasswort = globals.Globals.mysqlpassword;
	
	/**
	 * 
	 */
	Query()
	{
		
	}
	
	
	public static void registerUser(ArrayList<String> attr)
	{
		Value user = new Value(attr);
		Attribute attribute = new Attribute(user_Table,mysqldb);
		Insert i = new Insert(user_Table,user.getValues(),attribute.getAllAttributes());
		QueryExecuter qe = new QueryExecuter(i.createCommand());
		//boolean reult = 
				qe.executeQueryWithoutResult();
	}
}
