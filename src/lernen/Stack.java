package lernen;

import java.util.ArrayList;

public class Stack
{
	private String Name;
	private String Description;
	private ArrayList<Karte> Cards = new ArrayList<Karte>();
	
	public String getName() {
		if (Name != null && Name != "") {
			return Name;
		} else {
			return "";
		}
	}
	
	public String getDescription() {
		if (Description != null && Description != "") {
			return Description;
		} else {
			return "";
		}
	}
	
	// TODO : Wenn DB funzt noch darin speichern
	public boolean setName(String newName) {
		if (newName != null && newName != "") {
			Name = newName;
			return true;
		} else {
			return false;
		}
	}
	
	// TODO : Wenn DB funzt noch darin speichern
	public boolean setDescription(String newDescription) {
		if (newDescription != null && newDescription != "" ) {
			Description = newDescription;
			return true;
		} else {
			return false;
		}
	}
	
	// TODO : Wenn DB funzt die Seiten davon holen
	// für jede Karte wird ein neues Objekt der Klasse Karte instanziert die
	// Daten darin gespeichert und dann das Objekt in die Liste Cards
	// gespeichert.
	public boolean pullCardsFromDb()
	{
		return false;
	}
}
