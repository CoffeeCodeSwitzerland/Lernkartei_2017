package gui;

import application.Constants;
import application.MainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class StatisticsView extends View
{		
	HBox karteiLayout;

	public StatisticsView(String setName, Stage window, MainController controller)
	{
		super (setName, window, controller);
		
		
		// Buttons
		AppButton zurueck = new AppButton("zurück");
		AppButton meineKlasse = new AppButton("Meine Klasse");
		AppButton rangliste = new AppButton("Ranglisten");

		// Layout für Controls
		HBox hBox = new HBox(20);
		hBox.setAlignment(Pos.CENTER);

		hBox.getChildren().addAll(zurueck, meineKlasse, rangliste);

		karteiLayout = new HBox(20);
		karteiLayout.setAlignment(Pos.CENTER);

		// Laayout für die Scene
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(15));

		borderPane.setCenter(karteiLayout);
		borderPane.setBottom(hBox);


		this.setupScene(new Scene(borderPane, Constants.OPTIMAL_WIDTH, Constants.OPTIMAL_HEIGHT));
		zurueck.setOnAction(e -> controller.showMain());

	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}
}
