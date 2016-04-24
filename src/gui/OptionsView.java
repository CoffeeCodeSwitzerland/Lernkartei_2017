package gui;

import application.MainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
		super (setName, primaryStage);

		// Contorls (Sample)
		Button resetStats = new Button("Statistiken zurücksetzten");
		CheckBox enableSound = new CheckBox("Audio");
		ColorPicker col = new ColorPicker();
		Button applyColor = new Button("Farbe speichern");

		// Setzt maximale Breite (ArrayList möglich)
		Button back = new Button("Zurück");
		resetStats.setMaxWidth(200);
		enableSound.setMaxWidth(200);
		col.setMaxWidth(200);
		applyColor.setMaxWidth(200);
		back.setMaxWidth(200);

		// Erstellt Layout
		VBox tempVBox = new VBox();
		tempVBox.setPadding(new Insets(10));
		tempVBox.setSpacing(10);
		tempVBox.setAlignment(Pos.CENTER);
		tempVBox.getChildren().addAll(resetStats, enableSound, col, applyColor, back);

		// Setzt Verhalten
		// TODO Design 100% auslagern
		applyColor.setOnAction(e -> {
			if (col.getValue().getBrightness() < 0.6)
			{
				enableSound.setStyle("-fx-text-fill: white");
			}
			else
			{
				enableSound.setStyle("-fx-text-fill: black");
			}
			tempVBox.setStyle("-fx-background-color: rgb("
					+ col.getValue().getRed() * 255 + ","
					+ col.getValue().getGreen() * 255 + ","
					+ col.getValue().getBlue() * 255 + ")");
		});

		// Behaviour
		back.setOnAction(e -> controller.show("mainview"));

		this.setScene(new Scene(tempVBox, 800, 450));
	}
}
