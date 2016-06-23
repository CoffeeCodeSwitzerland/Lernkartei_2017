package user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import database.LKDatabase;
import globals.Globals;

public class User
{

	private static String Username; // Username
	private static String Email; // Email
	private static String Password; // Password

	// Zum Registrieren
	private static Boolean isOk;
	
	private static String[] genData = new String[3];

	public static Boolean Register(String Userdata, Boolean istLehrer)
	{

		String[] Data = genArray(Userdata);
		if (validateUsername() && validatePassword() && validateEmail())
		{
			if (LKDatabase.myLogins.checkPossible(Data))
			{
				LKDatabase.myLogins.newUser(Data, istLehrer);
				isOk = true; // Wenn Success
			} else
			{
				debug.Debugger.out("Username oder E-Mail bereits vorhanden / verwendet");
				isOk = false; // Wenn bereits vorhanden
			}
		}

		return isOk;
	}
	
	/**
	 * Gibt Username als String
	 * 
	 * @return Username
	 */
	public static String getUsername() {
		return Username;
	}
	
	/**
	 * USernamen ändern
	 * 
	 * @param newName Neuer Name
	 * @param oldName "Alter" Name
	 * @return true -> erfolgreich geändert | false -> nicht geändert
	 */
	public static boolean setUsername(String newName, String oldName) {
		try
		{
			boolean possible = LKDatabase.myLogins.changeUsername(newName,oldName);
			return possible;
		} catch (Exception e)
		{
			return false;
		}
	}
	
	private static String[] genArray(String toGenerate)
	{
		genData = toGenerate.split(Globals.SEPARATOR);
		
		return genData;
	}

	// Zum Einloggen im Programm
	private static Boolean isCorrect;

	public static Boolean Login(String LoginData)
	{
		
		String[] Data = genArray(LoginData);
		isCorrect = LKDatabase.myLogins.loginUser(Data);
		if (isCorrect)
		{
			new Profil(); //Momentan noch eine Sackgasse --> Date 28.04.2016
			debug.Debugger.out("Es funzt ;)");
		} else
		{
			debug.Debugger.out("Sorry die Eingaben sind nicht korrekt");
		}

		return isCorrect;
	}

	// Zum Löschen von Benutzern --> Nur möglich wenn man eingeloggt ist
	private static Boolean isDeleted;

	public static Boolean Delete(String Username)
	{

		try
		{
			LKDatabase.myLogins.delUser(Username);
			isDeleted = true; // if Successfull deleted
		} catch (Exception e)
		{
			debug.Debugger.out(e.getMessage());
			isDeleted = false;
		}

		return isDeleted; 
	}

	// Validiert Username mit Regex
	private static boolean validateUsername()
	{
		// Validierung Username
		final String R_USERNAME = "^[A-Za-z0-9]{4,30}$";
		Pattern pUsername = Pattern.compile(R_USERNAME);
		Matcher mUsername = pUsername.matcher(Username);
		if (mUsername.matches())
		{
			debug.Debugger.out("Username: True");
			return true;
		} else
		{
			debug.Debugger.out("Username: false");
			return false;
		}
	}

	// validiert Password mit Regex
	private static boolean validatePassword()
	{
		// Validierung Password
		final String R_PASSWORD = "^[A-Za-z0-9!?+*,ç%&=]{8,50}$";
		Pattern pPassword = Pattern.compile(R_PASSWORD);
		Matcher mPassword = pPassword.matcher(Password);
		if (mPassword.matches())
		{
			debug.Debugger.out("Password: True");
			return true;
		} else
		{
			debug.Debugger.out("Password: false");
			return false;
		}
	}

	// validiert E-Mail mit Regex
	private static boolean validateEmail()
	{
		// Validierung Email
		final String R_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pEmail = Pattern.compile(R_EMAIL);
		Matcher mEmail = pEmail.matcher(Email);
		if (mEmail.matches())
		{
			debug.Debugger.out("Email: True");
			return true;
		} else
		{
			debug.Debugger.out("Email: false");
			return false;
		}
	}
}