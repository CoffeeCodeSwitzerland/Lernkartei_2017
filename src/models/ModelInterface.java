package models;

import java.util.ArrayList;

import gui.View;


public interface ModelInterface
{
	public abstract int doAction (String functionName, String paramS, double paramD);
	public abstract int doAction (String functionName, String paramS);
	public abstract int doAction (String functionName);

	public abstract String getString (String query);
	public abstract ArrayList<String> getData (String query);

	public abstract void registerView (View theView);

}