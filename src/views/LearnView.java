package views;

import java.util.ArrayList;

import debug.Debugger;
import globals.Functions;
import globals.Globals;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import mvc.fx.FXController;
import mvc.fx.FXViewModel;
import views.components.AppButton;


/**
 * Lernen. Es werden Karten nacheinander angezeigt, die der User als Richtig
 * oder Falsch markieren kann.
 * 
 * @author miro albrecht
 *
 */
public class LearnView extends FXViewModel
{
	public LearnView (String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}

	AppButton	successfulBtn	= new AppButton("Richtig");
	AppButton	wrongBtn		= new AppButton("Falsch");
	Label		headLbl			= new Label("");
	Label		cardNrLbl		= new Label("");
	WebView		card			= new WebView();
	WebEngine	engine			= card.getEngine();

	boolean		frontIsShowed	= true;

	Button		preCard			= new Button("\u25C0");
	Button		nextCard		= new Button("\u25B6");

	int			counter			= 0;
	int			counterBase		= 0;

	String[]	cardData		= new String[3];

	@Override
	public Parent constructContainer ()
	{
		AppButton backBtn = new AppButton("_Zurück");
		backBtn.setOnAction(e ->
		{
			getFXController().showView("stack");
		});

		headLbl.setId("bold");

		successfulBtn.setOnAction(e ->
		{
			int feedback = changeCardPriority("Richtig");
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
			int feedback = changeCardPriority("Falsch");
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
			counter = counter > counterBase ? counter - 1 : counter;
			refreshView();
		});

		nextCard.setOnAction(e ->
		{
			counter++;
			refreshView();
		});

		VBox cardLayout = new VBox(20);
		cardLayout.setAlignment(Pos.CENTER);
		cardLayout.getChildren().addAll(card, cardNrLbl);

		cardLayout.setOnMouseClicked(e ->
		{
			turnCard();
		});

		HBox controlLayout = new HBox(20);
		controlLayout.setAlignment(Pos.CENTER);
		controlLayout.getChildren().addAll(backBtn, preCard, successfulBtn, wrongBtn, nextCard);

		BorderPane headLayout = new BorderPane(headLbl);
		
		BorderPane mainLayout = new BorderPane();
		mainLayout.setCenter(cardLayout);
		mainLayout.setBottom(controlLayout);

		mainLayout.setPadding(new Insets(15));
		mainLayout.setTop(headLayout);

		mainLayout.setOnKeyReleased(e ->
		{
			if (e.getCode() == KeyCode.T)
			{
				turnCard();
			}
			else if (e.getCode() == KeyCode.R)
			{
				int feedback = changeCardPriority("Richtig");
				if (feedback < 0)
				{
					counter--;
				}
				else if (feedback == 0)
				{
					Debugger.out("views/LearnView/constructContainer: doAction(Richtig) Parameter ungültig");
				}
			}
			else if (e.getCode() == KeyCode.F)
			{
				int feedback = changeCardPriority("Falsch");
				if (feedback < 0)
				{
					counter--;
				}
				else if (feedback == 0)
				{
					Debugger.out("views/LearnView/constructContainer: doAction(Falsch) Parameter ungültig");
				}
			}
		});

		getFXController().getModel("learn").registerView(this);
		return mainLayout;
	}

	boolean doNotSkip = true;

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
			ArrayList<String> cards = getFXController().getModel("learn").getDataList(getData());

			int stackPartSize = Globals.defaultStackPartSize;
			if (getFXController().getModel("config").getDataList("cardLimit") != null)
			{
				String cardLimitString = getFXController().getModel("config").getDataList("cardLimit").get(0);
				if (cardLimitString != null)
				{
					if (cardLimitString.length() >= Globals.minStackPartSize)
					{
						stackPartSize = Integer.parseInt(cardLimitString);
					}
				}
			}

			headLbl.setText(getData());
			cardNrLbl.setText("(" + (counter % stackPartSize + 1) + "/20)");

			if (counter == counterBase || (counter % stackPartSize > 0 && counter < cards.size()))
			{
				doNotSkip = false;
				successfulBtn.setDisable(false);
				wrongBtn.setDisable(false);
				nextCard.setDisable(false);
				String d = cards.get(counter); // Ensure valid counter variable
				cardData = d.split(Globals.SEPARATOR);

				for (int i = 1; i < 3; i++)
				{
					cardData[i] = Functions.removeHTMLTags(cardData[i]);
					cardData[i] = Functions.colorBbCode2HTML(cardData[i]);
					cardData[i] = Functions.simpleBbCode2HTML(cardData[i], Globals.evenTags);
					cardData[i] = Functions.realBbCode2HTML(cardData[i], Globals.pairedTags);
				}

				engine.loadContent(cardData[1]);
				frontIsShowed = true;
			}
			else
			{
				counterBase = counter;
				successfulBtn.setDisable(true);
				wrongBtn.setDisable(true);
				nextCard.setDisable(true);
				engine.loadContent("");
				cardData = null;
				doNotSkip = true;
				counter = counter < cards.size() ? counter : 0;
				String canComeBack = counter == 0 ? "n" : "y";
				getFXController().setViewData("postlearn", canComeBack + Globals.SEPARATOR + getData());
				getFXController().showView("postlearn");
			}
		}
	}

	public void clearShuffle ()
	{
		getFXController().getModel("learn").getDataList(null).clear();
		getFXController().getModel("learn").setString(null);
	}

	private void turnCard ()
	{
		engine.loadContent(frontIsShowed ? cardData[2] : cardData[1]);
		frontIsShowed = !frontIsShowed;
	}

	private int changeCardPriority (String command)
	{
		counter++;
		return getFXController().getModel("learn").doAction(command, cardData[0]);
	}

	@Override
	public void setData (String data)
	{
		counter = 0;
		counterBase = 0;
		getMyModel().setString(data);
	}
}