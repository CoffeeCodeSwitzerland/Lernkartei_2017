package views;

import java.awt.Color;
import java.util.ArrayList;

import debug.Debugger;
import globals.Functions;
import globals.Globals;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
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
	Label		countSuccess	= new Label("0");
	Label		lblSuccess		= new Label("Anzahl richtige Antworten: ");
	Label		lblWrong		= new Label("Anzahl falsche Antworten: ");
	Label		countWrong		= new Label("0");
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
		AppButton backBtn = new AppButton("_Zur�ck");
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
				Debugger.out("views/LearnView/constructContainer: doAction(Richtig) Parameter ung�ltig");
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
				Debugger.out("views/LearnView/constructContainer: doAction(Falsch) Parameter ung�ltig");
			}
		});
		//TODO turn Card Knopf gleich wie oben(Richtig/Falsch noch anpassen kurz erkl�ren @Author Yanis
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
		
		//Hier wird nur best�tigt ob richtig oder falsch
		HBox zweiteReihe = new HBox(20);
		zweiteReihe.setAlignment(Pos.CENTER);
		zweiteReihe.getChildren().addAll(successfulBtn, wrongBtn);
				
		//Navigation zwischen K�rtchen und K�rtchen drehen Button
		HBox ersteReihe = new HBox(40);
		ersteReihe.setAlignment(Pos.CENTER);
		ersteReihe.getChildren().addAll(preCard, turnCard, nextCard);
		
		VBox countLayout = new VBox(20);
		countLayout.setAlignment(Pos.TOP_LEFT);
		countLayout.getChildren().addAll(lblSuccess, countSuccess, lblWrong, countWrong);
				
		VBox cardLayout = new VBox(20);
		cardLayout.setAlignment(Pos.CENTER);
		cardLayout.getChildren().addAll(card, cardNrLbl, ersteReihe, zweiteReihe);

		cardLayout.setOnMouseClicked(e ->
		{
			turnCard();
		});
						
		ControlLayout controlLayout = new ControlLayout(backBtn);
		controlLayout.setAlignment(Pos.BOTTOM_LEFT);
		
		//Hier noch die IDs f�rs CSS
		preCard.setId("preCard");
		turnCard.setId("TurnIcon");
		nextCard.setId("nextCard");
		successfulBtn.setId("zweiteReihe");
		wrongBtn.setId("zweiteReihe");
		countSuccess.setId("countSuccess");
		lblSuccess.setId("lblSuccess");
		countWrong.setId("countWrong");
		lblWrong.setId("lblWrong");
		
		//Hab hier die Gr�ssen festgelegt da es das Padding im CSS ignoriert.
		turnCard.setMinWidth(100);
		preCard.setMinWidth(60);
		nextCard.setMinWidth(60);
		successfulBtn.setMinWidth(80);
		wrongBtn.setMinWidth(80);
		
		BorderPane headLayout = new BorderPane(headLbl);
		
		MainLayout mainLayout = new MainLayout(cardLayout, headLayout, controlLayout, countLayout);

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
					Debugger.out("views/LearnView/constructContainer: doAction(Richtig) Parameter ung�ltig");
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
					Debugger.out("views/LearnView/constructContainer: doAction(Falsch) Parameter ung�ltig");
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
		switch(command)
		{
			case TRUE: countSuccess.setText(Integer.toString(Integer.parseInt(countSuccess.getText())+1)); break;
			case FALSE: countWrong.setText(Integer.toString(Integer.parseInt(countWrong.getText())+1)); break;
			default: break;
		}
		return getFXController().getModel("learn").doAction(command, cardData[0]);
	}

	@Override
	public void setData (String data)
	{
		counter = 0;
		counterBase = 0;
		getMyModel().setString(data);
		/* Reset counters each time when datas are loaded */
		countSuccess.setText("0");
		countWrong.setText("0");
	}
}