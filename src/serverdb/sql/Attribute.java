package serverdb.sql;

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
		
		attributesQuery = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '" + db +"' AND TABLE_NAME = '" + table + "'";
		
	    return allAttributes;
	}
}
