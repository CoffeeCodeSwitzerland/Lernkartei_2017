package models;

import java.util.ArrayList;

public class SetModel extends Model {

	public SetModel (String myName) {
		super(myName);
	}

	@Override
	public int doAction (String functionName, String paramS, double paramD) {
		if (functionName.equals("new")) {
			
			String eingabe[] = paramS.split(application.Constants.SEPARATOR);
			database.Categories.newKategorie(eingabe[1], eingabe[0]);	
			
		} else if (functionName.equals("delete")) {
			
			database.Categories.delKategorie(paramS);
			
		}
		
		return 0;
	}

	@Override
	public ArrayList<String> getData (String query) {
		
		// query = Name der Tür
		database.Categories.getKategorien(query);
		return null;
		
	}
	
	
	
}
