package serverdb.sql;

public class Database extends Query 
{
	String dbname = "";
	
	public Database(String name)
	{
		this.dbname = name;
	}
	
	public String create()
	{
		String query = "";
		
		query = "CREATE DATABASE IF NOT EXISTS " + dbname;
		
		return query;
	}
}
