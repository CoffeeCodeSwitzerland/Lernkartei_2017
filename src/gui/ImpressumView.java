package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
		
		//Box für die Buttons
		HBox hBox = new HBox(20);
		hBox.getChildren().addAll(backBtn);
			
		// Behaviour
		backBtn.setOnAction(e -> getController().showMain());
		
		// Layout
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(15));
		borderPane.setBottom(hBox);
		
		setupScene(new Scene(borderPane, FXSettings.OPTIMAL_WIDTH, FXSettings.OPTIMAL_HEIGHT));
	}

	@Override
	public void refreshView ()
	{
		return;
	}

}
