package gui;

import java.util.ArrayList;

import application.Constants;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.Controller;
import mvc.FXSettings;
import mvc.FXView;

public class EditorView extends FXView
{
	//ArrayList<VBox> cards;
	
	VBox layout = new VBox();
	
	public EditorView (String setName, Controller controller)
	{
		super(setName, controller);

		//cards = new ArrayList<VBox>();
		
		setupScene(new Scene(layout, FXSettings.OPTIMAL_WIDTH, FXSettings.OPTIMAL_HEIGHT));
		getController().getModel("cards").registerView(this);
	}

	@Override
	public void refreshView ()
	{
		layout.getChildren().clear();
		
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
				delete.setOnAction(e -> System.out.println("Delete"));
				
				HBox v = new HBox(4);
				v.getChildren().addAll(front, back, delete);
				cards.add(v);
			}
			
			TextField front = new TextField();
			TextField back = new TextField();
			Button delete = new Button("Add");
			delete.setOnAction(e -> getController().getModel("cards").doAction("new", back.getText() + Constants.SEPARATOR + front.getText() + Constants.SEPARATOR + data));
			
			HBox v = new HBox(4);
			v.getChildren().addAll(front, back, delete);
			cards.add(v);
			
			layout.getChildren().addAll(cards);
		}
	}

}
