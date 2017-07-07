package serverdb.sql;

import java.util.ArrayList;

public class Table
{
	String tableName = "";
	ArrayList<String[]> attribute = new ArrayList<String[]>();
	
	public Table(String tableName,ArrayList<String[]> attribute)
	{
		this.tableName = tableName;
		this.attribute = attribute;
	}
	
	public String createTable()
	{
		String result = "";
		
		result += "CREATE TABLE " + tableName +"(";
		
		boolean isFirstTime = true;
		boolean isFirstValue = true;
		
		for(String[] sa: attribute)
		{
			if(!isFirstTime)
			{
				if(!isFirstValue)
				{
					result += "," + sa[0];
				}
				else
				{
					result += sa[0];
					isFirstValue = false;
				}
			}
			else
			{
				isFirstTime = false;
			}
		}
		
		isFirstTime = true;
		isFirstValue = true;
		
		result += ")";
		
		return result;
	}
}
