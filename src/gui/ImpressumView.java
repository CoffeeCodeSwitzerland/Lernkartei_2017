package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
		
		//Labels (für die Infotexte)
		Label labelTitel = new Label("Impressum");
		Label labelText = new Label("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");
		labelText.setWrapText(true);
		
		//Box für die Navigation
		HBox hBox = new HBox(20);
		hBox.getChildren().addAll(backBtn);
			
		//Box für Labels
		VBox vBox = new VBox(20);
		vBox.getChildren().addAll(labelTitel, labelText);
		vBox.setAlignment(Pos.CENTER);
		
		// Behaviour
		backBtn.setOnAction(e -> getController().showMain());
		
		// Layout
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(15));
		borderPane.setBottom(hBox);
		borderPane.setCenter(vBox);
		
		setupScene(new Scene(borderPane, FXSettings.OPTIMAL_WIDTH, FXSettings.OPTIMAL_HEIGHT));
	}

	@Override
	public void refreshView ()
	{
		return;
	}

}
