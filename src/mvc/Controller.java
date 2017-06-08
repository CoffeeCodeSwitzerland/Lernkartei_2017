package mvc;

import java.util.HashMap;

import debug.Logger;

/**
 * Abstract GUI-Toolkit independent Controller of my MVC concept
 * =============================================================
 * - allows navigation by name to a model instantiated for this controller 
 * - allows navigation by name to a view instantiated for this controller
 * - allows name-less navigation to the main-view (first-view) of this controller
 * - allows name-less navigation to the last shown view of the stage of this controller
 * 
 * @author  hugo-lucca
 * @version Mai 2016
 */
public abstract class Controller implements ControllerInterface
{
	private final HashMap<String,View> views = new HashMap<String,View>();	  // list of my views's
	private final HashMap<String,Model> models = new HashMap<String,Model>(); // list of my model's

	private String mainViewName;	// to simple navigation to main view of this stage
	private View lastView = null;	// to simple navigation to last shown view
	private View thisView = null;	// actual view to remember last view when leaving
	
	/**
	 * To give a main default name to any constructor 
	 */
	public Controller ()
	{
		this.mainViewName = "mainview"; // default name, to avoid knowing that name
	}
	
	/**
	 * To start the application:
	 * - will use a predefined chronological order
	 * - must be invoked by the extending class at the end of his constructor!!!
	 */
	protected void start() {
		initMyModels(); // do first
		initMyViews();
		startApp(); // do last
	}

	/**
	 * To communicate with any application model:
	 * - seeks the model by his name
	 * - used in view's to interchange data and call functions of a model
	 */
	public Model getModel (String withName)
	{
		return models.get(withName);
	}

	/**
	 * To navigate to the first view, also called main-view:
	 * - without knowing his name
	 * - switches the main view to the front of the controllers window
	 */
	public void showMainView ()
	{
		showView(mainViewName);
	}

	/**
	 * To navigate back to the last shown view:
	 * - without need of name
	 * - switch to the last view in user's history
	 */
	public void showLastView ()
	{
		if (lastView == null) {
			showMainView(); // switch to main view if no such view
			thisView=this.seekView(getMainViewName());
		} else {
			lastView.setVisible();
			thisView=lastView;
		}
	}
	
	public String getLastViewName ()
	{
		if (lastView == null) {
			return getMainViewName();
		} else {
			return lastView.toString();
		}
	}

	/**
	 * To navigate to the next view:
	 * - seek and show (switch to) the unique view with the given name
	 */
	public void showView (String withName)
	{
		debug.Debugger.out("Controller.showView("+withName+")");
		if (withName != null) {
			View v = seekView(withName);
			if (v!=null) {
				if (this == v.getMyController()) {
					lastView=thisView; // build show history (depth 2)
					thisView=v;
				} else {
					debug.Debugger.out("No Last View Setting (view is on an other Controller)!");
				}
				v.setVisible();
			} else {
				Logger.out("no view("+withName+")!");
			}
		}
	}

	/**
	 * For this class only: To seek a view with his name:
	 * - do not make it public, if you want to be sure that last-view remains valid!!! 
	 * - in the most cases a view, does not know details about another view
	 * - if necessary, solve this through the models or use methods get-/setViewData(...) below
	 * - if you want to invoke a modal view/dialog, you do not need to know the view, but
	 *   just invoke the public showView(name) method below.
	 */
	protected View seekView (String withName)
	{
		return views.get(withName);
	}

	/**
	 * To add a model with a unique name to the controllers list called models
	 */
	public boolean addUniqueModel (Model newModel, String name) {
		if (newModel != null) {
			if (models.get(name) == null) {
				models.put(name, newModel);
				return true;
			} else {
				Logger.out("the model '"+name+"' already exists!");
			}
		} else {
			Logger.out("the model 'null' may not be added!");
		}
		return false;
	}

	/**
	 * To insert a view with a unique name to the controllers list called views
	 * 
	 * @return true if ok, false if not unique or called twice
	 */
	public boolean addUniqueView (View newView, String name) {
		if (newView != null) {
			if (views.get(name) == null) {
				views.put(name, newView);
				return true;
			} else {
				Logger.out("the view '"+name+"' already exists!");
			}
		} else {
			Logger.out("the view 'null' may not be added!");
		}
		return false;
	}

	/**
	 * To insert a main-view with a unique name to the controllers list
	 * - the main-view name is hidden for external navigation calls
	 * 
	 * @return true if ok, false if not unique or called twice
	 */
	public boolean addUniqueMainView(View mainView, String name) {
		boolean result = this.addUniqueView(mainView, name);
		if (result) {
			this.mainViewName = name;
		}
		return result;
	}

	/**
	 * GETTERS / SETTERS:
	 * =================
	 * 
	 * To allow access from extended classes that needs the mainViewName.
	 * May be used in extending classes.
	 */
	protected String getMainViewName() {
		return mainViewName;
	}
}
