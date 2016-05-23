package views.components;

import mvc.fx.FXController;

/**
 * Back-Button's are always the same and use the same event handler
 * 
 * @author hugo-lucca
 */
public class HomeButton extends AppButton {
	
	FXController myController;

	/**
	 * To go back to last shown view you need to know the main-controller 
	 */
	public HomeButton (FXController c, String btnText) {
		super(btnText);
		setId("backbtn");
		myController = c;
		setOnAction(e ->
		{
			myController.showMainView();
		});
	}

	/**
	 * To go back to last shown view you need to know the main-controller.
	 * This is using a default button text. 
	 */
	public HomeButton (FXController c) {
		this(c, "_Hauptmenü");
	}
}
