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
		
		for(String[] sa: attribute)
		{
			for(int i = 0; i < sa.length;i++)
			{
				if(i != 0)
				{
					result += "," + sa[i];
				}
				else
				{
					result += sa[i];
				}
			}
		}
		
		result += ")";
		
		return result;
	}
}
