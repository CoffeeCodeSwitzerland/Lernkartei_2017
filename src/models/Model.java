package models;

import java.util.ArrayList;
import java.util.Iterator;

import gui.View;

public abstract class Model implements ModelInterface {
	
	private ArrayList<View> myViews = new ArrayList<View>();
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
	public void registerView(View theView) {
		myViews.add(theView);
	}

	public void refreshViews() {
		Iterator<View> it = myViews.iterator();
		while (it.hasNext()) {
			View v = it.next();
			v.refreshView();
			v.notify(); // evtl. nicht nötig
		}
	}


}
