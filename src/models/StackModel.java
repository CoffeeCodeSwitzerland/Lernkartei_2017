package models;

import java.util.ArrayList;

import mvc.Model;


public class StackModel extends Model
{
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
			int i = database.Stack.newStack(eingabe[1], eingabe[0]);
			refreshViews();
			return i;
		}
		else if (functionName.equals("delete"))
		{
			if (database.Stack.delStack(paramS))
			{
				refreshViews();
				return 2;
			}
			else
			{
				refreshViews();
				return -3;
			}
		} else if (functionName.equals("possible")) {
			if (database.Stack.possible(paramS)) {
				return 1;
			} else {
				return -1;
			}
		} else if (functionName.equals("update")) {
			if (database.Stack.update(paramS.split(globals.Globals.SEPARATOR)[0], paramS.split(globals.Globals.SEPARATOR)[1])) {
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

		return database.Stack.getKategorien(query);
	}
}
