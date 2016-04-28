package models;

import java.util.ArrayList;

import gui.View;

public interface ModelInterface {
	public int doAction(String functionName, String paramS, double paramD);
	public int doAction(String functionName, String paramS);
	public int doAction(String functionName);

	public abstract void registerView(View theView);
	ArrayList<String> getData (String query);

//	public abstract ListModel<ModelInterface> getEntity();
//	public abstract ListModel<ModelInterface> saveEntity(ModelInterface theRecord);
//	public abstract void deleteEntity();

}