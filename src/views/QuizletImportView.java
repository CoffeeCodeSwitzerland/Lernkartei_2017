package views;

import java.util.ArrayList;

import debug.Debugger;
import globals.Globals;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.ModelInterface.Command;
import mvc.fx.FXController;
import mvc.fx.FXViewModel;
import views.components.Alert;
import views.components.AppButton;
import views.components.ControlLayout;
import views.components.MainLayout;
import views.components.VerticalScroller;


/**
 * Diese View erm�glicht dem User den Import von Stapeln aus Quizlet
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
	String				currentSearch			= "";
	int					cardNumber				= -1;
	int					page					= 1;
	int					pageCount				= 0;

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

		searchInput.setPromptText("Suchen");
		searchInput.setOnKeyReleased(e ->
		{
			if (e.getCode() == KeyCode.ENTER)
			{
				refreshView();
			}
		});

		okBtn.setId("small");
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

		additionalInfoLayout.setMaxWidth(300); // TODO no hard coded value

		VerticalScroller scroLay = new VerticalScroller(listLayout, 1);
		scroLay.setMaxWidth(getWindow().getMaxWidth());

		AppButton backBtn = new AppButton("_Zur�ck");
		backBtn.setOnAction(e -> getFXController().showAndTrackView("stack"));

		AppButton prevPageBtn = new AppButton("Vorherige Seite");
		prevPageBtn.setOnAction(e ->
		{
			if (page > 1)
			{
				page--;
				refreshView();
			}
		});

		AppButton nextPageBtn = new AppButton("N�chste Seite");
		nextPageBtn.setOnAction(e ->
		{
			if (page < pageCount)
			{
				page++;
				refreshView();
			}
		});

		ControlLayout conLay = new ControlLayout(backBtn, prevPageBtn, nextPageBtn, new Label("Powered by Quizlet"));
		conLay.setAlignment(Pos.CENTER_LEFT);
		conLay.setPadding(new Insets(15));

		VBox bottomLayout = new VBox();
		bottomLayout.getChildren().addAll(conLay, loading);

		MainLayout maLay = new MainLayout(scroLay, headLayout, additionalInfoLayout, bottomLayout, null);
		maLay.setPadding(new Insets(0));

		try {
			getFXController().getModel("stack").registerView(this);
			getFXController().getModel("cards").registerView(this);
		} catch (Exception e) {
			Debugger.out("QuitzletImportView-constructContainer: did not found a Model named 'stack' or 'cards'!");
		}		

		return maLay;
	}
	
	boolean createtingCard = false;
	
	AnimationTimer newAnimation = new AnimationTimer()
	{
		@Override
		public void handle (long now)
		{
			if (createtingCard) { return; }
			createtingCard = true;
			
			int result=0;
			try {
				result = getFXController().getModel("quizlet").doAction(Command.UPDATE, downloadStackName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Debugger.out(Integer.toString(result));
			if (result == -7)
			{
				newAnimation.stop();
				getWindow().getScene().widthProperty().removeListener(oldEvent -> loading.setMinWidth(getWindow().getScene().getWidth()));

				downloadStackName = "";
				cardNumber = -1;

				loading.setProgress(0);
				getFXController().showAndTrackView("stack");
			}
			else if (result != -1)
			{
				loading.setProgress((double)result / 1000); 
			}
			createtingCard = false;
		}
	};
	
	@Override
	public void refreshView ()
	{
		if (cardNumber == -1)
		{
			loading.setMinWidth(getWindow().getScene().getWidth());
			// TODO change when windows isn't resizeable yet
			getWindow().getScene().widthProperty().addListener(e -> loading.setMinWidth(getWindow().getScene().getWidth()));

			if (searchInput.getText() == null || searchInput.getText().equals("")) { return; }

			if (!currentSearch.equals(searchInput.getText()))
			{
				page = 1;
				pageCount = 0;
				currentSearch = searchInput.getText();
			}

			searchTitle.setText("Suche '" + currentSearch + "'");

			if (listLayout.getChildren() != null)
			{
				listLayout.getChildren().clear();
			}
			if (additionalInfoLayout.getChildren() != null)
			{
				additionalInfoLayout.getChildren().clear();
				additionalInfoLayout.setPadding(new Insets(0));
			}

			boolean hideImg = false;

			ArrayList<String> configDataList=null;
			try {
				configDataList = getFXController().getModel("config").getDataList("hideImageStacks");
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if (configDataList != null)
			{
				String configData = configDataList.get(0);
				if (configData != null)
				{
					if (configData.equals("true"))
					{
						hideImg = true;
					}
				}
			}

			quizletSets = new ArrayList<>();
			try {
				searchResult = getFXController().getModel("quizlet").getDataList(currentSearch + Globals.SEPARATOR + page);
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			if (searchResult != null)
			{
				// Erstellt Liste mit den Resultaten
				for (String s : searchResult)
				{
					String[] stackInfo = s.split(Globals.SEPARATOR);
					if (searchResult.indexOf(s) != 0)
					{
						if (hideImg && stackInfo[4].equals("true"))
						{
							continue;
						}
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
							if (e.getCode() == KeyCode.ENTER) showStackInfo.fire();
						});
						showStackInfo.setOnAction(e ->
						{
							// Zeigt mehr Infos an
							Label stackTitle = new Label(stackInfo[1]);
							stackTitle.setId("bold");
							stackTitle.setWrapText(true);
							Label stackCount = new Label("Anzahl: " + stackInfo[3]);
							Label stackAuthor = new Label("Author: " + stackInfo[2]);
							Label stackLangs = new Label("Sprachen: " + stackInfo[6] + " - " + stackInfo[7]);
							Label stackHasImgs = new Label("Bilder: " + stackInfo[4]);
							Button downloadStack = new Button("_Herunterladen");
							downloadStack.setOnKeyReleased(dnldEvent ->
							{
								if (dnldEvent.getCode() == KeyCode.ENTER) downloadStack.fire();
							});
							downloadStack.setOnAction(e1 ->
							{
								// L�dt Stapel herunter
								if (stackInfo[4].equals("true"))
								{
									Alert.simpleInfoBox("Download nicht m�glich", "Es werden keine Bilder unterst�tzt...");
									return;
								}

								//searchResult = getFXController().getModel("quizlet").getDataList("set" + Globals.SEPARATOR + stackInfo[0]);

								String name = Alert.simpleString("Neuer Stapel", "Name f�r den Quizletstapel", stackInfo[1]);

								if (name == null) { return; }

								int possible = -1;
								try {
									possible = getFXController().getModel("stack").doAction(Command.CAN_CREATE, name);
								} catch (Exception e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}

								while (name.equals("") || possible < 0)
								{
									if (possible < 1)
									{
										int i = 1;
										try {
											while (getFXController().getModel("stack").doAction(Command.CAN_CREATE, name + " (" + i + ")") < 0)
											{
												i++;
											}
										} catch (Exception e2) {
											// TODO Auto-generated catch block
											e2.printStackTrace();
										}
										name = Alert.simpleString("Neuer Stapel", "'" + name + "' ist nicht g�ltig. Bitte w�hlen sie einen Anderen.", name + " (" + i + ")");
										break;
									}
									name = Alert.simpleString("Neuer Stapel", "Dieser Name ist nicht g�ltig. Bitte w�hlen sie einen Anderen.", stackInfo[1]);
									try {
										possible = getFXController().getModel("stack").doAction(Command.CAN_CREATE, name);
									} catch (Exception e2) {
										// TODO Auto-generated catch block
										e2.printStackTrace();
									}
								}

								downloadStackName = name;
								loading.setProgress(-1);
								cardNumber = 1;
								Debugger.out(getData());
								int success=0;
								try {
									success = getFXController().getModel("quizlet").doAction(Command.NEW, stackInfo[0], name, getData());
								} catch (Exception e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
								if (success == 1)
								{
									newAnimation.start();
								}
							});
							additionalInfoLayout.getChildren().clear();
							additionalInfoLayout.getChildren().addAll(stackTitle, stackCount, stackAuthor, stackLangs, stackHasImgs, downloadStack);

							additionalInfoLayout.setPadding(new Insets(20));
						});

						stackInfoLayout.getChildren().addAll(showStackInfo, stackLayout);
						stackInfoLayout.setAlignment(Pos.CENTER_LEFT);
						quizletSets.add(stackInfoLayout);
					}
					else
					{
						searchInfoLbl.setText(stackInfo[0] + " Sets gefunden. Seite " + stackInfo[3] + " von " + stackInfo[1]);
						pageCount = Integer.parseInt(stackInfo[1]);
					}
				}

				listLayout.getChildren().addAll(quizletSets);
			}
		}
	}
}
