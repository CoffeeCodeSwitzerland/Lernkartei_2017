package views;

import java.util.ArrayList;

import globals.Globals;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXViewModel;
import views.components.AppButton;

public class RenameView extends FXViewModel
{

	public RenameView (String setName, FXController newController)
	{
		super(setName, newController);
		construct();
	}

	VBox elements = new VBox(20);
	String oldValue = "";
	
	@Override
	public Parent constructContainer ()
	{
		AppButton backBtn = new AppButton("Zurück");
		backBtn.setOnAction(e -> getFXController().showLastView());
		
		BorderPane mainLayout = new BorderPane(elements);
		mainLayout.setBottom(backBtn);
		
		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
		String[] info = getData().split(Globals.SEPARATOR);
		ArrayList<String> list = getFXController().getModel(info[0]).getDataList(info[1]);
		
		elements.getChildren().clear();
		
		for (String s : list)
		{
			TextField field = new TextField(s);
			field.focusedProperty().addListener(e ->
			{
				if (field.isFocused())
				{
					oldValue = field.getText();
				}
				else
				{
					getFXController().getModel(info[0]).doAction("update", oldValue + Globals.SEPARATOR + field.getText());
				}
			});
			
			elements.getChildren().add(field);
		}
	}

}
