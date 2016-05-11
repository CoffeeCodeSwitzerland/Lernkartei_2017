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
		
		return Bewertungsklasse.getShuffledCards(query);
		 
	}
	
	// TODO implement doAction & getDataList
}
