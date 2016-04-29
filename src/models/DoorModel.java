package models;

import java.util.ArrayList;
import database.Doors;

public class DoorModel extends DataModel {

	public DoorModel (String myName) {
		super(myName);
				
	}
		
	@Override	
	public int doAction (String functionName, String paramS, double paramD) {

		if (functionName == "new") {
			try {
				boolean success = Doors.newDoor(paramS);
				return success ? 1 : -1;
			}
			catch (Exception e) {
				return -2;
			}	
			
		} else if (functionName == "delete") {
			try {
				boolean success = Doors.delDoor(paramS);
				return success ? 1 : -1;
			}
			catch (Exception e) {
				return -2;
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
