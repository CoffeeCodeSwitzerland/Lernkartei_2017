package lernen;

import java.util.ArrayList;

public class Door
{
	private int doorId;
	private String Name;
	private ArrayList<Stack> Stacks = new ArrayList<Stack>();

	public String getName()
	{
		if (Name != null && Name != "")
		{
			return Name;
		} else
		{
			return "";
		}
	}
	
	public int getDoorId() {
		if (doorId >= 0) {
			return doorId;
		} else {
			return -1;
		}
	}
	
	// TODO : In Datenbank speichern
	public boolean setDoorId() {
		return false;
	}

	// TODO : Wenn DB funzt noch darin speichern
	public boolean setName(String newName)
	{
		if (newName != null)
		{
			Name = newName;
			return true;
		} else
		{
			return false;
		}
	}

	// TODO : Wenn DB funzt die Seiten davon holen
	// für jeden Stack wird ein neues Objekt der Klasse Stack instanziert die
	// Daten darin gespeichert und dann das Objekt in die Liste Stacks
	// gespeichert.
	public boolean pullStacksFromDb()
	{
		return false;
	}
}
