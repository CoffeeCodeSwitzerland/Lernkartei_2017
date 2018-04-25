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
	 * @return 
	 * @throws Exception 
	 */
	private void init (FXController c, String btnText, String backViewName) throws Exception
	{
		setId("backbtn");
		myController = c;
		if (myController != null) {
			if (backViewName != null) {
				if (myController.showAndTrackView(backViewName)) {
					setOnAction(e -> myController.showAndTrackView(backViewName));
				} else {
					myController.showAndTrackView(backViewName);
				}
			} else {
				myController.showAndTrackView(backViewName);
			}
		} else {
			throw new Exception ("was trying to set a button without FCController!");
		}
	}

	/**
	 * To go back to last shown view you need to know the main-controller
	 */	
	public BackButton (FXController c, String btnText, String backViewName)
	{
		super(btnText);
		try {
			init (c, btnText, backViewName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * To go back to last shown view you need to know the main-controller
	 */	
	public BackButton (FXController c, String btnText)
	{
		this(c,btnText,null);
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
