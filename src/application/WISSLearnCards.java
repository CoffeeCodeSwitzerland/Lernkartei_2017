package application;

import javafx.application.Application;
import javafx.stage.Stage;
import mvc.FXSettings;

/**
 * Startet die Lernkartei
 * 
 * @author miro albrecht
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
		FXSettings.setPrimaryStage(primaryStage);

		new MainController();
	}

}
