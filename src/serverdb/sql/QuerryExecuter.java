package serverdb.sql;

import java.util.ArrayList;

import database.jdbc.MYSQLDriver;

public class QuerryExecuter 
{
	String query = "";
	
	QuerryExecuter(String query)
	{
		this.query = query;
	}
	
	public ArrayList<String> executeAttributeCommand()
	{
		return null;
			
	}
	
	MYSQLDriver driver = new MYSQLDriver();
	
}
