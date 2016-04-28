package models;

import java.util.ArrayList;

public class SetModel extends Model {

	public SetModel (String myName) {
		super(myName);
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
			return database.Categories.newKategorie(eingabe[1], eingabe[0]);	
			
		} else if (functionName.equals("delete")) {
			
			if (database.Categories.delKategorie(paramS)) {
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
	
}
