package mvc;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is the model base class and not the model class itself.
 * - a final model should not be forced to implement all this methods, 
 *   but only the mostly used
 * - the model class will extend this
 * 
 * @author hugo-lucca
 */
public abstract class DataModel implements ModelInterface
{
	// view's may register itself, to be notified in case of data change:
	private final ArrayList<View> registredViews = new ArrayList<View>();

	// the models name (to find it within a list):
	private String name;
	
	// yet implemented, because frequently used:
	private String smallData;  // a single data element as String and/or
	private final ArrayList<String> dataList = new ArrayList<>(); // a data list of String's

	/**
	 * To save the model name: 
	 * @param myNewName
	 */
	public DataModel (String myNewName)
	{
		name = myNewName;
	}

	/**
	 * To compare models by her name:
	 * @return name of this model
	 */
	public String getName ()
	{
		return this.name;
	}

	/**
	 * Allows different parameter settings for the same method:
	 */
	@Override
	public int doAction (String functionName, String paramS)
	{
		return doAction(functionName, paramS, 0.0F);
	}

	/**
	 * Allows different parameter settings for the same method:
	 */
	@Override
	public int doAction (String functionName)
	{
		return doAction(functionName, null, 0.0F);
	}

	/**
	 * To register a view for his notification in case of data changes:
	 * Remember: notification will be invoked, only if a call to the refreshViews() 
	 * method is implemented, after a data change.
	 */
	@Override
	public void registerView (View theView)
	{
		registredViews.add(theView);
	}

	/**
	 * To be called for the notification of the registered view's:
	 */
	public void refreshViews ()
	{
		Iterator<View> it = registredViews.iterator();
		while (it.hasNext())
		{
			View v = it.next();
			v.refreshView();
		}
	}

	/**
	 * To get the value of the single data element (a String):
	 */
	@Override
	public String getString (String unusedQuery)
	{
		if (smallData != null)	return smallData;
		return "";
	}

	/**
	 * To get the list data elements (list of String's):
	 */
	@Override
	public ArrayList<String> getDataList(String unusedQuery) {
		return dataList;
	}

	/**
	 * To set the value of the single data element:
	 */
	@Override
	public void setString(String data) {
		smallData = data;
	}

	/**
	 * To add single data value to the data list:
	 */
	@Override
	public void add(String data) {
		dataList.add(data);
	}

	/**
	 * To add single data value to the data list by index:
	 */
	@Override
	public void set(int index, String dataValue) {
		dataList.set(index, dataValue);
	}
}
