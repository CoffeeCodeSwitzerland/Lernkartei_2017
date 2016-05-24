package models;

import java.util.ArrayList;

import database.UserCards;
import mvc.Model;
import user.User;


public class UserModel extends Model
{
//	public UserModel (String myName)
//	{
//		super(myName);
//	}

	@Override
	public int doAction (String functionName, String paramS, double paramD)
	{
		// Wenn "Ich bin ein Lehrer" angekreuzt -> 1 als double übergeben sonst
		// 0
		boolean isTeacher;
		if (paramD == 1)
		{
			isTeacher = true;
		}
		else
		{
			isTeacher = false;
		}

		// Funktionen von "User"
		if (functionName.equals("register"))
		{
			// Um User zu registrieren
			// Als String bitte Username:::Email:::Password -> ::: = Separator
			// (Constants.SEPARATOR)
			try
			{
				boolean success = User.Register(paramS, isTeacher);
				return success ? 1 : -1;
			}
			catch (Exception e)
			{
				return -2;
			}
			// Funktion von User zum Einloggen
			// Als String bitte Username:::Password -> ::: = Separator
			// (Constants.SEPARATOR)
		}
		else if (functionName.equals("login"))
		{
			try
			{
				boolean success = User.Login(paramS);
				return success ? 1 : -1;
			}
			catch (Exception e)
			{
				return -2;
			}
			// Funktion zum Löschen von Benutzern -> Nur durchführbar wenn man
			// eingeloggt ist mit dem User, welchen man löschen will
			// Als String wird hier nur Username nötig
		}
		else if (functionName.equals("delete"))
		{
			try
			{
				boolean success = User.Delete(paramS);
				return success ? 1 : -1;
			}
			catch (Exception e)
			{
				return -2;
			}
		}
		else
		{
			return 0;
		}
	}

	@Override
	public ArrayList<String> getDataList (String query)
	{
		// Returnt alle Karteien des Benutzers in form von einer ArrayList
		return UserCards.getCards();
	}
}
