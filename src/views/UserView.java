package views;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import mvc.fx.FXController;
import mvc.fx.FXView;

public class UserView extends FXView
{

	public UserView(FXController newController)
	{
		super(newController);
	}

	
	
	@Override
	public Parent constructContainer()
	{
		BorderPane p = new BorderPane();
		// TODO Auto-generated method stub
		
		p.getChildren().addAll(new Label("TEST"));
		
		return null;
	}

	@Override
	public void refreshView()
	{
		// TODO Auto-generated method stub
		
	}

}
