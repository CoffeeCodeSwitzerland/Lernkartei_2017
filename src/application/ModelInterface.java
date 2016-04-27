package application;

<<<<<<< Upstream, based on branch 'master' of https://github.com/RookStudios/Lernkartei.git
import gui.ViewInterface;

public interface ModelInterface {
	public int doAction(String functionName, String paramS, double paramD);
	public int doAction(String functionName, String paramS);
	public int doAction(String functionName);

	public abstract void registerView(ViewInterface theView);

}
=======
public interface ModelInterface
{
	public int doAction (String functionName, String paramS, double paramD);

	public int doAction (String functionName, String paramS);

	public int doAction (String functionName);
}
>>>>>>> 2d675ef Beautify
