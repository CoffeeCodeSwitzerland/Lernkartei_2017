package mvc;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is the model base class and not the model class itself.
 * The reason is, the default model should not be forced to implement all the
 * base model methods, but only the mostly used.
 * The model class will then extend this.
 * 
 * @author hugo-lucca
 */
public abstract class DataModel implements ModelInterface
{
	private final ArrayList<View> registredViews = new ArrayList<View>();
	private String name;
	
	private String smallData;
	private final ArrayList<String> dataList = new ArrayList<>();

	public DataModel (String myName)
	{
		name = myName;
	}

	/**
	 * To seek models by name.
	 * @return name of this model
	 */
	public String getName ()
	{
		return this.name;
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
		registredViews.add(theView);
	}

	protected void refreshViews ()
	{
		Iterator<View> it = registredViews.iterator();
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

	/**
	 * To set the single data element.
	 */
	@Override
	public void setString(String data) {
		smallData = data;
	}

	/**
	 * to add data to ma list.
	 */
	@Override
	public void add(String data) {
		dataList.add(data);
	}
}
