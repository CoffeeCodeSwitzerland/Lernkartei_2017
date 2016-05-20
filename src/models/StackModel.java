package models;

import java.util.ArrayList;

import mvc.Model;


public class StackModel extends Model
{
	public StackModel (String myName)
	{
		super(myName);
	}

	@Override
	public int doAction (String functionName, String paramS, double paramD)
	{
		// +0: Falsche Parameter in doAction
		// +1: Erfolgreich erstellt
		// +2: Erfolgreich gelöscht
		// -1: Keine Tür mit dem Namen
		// -2: Kategorie bereits vorhanden
		// -3: Konnte nicht gelöscht werden
		
		// possible --> 1 = Nicht vorhanden, -1 = vorhanden

		if (functionName.equals("new"))
		{
			String eingabe[] = paramS.split(globals.Globals.SEPARATOR);
			int i = database.Categories.newStack(eingabe[1], eingabe[0]);
			refreshViews();
			return i;
		}
		else if (functionName.equals("delete"))
		{
			if (database.Categories.delStack(paramS))
			{
				refreshViews();
				return 2;
			}
			else
			{
				return -3;
			}
		} else if (functionName.equals("possible")) {
			if (database.Categories.possible(paramS)) {
				return 1;
			} else {
				return -1;
			}
		}

		return 0;
	}

	@Override
	public ArrayList<String> getDataList (String query)
	{
		// query = Name der Tür
		// Return: ArrayList<String> mit allen Kategorien

		return database.Categories.getKategorien(query);
	}
}
