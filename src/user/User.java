package user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import database.UserLogin;

public class User
{

	private String Username; // Username
	private String Email; // Email
	private String Password; // Password

	// Zum Registrieren
	private Boolean isOk;

	public Boolean Register(String[] Userdata, Boolean istLehrer)
	{

		if (validateUsername() && validatePassword() && validateEmail())
		{
			if (UserLogin.checkPossible(Userdata))
			{
				UserLogin.newUser(Userdata, istLehrer);
				isOk = true; // Wenn Success
			} else
			{
				System.out.println("Username oder E-Mail bereits vorhanden / verwendet");
				isOk = false; // Wenn bereits vorhanden
			}
		}

		return isOk;
	}

	// Zum Einloggen im Programm
	private Boolean isCorrect;

	public Boolean Login(String[] LoginData)
	{

		isCorrect = UserLogin.loginUser(LoginData);
		if (isCorrect)
		{
			new Profil();
			System.out.println("Es funzt ;)");
		} else
		{
			System.out.println("Sorry die Eingaben sind nicht korrekt");
		}

		return isCorrect;
	}

	// Zum Löschen von Benutzern --> Nur möglich wenn man eingeloggt ist
	private Boolean isDeleted;

	public Boolean Delete(String Username)
	{

		try
		{
			UserLogin.delUser(Username);
			isDeleted = true; // if Successfull deleted
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
			isDeleted = false;
		}

		return isDeleted; 
	}

	// Validiert Username mit Regex
	private boolean validateUsername()
	{
		// Validierung Username
		final String R_USERNAME = "^[A-Za-z0-9]{4,30}$";
		Pattern pUsername = Pattern.compile(R_USERNAME);
		Matcher mUsername = pUsername.matcher(Username);
		if (mUsername.matches())
		{
			System.out.println("Username: True");
			return true;
		} else
		{
			System.out.println("Username: false");
			return false;
		}
	}

	// validiert Password mit Regex
	private boolean validatePassword()
	{
		// Validierung Password
		final String R_PASSWORD = "^[A-Za-z0-9!?+*,ç%&=]{8,50}$";
		Pattern pPassword = Pattern.compile(R_PASSWORD);
		Matcher mPassword = pPassword.matcher(Password);
		if (mPassword.matches())
		{
			System.out.println("Password: True");
			return true;
		} else
		{
			System.out.println("Password: false");
			return false;
		}
	}

	// validiert E-Mail mit Regex
	private boolean validateEmail()
	{
		// Validierung Email
		final String R_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pEmail = Pattern.compile(R_EMAIL);
		Matcher mEmail = pEmail.matcher(Email);
		if (mEmail.matches())
		{
			System.out.println("Email: True");
			return true;
		} else
		{
			System.out.println("Email: false");
			return false;
		}
	}
}
