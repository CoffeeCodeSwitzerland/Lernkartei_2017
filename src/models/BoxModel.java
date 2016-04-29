package models;

import java.util.ArrayList;

import application.MainController;
import gui.View;

public class BoxModel extends Model {

	MainController m;
	
	public BoxModel (String myName, MainController controller) {
		super(myName);
		m = controller;
	}

	@Override
	public int doAction (String functionName, String paramS, double paramD) {
		
		//  0: Falsche Parameter in doAction
		//  1: Erfolgreich erstellt
		//  2: Erfolgreich gelöscht
		// -1: Keine Tür mit dem Namen
		// -2: Kategorie bereits vorhanden
		// -3: Konnte nicht gelöscht werden
		
		if (functionName.equals("new")) {
			
			String eingabe[] = paramS.split(application.Constants.SEPARATOR);
			int i = database.Categories.newKategorie(eingabe[1], eingabe[0]);
			refresh();
			return i;
			
		} else if (functionName.equals("delete")) {
			
			if (database.Categories.delKategorie(paramS)) {
				refresh();
				return 2;
			} else {
				return -3;
			}
			
		}
		
		return 0;
	}

	@Override
	public ArrayList<String> getData (String query) {
		
		// query = Name der Tür
		// Return: ArrayList<String> mit allen Kategorien
		
		return database.Categories.getKategorien(query);
		
	}
	
	private void refresh()
	{
		View v = m.getCurrent();
		if (v != null)
		{
			v.refreshView();
		}
	}
	
}
