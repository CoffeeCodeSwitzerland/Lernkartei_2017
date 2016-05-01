package gui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import mvc.Controller;
import mvc.FXSettings;
import mvc.FXView;

public class ImpressumView extends FXView
{
	public ImpressumView (String setName, Controller controller)
	{
		super(setName, controller);
		
		// Buttons
		AppButton backBtn = new AppButton("Zurück");
		
		// Behaviour
		backBtn.setOnAction(e -> getController().showMain());
		
		// Layout
		BorderPane layout = new BorderPane();
		layout.setCenter(backBtn);
		
		setupScene(new Scene(layout, FXSettings.OPTIMAL_WIDTH, FXSettings.OPTIMAL_HEIGHT));
	}

	@Override
	public void refreshView ()
	{
		return;
	}

}
