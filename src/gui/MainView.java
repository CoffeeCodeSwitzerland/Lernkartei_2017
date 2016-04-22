package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.MainController;

public class MainView
{
	Stage window;
	Scene s;
	
	public MainView (Stage primaryStage, MainController controller)
	{
		window = primaryStage;
		
		Button b = new Button("Options");
		VBox layout = new VBox();
		layout.getChildren().add(b);
		
		s = new Scene(layout, 800, 450);
		
		b.setOnAction(e -> {
			controller.setOptionsView();
		});
		
		setMainScene();
		window.show();
	}
	
	public void setMainScene()
	{
		window.setScene(s);
	}
}
