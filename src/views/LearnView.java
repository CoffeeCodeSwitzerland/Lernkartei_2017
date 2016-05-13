package views;

import java.util.ArrayList;

import controls.Globals;
import debug.Debugger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import mvc.Controller;
import mvc.FXViewModel;

public class LearnView extends FXViewModel
{
	public LearnView(String newName, Controller newController) {
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}

	AppButton successfulBtn = new AppButton("_Richtig");
	AppButton wrongBtn = new AppButton("_Falsch");
	Label headLbl = new Label("");
	AppButton card = new AppButton("");
	
	Button preCard = new Button("\u25C0");
	Button nextCard = new Button("\u25B6");
	
	int counter = 0;
	
	String[] cardData = new String[3];
	

	@Override
	public Parent constructContainer() {
		
		AppButton backBtn = new AppButton("_Zurück");
		backBtn.setOnAction(e -> {counter = 0; getController().getView("stack").show();});
		
		headLbl.setId("bold");
		
		successfulBtn.setOnAction(e ->
		{
			counter++;
			int feedback = getController().getModel("learn").doAction("Richtig", cardData[0]);
			if (feedback < 0)		{counter--;}
			else if (feedback == 0)	{Debugger.out("views/LearnView/constructContainer: doAction(Richtig) Parameter ungültig");}
		}); 
		
		wrongBtn.setOnAction(e ->
		{
			counter++;
			int feedback = getController().getModel("learn").doAction("Falsch", cardData[0]);
			if (feedback < 0)		{counter--;}
			else if (feedback == 0)	{Debugger.out("views/LearnView/constructContainer: doAction(Falsch) Parameter ungültig");}
		});
		
		
		card.setMinWidth(320);
		card.setMinHeight(180);
		card.setId("bold");
		card.setOnAction(e -> card.setText(card.getText().equals(cardData[1]) ? cardData[2] : cardData[1]));
		
		
		preCard.setOnAction(e -> {
			counter = counter > 0 ? counter - 1 : counter;
			refreshView();
		});
		
		nextCard.setOnAction(e -> {
			counter++;
			refreshView();
		});
		
		HBox controlLayout = new HBox(20);
		controlLayout.setAlignment(Pos.CENTER);
		controlLayout.getChildren().addAll(backBtn, preCard, successfulBtn, wrongBtn, nextCard);
		
		BorderPane mainLayout = new BorderPane();
		mainLayout.setPadding(new Insets(15));
		mainLayout.setTop(headLbl);
		mainLayout.setCenter(card);
		mainLayout.setBottom(controlLayout);
		
		getController().getModel("learn").registerView(this);
		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
		if (getData() == null || getData().equals(""))
		{
			successfulBtn.setDisable(false);
			wrongBtn.setDisable(false);
		}
		else
		{
			headLbl.setText(getData());
			ArrayList<String> cards = getController().getModel("learn").getDataList(getData());
			if (counter < cards.size())
			{
				successfulBtn.setDisable(false);
				wrongBtn.setDisable(false);
				nextCard.setDisable(false);
				String d = cards.get(counter); // Ensure valid counter variable
				cardData = d.split(Globals.SEPARATOR);
				card.setText(cardData[1]);
			}
			else
			{
				successfulBtn.setDisable(true);
				wrongBtn.setDisable(true);
				nextCard.setDisable(true);
				card.setText("");
				cardData = null;
				counter = cards.size();
			}
		}
	}
	
	public void clearShuffle ()
	{
		getController().getModel("learn").getDataList(null).clear();
		getController().getModel("learn").setString(null);
	}
}
