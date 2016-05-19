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
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import mvc.fx.FXController;
import mvc.fx.FXViewModel;


public class LearnView extends FXViewModel
{
	public LearnView (String newName, FXController newController)
	{
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}

	AppButton	successfulBtn	= new AppButton("_Richtig");
	AppButton	wrongBtn		= new AppButton("_Falsch");
	Label		headLbl			= new Label("");
	WebView		card			= new WebView();
	WebEngine	engine			= card.getEngine();

	boolean		frontIsShowed	= true;

	Button		preCard			= new Button("\u25C0");
	Button		nextCard		= new Button("\u25B6");

	int			counter			= 0;

	String[]	cardData		= new String[3];

	@Override
	public Parent constructContainer ()
	{
		AppButton backBtn = new AppButton("_Zurück");
		backBtn.setOnAction(e ->
		{
			counter = 0;
			getController().showView("stack");
		});

		headLbl.setId("bold");

		successfulBtn.setOnAction(e ->
		{
			counter++;
			int feedback = getController().getModel("learn").doAction("Richtig", cardData[0]);
			if (feedback < 0)
			{
				counter--;
			}
			else if (feedback == 0)
			{
				Debugger.out("views/LearnView/constructContainer: doAction(Richtig) Parameter ungültig");
			}
		});

		wrongBtn.setOnAction(e ->
		{
			counter++;
			int feedback = getController().getModel("learn").doAction("Falsch", cardData[0]);
			if (feedback < 0)
			{
				counter--;
			}
			else if (feedback == 0)
			{
				Debugger.out("views/LearnView/constructContainer: doAction(Falsch) Parameter ungültig");
			}
		});

		card.setMaxSize(320, 180);
		card.setId("bold");
		card.setDisable(true);

		preCard.setOnAction(e ->
		{
			counter = counter > 0 ? counter - 1 : counter;
			refreshView();
		});

		nextCard.setOnAction(e ->
		{
			counter++;
			refreshView();
		});

		Button turnCardBtn = new Button("Drehen");
		turnCardBtn.setOnAction(e ->
		{
			engine.loadContent(frontIsShowed ? cardData[2] : cardData[1]);
			frontIsShowed = !frontIsShowed;
		});

		VBox cardLayout = new VBox(20);
		cardLayout.setAlignment(Pos.CENTER);
		cardLayout.getChildren().addAll(card, turnCardBtn);

		HBox controlLayout = new HBox(20);
		controlLayout.setAlignment(Pos.CENTER);
		controlLayout.getChildren().addAll(backBtn, preCard, successfulBtn, wrongBtn, nextCard);

		BorderPane mainLayout = new BorderPane();
		mainLayout.setCenter(cardLayout);
		mainLayout.setBottom(controlLayout);

		mainLayout.setPadding(new Insets(15));
		mainLayout.setTop(headLbl);

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
				if(cardData[2].contains("<") && cardData[2].contains(">") || cardData[1].contains("<") && cardData[1].contains(">")){
					cardData[2] = cardData[2].replace("<", "");
					cardData[2] = cardData[2].replace(">", "");
					cardData[1] = cardData[1].replace("<", "");
					cardData[1] = cardData[1].replace(">", "");
				}
				if(cardData[2].contains("[b]") || cardData[1].contains("[b]") || 
						cardData[2].contains("[i]") || cardData[1].contains("[i]") || 
						cardData[2].contains("[u]") || cardData[1].contains("[u]") ||
						cardData[2].contains("[s]") || cardData[1].contains("[s]") ||
						cardData[2].contains("[sup]") || cardData[1].contains("[sup]") ||
						cardData[2].contains("[sub]") || cardData[1].contains("[sub]")){
					cardData[2] = cardData[2].replace("[b]", "<b>");
					cardData[1] = cardData[1].replace("[b]", "<b>");
					cardData[2] = cardData[2].replace("[i]", "<i>");
					cardData[1] = cardData[1].replace("[i]", "<i>");
					cardData[2] = cardData[2].replace("[u]", "<u>");
					cardData[1] = cardData[1].replace("[u]", "<u>");
					cardData[2] = cardData[2].replace("[s]", "<s>");
					cardData[1] = cardData[1].replace("[s]", "<s>");
					cardData[2] = cardData[2].replace("[sup]", "<sup>");
					cardData[1] = cardData[1].replace("[sup]", "<sup>");
					cardData[2] = cardData[2].replace("[sub]", "<sub>");
					cardData[1] = cardData[1].replace("[sub]", "<sub>");
				}
				if(cardData[2].contains("[/b]") || cardData[1].contains("[/b]") || 
						cardData[2].contains("[/i]") || cardData[1].contains("[/i]") || 
						cardData[2].contains("[/u]") || cardData[1].contains("[/u]") ||
						cardData[2].contains("[/s]") || cardData[1].contains("[/s]") ||
						cardData[2].contains("[/sup]") || cardData[1].contains("[/sup]") ||
						cardData[2].contains("[/sub]") || cardData[1].contains("[/sub]")){
					cardData[2] = cardData[2].replace("[/b]", "</b>");
					cardData[1] = cardData[1].replace("[/b]", "</b>");
					cardData[2] = cardData[2].replace("[/i]", "</i>");
					cardData[1] = cardData[1].replace("[/i]", "</i>");
					cardData[2] = cardData[2].replace("[/u]", "</u>");
					cardData[1] = cardData[1].replace("[/u]", "</u>");
					cardData[2] = cardData[2].replace("[/s]", "</s>");
					cardData[1] = cardData[1].replace("[/s]", "</s>");
					cardData[2] = cardData[2].replace("[/sup]", "</sup>");
					cardData[1] = cardData[1].replace("[/sup]", "</sup>");
					cardData[2] = cardData[2].replace("[/sub]", "</sub>");
					cardData[1] = cardData[1].replace("[/sub]", "</sub>");
				}
				engine.loadContent(cardData[1]);
				frontIsShowed = true;
			}
			else
			{
				successfulBtn.setDisable(true);
				wrongBtn.setDisable(true);
				nextCard.setDisable(true);
				engine.loadContent("");
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