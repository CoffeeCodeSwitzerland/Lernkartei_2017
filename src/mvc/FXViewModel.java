package mvc;

/**
 * Diese FX View Klasse beinhaltet zusätzlich ein eigenes lokales Modell. 
 * 
 * @author hugo-lucca
 */
public abstract class FXViewModel extends FXView
{
	private Model myModel = null;
	
	public FXViewModel(String setName, Controller controller) {
		super(setName, controller);
		myModel = new Model(setName);
		myModel.registerView(this);
	}

	public Model getMyModel() {
		if (myModel == null) myModel = new Model(getName());
		return myModel;
	}
	
	@Override
	public void setData (String data)
	{
		getMyModel().setString(data);
	}
	
	@Override
	public String getData()
	{
		return getMyModel().getString(null);
	}
}
