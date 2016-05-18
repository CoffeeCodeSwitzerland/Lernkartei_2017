package views;

import debug.Debugger;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mvc.Controller;
import mvc.fx.FXViewModel;

/**
 * 
 * @author miro albrecht
 *
 */
public class PreLearnView extends FXViewModel
{

	public PreLearnView (String setName, Controller controller)
	{
		super(setName, controller);
		construct();
	}

	
	
	BorderPane mainLayout = new BorderPane();
	
	@Override
	public Parent constructContainer ()
	{
		getController().getModel("learn").getDataList(null).clear();
		getController().getModel("learn").setString(null);
		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
		if (getData() == null || getData().equals(""))
		{
			Debugger.out("PreLearnView has no Stack Data");
			return;
		}
		
		Label stackName = new Label(getData());
		stackName.setId("bold");
		
		Label stackInfo = new Label("Anzahl Karten: " + getController().getModel("learn").getDataList(getData()).size());
		
		VBox layout = new VBox(30);
		layout.getChildren().addAll(stackName, stackInfo);
		
		mainLayout.setCenter(layout);
	}

}
