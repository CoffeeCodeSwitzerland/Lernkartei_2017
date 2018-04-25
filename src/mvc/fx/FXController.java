package mvc.fx;

import java.util.ArrayList;

import debug.Debugger;
import mvc.Controller;
/**
 * Abstract GUI-Toolkit independent FX-Controller
 * ==============================================
 * - extends the controller features
 * - handles a single FX Stage owned by a controller
 * 
 * @author  hugo-lucca
 * @version 2016
 */
public abstract class FXController extends Controller
{
	private FXStage myFXStage;

	/**
	 * To construct a new stage for this new controller:
	 */
	public FXController ()
	{
		myFXStage = new FXStage(null);
		this.start();
	}

	/**
	 * To construct a new controller to an existig stage (the primary FX stage):
	 */
	public FXController (FXStage primaryStage)
	{
		myFXStage = new FXStage(primaryStage);
		this.start();
	}
	
	/**
	 * To add a first view with a new stage to this controller:
	 * 
	 * @param newView
	 * @return true if ok, false if not unique or called twice
	 */
	public boolean addViewOnNewStage (FXView newView) {
		String name = newView.myParentLayout.getId();
		boolean result = this.addUniqueView(newView, name);
		if (result) {
			newView.getFXController().addUniqueMainView(newView, name);
			newView.isConstructed();
		}
		return result;
	}

	/**
	 * To insert a view with a unique name to the controllers list called views
	 * 
	 * @return true if ok, false if not unique or called twice
	 */
	public boolean addUniqueView (FXView newView) {
		String name = newView.myParentLayout.getId();
		boolean result = super.addUniqueView(newView, name);
		// check if the new view is not an View (must be Toolkit-class):
		if (newView.getClass().getGenericSuperclass().toString().endsWith(".View")) {
			Debugger.out("FXController.addUniqueView(View("+name+")) is not a FXView!");
		} else {
			((FXView) newView).isConstructed();
		}
		return result; 
	}
	
	/**
	 * To communicate with any application model: 
	 * - seeks the model by name
	 * - used in application view classes to address a specific model of
	 *   a specific controller 
	 * @throws Exception 
	 */
	public FXModel getFXModel (String withName) throws Exception
	{
		if (getModel(withName) == null) {
			Debugger.out("FXController.getFXModel("+withName+") no FXModel found!");
			return null;
		} else {
			// assert the model is not a Model (without Toolkit) before casting:
			if (getModel(withName).getClass().getGenericSuperclass().toString().endsWith(".Model")) {
				Debugger.out("FXController.getFXModel("+withName+") did not found a FXModel of this name!");
				return null;
			} else {
				return (FXModel)super.getModel(withName);
			}
		}
	}

	/**
	 * To allow direct data interchange between view's and view-model's:
	 * @param withViewName
	 * @return the valid FXViewModel of that name
	 * @throws Exception if something does not match to the requested name
	 */
	private FXViewModel getFXViewModel(String withViewName) throws Exception {
		// assert there is a view with that name that allows data access
		if (seekViewForDataAccess(withViewName) == null) {
			throw new Exception ("FXController.getViewData("+withViewName+") no FXView or FXViewModel found!");
		}
		// assert the view is an instance of a FXViewModel before casting:
		if (   seekViewForDataAccess(withViewName).getClass().getGenericSuperclass().toString().endsWith(".View")
			|| seekViewForDataAccess(withViewName).getClass().getGenericSuperclass().toString().endsWith(".FXView") ) {
			throw new Exception ( "FXController.getViewData("+withViewName+") is not a FXViewModel ("+
								  seekViewForDataAccess(withViewName).getClass().getGenericSuperclass()+")!" );
		}
		return ((FXViewModel)seekViewForDataAccess(withViewName));
	}
	
	/**
	 * gets a data String element from an other view-model 
	 * @throws Exception 
	 */
	public String getViewData (String withViewName) throws Exception
	{
		return getFXViewModel(withViewName).getData();
	}

	/**
	 * To allow direct data interchange between view's and view-model's: 
	 * - sets a data String element into another view-model 
	 */
	public void setViewData (String withName, String data)
	{
		if (seekViewForDataAccess(withName) == null) {
			Debugger.out("FXController.setViewData("+withName+") no FXView or FXViewModel found!");
		} else {
			// assert the model is not a View or FXView (must be FXViewModel) before casting:
			if (   seekViewForDataAccess(withName).getClass().getGenericSuperclass().toString().endsWith(".View")
				|| seekViewForDataAccess(withName).getClass().getGenericSuperclass().toString().endsWith(".FXView") ) {
				Debugger.out("FXController.setViewData("+withName+") is not a FXViewModel ("+seekViewForDataAccess(withName).getClass().getGenericSuperclass()+")!");
			} else {
				((FXViewModel)seekViewForDataAccess(withName)).setData(data); // not found
			}
		}
	}
	
	public void addViewData (String withName, ArrayList<String> data)
	{
		if (seekViewForDataAccess(withName) == null) {
			Debugger.out("FXController.setViewData("+withName+") no FXView or FXViewModel found!");
		} else {
			// assert the model is not a View or FXView (must be FXViewModel) before casting:
			if (   seekViewForDataAccess(withName).getClass().getGenericSuperclass().toString().endsWith(".View")
				|| seekViewForDataAccess(withName).getClass().getGenericSuperclass().toString().endsWith(".FXView") ) {
				Debugger.out("FXController.setViewData("+withName+") is not a FXViewModel ("+seekViewForDataAccess(withName).getClass().getGenericSuperclass()+")!");
			} else {
				((FXViewModel)seekViewForDataAccess(withName)).addData(data); // not found
			}
		}
	}

	/**
	 * GETTER/SETTER
	 * =============
	 * @return my stage
	 */
	public FXStage getMyFXStage () {
		if (myFXStage == null) {
			Debugger.out("getMyFXStage(): no FXStage for this controller");
		}
		return myFXStage;
	}

	public void setMyFXStage (FXStage theFXStage) {
		this.myFXStage = theFXStage;
	}
}
