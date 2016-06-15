package views.components;

import mvc.fx.FXController;


/**
 * Back-Button's are always the same and use the same event handler
 * 
 * @author hugo-lucca
 */
public class BackButton extends AppButton
{

	FXController myController;

	/**
	 * To go back to last shown view you need to know the main-controller
	 */
	public BackButton (FXController c, String btnText)
	{
		super(btnText);
		setId("backbtn");
		myController = c;
		setOnAction(e ->
		{
			myController.showLastView();
		});
	}

	/**
	 * To go back to last shown view you need to know the main-controller. This
	 * is using a default button text.
	 */
	public BackButton (FXController c)
	{
		this(c, "Zurück");
	}
}
