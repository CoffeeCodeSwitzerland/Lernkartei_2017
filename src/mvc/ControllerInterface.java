package mvc;

/**
 * Defines which methods must be implemented by a final extended controller class
 * - this does not include methods of the abstract class Controller, needed to make MVC work.
 *  
 * @author hugo-lucca
 */
public interface ControllerInterface
{
	public abstract void initMyModels(); // to instantiate model
	public abstract void initMyViews();  // to instantiate views
	public abstract void startApp();	 // to start application
}
