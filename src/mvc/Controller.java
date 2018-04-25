package mvc;

import java.util.HashMap;

import debug.Logger;

/**
 * Abstract GUI-Toolkit independent abstract Controller of my MVC concept
 * ======================================================================
 * - any controller has a list of models and views to manage
 * - it manages things like a main-view (has exactly one) and last visible/shown views
 * - it allows navigation through the lists of views and models 
 *      - by name to a model or a view added to this controller or
 * 		- name-less navigation to the main-view (first or per default shown view) of this controller or
 * 		- name-less navigation to the last shown view of the stage of this controller
 * 
 * @author  hugo-lucca
 * @version Apr 2018
 */
public abstract class Controller implements ControllerInterface
{
	private final HashMap<String,View>  views = new HashMap<String,View>();	  // list of my views's
	private final HashMap<String,Model> models = new HashMap<String,Model>(); // list of my model's

	private String mainViewName;	// for simple navigation to main view of this stage
	private View lastView = null;	// for simple navigation to last shown view
	private View thisView = null;	// actual view, to remember it as last view when leaving

	boolean isStarted = false;		// to assert controller was started first

	/**
	 * To give a not null default name of the main-view in any derived constructor 
	 */
	public Controller ()
	{
		setMainViewName("mainview"); // default name, to avoid knowing that name
	}

	/**
	 * To start the application:
	 * - to use the chronological correct order
	 * - must be invoked once by any derived class at the end of his constructor!!!
	 */
	protected void start() {
		initMyModels(); // perform this first ...
		initMyViews();
		startApp(); // ... and perform this as last operation
		isStarted = true;
	}

	/**
	 * To communicate with any model attached to the application-controller (model navigation):
	 * - seeks the model by his name
	 * - used in view's to interchange data and call model methods
	 * @throws Exception if the model is not in the controller list or 
	 *         if the name is null or if the controller is not yet started
	 */
	public Model getModel (String withModelName) throws Exception
	{
		Model modelFound = null;
		if (withModelName != null && isStarted == true) {
			modelFound = models.get(withModelName);
		    if (modelFound != null) return modelFound;
		} else withModelName ="{"+((withModelName == null)?"null name":withModelName)+"/"+((isStarted == true)?"started":"stopped")+"}";
		throw new Exception("Model '"+withModelName+"' not found or not started!"); 
	}

	/**
	 * To navigate to the first view, also called main-view:
	 * - without knowing the view name
	 * - switches (make it visible) the main view to the front of the controllers window
	 */
	public void showMainView ()
	{
		showAndTrackView(getMainViewName());
	}

	/**
	 * To navigate back to the last shown view:
	 * - without need of name
	 * - switch to the last view in user's history
	 * - show or switch means here: set it visible on the actual window (it will be displayed next)
	 * - the marker to the last shown view is updated
	 */
	public void showLastView ()
	{
		if (lastView == null) {
			showMainView(); // switch to main view if no such view
			thisView = this.seekViewForDataAccess(getMainViewName());
		} else {
			lastView.setVisible();
			thisView = lastView;
		}
	}
	
	/**
	 * seeks for last visible view name and if there is'nt one, get the main view name
	 * @return
	 */
	public String getLastViewName ()
	{
		if (lastView != null) {
			return lastView.toString();
		}
		return getMainViewName(); // default: main view
	}

	/**
	 * To navigate to the next view:
	 * - seek and show (switch to) the unique view with the given name
	 * - there may be no such view (by programmers fault), in that case nothing happens, 
	 *   but a log entry will be generated
	 */
	public boolean showAndTrackView (String withName)
	{
		debug.Debugger.out("Controller intent to show view named '"+withName+"'...");
		if (withName != null) {
			View v = seekViewForDataAccess(withName);
			if (v != null) {
				if (this == v.getMyController()) {
					lastView = thisView; // build shown view history...
					thisView = v;        // ...only depth 2 (TODO if needed increase depth)
				} else {
					debug.Debugger.out(">>view is used by other controller, thus last view setting omitted!");
				}
				v.setVisible();
				return true;
			} else {
				Logger.out(">>no view found with name '"+withName+"'!");
			}
		} else {
			Logger.out(">>null name!?!");
		}
		return false;
	}

	/**
	 * For this class only: seek a view with his name:
	 * - do not make it public, if you want to be sure last-view remains valid!!! 
	 * - in the most cases a view, does not know details about another view
	 * - if necessary, solve this through the models or use methods get-/setViewData(...) below
	 * - if you want to invoke a modal view/dialog, you do not need to know the view, but
	 *   just invoke the public showView(name) method below.
	 */
	protected View seekViewForDataAccess (String withName)
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
			Logger.out("the view with 'null'-name may not be added!");
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
		if (result == true) {
			return setMainViewName(name);
		}
		return false;
	}

	/**
	 * =================
	 * GETTERS / SETTERS:
	 * ================= 
	 */
	public String getMainViewName() {
		return mainViewName;
	}

	private boolean setMainViewName(String newMainViewName) {
		if (newMainViewName != null) {
		   mainViewName = newMainViewName;
		   return true;
		}
		return false;
	}
}
