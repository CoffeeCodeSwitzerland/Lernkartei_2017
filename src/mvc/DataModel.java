package mvc;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Diese Klasse ist des Basis Codegerüst für die Umsetzung des DatenModells in diesem MVC Konzept.
 * 
 * @author hugo-lucca
 */
public abstract class DataModel implements ModelInterface
{
	private final ArrayList<View> myViews = new ArrayList<View>();
	private String name;
	
	private String smallData;
	private final ArrayList<String> dataList = new ArrayList<>();

	public DataModel (String myName)
	{
		name = myName;
	}

	public String getName ()
	{
		return name;
	}

	public void setName (String name)
	{
		this.name = name;
	}

	@Override
	public int doAction (String functionName, String paramS)
	{
		return doAction(functionName, paramS, 0.0F);
	}

	@Override
	public int doAction (String functionName)
	{
		return doAction(functionName, null, 0.0F);
	}

	@Override
	public void registerView (View theView)
	{
		myViews.add(theView);
	}

	public void refreshViews ()
	{
		Iterator<View> it = myViews.iterator();
		while (it.hasNext())
		{
			View v = it.next();
			v.refreshView();
		}
	}

	@Override
	public String getString (String query)
	{
		if (smallData != null)	return smallData;
		return "";
	}

	@Override
	public ArrayList<String> getDataList(String query) {
		return dataList;
	}

	@Override
	public void setString(String data) {
		smallData = data;
	}

	@Override
	public void add(String data) {
		dataList.add(data);
	}
}
