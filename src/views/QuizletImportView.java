package views;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import mvc.Controller;
import mvc.FXView;

/**
 * 
 * @author miro
 *
 */
public class QuizletImportView extends FXView
{

	public QuizletImportView (String newName, Controller newController)
	{
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}


	@Override
	public Parent constructContainer ()
	{
		Label l = new Label("Test");
		
		BorderPane mainLayout = new BorderPane(l);

		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
		
		
	}

}
