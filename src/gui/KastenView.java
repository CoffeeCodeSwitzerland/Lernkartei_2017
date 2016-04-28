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
public class KastenView extends View
{
	HBox kastenLayout;
	boolean delMode = false;
	
	public KastenView (String setName, Stage primaryStage, MainController controller)
	{
		super(setName, primaryStage, controller);

		// Buttons
		AppButton zurueckButton = new AppButton("zurück");
		AppButton neuerKasten = new AppButton("Neuer Kasten");
		AppButton bearbeitenKasten = new AppButton("Bearbeiten");
		AppButton weitereKasten = new AppButton("weitere Kasten");

//		ArrayList<String> doorNames = getController().getMyModel("door").getData("doors");
//
//		ArrayList<AppButton> doors = new ArrayList<AppButton>();
//
//		if (doorNames != null)
//		{
//			for (String s : doorNames)
//			{
//				doors.add(new AppButton(s));
//			}
//		}

		// Layout für Controls
		HBox hBox = new HBox(20);
		hBox.setAlignment(Pos.CENTER);

		hBox.getChildren().addAll(zurueckButton, neuerKasten, bearbeitenKasten, weitereKasten);

		kastenLayout = new HBox(20);
		kastenLayout.setAlignment(Pos.CENTER);
//		doorLayout.getChildren().addAll(doors);

		// Laayout für die Scene
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(15));

		borderPane.setCenter(kastenLayout);
		borderPane.setBottom(hBox);

//		// Behaviour
//		zurueckButton.setOnAction(e -> getController().showMain());
//		neueTuer.setOnAction(e -> {
//			String doorName = Alert.simpleString("Neue Tür", "Wie soll die neue Tür heissen?");
//			if (doorName != null && !doorName.equals(""))
//			{
//				getController().getMyModel("door").doAction("new", doorName);
//				refreshView();
//			}
//		});
//		bearbeitenButton.setOnAction(e -> {
//			delMode = !delMode;
//			bearbeitenButton.setText(delMode ? "Fertig" : "Bearbeiten");
//			for (AppButton a : doors)
//			{
//				a.setId(delMode ? "delMode" : "");
//			}
//		});
//		
		zurueckButton.setOnAction(e -> getController().show("DoorView"));
		weitereKasten.setDisable(true);

		this.setupScene(new Scene(borderPane, Constants.OPTIMAL_WIDTH, Constants.OPTIMAL_HEIGHT));
	}

	@Override
	public void refreshView ()
	{
//		doorLayout.getChildren().clear();
//		ArrayList<String> doorNames = getController().getMyModel("door").getData("doors");
//		ArrayList<AppButton> doors = new ArrayList<>();
//		if (doorNames != null)
//		{
//			for (String s : doorNames)
//			{
//				doors.add(new AppButton(s));
//			}
//		}
//		doorLayout.getChildren().addAll(doors);
//		refresh();
	}
}
