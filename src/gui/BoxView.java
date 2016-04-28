package gui;

import application.Constants;
import application.MainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


/**
 * Gamestartfenster
 * 
 * @author nina-egger
 *
 */
public class BoxView extends View
{
	HBox kastenLayout;
	boolean delMode = false;
	
	public BoxView (String setName, Stage primaryStage, MainController controller)
	{
		super(setName, primaryStage, controller);

		// Buttons
		AppButton zurueckButton = new AppButton("zurück");
		AppButton neuerKasten = new AppButton("Neuer Kasten");
		AppButton bearbeitenKasten = new AppButton("Bearbeiten");
		AppButton weitereKasten = new AppButton("weitere Kasten");


		// Layout für Controls
		HBox hBox = new HBox(20);
		hBox.setAlignment(Pos.CENTER);

		hBox.getChildren().addAll(zurueckButton, neuerKasten, bearbeitenKasten, weitereKasten);

		kastenLayout = new HBox(20);
		kastenLayout.setAlignment(Pos.CENTER);

		// Laayout für die Scene
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(15));

		borderPane.setCenter(kastenLayout);
		borderPane.setBottom(hBox);

		zurueckButton.setOnAction(e -> getController().show("doorview"));
		weitereKasten.setOnAction(e -> getController().show("karteiview"));
		weitereKasten.setDisable(false);

		this.setupScene(new Scene(borderPane, Constants.OPTIMAL_WIDTH, Constants.OPTIMAL_HEIGHT));
	}

	@Override
	public void refreshView ()
	{

	}
}
