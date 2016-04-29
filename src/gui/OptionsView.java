package gui;

import application.Constants;
import application.MainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Optionen
 * 
 * @author miro-albrecht
 *
 */
public class OptionsView extends View
{
	public OptionsView (String setName, Stage primaryStage, MainController controller)
	{
		super (setName, primaryStage, controller);

		// Contorls (Sample)
		AppButton resetStats = new AppButton("Statistiken zurücksetzen");
		CheckBox enableSound = new CheckBox("Audio");
		CheckBox enableAnimation = new CheckBox("Animation");
		ColorPicker col = new ColorPicker();

		// Setzt maximale Breite der nicht-Button Elemente:
		enableSound.setMaxWidth(AppButton.DEFAULT_BUTTON_WIDTH);
		enableAnimation.setMaxWidth(AppButton.DEFAULT_BUTTON_WIDTH);
		col.setMaxWidth(AppButton.DEFAULT_BUTTON_WIDTH);

		// Buttons:
		AppButton applyColor = new AppButton("Farbe speichern");
		AppButton back = new AppButton("Zurück");

		// Erstellt Layout:
		VBox tempVBox = new VBox();
		tempVBox.setPadding(new Insets(10));
		tempVBox.setSpacing(10);
		tempVBox.setAlignment(Pos.CENTER);
		tempVBox.getChildren().addAll(resetStats, enableSound,enableAnimation, col, applyColor, back);

		// Setzt Verhalten  -> TODO in CSS auslagern
		applyColor.setOnAction(e -> {
			if (col.getValue().getBrightness() < 0.6)
			{
				enableSound.setStyle("-fx-text-fill: white"); // TODO in CSS auslagern
			}
			else
			{
				enableSound.setStyle("-fx-text-fill: black"); // TODO in CSS auslagern
			}
			tempVBox.setStyle("-fx-background-color: rgb("
					+ col.getValue().getRed() * 255 + ","
					+ col.getValue().getGreen() * 255 + ","
					+ col.getValue().getBlue() * 255 + ")");
		});

		// Behavior
		back.setOnAction(e -> controller.showMain());

		this.setupScene(new Scene(tempVBox, Constants.OPTIMAL_WIDTH, Constants.OPTIMAL_HEIGHT));
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}
}
