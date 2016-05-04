package gui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import mvc.Controller;
import mvc.FXViewModel;

public class LearnView extends FXViewModel
{

	public LearnView (String setName, Controller controller)
	{
		super(setName, controller);

		AppButton backBtn = new AppButton("Zurück");
		backBtn.setOnAction(e -> getController().getView("boxview").show());
		
		BorderPane layout = new BorderPane(backBtn);
		
		setupScene(new Scene(layout, 800, 450));
	}

	@Override
	public void refreshView ()
	{
		
	}

}
