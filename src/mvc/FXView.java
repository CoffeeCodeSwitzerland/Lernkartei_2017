package mvc;

import java.net.URL;

import debug.Debugger;
import debug.Logger;
import javafx.scene.Parent;
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
	private Scene scene;
	private static String stylePath	= "style.css";
	
	public FXView (String newName, Controller newController) {
		super(newName, newController);
		scene  = null;
	}
	
	public Stage getWindow () {
		return getController().getTheFXSettings().getPrimaryStage();
	}
	
	public void show()
	{
		Logger.log("Get Window....");
		Stage st = getWindow();
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
	
	public void setupScene(Parent p) {
		
		Double width = getController().getTheFXSettings().getOPTIMAL_WIDTH();
		Double height = getController().getTheFXSettings().getOPTIMAL_HEIGHT();
		this.scene = new Scene(p, width, height);
		// TODO: add Color settings to scene
		
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
}
