package controls;

import debug.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Startet die Lernkartei
 *
 * @author Rook Studios!
 * 
 */
public class WISSLearnCards extends Application
{
	public static void main (String[] args)
	{
		launch(args); // Initialize java fx
	}

	@Override
	public void start (Stage primaryStage) throws Exception
	{
//		debug.Debugger.stop();
//		Logger.stop();
		Logger.log("Instanziere Models....");
		new MainController(primaryStage); // start application
	}

}