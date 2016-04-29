package gui;

import application.Constants;
import application.MainController;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ImpressumView extends View
{

	public ImpressumView (String setName, Stage primary, MainController controller)
	{
		super(setName, primary, controller);
		
		// Buttons
		AppButton backBtn = new AppButton("Zurück");
		
		// Behaviour
		backBtn.setOnAction(e -> getController().showMain());
		
		// Layout
		BorderPane layout = new BorderPane();
		layout.setCenter(backBtn);
		
		setupScene(new Scene(layout, Constants.OPTIMAL_WIDTH, Constants.OPTIMAL_HEIGHT));
	}

	@Override
	public void refreshView ()
	{
		return;
	}

}
