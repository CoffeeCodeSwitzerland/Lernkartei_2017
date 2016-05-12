package controls;

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
		// Initialisiert javafx
		launch(args);
	}

	@Override
	public void start (Stage primaryStage) throws Exception
	{
		new MainController(primaryStage);
	}

}
