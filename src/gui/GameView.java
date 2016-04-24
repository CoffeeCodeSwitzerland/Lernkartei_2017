package gui;


import application.MainController;
import debug.Debugger;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * Gamestartfenster
 * 
 * @author miro-albrecht
 *
 */
@SuppressWarnings("restriction")
public class GameView extends View
{
	MainController myController;
	
	public GameView (String setName, Stage window, MainController controller)
	{
		super (setName, window);
		myController = controller;
		Button b = new Button("Starting game (please wait)...");
		BorderPane bp = new BorderPane();
		bp.setCenter(b);
		this.setScene(new Scene(bp, 800, 450));
	}

	public void show () {
		super.show();
//		Rectangle rect = new Rectangle (100, 40, 100, 100);
//	     rect.setArcHeight(50);
//	     rect.setArcWidth(50);
//	     rect.setFill(Color.VIOLET);
//	 
//	     RotateTransition rt = new RotateTransition(Duration.millis(3000), rect);
//	     rt.setByAngle(180);
//	     rt.setCycleCount((int) 4f);
//	     rt.setAutoReverse(true);
//		 new SequentialTransition (
//		         new PauseTransition(Duration.millis(2000)), null // wait a second
//		 );
		scrollyv8.ScrollyV8.main(null);
		
		myController.show("mainview");
	}
}
