package views;

import java.util.ArrayList;

import globals.Globals;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mvc.ModelInterface.Command;
import mvc.fx.FXController;
import mvc.fx.FXViewModel;
import views.components.AppButton;
import views.components.ControlLayout;
import views.components.MainLayout;
import views.components.VerticalScroller;


/**
 * Flexible View, die das Umbenennen von Doors und Stacks erlaubt.
 * 
 * @author miro albrecht
 *
 */
public class RenameView_old extends FXViewModel
{

	VBox		elements	= new VBox(20);
	String		oldValue	= "";
	AppButton 	backBtn 	= new AppButton("Zurück");
	Label 		headLabel 	= new Label("Umbenennen");

	public RenameView_old (String setName, FXController newController)
	{
		super(newController);
		construct(setName);
	}

	@Override
	public Parent constructContainer ()
	{
		backBtn.setOnAction(e ->
		{
			setData(getMyModel().getString(null));
			getWindow().getScene().widthProperty().removeListener(event ->
			{
				elements.setMinWidth(getWindow().getScene().getWidth() - 80);
			});
			getFXController().showLastView();
		});

		elements.setPadding(new Insets(30));
		elements.setAlignment(Pos.TOP_CENTER);

		VerticalScroller scroLay = new VerticalScroller(elements, 1);

		headLabel.setId("bold");
/*
		backBtn.setOnAction(e ->
		{
			setData();
		});
		*/
		BorderPane headLayout = new BorderPane(headLabel);
		headLayout.setPadding(new Insets(0, 0, 25, 0));
		
		MainLayout maLay = new MainLayout(scroLay, headLayout, new ControlLayout(backBtn));

		return maLay;
	}

	@Override
	public void refreshView ()
	{
		// info[0] = stack OR door (model)
		// info[1] = doors OR stacks of which door (getDataList command)
//		getFXController().getModel("door").doAction(Command.UPDATE, "name" ,  getFXController().getViewData("rename"); );

		String[] info = getData().split(Globals.SEPARATOR);
		ArrayList<String> list = getFXController().getModel(info[0]).getDataList(info[1]);

		headLabel.setText(info[1].equals("doors") ? "Fächer umbenennen" : "Stapel im Fach " + info[1] + " umbenennen");
		
		elements.getChildren().clear();

		for (String s : list)
		{
			TextField field = new TextField(s);
			field.focusedProperty().addListener(event ->
			{
				
				if (field.isFocused())
				{
					oldValue = field.getText();
				}
				else
				{
					int canCreate = getFXController().getModel(info[0]).doAction(Command.CAN_CREATE, field.getText());
					if (canCreate == 1)
					{
						getFXController().getModel(info[0]).doAction(Command.UPDATE, oldValue, field.getText(), info[1]);
					}
					else if (canCreate == -1 && !field.getText().equals(oldValue))
					{
						field.setText(oldValue);
					}
				}
			});

			elements.getChildren().add(field);
		}
		
		backBtn.requestFocus();
	}

}
