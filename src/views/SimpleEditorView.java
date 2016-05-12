package views;

import java.util.ArrayList;

import controls.Globals;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.Controller;
import mvc.FXViewModel;


public class SimpleEditorView extends FXViewModel
{
	// ArrayList<VBox> cards;

	public SimpleEditorView (String newName, Controller newController)
	{
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}

	VBox	editLayout	= new VBox(10);
	Label	headLbl;

	@Override
	public Parent constructContainer ()
	{
		headLbl = new Label("");
		headLbl.setId("bold");

		AppButton backBtn = new AppButton("Zurück");
		backBtn.setOnAction(e -> getController().getView("boxview").show());

		BorderPane headLayout = new BorderPane(headLbl);
		headLayout.setPadding(new Insets(25));

		editLayout.setPadding(new Insets(10));
		editLayout.setAlignment(Pos.TOP_CENTER);

		HBox controlLayout = new HBox(20);
		controlLayout.setAlignment(Pos.CENTER);
		controlLayout.getChildren().addAll(backBtn);

		BorderPane mainLayout = new BorderPane();
		mainLayout.setPadding(new Insets(15));
		mainLayout.setTop(headLayout);
		mainLayout.setCenter(editLayout);
		mainLayout.setBottom(controlLayout);

		getController().getModel("cards").registerView(this);
		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
		editLayout.getChildren().clear();

		String data = getData();

		if (data != null)
		{
			headLbl.setText(data + " - " + getController().getView("boxview").getData());

			ArrayList<String> cardStrings = getController().getModel("cards").getDataList(data);
			ArrayList<HBox> cards = new ArrayList<>();
			debug.Debugger.out("" + cardStrings.size());
			for (String s : cardStrings)
			{
				String[] cardSides = s.split(Globals.SEPARATOR);
				TextField front = new TextField(cardSides[1]);
				TextField back = new TextField(cardSides[2]);

				front.focusedProperty().addListener(e ->
				{
					if (!front.isFocused())
					{
						if (back.getText() != null && !back.getText().equals("") && front.getText() != null
								&& !front.getText().equals(""))
						{
							getController().getModel("cards").doAction("edit", cardSides[0] + Globals.SEPARATOR
									+ front.getText() + Globals.SEPARATOR + back.getText());
						}
					}
				});

				back.focusedProperty().addListener(e ->
				{
					if (!back.isFocused())
					{
						if (back.getText() != null && !back.getText().equals("") && front.getText() != null
								&& !front.getText().equals(""))
						{
							getController().getModel("cards").doAction("edit", cardSides[0] + Globals.SEPARATOR
									+ front.getText() + Globals.SEPARATOR + back.getText());
						}
					}
				});

				Button delete  = new Button("X");
				Button editBtn = new Button("\u270E"); // \u270d \u2055 \u2699 \u270E
				
				delete.setOnAction(e -> getController().getModel("cards").doAction("delete", cardSides[0]));
				editBtn.setOnAction(e ->
				{
					getController().getView("editorview").setData(front.getText() + Globals.SEPARATOR + back.getText());
					getController().getView("editorview").show();
				});
				HBox v = new HBox(8);
				v.setAlignment(Pos.CENTER);
				v.getChildren().addAll(front, back, delete, editBtn);
				cards.add(v);
			}

			TextField front = new TextField();
			TextField back = new TextField();
			Button editBtn = new Button("\u270E"); // \u270d \u2055 \u2699 \u270E
			
			editBtn.setOnAction(e ->
			{
				getController().getView("editorview").setData(front.getText() + Globals.SEPARATOR + back.getText());
				getController().getView("editorview").show();
			});
			Button addBtn = new Button("\u2713");
			addBtn.setMaxWidth(35);

			addBtn.setOnAction(e ->
			{
				if (back.getText() != null && !back.getText().equals("") && front.getText() != null
						&& !front.getText().equals(""))
				{
					getController().getModel("cards").doAction("new",
							front.getText() + Globals.SEPARATOR + back.getText() + Globals.SEPARATOR + data);
				}
			});

			HBox v = new HBox(8);

			v.setAlignment(Pos.CENTER);
			v.getChildren().addAll(front, back, addBtn, editBtn);
			cards.add(v);

			editLayout.getChildren().addAll(cards);
		}
	}
}
