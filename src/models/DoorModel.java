package models;

import java.util.ArrayList;
import database.Doors;

public class DoorModel extends Model {

	public DoorModel (String myName) {
		super(myName);
				
	}

	@Override
	public int doAction (String functionName, String paramS, double paramD) {

		if (functionName == "new") {
					
			
		} else if (functionName == "del") {
			
			
		} 
		
		return 0;
	}

	@Override
	public ArrayList<String> getData (String query) {

		if (query.equals("doors")) {
			
			return Doors.getDoors();
			
		}
		
		return null;
	}
	
	
	
}
