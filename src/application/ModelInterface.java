package application;

import gui.ViewInterface;

public interface ModelInterface {
	public int doAction(String functionName, String paramS, double paramD);
	public int doAction(String functionName, String paramS);
	public int doAction(String functionName);

	public abstract void registerView(ViewInterface theView);

}