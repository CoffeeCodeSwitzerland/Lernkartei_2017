package mvc;

import java.util.ArrayList;

/**
 * To define what a model must be able to do.
 * 
 * There is here a concept of two in one model:
 * - the data-model handles with complex data and functionality
 * - the action-model has simple data handling and functionality
 * 
 * These are not the only methods needed to make MVC working.
 * i.e. protected refreshViews(), which is needed to be called after each data change 
 * See model classes for details.
 * 
 * @author hugo-lucca
 */
public interface ModelInterface
{
	// for view to model communication (view registration):
	public abstract void registerView (View theView); // notification may return errors

	// For action- and simple data-models: 
	public abstract int  doAction (String functionName, String paramS, double paramD);
	public abstract int  doAction (String functionName, String paramS);
	public abstract int  doAction (String functionName);

	// To handle a single Data element (DataModel only):
	public abstract void setString (String data);
	public abstract String getString (String query);

	// To handle a List of Data (DataModel only):
	public abstract void add (String data);
	public abstract ArrayList<String> getDataList (String query);
}