package models;

import java.util.ArrayList;
import database.Doors;
import mvc.Model;

public class EditorModel extends Model {

	public EditorModel (String myName) {
		super(myName);	
	}
		
	@Override	
	public int doAction (String functionName, String paramS, double paramD) {

		if (functionName == "new") {
			
			
		} else if (functionName == "delete") {
			try {
				boolean success = Doors.delDoor(paramS);
				refreshViews();
				return success ? 1 : -1;
			}
			catch (Exception e) {
				return -2;
			}			
		} 
		
		return 0;
	}

	@Override
	public ArrayList<String> getDataList (String query) {

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
