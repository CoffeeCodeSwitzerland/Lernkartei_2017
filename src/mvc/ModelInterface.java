package mvc;

import java.util.ArrayList;

/**
 * Dieses Interface definiert die Schnittstelle eines DatenModells in diesem MVC Konzept.
 * 
 * @author hugo-lucca
 */
public interface ModelInterface
{
	// For Action- and DataModel: 
	public abstract int  doAction (String functionName, String paramS, double paramD);
	public abstract int  doAction (String functionName, String paramS);
	public abstract int  doAction (String functionName);
	public abstract void registerView (View theView); // also for ActionModel: notification may return errors that need refresh

	// Handle a single Data element / DataModel only:
	public abstract void setString (String data);
	public abstract String getString (String query);

	// Handle a List of Data / DataModel only:
	public abstract void add (String data);
	public abstract ArrayList<String> getDataList (String query);
}