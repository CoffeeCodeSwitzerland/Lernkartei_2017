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
		return myModel;
	}
}
