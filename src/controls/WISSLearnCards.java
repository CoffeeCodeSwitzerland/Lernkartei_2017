package controls;

import debug.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Starter-Class
 * 
 * Will just start the Application as 
 * a single thread JavaFX-Application
 * 
 * Remark: 
 * ======
 * Other threads (Games) will be started separately 
 * unsing other toolkits like Swing (multi-threading) and
 * processing (with a special display thread and with his own event handler)
 * and displaying their output on separated windows, because they
 * are not compatible with JavaFX, that acts only on one singular thread.
 * 
 * @author Rook Studios!
 */
public class WISSLearnCards extends Application
{
	public static void main (String[] args)
	{
		launch(args); // launch java FX-Application thread, who calls start(Stage s) below
	}

	@Override
	public void start (Stage primaryStage) throws Exception
	{
		Logger.log("Staring instantiating models and views on primary stage....");
		new MainController(primaryStage); // start my application
	}

}