package mvc;

/**
 * Defines which methods must be implemented by a controller extending class
 * 
 * These are not the methods needed to make MVC work.
 * Moreover the controller has no obligations in front of models and views.
 *  
 * @author hugo-lucca
 */
public interface ControllerInterface
{
	public abstract void initMyModels(); // to instantiate model
	public abstract void initMyViews();  // to instantiate views
	public abstract void startApp();	 // to start application
}
