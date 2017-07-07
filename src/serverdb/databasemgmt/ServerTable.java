package serverdb.databasemgmt;

import serverdb.sql.Query;
import serverdb.sql.QueryExecuter;
import serverdb.sql.Table;
import java.util.ArrayList;

public class ServerTable
{
	String table = "";
	ArrayList<ArrayList<String[]>> attributes = new ArrayList<ArrayList<String[]>>();

	public ServerTable(ArrayList<ArrayList<String[]>> tables)
	{
		this.attributes = tables;
	}

	public void createTables()
	{
		ArrayList<String> executeStatements = new ArrayList<String>();
		
		for(ArrayList<String[]> as: attributes)
		{
			String[] temp = as.get(0);
			Table t = new Table(temp[0].toString(),as);
			executeStatements.add(t.createTable());
			System.out.print("--- create table =>"+t.createTable());
		}
	
		QueryExecuter q = new QueryExecuter(executeStatements);
		q.executeQueriesWithoutResult();
	}
}
