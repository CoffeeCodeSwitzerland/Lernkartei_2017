package mvc;

import java.net.URL;

import debug.Debugger;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Diese Klasse ist des Basis Codegerüst für die Umsetzung der GUI-View's in diesem MVC Konzept.
 * View's müssen sich beim Modell registrieren, wenn sie bei Änderungen im Modell automatisch ge-refresht werden wollen.
 * Der Constructor dieser Klasse muss immer zuerst aufgerufen werden 
 * 
 * @author hugo-lucca
 */
public abstract class FXFrame extends View
{
	private Stage window;
	private Scene scene;
	private static String stylePath	= "style.css";
	
	public FXFrame (String newName, Controller controller) {
		super(newName, controller);
		if (controller != null) {
			if (controller.getClass().toString().endsWith("MainController")) {
				window = FXSettings.getPrimaryStage();
			} else {
				Debugger.out("FXView-Constructor: Lost of MainController ("+controller.getClass().toString()+")!");
			}
		} else window = new Stage();
		scene  = null;
	}
	
	public void show()
	{
		Stage st = this.getWindow();
		if (st != null) {
			if (scene != null) {
				st.setScene(scene);
			} else {
				Debugger.out("show("+getName()+") has no scene!");
			}
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
