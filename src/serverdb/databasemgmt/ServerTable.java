package serverdb.databasemgmt;

import serverdb.sql.Table;
import java.util.ArrayList;

public class ServerTable
{
	String table = "";
	ArrayList<ArrayList<String[]>> attributes = new ArrayList<ArrayList<String[]>>();
	
	
	ServerTable(String table, ArrayList<ArrayList<String[]>> attributes)
	{
		this.table = table;
		this.attributes = attributes;
	}
	
	public void createTables()
	{
		ArrayList<String> executeStatements = new ArrayList<String>();
		
		for(ArrayList<String[]> sa:attributes)
		{
			Table t = new Table(table,sa);
			executeStatements.add(t.createTable());
		}
	}
}
