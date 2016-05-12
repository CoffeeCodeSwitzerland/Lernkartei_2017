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


	@Override
	public Parent constructContainer ()
	{
		Label searchTitle = new Label("Suche");
		searchTitle.setId("bold");
		Label searchInfoLbl = new Label("Info");
		
		ArrayList<HBox> quizletSets = new ArrayList<>();
		ArrayList<String> searchResult = getController().getModel("quizlet").getDataList("search"+Globals.SEPARATOR+"jsbms");
		
		if (searchResult != null)
		{
			for (String s : searchResult)
			{
				String[] setInfo = s.split(Globals.SEPARATOR);
				if (searchResult.indexOf(s) != 0)
				{
					Label stackName = new Label(setInfo[1] + " ("+setInfo[3]+")");
					stackName.setId("bold");
					
					Label stackCreator = new Label("by " + setInfo[2]);
					HBox stackInfoLayout = new HBox(25);
					VBox stackLayout = new VBox(5);
					stackLayout.getChildren().addAll(stackName, stackCreator);
					Button showStackInfo = new Button("i");
					showStackInfo.setId("icon");
					
					stackInfoLayout.getChildren().addAll(showStackInfo, stackLayout);
					stackInfoLayout.setAlignment(Pos.CENTER_LEFT);
					quizletSets.add(stackInfoLayout);
				}
				else
				{
					searchInfoLbl.setText(setInfo[0] + " Sets gefunden. Seite " + setInfo[3] + " von " + setInfo[1]);
				}
			}
		}
		
		
		VBox listLayout = new VBox(20);
		listLayout.getChildren().addAll(quizletSets);
		listLayout.setAlignment(Pos.CENTER);
		listLayout.setPadding(new Insets(35));
		 
		ScrollPane scroller = new ScrollPane(listLayout);
		scroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroller.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		
		BorderPane mainLayout = new BorderPane();
		mainLayout.setCenter(scroller);
		mainLayout.setTop(searchInfoLbl);

		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
		
		
	}

}
