package mvc;

import java.net.URL;

import debug.Debugger;
import debug.Logger;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Diese Klasse ist des Basis Codegerüst für die Umsetzung der GUI-View's in diesem MVC Konzept.
 * View's müssen sich beim Modell registrieren, wenn sie bei Änderungen im Modell automatisch ge-refresht werden wollen.
 * Der Constructor dieser Klasse muss immer zuerst aufgerufen werden 
 * 
 * @author hugo-lucca
 */
public abstract class FXView extends View
{
	private Stage window;
	private Scene scene;
	private static String stylePath	= "style.css";
	
	public FXView (String newName, Controller newController) {
		super(newName, newController);
		window = null;
		scene  = null;
		if (newController != null) {
			if (newController.getClass().toString().endsWith("MainController")) {
				window = FXSettings.getPrimaryStage();
			}
		}
	}
	
	public void show()
	{
		Logger.log("Get Window....");
		Stage st = this.getWindow();
		if (st != null) {
			if (scene != null) {
				Logger.log("Set scene....");
				st.setScene(scene);
			} else {
				Debugger.out("show("+getName()+") has no scene!");
			}
			Logger.log("stage show....");
			st.show();
		} else {
			Debugger.out("show("+getName()+") has no window!");
		}
		this.refreshView();
	}
	
	public void setupScene(Scene newScene) {
		this.scene = newScene;
		
		URL url = this.getClass().getResource(stylePath);
		if (url != null) {
			String urlstr = url.toExternalForm();
			if (scene != null && urlstr != null) {
			   scene.getStylesheets().add(getClass().getResource(stylePath).toExternalForm());
			} else {
			   Debugger.out("view("+getName()+").setScene no css-url found for "+stylePath);
			}
		} else {
		   Debugger.out("view("+getName()+").setScene: no css ressource found for "+stylePath);
		}
	}

	public Stage getWindow() {
		if (window == null) window = new Stage();
		return window;
	}
}
