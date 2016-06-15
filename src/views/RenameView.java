package views;

import java.util.ArrayList;

import globals.Globals;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXViewModel;
import views.components.AppButton;


/**
 * Flexible View, die das Umbenennen von Doors und Stacks erlaubt.
 * 
 * @author miro albrecht
 *
 */
public class RenameView extends FXViewModel
{

	VBox		elements	= new VBox(20);
	String		oldValue	= "";

	public RenameView (String setName, FXController newController)
	{
		super(newController);
		construct(setName);
	}

	@Override
	public Parent constructContainer ()
	{
		AppButton backBtn = new AppButton("Zurück");
		backBtn.setOnAction(e ->
		{
			getWindow().getScene().widthProperty().removeListener(event ->
			{
				elements.setMinWidth(getWindow().getScene().getWidth() - 80);
			});
			getFXController().showLastView();
		});

		HBox controlsLayout = new HBox();
		controlsLayout.setPadding(new Insets(20, 0, 0, 0));
		controlsLayout.setAlignment(Pos.CENTER);
		controlsLayout.getChildren().add(backBtn);

		elements.setPadding(new Insets(30));
		elements.setAlignment(Pos.TOP_CENTER);

		ScrollPane scrollLayout = new ScrollPane(elements);
		scrollLayout.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollLayout.setPadding(new Insets(10));
		scrollLayout.setMaxWidth(400);
		scrollLayout.setFitToWidth(true);

		BorderPane mainLayout = new BorderPane(scrollLayout);
		mainLayout.setBottom(controlsLayout);
		mainLayout.setPadding(new Insets(30));

		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
		//elements.setMinWidth(getWindow().getScene().getWidth() - 80);

		getWindow().getScene().widthProperty().addListener(event ->
		{
			//elements.setMinWidth(getWindow().getScene().getWidth() - 80);
		});

		// info[0] = stack OR door (model)
		// info[1] = doors OR stacks of which door (getDataList command)

		String[] info = getData().split(Globals.SEPARATOR);
		ArrayList<String> list = getFXController().getModel(info[0]).getDataList(info[1]);

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
					getFXController().getModel(info[0]).doAction("update", oldValue + Globals.SEPARATOR + field.getText());
				}
			});

			elements.getChildren().add(field);
		}
	}

}
