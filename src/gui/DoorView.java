package gui;

import application.MainController;
import application.WISSLearnCards;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Gamestartfenster
 * 
 * @author miro-albrecht & hugo-lucca
 *
 */
public class DoorView extends View
{
	Stage	window;
	Scene	scene;

	public DoorView (String setName, Stage primaryStage, MainController controller)
	{
		super (setName, primaryStage);

		//Buttons
		AppButton zurueckButton = new AppButton("zurück");
		AppButton neueTuer 		= new AppButton("Neue Tür");
		AppButton loescheTuer 	= new AppButton("Lösche Tür");
		AppButton weitereTueren = new AppButton("weitere Türen");

		// Box und Pane erstellen
		HBox hBox = new HBox(20);
		BorderPane borderPane = new BorderPane();
		hBox.setAlignment(Pos.CENTER);
		//Alle Buttons in die HBox
		hBox.getChildren().addAll(zurueckButton, neueTuer, loescheTuer, weitereTueren);
		
		//Die HBox in die Bottom BorderPane
		borderPane.setBottom(hBox);
		
		borderPane.setPadding(new Insets(15));
		zurueckButton.setOnAction(e -> controller.showMain());
		
		this.setScene(new Scene(borderPane, WISSLearnCards.OPTIMAL_WIDTH, WISSLearnCards.OPTIMAL_HEIGHT));
	}

}


