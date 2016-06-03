package models;

import java.util.ArrayList;

import database.Config;
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
				Config.setValue(param[0], param[1]);
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
		values.add(database.Config.getValue(query));

		return values;
	}

}
