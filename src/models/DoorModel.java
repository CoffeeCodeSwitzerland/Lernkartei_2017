package models;

import java.util.ArrayList;

import database.Doors;
import mvc.Model;


public class DoorModel extends Model
{
	@Override
	public int doAction (String functionName, String paramS, double paramD)
	{
		if (functionName == "new")
		{
			try
			{
				boolean success = Doors.newDoor(paramS);
				refreshViews();
				return success ? 1 : -1;
			}
			catch (Exception e)
			{
				return -2;
			}
		}
		else if (functionName == "delete")
		{
			try
			{
				boolean success = Doors.delDoor(paramS);
				refreshViews();
				return success ? 1 : -1;
			}
			catch (Exception e)
			{
				return -2;
			}
		}
		else if (functionName.equals("update"))
		{
			if (database.Doors.update(paramS.split(globals.Globals.SEPARATOR)[0], paramS.split(globals.Globals.SEPARATOR)[1]))
			{
				return 1;
			}
			else
			{
				return -1;
			}
		}

		return 0;
	}

	@Override
	public int doAction (Command command, String... param)
	{
		switch (command)
		{
			case NEW:
				boolean newIsSuccessful = Doors.newDoor(param[0]);
				refreshViews();
				return newIsSuccessful ? 1 : -1;
			case UPDATE:
				boolean updateIsSuccessful = Doors.update(param[0], param[1]);
				return updateIsSuccessful ? 1 : -1;
			case DELETE:
				boolean deleteIsSuccessful = Doors.delDoor(param[0]);
				refreshViews();
				return deleteIsSuccessful ? 1 : -1;
			default:
				int superIsSuccessful = super.doAction(command, param);
				return superIsSuccessful;
		}
	}

	@Override
	public ArrayList<String> getDataList (String query)
	{
		if (query.equals("doors"))
		{
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
