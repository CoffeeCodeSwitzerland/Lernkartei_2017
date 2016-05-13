package models;

import java.util.ArrayList;

import mvc.Model;

public class ConfigModel extends Model {

	public ConfigModel (String myName) {
		super(myName);
	}
	
	@Override	
	public int doAction (String functionName, String paramS, double paramD) {
		
		if (functionName.equals("setValue")) {
			database.Config.setValue(paramS.split(controls.Globals.SEPARATOR)[0],
									 paramS.split(controls.Globals.SEPARATOR)[1]);
			return 1;
		} 
		return 0;
	}

	@Override
	public ArrayList<String> getDataList (String query) {
		
		return null;
		
	}
	
}
