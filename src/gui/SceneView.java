package gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneView
{
	Stage window;
	Scene scene;
	
	public void show()
	{
		window.setScene(scene);
	}
}
