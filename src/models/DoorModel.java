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
			try {
				Doors.newDoor(paramS);	
				return 1;
			}
			catch (Exception e) {
				return -1;
			}	
			
		} else if (functionName == "delete") {
			try {
				Doors.delDoor(paramS);
				return 1;
			}
			catch (Exception e) {
				return -1;
			}			
		} 
		
		return 0;
	}

	@Override
	public ArrayList<String> getData (String query) {

		if (query.equals("doors")) {
			
			try
			{
				return Doors.getDoors();
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				return null;
			}
			
		}
		
		return null;
	}
	
	
	
}
