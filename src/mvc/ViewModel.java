package mvc;

import javafx.scene.Parent;
import mvc.DataModel;
import mvc.Model;

/**
 * This only shows how to implement a ViewModel:
 * You may have to copy these functions.
 * 
 * @author hugo-lucca
 */
public final class ViewModel extends View
{
	private Model myModel = null; // my local model

	/**
	 * To construct a view with a local model:
	 * @param setName
	 * @param newController
	 */
	public ViewModel(String setName) {
		super(setName);
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

	/* (non-Javadoc)
	 * @see mvc.ViewInterface#constructContainer()
	 */
	@Override
	public Parent constructContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see mvc.ViewInterface#refreshView()
	 */
	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}
}
