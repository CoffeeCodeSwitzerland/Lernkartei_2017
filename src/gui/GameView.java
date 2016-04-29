package gui;


import application.Constants;
import application.MainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Gamestartfenster
 * ================
 * Zweck dieses BS ist eine Art Warte- und Info-BS (zum Bsp. Anzahl Punkte) zum Gamestart, 
 * mann kann dann alternativ weiter lernen.
 * TODO: das game darf die LK nicht anhalten! 
 * @author hugo-lucca
 *
 */
public class GameView extends View
{
	Text text;
	public GameView (String setName, Stage window, MainController controller)
	{
		super (setName, window, controller);

		text = new Text("Starting game...");
		text.setId("fancytext"); // CSS formatierte Meldung auf BS bringen (mit div. Schrifteffekten)

		// Button für Zurück zum Hauptmenue:
		AppButton btn = new AppButton("Zurück...");
		btn.setOnAction(e -> getController().showMain());

		// Erstellt VBox Layout für beide obige Elemente:
		VBox tempVBox = new VBox();
		tempVBox.setPadding(new Insets(10));
		tempVBox.setSpacing(10);
		tempVBox.setAlignment(Pos.CENTER);
		tempVBox.getChildren().addAll(text, btn);
		
		// VBox in neuem Borderpane einfügen, zwingend wenn Hintergrund neu sein soll
		// CSS liefert neue Darstellung:
		BorderPane bp = new BorderPane();
		bp.setCenter(tempVBox);
		bp.setId("gamebg");
		
		// BP in Scene einfügen:
		this.setupScene(new Scene(bp, Constants.OPTIMAL_WIDTH, Constants.OPTIMAL_HEIGHT));

	}

	@Override
	public void refreshView() {
		text.setText("Starting game (please wait)...");
		getController().getMyModel("game").doAction("start");
	}
}
