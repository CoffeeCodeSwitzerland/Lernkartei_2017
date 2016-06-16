package views;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import mvc.fx.FXController;
import mvc.fx.FXView;

public class UserView extends FXView
{

	public UserView(String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}

	Button back = new Button("Zurück");
	
	@Override
	public Parent constructContainer()
	{
		BorderPane bp = new BorderPane();
		// TODO Auto-generated method stub
		
		Label lblVoruebergehend = new Label();
		lblVoruebergehend.setText("HIER WIRD NOCH DER USERVIEW ERSTELLT");
		
		bp.setCenter(lblVoruebergehend);
		bp.setBottom(back);
		
		back.setOnAction(e -> getFXController().showMainView());
		
		return bp;
	}

	@Override
	public void refreshView()
	{
		// TODO Auto-generated method stub
		
	}

}
