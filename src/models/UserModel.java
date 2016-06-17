package models;

import java.util.ArrayList;

import debug.Supervisor;
import mvc.Model;
import user.User;


public class UserModel extends Model
{
	@Override
	public int doAction (String functionName, String paramS, double paramD)
	{
		Supervisor.errorAndDebug(this, "Deprecated method (UserModel). Please use the new doAction");
		return -9;
	}

	@Override
	public int doAction (Command command, String... param)
	{
		switch (command)
		{
			case NEW:
				if (param.length != 2) { return -2; }
				try
				{
					boolean success = User.Register(param[0], param[1].equals("1") ? true : false); // saubere
																									 // Lösung
					return success ? 1 : -1;
				}
				catch (Exception e)
				{
					return -3;
				}

			case SET:
				if (param.length != 1) { return -2; }
				try
				{
					boolean success = User.Login(param[0]);
					return success ? 1 : -1;
				}
				catch (Exception e)
				{
					return -3;
				}

			case DELETE:
				if (param.length != 1) { return -2; }
				try
				{
					boolean success = User.Delete(param[0]);
					return success ? 1 : -1;
				}
				catch (Exception e)
				{
					return -3;
				}

			default:
				return super.doAction(command, param);
		}
	}

	@Override
	public ArrayList<String> getDataList (String query)
	{
		// Returnt alle Karteien des Benutzers in form von einer ArrayList
		
		return null; // TODO ... UserCards.getCards();
	}
}
