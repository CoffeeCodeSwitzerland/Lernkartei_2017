package application;

import gui.ViewInterface;

public interface ModelInterface {
	public int doAction(String functionName, String paramS, double paramD);
	public int doAction(String functionName, String paramS);
	public int doAction(String functionName);

	public abstract void registerView(ViewInterface theView);

//	public abstract ListModel<ModelInterface> getEntity();
//	public abstract ListModel<ModelInterface> saveEntity(ModelInterface theRecord);
//	public abstract void deleteEntity();

}