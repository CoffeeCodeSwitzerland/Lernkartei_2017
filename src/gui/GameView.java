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
 * 
 * @author miro-albrecht & hugo-lucca
 *
 */
public class GameView extends View
{
	/**
	 * Zweck dieses BS ist eine Art Warte- und Info-BS (zum Bsp. Anzahl Punkte) zum Gamestart, 
	 * mann kann dann alternativ weiter lernen.
	 * TODO: das game darf die LK nicht anhalten! 
	 */
	public GameView (String setName, Stage window, MainController controller)
	{
		super (setName, window, controller);

		// CSS formatierte Meldung auf BS bringen (mit div. Schrifteffekten)
		Text t = new Text("Starting game (please wait)...");
		t.setId("fancytext");

		// Button für Zurück zum Hauptmenue:
		AppButton b = new AppButton("Zurück...");
		b.setOnAction(e -> getController().showMain());

		// Erstellt VBox Layout für beide obige Elemente:
		VBox tempVBox = new VBox();
		tempVBox.setPadding(new Insets(10));
		tempVBox.setSpacing(10);
		tempVBox.setAlignment(Pos.CENTER);
		tempVBox.getChildren().addAll(t, b);
		
		// VBox in neuem Borderpane einfügen, zwingend wenn Hintergrund neu sein soll
		// CSS liefert neue Darstellung:
		BorderPane bp = new BorderPane();
		bp.setCenter(tempVBox);
		bp.setId("gamebg");
		
		// BP in Scene einfügen:
		this.setScene(new Scene(bp, Constants.OPTIMAL_WIDTH, Constants.OPTIMAL_HEIGHT));

	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		getController().getMyModel("game").doAction("start");
		//refresh();
	}
}
