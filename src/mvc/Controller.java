package mvc;

import java.util.ArrayList;
import java.util.Iterator;

import debug.Debugger;
import debug.Supervisor;
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
	private final ArrayList<View> views	= new ArrayList<View>();	// list of the controllers views's
	private final ArrayList<Model> models = new ArrayList<Model>();	// list of the controllers model's

	private String mainViewName;	// to simplify navigation to main view

	private View lastView = null;	// to navigate to last view
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
		for (Model m : models)
		{
			if (m.getName().equals(withName)) { return m; }
		}

		if (withName != null)
			Supervisor.warnAndDebug(this, "model(" + withName + ") not found!");
		else
			Supervisor.warnAndDebug(this, "model(null) not allowed!");

		return null; // not found
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
		} else {
			lastView.show();
		}
	}

	/**
	 * To navigate to the next view:
	 * - seek and show (switch to) the unique view with the given name
	 */
	public void showView (String withName)
	{
		if (withName != null) {
			View v = seekView(withName);
			if (v!=null) {
				lastView=thisView; // build show history (depth 2)
				thisView=v;
				v.show();
			} else {
				Debugger.out("Controller.showView(): no view("+withName+")!");
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
		for (View v : views)
		{
			if (v.getName().equals(withName))
			{
				return v;
			}
		}
		if (withName != null)
			Supervisor.warnAndDebug(this, "show("+withName+") not found!");
		else
			Supervisor.warnAndDebug(this, "show(null) not allowed!");
		return null; // not found
	}

	/**
	 * To add a model with a unique name to the controllers list called models
	 */
	public boolean addUniqueModel (Model newModel) {
		Iterator<Model> it = models.iterator();
		int found = 0;
		while (it.hasNext()) {
			Model m = it.next();
			if (m.getName().equals(newModel.getName())) {
				found++;
			}
		}
		if (found == 0) {
			models.add(newModel);
			return true;
		} else {
			Debugger.out("AddUniqueModel: the model '"+newModel.getName()+"' alread exists!");
		}
		return false;
	}

	/**
	 * To insert a view with a unique name to the controllers list called views
	 */
	public boolean addUniqueView (View newView) {
		Iterator<View> it = views.iterator();
		int found = 0;
		while (it.hasNext()) {
			View v = it.next();
			if (v.getName().equals(newView.getName())) {
				found++;
			}
		}
		if (found == 0) {
			views.add(newView);
			return true;
		} else {
			Debugger.out("AddUniqueView: the view '"+newView.getName()+"' alread exists!");
		}
		return false;
	}

	/**
	 * To insert a main-view with a unique name to the controllers list
	 * - the main-view name is hidden for external navigation calls
	 */
	public void addUniqueMainView(View mainView) {
		this.addUniqueView(mainView);
		this.mainViewName = mainView.getName();
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
