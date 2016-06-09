package multiuser;

import javax.crypto.Cipher;

import javafx.collections.*;

public class User
{
	/*
	 * @author Joel Häberli
	 */
	
	//Alle Angaben die ein User hat
	private String Name;
	private String Passwort;
	private String Email;
	private Integer Usertype;
	private ObservableList<String> myGroups = FXCollections.observableArrayList();
	
	//TODO : Passwort Verschlüsselungs- und Entschlüsselungsfunktion
	private String cryptPasswort(String passwort) {
		try {
			Cipher c = Cipher.getInstance("AES");
			String crypted = "";
			
			//TODO : https://www.youtube.com/watch?v=8AID7DKhSoM -> Verschlüsselung
			
			return crypted;
		} catch(Exception e) {
			return "";
		}
	}
	
	private String encryptPasswort(String cryptedPassword) {
		try {
			Cipher c = Cipher.getInstance("AES/CBC/crYphtPAtt");
			String crypted = "";
			
			return crypted;
		} catch(Exception e) {
			return "";
		}
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
	private Boolean checkPasswort(String passwort) {
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
		if (checkPasswort(passwort)) {
			if (cryptPasswort(passwort) == "") {
				return false;
			} else {
				Passwort = passwort;
				return true;
			}
		} else {
			return false;
		}
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
		myGroups = myGroups;
	}
}
