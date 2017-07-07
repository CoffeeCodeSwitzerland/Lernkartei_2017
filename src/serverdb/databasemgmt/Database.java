package serverdb.databasemgmt;

public class Database 
{
	String dbname = "";
	String dbpath = "";
	String dbuser = "";
	String dbpassword = "";
	
	Database()
	{
		
	}
	

	Database(String dbname, String dbpath,String dbuser, String dbpasswort)
	{
		this.dbname = dbname;
		this.dbpath = dbpath;
		this.dbuser = dbuser;
		this.dbpassword = dbpath;
	}
	
	
}
