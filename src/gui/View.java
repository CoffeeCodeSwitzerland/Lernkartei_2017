package gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class View
{
	Stage window;
	Scene scene;
	String name;

	public View (String setName, Stage primary) {
		window = primary;
		name   = setName;
		scene  = null;
	}
	
	public void show()
	{
		if (scene != null)
			window.setScene(scene);
	}
	
	public String getName()
	{
		return name;
	}
}
