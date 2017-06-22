package mvc;

import javafx.scene.Parent;

/**
 * How to implement a view in this MVC concept.
 * 
 * @author hugo-lucca
 */
public interface ViewInterface {
	public abstract Parent constructContainer (); // called to construct the view. Must return Layout (Parent)!
	
	public abstract void refreshView ();	// informs the view about changed data with (notification from a model)
}
