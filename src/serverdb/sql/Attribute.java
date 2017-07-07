package serverdb.sql;

import java.util.ArrayList;
import serverdb.sql.*;

/**
 * 
 * @author Frithjof Hoppe
 *
 */

public class Attribute extends Query
{
	String table = "";
	String query = "";
	String db = "";
	
	Attribute(String table,String db)
	{
		 this.table = table;
		 this.db = db;
	}
	
	public String getAllAttributes()
	{
		String attributesQuery ="";
		String allAttributes = "";
		ArrayList<String> queryResult = new ArrayList<String>();
		
		attributesQuery = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '" + db +"' AND TABLE_NAME = '" + table + "'";
		
		System.out.println(attributesQuery + " attr query");
		
		QueryExecuter qe = new QueryExecuter(attributesQuery);
		
		queryResult = qe.executeAttributeQuery();
		
		allAttributes += "(";
		
		for(int i = 0; i < queryResult.size();i++)
		{
			if(i == 0)
			{
				allAttributes += queryResult.get(i);
			}
			else
			{
				allAttributes += ","+queryResult.get(i);
			}
		}
		
		allAttributes += ")";
		
		System.out.println(allAttributes + " full query");
		
	    return allAttributes;
	}
}
