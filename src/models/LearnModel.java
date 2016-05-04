package models;

import java.util.ArrayList;

import mvc.Model;

public class LearnModel extends Model
{

	public LearnModel (String myName)
	{
		super(myName);
		
	}
	
	@Override
	public int doAction(String functionName, String freeStringParam, double freeDoubleParam) {

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
		
		// query = set + SEPARATOR + nr
		
		// query to int
		// return card nr query
		
		return null;
	}
	
	// TODO implement doAction & getDataList
}
