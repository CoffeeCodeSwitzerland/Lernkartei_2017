package views;

import java.util.ArrayList;

import controls.Constants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.Controller;
import mvc.FXViewModel;


public class EditorView extends FXViewModel
{
	// ArrayList<VBox> cards;

	VBox editLayout = new VBox(10);
	Label headLbl;

	public EditorView (String setName, Controller controller)
	{
		super(setName, controller);

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

		setupScene(new Scene(mainLayout, getController().getFXSettings().OPTIMAL_WIDTH, getController().getFXSettings().OPTIMAL_HEIGHT));
		getController().getModel("cards").registerView(this);
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
				String[] cardSides = s.split(Constants.SEPARATOR);
				TextField front = new TextField(cardSides[1]);
				TextField back = new TextField(cardSides[2]);
				
				front.focusedProperty().addListener(e ->
				{
					if (!front.isFocused())
					{
						if (back.getText() != null && !back.getText().equals("") && front.getText() != null
								&& !front.getText().equals(""))
						{
							getController().getModel("cards").doAction("edit", cardSides[0] + Constants.SEPARATOR + front.getText() + Constants.SEPARATOR + back.getText());
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
							getController().getModel("cards").doAction("edit", cardSides[0] + Constants.SEPARATOR + front.getText() + Constants.SEPARATOR + back.getText());
						}
					}
				});
				

				Button delete = new Button("X");
				delete.setMaxWidth(35);
				delete.setMinWidth(35);
				delete.setOnAction(e -> getController().getModel("cards").doAction("delete", cardSides[0]));

				HBox v = new HBox(8);
				v.setAlignment(Pos.CENTER);
				v.getChildren().addAll(front, back, delete);
				cards.add(v);
			}

			TextField front = new TextField();
			TextField back = new TextField();
			Button addBtn = new Button("\u2713");
			addBtn.setMaxWidth(35);

			addBtn.setOnAction(e ->
			{
				if (back.getText() != null && !back.getText().equals("") && front.getText() != null
						&& !front.getText().equals(""))
				{
					getController().getModel("cards").doAction("new",
							front.getText() + Constants.SEPARATOR + back.getText() + Constants.SEPARATOR + data);
				}
			});

			HBox v = new HBox(8);

			v.setAlignment(Pos.CENTER);
			v.getChildren().addAll(front, back, addBtn);
			cards.add(v);

			editLayout.getChildren().addAll(cards);
		}
	}

}
