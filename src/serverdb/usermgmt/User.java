package serverdb.usermgmt;

import java.util.ArrayList;
import java.util.List;

import globals.Globals;
import globals.Globals.loginState;

public class User 
{
	public boolean validateLoginParams(String password, String... username)
	{
		return false;
	}
	
	public ArrayList<String>getGroups(String art)
	{
		ArrayList<String> groups = new ArrayList<String>();
		
		return groups;
	}
	
	public loginState getLoginState()
	{
		return Globals.loginStatus;
	}
	
	public ArrayList<String>getOwnedGroups(String... username)
	{
		ArrayList<String> groups = new ArrayList<String>();
		
		return groups;
	}
	
	public String getPassword(String... username)
	{
		String password = "";
		
		return password;
	}
	
	public void setLoginState(loginState state)
	{
		Globals.loginStatus = state;
	}
	
	public void setPassword(String newPassword,String... username)
	{
		
	}
	
	public void registerUser(String firstName,String lastName,String passwort,String... username)
	{
		
	}
	
	public boolean isUsernameTaken(String... username)
	{
		return false;	
	}
	
	public Integer getIdByUsername(String usernanme)
	{
		return null;
	}
	
	public String getUsernameById (String usernanme)
	{
		return null;
	}
}
