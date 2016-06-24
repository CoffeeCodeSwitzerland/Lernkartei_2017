package models;

import java.util.ArrayList;

import database.LKDatabase;
import mvc.Model;


public class ConfigModel extends Model
{
	@Override
	public int doAction (Command command, String... param)
	{
		switch (command)
		{
			case SET:
				if (param.length != 2) { return -2; }
				LKDatabase.myConfig.setKeyValue(param[0],param[1]);
				// TODO return von setKeyValue auswerten
				return 1;

			default:
				int successfulSuper = super.doAction(command, param);
				return successfulSuper;
		}
	};

	@Override
	public ArrayList<String> getDataList (String query)
	{
		ArrayList<String> values = new ArrayList<String>();
		values.add(LKDatabase.myConfig.getValue(query));
		return values;
	}
	
	@Override
	public String getString (String query)
	{
		String result = LKDatabase.myConfig.getValue(query);
		if (result == null)
		{
			// save default
		}
		return result;
	}
	
}
