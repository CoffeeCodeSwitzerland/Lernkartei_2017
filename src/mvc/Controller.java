package mvc;

import java.util.ArrayList;
import java.util.Iterator;

import debug.Debugger;
import debug.Supervisor;
/**
 * Abstract GUI-Toolkit independent Controller of my MVC concept
 * =============================================================
 * - allows navigation by name to a model of the stage of this controller 
 * - allows navigation by name to a view of the stage of this controller
 * - allows name-less navigation to the main-view of the stage of this controller
 * - allows name-less navigation to the last shown view of the stage of this controller
 * 
 * @author  hugo-lucca
 * @version 2016
 * License: LGPL
 */
public abstract class Controller implements ControllerInterface
{
	private final ArrayList<View> views	= new ArrayList<View>();
	private final ArrayList<Model> models = new ArrayList<Model>();
	private String mainViewName;
	// Navigate to last view:
	private View lastView = null;
	private View thisView = null;
	
	public Controller ()
	{
		this.mainViewName = "mainview"; // default name, to avoid knowing that name
	}
	
	/**
	 * start application using a predefined chronological order
	 * 
	 * !!! Must be invoked by the extending class at the end of his constructor!!!
	 */
	protected void start() {
		initMyModels(); // do first
		initMyViews();
		startApp(); // do last
	}

	/**
	 * To communicate with any application model
	 * seek the model by that name
	 * used in application view classes to address a specific model of
	 * a specific controller 
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
	 * To navigate to first shown view on this stage (without need of name)
	 * switch to the main view (scene) to the front of the window (stage)
	 */
	public void showMainView ()
	{
		showView(mainViewName);
	}

	/**
	 * To navigate back to last shown view (without need of name)
	 * switch to the last view (scene) in history
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
	 * To navigate to next view:
	 * seek and show (switch to) the view with that name
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
	 * Class internal only: To seek a view with his name.
	 * Do not make it public, if you want to be sure
	 * that last-view remains valid! 
	 * In the most cases a view, does no need to know details about another view.
	 * If necessary, solve this through the models.
	 * If you want to use modal-Views, you do not need to know the view, but
	 * just invoke the public showView(name) method.
	 */
	private View seekView (String withName)
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

	// TODO diese Funktion muss weg = tempräre Lösung
//	public View getView (String withName)
//	{
//		return seekView(withName); // not found
//	}

	public String getViewData (String withName)
	{
		return seekView(withName).getData(); // not found
	}

	public void setViewData (String withName, String data)
	{
		seekView(withName).setData(data);; // not found
	}

	/**
	 * To insert a Model with a unique name in the controllers models list
	 * add a new model to the model list and assert his name is unique
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
	 * To insert a View with a unique name in the controllers views list
	 * add a new view to the view list and assert his name is unique
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
	 * To insert a main-View with a unique name in the new controllers views list
	 * adds additionally a new main-View name
	 */
	public void addUniqueMainView(View mainView) {
		this.addUniqueView(mainView);
		this.mainViewName = mainView.getName();
	}

	/**
	 * To allow access from extended classes that needs the mainViewName.
	 * May be used in extending classes.
	 * Getters and Setters
	 */
	protected String getMainViewName() {
		return mainViewName;
	}
}
