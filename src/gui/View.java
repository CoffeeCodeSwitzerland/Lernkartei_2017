package gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class View
{
	Stage window;
	Scene scene;
	
	String name;
	
	public void show()
	{
		window.setScene(scene);
	}
	
	public String getName()
	{
		return name;
	}
}
