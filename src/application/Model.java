package application;

import java.util.ArrayList;
import java.util.Iterator;

import gui.ViewInterface;

public abstract class Model implements ModelInterface {
	
	private ArrayList<ViewInterface> myViews = new ArrayList<ViewInterface>();
	private String name;

	public Model (String myName) {
		name = myName;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int doAction(String functionName, String paramS) {
		return doAction(functionName, paramS, 0.0F);
	}

	@Override
	public int doAction(String functionName) {
		return doAction(functionName, null, 0.0F);
	}

	@Override
	public void registerView(ViewInterface theView) {
		myViews.add(theView);
	}

	public void refreshViews() {
		Iterator<ViewInterface> it = myViews.iterator();
		while (it.hasNext()) {
			ViewInterface v = it.next();
			v.refreshView();
			v.notify(); // evtl. nicht nötig
		}
	}

}
