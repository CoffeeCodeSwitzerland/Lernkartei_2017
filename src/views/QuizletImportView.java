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
		Label l = new Label("Test");
		
		ArrayList<HBox> quizletSets = new ArrayList<>();
		ArrayList<String> searchResult = getController().getModel("quizlet").getDataList("search"+Globals.SEPARATOR+"jsbms");
		
		if (searchResult != null)
		{
			for (String s : searchResult)
			{
				String[] setInfo = s.split(Globals.SEPARATOR);
				Label b = new Label(setInfo[1] + " ("+setInfo[3]+")");
				b.setId("bold");
				Label byLbl = new Label("by " +setInfo[2]);
				HBox setLayout = new HBox(15);
				VBox qSetInformation = new VBox(5);
				qSetInformation.getChildren().addAll(b, byLbl);
				Button button = new Button("i");
				button.setId("icon");
				setLayout.getChildren().addAll(button, qSetInformation);
				setLayout.setAlignment(Pos.CENTER_LEFT);
				quizletSets.add(setLayout);
			}
		}
		
		
		
		VBox listLayout = new VBox(20);
		listLayout.getChildren().addAll(quizletSets);
		listLayout.setAlignment(Pos.CENTER);
		listLayout.setPadding(new Insets(25));
		 
		ScrollPane scroller = new ScrollPane(listLayout);
		scroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroller.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		
		BorderPane mainLayout = new BorderPane();
		mainLayout.setCenter(scroller);
		mainLayout.setTop(l);

		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
		
		
	}

}
