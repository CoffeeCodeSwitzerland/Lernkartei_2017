package multiuser;

import javafx.collections.*;

public class User
{
	/*
	 * @author Joel Häberli
	 */
	
	//Alle Angaben die ein User hat
	private String Name;
	private String Passwort;
	private String Hash;
	private byte[] Salz;
	private String Email;
	private Integer Usertype;
	//Das system mit klassen wird ev. nicht implementiert aus Zeitgründen
	private ObservableList<String> myGroups = FXCollections.observableArrayList();
	
	//TODO : Wenn datenbank funktioniert darin speichern
	public void setSalt(byte[] salt) {
		Salz = salt;
	}
	
	// TODO : Login Name in lokale DB speichern, damit man sich nicht immer einloggen muss.
	public void pushToLocalDb() {
		
	}
	
	//TODO : Wenn Datenbank funktioniiert Salt daraus auslesen
	@SuppressWarnings("unused")
	private byte[] getSalt() {
		return Salz = null;	
	}	
	
	//Validierung der drei Eingaben mit entsprechendem Regex
	private Boolean checkName(String name) {
		String regexName = "[A-Za-z\\d](4,100)";
		if (name.matches(regexName)) {
			return true;
		} else {
			return false;
		}
	}
	private Boolean checkEmail(String mail) {
		String regexMail = "[A-Za-z\\d]{@}[A-Za-z\\d]{\\.}[A-Za-z0-9]";
		if (mail.matches(regexMail)) {
			return true;
		} else {
			return false;
		}
	}
	private Boolean checkPasswortMachbarkeit(String passwort) {
		String regexPw = "[A-Za-z\\d\\!\\?\\$\\*\\%\\&\\£\\@\\'](8,100)";
		if (passwort.matches(regexPw)) {
			return true;
		} else {
			return false;
		}
	}
	
	//Getter und Setter der Angaben
	public String getName()
	{
		return Name;
	}
	//Wenn true -> gespeichert / Wenn false -> nicht gespeicher weil Fehler (User eine Meldung geben)
	//TODO : Wenn datenbank funktioniert darin speichern
	public Boolean setName(String name)
	{
		if (checkName(name)) {
			Name = name;
			return true;
		} else {
			return false;
		}
	}
	public String getPasswort()
	{
		return Passwort;
	}
	//Wenn true -> gespeichert / Wenn false -> nicht gespeicher weil Fehler (User eine Meldung geben)
	//TODO : Wenn datenbank funktioniert darin speichern
	public Boolean setPasswort(String passwort)
	{
		//Wenn Passwort zulässig und das Salz gefüllt
		if (checkPasswortMachbarkeit(passwort) && !(Salz == null)) {
			char[] pw = passwort.toCharArray();
			try
			{
				if (Security.createHash(pw, Salz) == null) {
					return false;
				} else {
					Passwort = passwort;
					return true;
				}
			} catch (CannotPerformOperationException e)
			{
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}
	public String getHash()
	{
		return Hash;
	}

	public void setHash(String hash)
	{
		Hash = hash;
	}

	public String getEmail()
	{
		return Email;
	}
	//Wenn true -> gespeichert / Wenn false -> nicht gespeicher weil Fehler (User eine Meldung geben)
	//TODO : Wenn datenbank funktioniert darin speichern
	public Boolean setEmail(String email)
	{
		if (checkEmail(email)) {
			Email = email;
			return true;
		} else {
			return false;
		}
	}
	public Integer getUsertype()
	{
		return Usertype;
	}
	public void setUsertype(Integer usertype)
	{
		Usertype = usertype;
	}
	public ObservableList<String> getMyGroups()
	{
		return myGroups;
	}
	public void setMyGroups(ObservableList<String> myGroups)
	{
	}
}
