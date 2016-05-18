package mvc.fx;

import java.net.URL;

import debug.Debugger;
import debug.Logger;
import debug.Supervisor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import mvc.View;
/**
 * Abstract GUI-Toolkit independent FX-View
 * ========================================
 * - extends the controller features
 * 
 * @author  hugo-lucca
 * @version 2016
 * License: LGPL
 */
/**
 * Diese Klasse ist des Basis Codegerüst für die Umsetzung der GUI-View's in diesem MVC Konzept.
 * View's müssen sich beim Modell registrieren, wenn sie bei Änderungen im Modell automatisch 
 * ge-refresht werden wollen.
 */
public abstract class FXView extends View
{
	private Scene scene;
	private final BorderPane mainLayout = new BorderPane();
	private FXController myFXController;
	
	public void construct () {
		// call this to construct the view
		Parent p = constructContainer();
		if (p==null) {
			p = getMainLayout();
		}
		p.setId(this.getName());
		setupScene(p);
	}

	public FXView(String newName, FXController newController) {
		// this constructor is the same for all view's on same stage
		super(newName);
		myFXController = newController;
		scene = null;
	}

	public FXController getFXController() {
		if (myFXController != null) return myFXController;
		Supervisor.warnAndDebug(this, "FXView.getFXController(): no FXController defined for this stage!");
		return null;
	}
	
	public FXController getController() {
		
		return getFXController();
	}
	
	public Stage getWindow () {
		return this.getFXController().getMyFXStage().getStage();
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
		
		Double width = getFXController().getMyFXStage().getOPTIMAL_WIDTH();
		Double height = getFXController().getMyFXStage().getOPTIMAL_HEIGHT();
		this.scene = new Scene(p, width, height);
		// TODO: add Color settings to scene
		
		String stylePath = getFXController().getMyFXStage().getStylePath();		
		URL url = this.getClass().getResource(stylePath);
		if (url != null) {
			String urlstr = url.toExternalForm();
			if (scene != null && urlstr != null) {
				scene.getStylesheets().add(getClass().getResource(stylePath).toExternalForm());
			} else {
			   Debugger.out("FXView("+getName()+").setUpScene() no css-url found for "+stylePath);
			}
		} else {
		   Debugger.out("FXView("+getName()+").setUpScene(): no css ressource found for "+stylePath);
		}
	}

	public BorderPane getMainLayout() {
		return mainLayout;
	}
}
