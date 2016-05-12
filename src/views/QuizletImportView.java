package views;

import java.util.ArrayList;

import controls.Globals;
import javafx.scene.Parent;
import javafx.scene.control.Label;
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
		
		for (String s : getController().getModel("quizlet").getDataList("search:jsbms"))
		{
			String[] setInfo = s.split(Globals.SEPARATOR);
			AppButton b = new AppButton(setInfo[1]);
			HBox setLayout = new HBox(5);
			setLayout.getChildren().add(b);
			quizletSets.add(setLayout);
		}
		
		VBox listLayout = new VBox(20);
		listLayout.getChildren().addAll(quizletSets);
		
		BorderPane mainLayout = new BorderPane();
		mainLayout.setCenter(listLayout);
		mainLayout.setTop(l);

		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
		
		
	}

}
