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
import javafx.scene.web.HTMLEditor;
import mvc.Controller;
import mvc.FXViewModel;


public class EditorView1 extends FXViewModel
{
	// ArrayList<VBox> cards;

	public EditorView1(String newName, Controller newController) {
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}

	VBox editLayout = new VBox(10);
	Label headLbl;
	HTMLEditor front, back;
	Button update = new Button("\u2713");
	

	@Override
	public Parent constructContainer() {
		headLbl = new Label("");
		headLbl.setId("bold");	
		
		BorderPane headLayout = new BorderPane(headLbl);
		headLayout.setPadding(new Insets(25));
		
		//Zurück Button
		AppButton backBtn = new AppButton("_Zurück");
		backBtn.setOnAction(e -> 
		getController().getView("simpleeditorview").show());	
		
		//EditLayout
		editLayout.setPadding(new Insets(10));
		editLayout.setAlignment(Pos.TOP_CENTER);

		//Controll Layout
		HBox controlLayout = new HBox(20);
		controlLayout.setAlignment(Pos.CENTER);
		controlLayout.getChildren().addAll(backBtn, update);

		//Main Layout
		BorderPane mainLayout = new BorderPane();
		mainLayout.setPadding(new Insets(15));
		mainLayout.setTop(headLayout);
		mainLayout.setCenter(editLayout);
		mainLayout.setBottom(controlLayout);

		return mainLayout;
	}

	@Override
	public void refreshView() {

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
				
				front.setText(data);
				back.setText(data);
				
				//Update Button
				update.setOnAction(e ->
				{
					if (back.getText() != null && !back.getText().equals("") && front.getText() != null
							&& !front.getText().equals(""))
					{
						System.out.println(cardSides[0]);
						getController().getModel("cards").doAction("edit", cardSides[0] + Globals.SEPARATOR
								+ front.getText() + Globals.SEPARATOR + back.getText());}
				});
				
				HBox v = new HBox(8);
				v.setAlignment(Pos.CENTER);
				v.getChildren().addAll(front, back);
				cards.add(v);
			}

			TextField front = new TextField();
			TextField back = new TextField();

			HBox v = new HBox(8);

			v.setAlignment(Pos.CENTER);
			v.getChildren().addAll(front, back);
			cards.add(v);

			editLayout.getChildren().addAll(cards);
		}
		
	}
}
