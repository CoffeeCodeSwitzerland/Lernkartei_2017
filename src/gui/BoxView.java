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
 * @author nina-egger
 *
 */
public class BoxView extends View
{
	HBox boxLayout;
	
	public BoxView (String setName, Stage primaryStage, MainController controller)
	{
		super(setName, primaryStage, controller);

		// Buttons
		AppButton zurueckButton = new AppButton("zurück");
		AppButton newBox = new AppButton("Neuer Kasten");
		AppButton bearbeitenKasten = new AppButton("Bearbeiten");
		AppButton weitereKasten = new AppButton("weitere Kasten");


		// Layout für Controls
		HBox hBox = new HBox(20);
		hBox.setAlignment(Pos.CENTER);

		hBox.getChildren().addAll(zurueckButton, newBox, bearbeitenKasten, weitereKasten);

		boxLayout = new HBox(20);
		boxLayout.setAlignment(Pos.CENTER);

		// Laayout für die Scene
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(15));

		borderPane.setCenter(boxLayout);
		borderPane.setBottom(hBox);

		zurueckButton.setOnAction(e -> getController().show("doorview"));
		newBox.setOnAction(e -> 
		{
			final String boxName = Alert.simpleString("Neue Box", "Wie soll die neue Box heissen?");
			if (setName != null || !boxName.equals(""))
			{
				System.out.println(getController().getMyModel("set").doAction("new", getData() + application.Constants.SEPARATOR + boxName));
			}
		});
		weitereKasten.setOnAction(e -> getController().show("karteiview"));
		weitereKasten.setDisable(true);

		this.setupScene(new Scene(borderPane, Constants.OPTIMAL_WIDTH, Constants.OPTIMAL_HEIGHT));
	}

	@Override
	public void refreshView ()
	{
		String data = getData();
		System.out.println(data);
		if (data != null)
		{
			ArrayList<String> setData = getController().getMyModel("set").getData(data);
			ArrayList<AppButton> sets = new ArrayList<AppButton>();
			
			for (String s : setData)
			{
				AppButton a = new AppButton(s);
				a.setOnAction(e -> {
					System.out.println("Clicked on Set");
				});
				sets.add(a);
			}
			
			boxLayout.getChildren().addAll(sets);
		}
	}
}
