package application;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Startet die Lernkartei
 * 
 * @author miro-albrecht
 *
 */
public class WISSLearnCards extends Application
{
	public static void main (String[] args)
	{
		launch(args);
	}

	@Override
	public void start (Stage primaryStage) throws Exception
	{
		Constants.setMinHeight(primaryStage.getMaxHeight());
		Constants.setMinWidth(primaryStage.getMaxWidth());
		new MainController(primaryStage);
	}

}
