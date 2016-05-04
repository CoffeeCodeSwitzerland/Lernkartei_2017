package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import mvc.Controller;
import mvc.FXView;
/**
 * Gamestartfenster
 * 
 * @author nina-egger
 *
 */
public class KarteiView extends FXView
{
	HBox karteiLayout;
	boolean delMode = false;
	
	public KarteiView (String setName, Controller controller)
	{
		super(setName, controller);

		// Buttons
		AppButton zurueckButton = new AppButton("zurück");
		AppButton ueben = new AppButton("Üben");
		AppButton weitereKasten = new AppButton("Alle Karten");

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

		hBox.getChildren().addAll(zurueckButton, ueben, weitereKasten);

		karteiLayout = new HBox(20);
		karteiLayout.setAlignment(Pos.CENTER);
//		doorLayout.getChildren().addAll(doors);

		// Laayout für die Scene
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(15));

		borderPane.setCenter(karteiLayout);
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
		zurueckButton.setOnAction(e -> getController().getView("kastenview").show());
		weitereKasten.setDisable(true);

		this.setupScene(new Scene(borderPane, getController().getFXSettings().OPTIMAL_WIDTH, getController().getFXSettings().OPTIMAL_HEIGHT));
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
