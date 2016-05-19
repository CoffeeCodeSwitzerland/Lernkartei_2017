package mvc;

import debug.Debugger;
/**
 * Abstract GUI-Toolkit independent View of my MVC concept
 * =======================================================
 * - allows navigation by name to a view
 * - further functionality will be implemented by his extensions
 * 
 * @author  hugo-lucca
 * @version Mai 2016
 */
public abstract class View implements ViewInterface
{
	private String name; // name of the view
	
	public View (String newName) {
		name   = newName;
	}

	/**
	 * To compare when searching a view:
	 * - no setter (only set by the constructor)
	 * 
	 * @return name of the view
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * May not be invoked!
	 */
	protected void show()
	{
		Debugger.out("view.show("+getName()+") has no toolkit implementation!");
	}
}
