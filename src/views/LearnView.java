package views;

import java.util.ArrayList;
import java.util.Arrays;

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
				
				String[] po = {"[b]", "[i]", "[u]", "[s]", "[sup]", "[sub]"};
				String[] ne = {"[/b]", "[/i]", "[/u]", "[/s]", "[/sup]", "[/sub]"};
				String[] pon = {"<b>", "<i>", "<u>", "<s>", "<sup>", "<sub>"};
				String[] nen = {"</b>", "</i>", "</u>", "</s>", "</sup>", "</sub>"};
				String[] dang = {"<", ">"};
				
				for(int i =0; i < dang.length; i++)
			    {
			        if(cardData[1].contains(dang[i]) || cardData[2].contains(dang[i]))
			        {
			        	cardData[1] = cardData[1].replace(dang[i], "");
			        	cardData[2] = cardData[2].replace(dang[i], "");
			        }
			    }
				for(int i =0; i < po.length; i++)
			    {
			        if(cardData[1].contains(po[i]) || cardData[2].contains(po[i]))
			        {
			        	cardData[1] = cardData[1].replace(po[i], pon[i]);
			        	cardData[2] = cardData[2].replace(po[i], pon[i]);
			        }
			    }
				for(int i =0; i < ne.length; i++)
			    {
			        if(cardData[1].contains(ne[i]) || cardData[2].contains(ne[i]))
			        {
			        	cardData[1] = cardData[1].replace(ne[i], nen[i]);
			        	cardData[2] = cardData[2].replace(ne[i], nen[i]);
			        }
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