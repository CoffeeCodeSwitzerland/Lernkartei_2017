package serverdb.sql;

import java.util.List;

public class Insert extends Query
{
	String database = "";
	String query = "";
	
	Insert(String db, String query)
	{
		database = db;
		this.query  = query;
	}
	
	public String createCommand()
	{
		String fullInsertQuery = "";
		
		fullInsertQuery = "INSERT INTO "+database+"VALUES(";
		
		return fullInsertQuery;
		
	}
}
