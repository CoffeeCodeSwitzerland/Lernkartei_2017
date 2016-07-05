package views.components;

import debug.Logger;
import javafx.stage.Stage;
import javafx.stage.Window;
import models.GameModel;
import mvc.fx.FXController;

/**
 * Close-Button's are always the same and use the same event handler
 * 
 * @author hugo-lucca
 */
public class CloseButton extends AppButton {
	
	FXController myController;
	
	public CloseButton (String btnText) {
		super(btnText);
		setId("closebtn");
		setOnAction(e ->
		{
			debug.Debugger.out("closing button");
			Window window = this.getScene().getWindow();   
			if (myController != null) {
				GameModel gm = (GameModel) myController.getModel("game");
				if (gm != null) gm.dispose();
			}
	        if (window instanceof Stage){
	            ((Stage) window).close();
	        } else {
				Logger.out("no stage found for closing button");
	        }
		});
	}

	/**
	 * to instantiate with default button text and/or a controller
	 */
	public CloseButton () {
		this("B_eenden");
	}

	/**
	 * To close the complete application (including swing threads) this button
	 * needs to know the main-controller, thus this instantiate the button
	 * using a specific button text.
	 * Do'nt use this if you do not want to close the main application! 
	 */
	public CloseButton (FXController c, String btnText) {
		this(btnText);
		myController = c;
	}

	/**
	 * To close the complete application (including swing threads) this button
	 * needs to know the main-controller, thus this instantiate the button
	 * using the default button text.
	 * Do'nt use this if you do not want to close the main application! 
	 */
	public CloseButton (FXController c) {
		this();
		myController = c;
	}
}
