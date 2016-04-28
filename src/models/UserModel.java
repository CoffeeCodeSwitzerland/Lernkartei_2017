package models;

import java.util.ArrayList;

import database.UserCards;
import user.User;

public class UserModel extends Model
{

	public UserModel(String myName)
	{
		super(myName);
	}

	@Override
	public int doAction(String functionName, String paramS, double paramD)
	{
		//Wenn "Ich bin ein Lehrer" angekreuzt -> 1 als double übergeben sonst 0
		boolean ifTeacher;
		if (paramD == 1) {
			ifTeacher = true;
		} else 
		{
			ifTeacher = false;
		}
		
		//Funktion von "User" --> Delete kommt hier später noch dazu
		if (functionName.equals("register"))
		{
			boolean success = User.Register(paramS, ifTeacher);
			return success ? 1 : -1;
		} else if (functionName.equals("login"))
		{
			boolean success = User.Login(paramS);
			return success ? 1 : -1;
		} else
		{
			
		}
		return 0;
	}

	@Override
	public ArrayList<String> getData(String query)
	{
		//Returnt alle Karteien des Benutzers in form von einer ArrayList
		return UserCards.getCards();
	}

	
}
