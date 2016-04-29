package models;

import java.util.ArrayList;

import database.Database;

public class CardModel extends DataModel {

	public CardModel (String myName) {
		super(myName);
		
	}

	@Override
	public int doAction (String functionName, String paramS, double paramD) {
		
		// Returns:
		//  0: Falsche Eingabe --> functionName existiert nicht
		//  1: Erfolgreich hinzugefüg
		// -1: Fehler beim hinzufügen der Karte
		// 	2: Erfolgreich gelöscht
		// -2: Nicht gelöscht
		
		if (functionName.equals("new")) {
			
			// paramS = Vorderseite:::Rückseite:::SetName
			
			String[] values = new String[5];
			values[0] = paramS.split(application.Constants.SEPARATOR)[0];
			values[1] = paramS.split(application.Constants.SEPARATOR)[1];
			values[2] = paramS.split(application.Constants.SEPARATOR)[2];
			values[3] = "1";
			values[4] = "-16777216"; // Standart Farbcode für Schwarz
			
			if (Database.pushToStock(values)) {
				refreshViews();
				return 1;
			} else {
				return -1;
			}
			
		} else if (functionName.equals("edit")) {
			
			// TODO: Funktionalität Edit hinzufügen
			
		} else if (functionName.equals("delete")) {
			
			// paramS = KartenID
			
			if (Database.delEntry(paramS)) {
				return 2;
			} else {
				return -2;
			}
			
		}
		
		return 0;
	}

	@Override
	public ArrayList<String> getData (String query) {

		ArrayList<String> result = new ArrayList<String>();
		
		if (Database.pullFromStock(query) == null) { 
		
			return result;
		
		} else {
			
			for (String[] s : Database.pullFromStock(query)) {
				
				String data = s[0] + application.Constants.SEPARATOR 
							+ s[1] + application.Constants.SEPARATOR 
							+ s[2];
				
				result.add(data);
				
			}
			
			return result;
			
		}
		
	}
}
