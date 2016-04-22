/* package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Türen extends Application{
	
	//Stage window;
	Scene doorScene;
	Button zurückButton;

	public static void main(String[] args) {
	launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
	
	//Fenster
	//window = primaryStage;
	//window.setTitle("Türen/Themen");
	
	//Buttons
	zurückButton = new Button("zurück");
	
	//Layouts
	VBox layout = new VBox(10);
	layout.setPadding(new Insets(10));
	layout.getChildren().addAll(zurückButton);
	
	//Erstelle Scene
	Scene doorScene = new Scene(layout, 800, 450);
	window.setScene(doorScene);
	
	//Zeige Fenster
	window.show();
		
	}
	
}
*/