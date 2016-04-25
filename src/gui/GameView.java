package gui;


import application.MainController;
import debug.Debugger;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
		BorderPane bp = new BorderPane();
		Text t = new Text("Starting game (please wait)...");
		t.setId("fancytext");
		Button b = new Button("Zurück...");
		b.setOnAction(e -> controller.show("mainview"));

		// Erstellt Layout
		VBox tempVBox = new VBox();
		tempVBox.setPadding(new Insets(10));
		tempVBox.setSpacing(10);
		tempVBox.setAlignment(Pos.CENTER);
		tempVBox.getChildren().addAll(t, b);
		bp.setCenter(tempVBox);
		bp.setId("gamebg");
		this.setScene(new Scene(bp, 800, 450));
	}

	public void show () {

		// TODO funktioniert noch nicht richtig...
		// - keine Umschaltung auf dieses Game BS, wenn Spiel aufgerufen wird
		// - und auch kein Zurück zum Main-menü möglich (Swing ist schuld)
		//
		super.show();
		scrollyv8.ScrollyV8.main(null);
		
	}
}
