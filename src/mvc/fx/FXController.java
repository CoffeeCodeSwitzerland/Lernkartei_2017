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
	 * To communicate with any application model
	 * seek the model by that name
	 * used in application view classes to address a specific model of
	 * a specific controller 
	 */
	public FXModel getFXModel (String withName)
	{
		// TODO assert the Model is an FXModel
		return (FXModel)super.getModel(withName);
	}
}
