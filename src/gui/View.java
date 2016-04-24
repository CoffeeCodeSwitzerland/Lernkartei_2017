package gui;

import java.net.URL;

import debug.Debugger;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class View
{
	private Stage window;
	private Scene scene;
	private String name;
	private static String stylePath	= "style.css";

	public View (String setName, Stage primary) {
		window = primary;
		name   = setName;
		scene  = null;
	}
	
	public void show()
	{
		if (scene != null) {
			getWindow().setScene(scene);
		}
		getWindow().show();
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setScene(Scene newScene) {
		this.scene = newScene;
		
		URL url = this.getClass().getResource(stylePath);
		if (url != null) {
			String urlstr = url.toExternalForm();
			if (scene != null && urlstr != null) {
			   scene.getStylesheets().add(getClass().getResource(stylePath).toExternalForm());
			} else {
			   Debugger.out("view("+name+").setScene no css-url found for "+stylePath);
			}
		} else {
		   Debugger.out("view("+name+").setScene: no css ressource found for "+stylePath);
		}
	}

	public Stage getWindow() {
		if (window == null) window = new Stage();
		return window;
	}
}
