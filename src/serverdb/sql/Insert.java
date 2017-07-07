package serverdb.sql;

import java.util.List;

public class Insert extends Query
{
	String table = "";
	String values = "";
	String attributes = "";
	
	Insert(String table, String values,String attributes)
	{
		this.table = table;
		this.values  = values;
		this.attributes = attributes;
	}
	
	public String createCommand()
	{
		String fullInsertQuery = "";
		
		fullInsertQuery = "INSERT INTO "+table+" "+attributes+" VALUES "+values;
		
		return fullInsertQuery;
	}
}
