package gui;

import java.util.ArrayList;

import application.Constants;
import application.MainController;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class EditorView extends View
{
	//ArrayList<VBox> cards;
	
	HBox layout = new HBox();
	
	public EditorView (String setName, Stage primary, MainController controller)
	{
		super(setName, primary, controller);

		//cards = new ArrayList<VBox>();
		
		setupScene(new Scene(layout, Constants.OPTIMAL_WIDTH, Constants.OPTIMAL_HEIGHT));
	}

	@Override
	public void refreshView ()
	{
		layout.getChildren().clear();
		
		String data = getData();
		
		if (data != null)
		{
			ArrayList<String> cardStrings = getController().getMyModel("cards").getData(data);
			ArrayList<VBox> cards = new ArrayList<>();
			
			for (String s : cardStrings)
			{
				String[] cardSides = s.split(Constants.SEPARATOR);
				TextField front = new TextField(cardSides[1]);
				TextField back = new TextField(cardSides[2]);
				Button delete = new Button("X");
				delete.setOnAction(e -> System.out.println("Delete"));
				
				VBox v = new VBox(4);
				v.getChildren().addAll(front, back, delete);
				cards.add(v);
			}
			
			layout.getChildren().addAll(cards);
		}
	}

}
