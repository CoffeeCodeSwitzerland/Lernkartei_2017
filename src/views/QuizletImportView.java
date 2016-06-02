package views;

import java.util.ArrayList;

import globals.Globals;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXViewModel;
import views.components.Alert;
import views.components.AppButton;


/**
 * Diese View ermöglicht dem User den Import von Stapeln aus Quizlet
 * 
 * @author miro
 *
 */
public class QuizletImportView extends FXViewModel
{
	ArrayList<HBox>		quizletSets;
	ArrayList<String>	searchResult;
	Label				searchTitle;
	Label				searchInfoLbl;
	TextField			searchInput				= new TextField();
	Button				okBtn					= new Button("Go");
	VBox				listLayout				= new VBox(20);
	VBox				additionalInfoLayout	= new VBox(20);
	ProgressBar			loading					= new ProgressBar(0);
	String				downloadStackName		= "";
	int					cardNumber				= -1;
	float				alreadyDownloaded		= 0;
	float				cardListSize			= 0;

	boolean				isLoading				= false;
	boolean				isLoadingACard			= false;

	public QuizletImportView (String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}

	@Override
	public Parent constructContainer ()
	{
		searchTitle = new Label("Suche");
		searchTitle.setId("bold");
		searchInfoLbl = new Label("");

		searchInput.setOnKeyReleased(e ->
		{
			if (e.getCode() == KeyCode.ENTER)
			{
				refreshView();
			}
		});

		okBtn.setOnAction(e ->
		{
			refreshView();
		});
		okBtn.setOnKeyReleased(e ->
		{
			if (e.getCode() == KeyCode.ENTER)
			{
				refreshView();
			}
		});

		HBox searchField = new HBox(15);
		searchField.getChildren().addAll(searchInput, okBtn);

		BorderPane headLayout = new BorderPane();
		headLayout.setTop(searchTitle);
		headLayout.setLeft(searchInfoLbl);
		headLayout.setPadding(new Insets(20));
		headLayout.setRight(searchField);

		listLayout.setAlignment(Pos.CENTER);
		listLayout.setPadding(new Insets(35));

		ScrollPane scroller = new ScrollPane(listLayout);
		scroller.setPadding(new Insets(20));
		scroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroller.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

		AppButton backBtn = new AppButton("_Zurück");
		backBtn.setOnAction(e -> getFXController().showView("stack"));

		HBox infoLayout = new HBox(15);
		infoLayout.setPadding(new Insets(15));
		infoLayout.setAlignment(Pos.CENTER_LEFT);
		infoLayout.getChildren().addAll(backBtn, new Label("Powered by Quizlet"));

		VBox bottomLayout = new VBox();
		bottomLayout.getChildren().addAll(infoLayout, loading);

		BorderPane mainLayout = new BorderPane();
		mainLayout.setCenter(scroller);
		mainLayout.setTop(headLayout);
		mainLayout.setRight(additionalInfoLayout);
		mainLayout.setBottom(bottomLayout);

		getFXController().getModel("stack").registerView(this);
		getFXController().getModel("cards").registerView(this);
		return mainLayout;
	}

	AnimationTimer loadingAnimation = new AnimationTimer()
	{
		@Override
		public void handle (long now)
		{
			if (isLoading)
			{
				if (!isLoadingACard)
				{
					if (cardNumber < searchResult.size())
					{
						isLoadingACard = true;
						String s1 = searchResult.get(cardNumber);
						cardNumber++;
						if (s1.split(Globals.SEPARATOR).length != 3)
						{
							s1 = Alert.simpleString("Achtung",
									"Ein ungültiger String wurde gefunden. Bitte passen sie den String an.",
									s1, 500);
						}
						loading.setProgress(cardNumber / cardListSize);
						getFXController().getModel("cards").doAction("new",
								s1.split(Globals.SEPARATOR)[1] + Globals.SEPARATOR
										+ s1.split(Globals.SEPARATOR)[2] + Globals.SEPARATOR + downloadStackName);

						isLoadingACard = false;
					}
					else
					{
						getWindow().getScene().widthProperty()
								.removeListener(oldEvent -> loading.setMinWidth(getWindow().getScene().getWidth()));

						downloadStackName = "";
						cardNumber = -1;
						alreadyDownloaded = 0;
						cardListSize = 0;

						isLoading = false;
						loadingAnimation.stop();

						loading.setProgress(0);
						getFXController().showView("stack");
					}
				}
			}
		}
	};

	@Override
	public void refreshView ()
	{
		if (cardNumber == -1)
		{
			loading.setMinWidth(getWindow().getScene().getWidth());
			getWindow().getScene().widthProperty()
					.addListener(e -> loading.setMinWidth(getWindow().getScene().getWidth()));

			if (searchInput.getText() == null || searchInput.getText().equals("")) { return; }

			searchTitle.setText("Suche '" + searchInput.getText() + "'");

			if (listLayout.getChildren() != null)
			{
				listLayout.getChildren().clear();
			}
			if (additionalInfoLayout.getChildren() != null)
			{
				additionalInfoLayout.getChildren().clear();
				additionalInfoLayout.setPadding(new Insets(0));
			}

			quizletSets = new ArrayList<>();
			searchResult = getFXController().getModel("quizlet")
					.getDataList("search" + Globals.SEPARATOR + searchInput.getText());

			if (searchResult != null)
			{
				// Erstellt Liste mit den Resultaten
				for (String s : searchResult)
				{
					String[] stackInfo = s.split(Globals.SEPARATOR);
					if (searchResult.indexOf(s) != 0)
					{
						Label stackName = new Label(stackInfo[1] + " (" + stackInfo[3] + ")");
						stackName.setId("bold");

						Label stackCreator = new Label("by " + stackInfo[2]);
						HBox stackInfoLayout = new HBox(25);
						VBox stackLayout = new VBox(5);
						stackLayout.getChildren().addAll(stackName, stackCreator);
						Button showStackInfo = new Button("i");
						showStackInfo.setId("icon");
						showStackInfo.setOnKeyReleased(e ->
						{
							if (e.getCode() == KeyCode.ENTER)
								showStackInfo.fire();
						});
						showStackInfo.setOnAction(e ->
						{
							// Zeigt mehr Infos an
							Label stackTitle = new Label(stackInfo[1]);
							stackTitle.setId("bold");
							Label stackCount = new Label("Anzahl: " + stackInfo[3]);
							Label stackAuthor = new Label("Author: " + stackInfo[2]);
							Label stackLangs = new Label("Sprachen: " + stackInfo[6] + " - " + stackInfo[7]);
							Label stackHasImgs = new Label("Bilder: " + stackInfo[4]);
							Button downloadStack = new Button("_Herunterladen");
							downloadStack.setOnKeyReleased(dnldEvent ->
							{
								if (dnldEvent.getCode() == KeyCode.ENTER)
									downloadStack.fire();
							});
							downloadStack.setOnAction(e1 ->
							{
								// Lädt Stapel herunter
								if (stackInfo[4].equals("true"))
								{
									Alert.simpleInfoBox("Download nicht möglich",
											"Es werden keine Bilder unterstützt...");
									return;
								}

								searchResult = getFXController().getModel("quizlet")
										.getDataList("set" + Globals.SEPARATOR + stackInfo[0]);

								String name = Alert.simpleString("Neuer Stapel", "Name für den Quizletstapel",
										stackInfo[1]);

								if (name == null) { return; }

								int possible = getFXController().getModel("stack").doAction("possible", name);

								while (name.equals("") || possible < 0)
								{
									if (possible < 1)
									{
										int i = 1;
										while (getFXController().getModel("stack").doAction("possible",
												name + " (" + i + ")") < 0)
										{
											i++;
										}
										name = Alert.simpleString("Neuer Stapel",
												"'" + name + "' ist nicht gültig. Bitte wählen sie einen Anderen.",
												name + " (" + i + ")");
										break;
									}
									name = Alert.simpleString("Neuer Stapel",
											"Dieser Name ist nicht gültig. Bitte wählen sie einen Anderen.",
											stackInfo[1]);
									possible = getFXController().getModel("stack").doAction("possible", name);
								}

								downloadStackName = name;
								loading.setProgress(-1);
								cardNumber = 1;
								cardListSize = Float.parseFloat(stackInfo[3]);
								getFXController().getModel("stack").doAction("new",
										getData() + globals.Globals.SEPARATOR + name);
							});
							additionalInfoLayout.getChildren().clear();
							additionalInfoLayout.getChildren().addAll(stackTitle, stackCount, stackAuthor, stackLangs,
									stackHasImgs, downloadStack);

							additionalInfoLayout.setPadding(new Insets(20));
						});

						stackInfoLayout.getChildren().addAll(showStackInfo, stackLayout);
						stackInfoLayout.setAlignment(Pos.CENTER_LEFT);
						quizletSets.add(stackInfoLayout);
					}
					else
					{
						searchInfoLbl.setText(
								stackInfo[0] + " Sets gefunden. Seite " + stackInfo[3] + " von " + stackInfo[1]);
					}
				}

				listLayout.getChildren().addAll(quizletSets);
			}
		}
		else
		{
			if (!isLoading)
			{
				isLoading = true;
				loadingAnimation.start();
				;
			}
		}
	}
}
