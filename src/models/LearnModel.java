package models;

import java.util.ArrayList;

import Learning.Bewertungsklasse;
import mvc.Model;

public class LearnModel extends Model
{

	public LearnModel (String myName)
	{
		super(myName);
		
	}
	
	@Override
	public int doAction(String functionName, String freeStringParam, double freeDoubleParam) {
		
		//Aufruf der Bewertungs Klasse
		

		// funcName = correct OR false
		// stringParam = card ID
		
		return 0; // no error
	}

	@Override
	public ArrayList<String> getDataList(String query) {
		
		if (this.getDataList("") == null)
		{
			// TODO fill dataList
		}
		else
		{
			
		}
		//1.Karte Anzeigen
		
		 return Bewertungsklasse.ListenFüller(query);
		
		// query = set + SEPARATOR + nr		
		// query to int
		// return card nr query
		
		 
	}
	
	// TODO implement doAction & getDataList
}
