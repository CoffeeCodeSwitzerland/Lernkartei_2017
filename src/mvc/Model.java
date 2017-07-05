package mvc;

import java.util.ArrayList;
import java.util.Iterator;

import debug.Debugger;
import globals.Functions;


/**
 * Abstract GUI-Toolkit independent Model of my MVC concept
 * ========================================================
 * - allows navigation by name
 * - handles list of String's with like() and filtered by query. 
 * - clear data is implemented as doAction("clear")
 * 
 * @author  hugo-lucca
 * @version Mai 2016
 */
public class Model extends DataModel
{
	@Override
	public String getString (String query)
	{
		if (query == null || query.equals("string"))
		{
			return super.getString(query);
		}
		else
		{
			try
			{
				int position = Integer.parseInt(query);
				if (position >= 0 && position < this.getDataList(null).size()) { return this.getDataList(null).get(position); }
			}
			catch (Exception e)
			{
				return super.getString(query);
			}
		}
		return "";
	}

	@Override
	public int doAction (String functionName, String freeStringParam, double freeDoubleParam)
	{
		if (functionName.equals("clear"))
		{
			this.setString(null);
			this.getDataList(null).clear();
		}
		return 1; // no error
	}

	@Override
	public int doAction (Command command, String... param)
	{
		if (command == Command.CLEAR)
		{
			this.setString(null);
			this.getDataList(null).clear();
			return 1; // command successful
		}

		Debugger.out("Model.doAction : command not found/implemented");
		return 0; // command not found/implemented
	}

	@Override
	public ArrayList<String> getDataList (String query)
	{
		if (query != null && !query.equals(""))
		{
			ArrayList<String> reducedList = new ArrayList<>();
			Iterator<String> it = super.getDataList(null).iterator();
			while (it.hasNext())
			{
				String s = it.next();
				if (s.equals(query) || s.equalsIgnoreCase(query) || s.contains(query) || s.matches(query) || Functions.like(s, query))
				{
					reducedList.add(s);
				}
			}
			return reducedList;
		}
		return super.getDataList(null);
	}

	@Override
	public String getProperty(String property)
	{
		return property;
	}
	
	public void setDataList (ArrayList<String> dataList)
	{
		
	}
	
	public void addDataList (ArrayList<String> dataList)
	{
		
	}
	
	public void addData (String dataString)
	{
		
	}
}
