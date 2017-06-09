package models;

import java.util.ArrayList;

import database.LKDatabase;
import debug.Supervisor;
import mvc.Model;


public class DoorModel extends Model
{
	/**
	 * @deprecated
	 */
	@Override
	public int doAction (String functionName, String paramS, double paramD)
	{
		Supervisor.errorAndDebug(this, "Deprecated method (DoorModel). Please use the new doAction");
		return -9;
	}

	@Override
	public int doAction (Command command, String... param)
	{
		switch (command)
		{
			case NEW:
				boolean newIsSuccessful = LKDatabase.myDoors.newDoor(param[0]);
				refreshViews();
				return newIsSuccessful ? 1 : -1;
			case UPDATE:
				boolean updateIsSuccessful = LKDatabase.myDoors.update(param[0], param[1]);
				return updateIsSuccessful ? 1 : -1;
			case DELETE:
				boolean deleteIsSuccessful = LKDatabase.myDoors.delDoor(param[0]);
				refreshViews();
				return deleteIsSuccessful ? 1 : -1;
			case CAN_CREATE:
				ArrayList<String> doors = LKDatabase.myDoors.getDoors();
				if (doors.contains(param[0]))
				{
					return -1;
				}
				return 1;
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
				return LKDatabase.myDoors.getDoors();
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				return null;
			}
		}
		return null;
	}
	
	public int getDataListCount(String query)
	{
		return getDataList(query).size();
	}
}
