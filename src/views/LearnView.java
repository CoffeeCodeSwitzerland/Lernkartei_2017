package views;

import java.util.ArrayList;

import debug.Debugger;
import globals.Functions;
import globals.Globals;
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
import mvc.ModelInterface.Command;
import mvc.fx.FXController;
import mvc.fx.FXViewModel;
import views.components.AppButton;
import views.components.ControlLayout;
import views.components.MainLayout;


/**
 * Lernen. Es werden Karten nacheinander angezeigt, die der User als Richtig
 * oder Falsch markieren kann.
 * 
 * @author miro albrecht, Dr.Med. David Schor
 *
 */
public class LearnView extends FXViewModel
{
	public LearnView (String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}

	Button	successfulBtn		= new Button("\u2714 ");
	Button	wrongBtn			= new Button("X");
	Label		headLbl			= new Label("");
	Label		cardNrLbl		= new Label("");
	WebView		card			= new WebView();
	WebEngine	engine			= card.getEngine();

	boolean		frontIsShowed	= true;
	Button		turnCard		= new Button("\u21BA");
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
			int feedback = changeCardPriority(Command.TRUE);
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
			int feedback = changeCardPriority(Command.FALSE);
			if (feedback < 0)
			{
				counter--;
			}
			else if (feedback == 0)
			{
				Debugger.out("views/LearnView/constructContainer: doAction(Falsch) Parameter ungültig");
			}
		});
		//TODO turn Card Knopf gleich wie oben(Richtig/Falsch noch anpassen kurz erklären @Author Yanis
		turnCard.setOnAction(e -> turnCard());

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

		//Navigation zwischen Kärtchen und Kärtchen drehen Button
		HBox ersteReihe = new HBox(40);
		ersteReihe.setAlignment(Pos.CENTER);
		ersteReihe.getChildren().addAll(preCard, turnCard, nextCard);
		
		//Hier wird nur bestätigt ob richtig oder falsch
		HBox zweiteReihe = new HBox(20);
		zweiteReihe.setAlignment(Pos.CENTER);
		zweiteReihe.getChildren().addAll(successfulBtn, wrongBtn);
		
		//Hier werden ersteReihe und zweiteReihe unterienander gestellt
		VBox reihenLayout = new VBox(20);
		reihenLayout.getChildren().addAll(ersteReihe,zweiteReihe);
						
		ControlLayout controlLayout = new ControlLayout(backBtn, reihenLayout);
		
		//Hier noch die IDs fürs CSS
		preCard.setId("ersteReihe");
		turnCard.setId("TurnIcon");
		nextCard.setId("ersteReihe");
		successfulBtn.setId("zweiteReihe");
		wrongBtn.setId("zweiteReihe");
		
		//Hab hier die Grössen festgelegt da es das Padding im CSS ignoriert.
		turnCard.setMinWidth(100);
		preCard.setMinWidth(60);
		nextCard.setMinWidth(60);
		successfulBtn.setMinWidth(80);
		wrongBtn.setMinWidth(80);
		
		BorderPane headLayout = new BorderPane(headLbl);
		
		MainLayout mainLayout = new MainLayout(cardLayout, headLayout, controlLayout);

		mainLayout.setOnKeyReleased(e ->
		{
			if (e.getCode() == KeyCode.T)
			{
				turnCard();
			}
			else if (e.getCode() == KeyCode.R)
			{
				int feedback = changeCardPriority(Command.FALSE);
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
				int feedback = changeCardPriority(Command.FALSE);
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
					if (cardLimitString.length() > 0)
					{
						stackPartSize = Integer.parseInt(cardLimitString);
						if (stackPartSize < Globals.minStackPartSize)
						{
							stackPartSize = Globals.minStackPartSize;
						}
					}
				}
			}

			headLbl.setText(getData());
			cardNrLbl.setText("(" + (counter % stackPartSize + 1) + "/"+stackPartSize+")");

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
					cardData[i] = Functions.complexBbCode2HTML(cardData[i]);
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

	private int changeCardPriority (Command command)
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