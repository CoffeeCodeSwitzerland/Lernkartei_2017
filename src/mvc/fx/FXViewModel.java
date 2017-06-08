package mvc.fx;

import java.util.ArrayList;

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
	public FXViewModel(FXController newController) {
		super(newController);
		myModel = new Model();
		myModel.registerView(this); // VM only: the View register himself to his single Model 
	}

	/**
	 * GETTER
	 * 
	 * @return myModel
	 */
	public DataModel getMyModel() {
		if (myModel == null) myModel = new Model();
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
	
	public void addData(ArrayList<String> data)
	{
		for(String d: data)
		{
			getMyModel().add(d);
		}
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
