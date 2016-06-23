 package models;

import java.util.ArrayList;

import database.LKDatabase;
import debug.Supervisor;
import mvc.Model;


public class StackModel extends Model
{
	/**
	 * @deprecated
	 */
	@Override
	public int doAction (String functionName, String paramS, double paramD)
	{
		// deprecated Method
		
		Supervisor.errorAndDebug(this, "WARNIG (StackModel): Use of deprecated method doAction(). Use the new one!");
		
		return -9;
	}

	@Override
	public int doAction (Command command, String... param)
	{
		switch (command)
		{
			case NEW:
				if (param.length != 2) { return -2; }
				int newIsSuccessful = LKDatabase.myStacks.newStack(param[0], param[1]);
				refreshViews(); // TODO siehe kommentar von 'case DELETE'
				return newIsSuccessful;
			case UPDATE:
				if (param.length != 2) { return -2; }
				boolean updateIsSuccessful = LKDatabase.myStacks.update(param[0], param[1]);
				return updateIsSuccessful ? 1 : -1;
			case DELETE:
				if (param.length != 1) { return -2; }
				boolean successfulDelete = LKDatabase.myStacks.delStack(param[0]);
				refreshViews(); // TODO überprüfen ob nötig oder ob view stack selbst löschen kann.
				return successfulDelete ? 1 : -1;
			case CAN_CREATE:
				if (param.length != 1) { return -2; }
				boolean canCreate = LKDatabase.myStacks.possible(param[0]);
				return canCreate ? 1 : -1;
			default:
				int superIsSuccessful = super.doAction(command, param);
				return superIsSuccessful;
		}
	};

	@Override
	public ArrayList<String> getDataList (String query)
	{
		// query = Name der Tür
		// Return: ArrayList<String> mit allen Kategorien

		return LKDatabase.myStacks.getKategorien(query);
	}
}
