package models;

import java.util.ArrayList;


public abstract class ActionModel extends DataModel
{
	public ActionModel (String myName)
	{
		super(myName);
	}

	@Override
	public String getString (String query)
	{
		debug.Debugger.out("getString() ist für ActionModel(" + getName() + ") nicht verfügbar");
		return null;
	}

	@Override
	public ArrayList<String> getData (String query)
	{
		debug.Debugger.out("getData() ist für ActionModel(" + getName() + ") nicht verfügbar");
		return null;
	}
}
