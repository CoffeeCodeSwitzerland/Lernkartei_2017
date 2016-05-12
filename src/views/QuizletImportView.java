package views;

import java.util.ArrayList;

import controls.Globals;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.Controller;
import mvc.FXView;


/**
 * 
 * @author miro
 *
 */
public class QuizletImportView extends FXView
{

	public QuizletImportView (String newName, Controller newController)
	{
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}

	ArrayList<HBox>		quizletSets;
	ArrayList<String>	searchResult;
	Label				searchTitle;
	Label				searchInfoLbl;
	TextField			searchInput	= new TextField();
	Button				okBtn		= new Button("Go");
	VBox				listLayout	= new VBox(20);
	VBox				additionalInfoLayout = new VBox(20);
	
	@Override
	public Parent constructContainer ()
	{
		searchTitle = new Label("Suche");
		searchTitle.setId("bold");
		searchInfoLbl = new Label("");

		BorderPane headLayout = new BorderPane();
		headLayout.setTop(searchTitle);
		headLayout.setLeft(searchInfoLbl);
		headLayout.setPadding(new Insets(20));

		okBtn.setOnAction(e ->
		{
			refreshView();
		});

		HBox searchField = new HBox(15);
		searchField.getChildren().addAll(searchInput, okBtn);

		headLayout.setRight(searchField);
		
		listLayout.setAlignment(Pos.CENTER);
		listLayout.setPadding(new Insets(35));

		ScrollPane scroller = new ScrollPane(listLayout);
		scroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroller.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		
		BorderPane mainLayout = new BorderPane();
		mainLayout.setCenter(scroller);
		mainLayout.setTop(headLayout);
		mainLayout.setRight(additionalInfoLayout);
		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
		if (searchInput.getText() == null || searchInput.getText().equals("")) { return; }

		searchTitle.setText("Suche '" + searchInput.getText() +"'");
		
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
		searchResult = getController().getModel("quizlet").getDataList("search" + Globals.SEPARATOR + searchInput.getText());

		if (searchResult != null)
		{
			for (String s : searchResult)
			{
				String[] setInfo = s.split(Globals.SEPARATOR);
				if (searchResult.indexOf(s) != 0)
				{
					Label stackName = new Label(setInfo[1] + " (" + setInfo[3] + ")");
					stackName.setId("bold");

					Label stackCreator = new Label("by " + setInfo[2]);
					HBox stackInfoLayout = new HBox(25);
					VBox stackLayout = new VBox(5);
					stackLayout.getChildren().addAll(stackName, stackCreator);
					Button showStackInfo = new Button("i");
					showStackInfo.setId("icon");
					showStackInfo.setOnAction(e -> {
						Label stackTitle = new Label(setInfo[1]);
						stackTitle.setId("bold");
						Label stackCount = new Label("Anzahl: "+setInfo[3]);
						Label stackAuthor = new Label("Author: " + setInfo[2]);
						Label stackLangs = new Label("Sprachen: " + setInfo[6] + " - " + setInfo[7]);
						Label stackDesciption = new Label("'" + setInfo[5] + "'");
						stackDesciption.setPadding(new Insets(5));
						Label stackHasImgs = new Label("Bilder: " + setInfo[4]);
						Button downloadStack = new Button("Herunterladen");
						additionalInfoLayout.getChildren().clear();
						additionalInfoLayout.getChildren().addAll(stackTitle, stackCount, stackAuthor, stackLangs, stackDesciption, stackHasImgs, downloadStack);

						additionalInfoLayout.setPadding(new Insets(20));
					});
					
					stackInfoLayout.getChildren().addAll(showStackInfo, stackLayout);
					stackInfoLayout.setAlignment(Pos.CENTER_LEFT);
					quizletSets.add(stackInfoLayout);
				}
				else
				{
					searchInfoLbl.setText(setInfo[0] + " Sets gefunden. Seite " + setInfo[3] + " von " + setInfo[1]);
				}
			}

			listLayout.getChildren().addAll(quizletSets);
		}
	}

}
