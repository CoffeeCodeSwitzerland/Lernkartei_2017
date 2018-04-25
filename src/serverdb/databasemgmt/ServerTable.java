package serverdb.databasemgmt;

import java.util.ArrayList;

import serverdb.sql.QueryExecuter;
import serverdb.sql.Table;

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
			System.out.print("--- create table =>"+t.createTable()+"\r\n");
		}
	
		QueryExecuter q = new QueryExecuter(executeStatements);
		System.out.print("---------Ausführer");
		q.executeQueriesWithoutResult();
	}
}
