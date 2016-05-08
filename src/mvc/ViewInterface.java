package mvc;

import javafx.scene.Parent;

/**
 * How to implement a view in this MVC concept
 * 
 * @author hugo-lucca
 */
public interface ViewInterface {
	public abstract Parent constructContainer ();
	public abstract void refreshView ();	// informs the view about changed data with (notification from a model)
	public abstract void show();

	public abstract void setData (String data); // this are for the ViewModel only
	public abstract String getData();
}
