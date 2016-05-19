package mvc.fx;

import mvc.DataModel;
import mvc.Model;

/**
 * This works like a FXView with a local model (a single Sting) handler:
 * 
 * @author hugo-lucca
 */
public abstract class FXViewModel extends FXView
{
	private Model myModel = null; // my local model

	/**
	 * To construct a view with a local model:
	 * @param setName
	 * @param newController
	 */
	public FXViewModel(String setName, FXController newController) {
		super(setName, newController);
		myModel = new Model(setName);
		myModel.registerView(this);
	}

	/**
	 * GETTER
	 * 
	 * @return myModel
	 */
	public DataModel getMyModel() {
		if (myModel == null) myModel = new Model(getName());
		return myModel;
	}
	
	/**
	 * To set data (a String) in my local model:
	 * @param data
	 */
	public void setData (String data)
	{
		getMyModel().setString(data);
	}
	
	/**
	 * To get data (a String) from my local model:
	 * @return
	 */
	public String getData()
	{
		return getMyModel().getString(null);
	}
}
