package mvc;

import debug.Debugger;

/**
 * Diese Klasse ist des Basis Codegerüst für die Umsetzung der GUI-View's in diesem MVC Konzept.
 * View's müssen sich beim Modell registrieren, wenn sie bei Änderungen im Modell automatisch ge-refresht werden wollen.
 * Der Constructor dieser Klasse muss immer zuerst aufgerufen werden 
 * 
 * @author hugo-lucca
 */
public abstract class View implements ViewInterface
{
	private String name;
	
	public View (String newName) {
		name   = newName;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setData (String data)
	{
		Debugger.out("view.setData(): view '"+name+"' does not have a Model (try 'extends FXViewModel')");
	}
	
	public String getData()
	{
		Debugger.out("view.setData(): view '"+name+"' does not have a Model  (try 'extends FXViewModel')");
		return "";
	}
}
