package mvc;

/**
 * Dieses Interface definiert die zwingenden Methoden für eine Klasse vom Typ Controller
 * für dieses MVC Konzept.
 * 
 * @author hugo-lucca
 */
public interface ControllerInterface
{
	public abstract void initMyModels();
	public abstract void initMyViews();
	
	public abstract Model getModel (String name);
	public abstract void showMain ();
	public abstract View getView (String name);
}
