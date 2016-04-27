package gui;

import java.net.URL;

import application.MainController;
import debug.Debugger;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class View implements ViewInterface
{
	private Stage window;
	private Scene scene;
	private String name;
	private MainController myController = null;
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
		this.refreshView();
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

	public MainController getMyController() {
		return myController;
	}

	public void setMyController(MainController myController) {
		this.myController = myController;
	}
}
