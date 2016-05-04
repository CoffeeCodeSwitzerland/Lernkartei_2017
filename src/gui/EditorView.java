package gui;

import java.util.ArrayList;

import application.Constants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.Controller;
import mvc.FXSettings;
import mvc.FXViewModel;

public class EditorView extends FXViewModel
{
	//ArrayList<VBox> cards;
	
	VBox editLayout= new VBox(10);
	
	public EditorView (String setName, Controller controller)
	{
		super(setName, controller);

		AppButton backBtn = new AppButton("Zurück");
		backBtn.setOnAction(e -> getController().getView("boxview").show());
		
		editLayout.setPadding(new Insets(10));
		editLayout.setAlignment(Pos.TOP_CENTER);
		
		VBox controlLayout = new VBox(20);
		controlLayout.setAlignment(Pos.CENTER);
		controlLayout.getChildren().addAll(backBtn);
		
		BorderPane mainLayout = new BorderPane();
		mainLayout.setPadding(new Insets(10));
		mainLayout.setCenter(editLayout);
		mainLayout.setBottom(controlLayout);
		
		setupScene(new Scene(mainLayout, FXSettings.OPTIMAL_WIDTH, FXSettings.OPTIMAL_HEIGHT));
		getController().getModel("cards").registerView(this);
	}

	@Override
	public void refreshView ()
	{
		editLayout.getChildren().clear();
		
		String data = getData();
		
		if (data != null)
		{
			ArrayList<String> cardStrings = getController().getModel("cards").getDataList(data);
			ArrayList<HBox> cards = new ArrayList<>();
			debug.Debugger.out("" + cardStrings.size()); 
			for (String s : cardStrings)
			{
				String[] cardSides = s.split(Constants.SEPARATOR);
				TextField front = new TextField(cardSides[1]);
				TextField back = new TextField(cardSides[2]);
				Button delete = new Button("X");
				delete.setOnAction(e -> getController().getModel("cards").doAction("delete", cardSides[0]));
				
				HBox v = new HBox(4);
				v.getChildren().addAll(front, back, delete);
				cards.add(v);
			}
			
			TextField front = new TextField();
			TextField back = new TextField();
			Button delete = new Button("Add");
			
			delete.setOnAction(e ->
			{
				if (back.getText() != null && !back.getText().equals("") && front.getText() != null && !front.getText().equals(""))
				{
					getController().getModel("cards").doAction("new", front.getText() + Constants.SEPARATOR + back.getText() + Constants.SEPARATOR + data);
				}
			});
			
			
			HBox v = new HBox(4);
			v.getChildren().addAll(front, back, delete);
			cards.add(v);
			
			editLayout.getChildren().addAll(cards);
		}
	}

}
