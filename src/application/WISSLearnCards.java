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
	public static double MIN_WIDTH  = 400;
	public static double MIN_HEIGHT = 300;
	public static double OPTIMAL_WIDTH  = 800;
	public static double OPTIMAL_HEIGHT = 450;

	public static void main (String[] args)
	{
		launch(args);
	}

	@Override
	public void start (Stage primaryStage) throws Exception
	{
		WISSLearnCards.MIN_HEIGHT = primaryStage.getMaxHeight();
		WISSLearnCards.MIN_WIDTH  = primaryStage.getMaxWidth();
		new MainController(primaryStage);
	}

}
