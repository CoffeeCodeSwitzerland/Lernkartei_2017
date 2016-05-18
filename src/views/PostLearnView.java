package views;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import mvc.fx.FXController;
import mvc.fx.FXViewModel;

/**
 * View um Lernergebnis und Gesamtforschritt nach einer 'Lernrunde' anzuzeigen.
 * 
 * @author miro albrecht
 *
 */
public class PostLearnView extends FXViewModel
{

	public PostLearnView (String setName, FXController newController) { super(setName, newController); }

	BorderPane mainLayout = new BorderPane();
	
	@Override
	public Parent constructContainer ()
	{
		mainLayout.setPadding(new Insets(50));
		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
		AppButton backBtn = new AppButton("Zurück");
		mainLayout.setCenter(backBtn);
	}

}
