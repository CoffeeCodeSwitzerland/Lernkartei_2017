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
		super(newName, newController);
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
