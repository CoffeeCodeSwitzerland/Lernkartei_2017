package mvc.fx;

import debug.Debugger;
import mvc.Controller;

/**
 * Abstract GUI-Toolkit independent FX-Controller
 * ==============================================
 * - extends the controller features
 * - handles a FX Settings object
 * 
 * @author  hugo-lucca
 * @version 2016
 * License: LGPL
 */
public abstract class FXController extends Controller
{
	private FXStage myFXStage;

	public FXController ()
	{
		myFXStage = new FXStage(null);
		this.start();
	}

	public FXController (FXStage primaryStage)
	{
		myFXStage = new FXStage(primaryStage);
		this.start();
	}

	public FXStage getMyFXStage () {
		if (myFXStage == null) {
			Debugger.out("getMyFXStage(): no FXStage for this controller");
		}
		return myFXStage;
	}

	public void setMyFXStage (FXStage theFXStage) {
		this.myFXStage = theFXStage;
	}

	public boolean addViewOnNewStage (FXView newView) {		
		this.addUniqueView(newView);
		newView.getFXController().addUniqueMainView(newView);
		return true;
	}

	/**
	 * To communicate with any application model: 
	 * - seeks the model by name
	 * - used in application view classes to address a specific model of
	 *   a specific controller 
	 */
	public FXModel getFXModel (String withName)
	{
		// assert the model is a FXModel before casting
		if (super.getModel(withName).getClass().getName().equals("FXModel")) {
			return (FXModel)super.getModel(withName);
		} else {
			Debugger.out("FXController.getFXModel("+withName+") ist not a FXModel!");
			return null;
		}
	}

	/**
	 * To allow direct data interchange between view's and view-model's:
	 * - gets a data String element from an other view-model 
	 */
	public String getViewData (String withName)
	{
		// assert the model is a FXViewModel before casting
		if (seekView(withName).getClass().getName().equals("FXViewModel")) {
			return ((FXViewModel)seekView(withName)).getData(); // not found
		} else {
			Debugger.out("FXController.getViewData("+withName+") ist not a FXViewModel!");
			return "";
		}
	}

	/**
	 * To allow direct data interchange between view's and view-model's: 
	 * - sets a data String element into another view-model 
	 */
	public void setViewData (String withName, String data)
	{
		// assert the model is a FXViewModel before casting
		if (seekView(withName).getClass().getName().equals("FXViewModel")) {
			((FXViewModel)seekView(withName)).setData(data); // not found
		} else {
			Debugger.out("FXController.setViewData("+withName+") ist not a FXViewModel!");
		}
	}
}
