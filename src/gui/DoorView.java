package gui;

import java.util.ArrayList;

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
 * @author miro-albrecht & hugo-lucca
 *
 */
public class DoorView extends View
{
	public DoorView (String setName, Stage primaryStage, MainController controller)
	{
		super(setName, primaryStage, controller);

		// Buttons
		AppButton zurueckButton = new AppButton("zurück");
		AppButton neueTuer = new AppButton("Neue Tür");
		AppButton loescheTuer = new AppButton("Lösche Tür");
		AppButton weitereTueren = new AppButton("weitere Türen");

		ArrayList<String> doorNames = getController().getMyModel("door").getData("doors");

		ArrayList<AppButton> doors = new ArrayList<>();

		if (doorNames != null)
		{
			for (String s : doorNames)
			{
				doors.add(new AppButton(s));
			}
		}

		// Layout für Controls
		HBox hBox = new HBox(20);
		hBox.setAlignment(Pos.CENTER);

		hBox.getChildren().addAll(zurueckButton, neueTuer, loescheTuer, weitereTueren);

		HBox doorLayout = new HBox(20);
		doorLayout.setAlignment(Pos.CENTER);
		doorLayout.getChildren().addAll(doors);

		// Laayout für die Scene
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(15));

		borderPane.setCenter(doorLayout);
		borderPane.setBottom(hBox);

		// Behaviour
		zurueckButton.setOnAction(e -> getController().showMain());
		neueTuer.setOnAction(e -> {
			String doorName = Alert.simpleString("Neue Tür", "Wie soll die neue Tür heissen?");
			if (doorName != null)
			{
				getController().getMyModel("door").doAction("new", doorName);
				refreshView();
			}
		});
		loescheTuer.setOnAction(e -> Alert.simpleInfoBox("Info", "Noch nicht implementiert"));
		weitereTueren.setDisable(true);

		this.setScene(new Scene(borderPane, Constants.OPTIMAL_WIDTH, Constants.OPTIMAL_HEIGHT));
	}

	@Override
	public void refreshView ()
	{
		refresh();
	}

}
