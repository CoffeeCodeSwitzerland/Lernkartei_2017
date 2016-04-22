package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.MainController;

public class DoorView
{
	Stage	window;
	Scene	scene;

	public DoorView (Stage primaryStage, MainController controller)
	{
		window = primaryStage;

		Button zurueckButton;
		
		//Fenster
		//window = primaryStage;
		//window.setTitle("Türen/Themen");
		
		//Buttons
		zurueckButton = new Button("zurück");
		
		//Layouts
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(10));
		layout.getChildren().addAll(zurueckButton);
		
		zurueckButton.setOnAction(e -> controller.showMain());
		
		scene = new Scene(layout, 800, 450);
	}

	public void show ()
	{
		window.setScene(scene);
	}
}


