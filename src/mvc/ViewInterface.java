package mvc;

/**
 * How to implement a view in this MVC concept
 * 
 * @author hugo-lucca
 */
public interface ViewInterface {
	public void refreshView ();	// informs the view about changed data with (notification from a model)
	public void show();

	public void setData (String data); // this are for the ViewModel only
	public String getData();
}
