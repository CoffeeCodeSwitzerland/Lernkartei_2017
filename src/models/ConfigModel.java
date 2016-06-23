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
				LKDatabase.myConfig.getMyAttributes().seekKeyNamed(param[0]).setValue(param[1]);
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

}
