package views;

import javafx.scene.layout.BorderPane;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.BackButton;


/**
 * Grundkonfiguration für jedes View
 * 
 * @author miro albrecht
 *
 */
public abstract class BasicFXView extends FXView
{
	public BasicFXView (String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}

	BorderPane bp = new BorderPane();
	BackButton btnBack;

}
