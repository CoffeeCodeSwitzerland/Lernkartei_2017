package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.MainController;

public class OptionsView
{
	Stage window;
	Scene s;
	
	public OptionsView (Stage primaryStage, MainController controller)
	{
		window = primaryStage;
		
		Button b = new Button("Back");
		VBox layout = new VBox();
		layout.getChildren().add(b);
		
		s = new Scene(layout, 800, 450);
		
		b.setOnAction(e -> {
			controller.setMainView();
		});
	}
	
	public void setOptionsScene()
	{
		window.setScene(s);
	}
}
