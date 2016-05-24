package mvc;

import debug.Logger;
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
	/**
	 * May not be invoked!
	 */
	protected void setVisible()
	{
		Logger.log("view.setVisible() has no toolkit implementation!");
	}
}
