package gui;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Lernkartei extends Application
{
	Stage		window;

	Scene		homeScene;
	Scene		optionsScene;

	BorderPane	tempBoerderPane;
	VBox		tempVBox;

	public static void main (String[] args)
	{
		launch(args);
	}

	@Override
	public void start (Stage primaryStage) throws Exception
	{
		window = primaryStage;
		window.setTitle("Lernkartei [Alpha]");

		homeScene = new Scene(home(), 800, 450);
		optionsScene = new Scene(options(), 800, 450);

		homeScene.getStylesheets().add("style.css");
		optionsScene.getStylesheets().add("style.css");

		window.setScene(homeScene);
		window.show();
	}

	private BorderPane home ()
	{
		// Buttons
		Button startButton = new Button("Lernen");
		Button statButton = new Button("Statistiken");
		Button optionsButton = new Button("Optionen");
		Button gameButton = new Button("Jump 'n' Run");
		Button helpButton = new Button("Hilfe");
		Button quitButton = new Button("Beenden");

		ArrayList<Button> menuButtons = new ArrayList<>();

		menuButtons.add(startButton);
		menuButtons.add(statButton);
		menuButtons.add(optionsButton);
		menuButtons.add(gameButton);
		menuButtons.add(helpButton);
		menuButtons.add(quitButton);

		// Set max width
		for (Button b : menuButtons)
		{
			b.setMaxWidth(200);
		}

		// Layout
		VBox menu = new VBox();
		menu.setPadding(new Insets(10));
		menu.setSpacing(10);
		menu.setAlignment(Pos.CENTER);
		menu.getChildren().addAll(menuButtons);

		tempBoerderPane = new BorderPane();
		tempBoerderPane.setCenter(menu);

		// Behaviour
		menuButtons.get(2).setOnAction(e -> window.setScene(optionsScene));
		menuButtons.get(5).setOnAction(e -> window.close());

		return tempBoerderPane;
	}

	private VBox options ()
	{
		Button resetStats = new Button("Statistiken zurücksetzten");
		CheckBox enableSound = new CheckBox("Audio");
		ColorPicker col = new ColorPicker();
		Button applyColor = new Button("Farbe speichern");

		Button back = new Button("Zurück");

		resetStats.setMaxWidth(200);
		enableSound.setMaxWidth(200);
		col.setMaxWidth(200);
		applyColor.setMaxWidth(200);
		back.setMaxWidth(200);

		tempVBox = new VBox();
		tempVBox.setPadding(new Insets(10));
		tempVBox.setSpacing(10);
		tempVBox.setAlignment(Pos.CENTER);
		tempVBox.getChildren().addAll(resetStats, enableSound, col, applyColor, back);

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
		back.setOnAction(e -> window.setScene(homeScene));

		return tempVBox;
	}
}
