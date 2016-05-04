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
	
	//
	// Beide folgende Methoden sind eigentlich nur "Kosmetik". Man könnte
	// auch die sich darin befindenden Aufrufe direkt tätigen.
	// Um Verwechslugen zu vermeiden wird hier "Data" statt "String" verwendet.
	//
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
