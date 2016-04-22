package gui;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.MainController;


/**
 * Hauptfenster
 * 
 * @author miro-albrecht
 *
 */
public class MainView extends SceneView
{
	public MainView (Stage primaryStage, MainController controller)
	{
		window = primaryStage;

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

		// Setzt maximale Breite aller Button, damit sie alle gleich gross
		// dargestellt werden
		for (Button b : menuButtons)
		{
			b.setMaxWidth(200);
		}

		// Layout

		VBox menu = new VBox();
		menu.setPadding(new Insets(10));
		menu.setSpacing(10);
		menu.setAlignment(Pos.CENTER);

		// Fügt Buttons hinzu
		menu.getChildren().addAll(menuButtons);

		BorderPane layout = new BorderPane();
		layout.setCenter(menu);

		// Behaviour
		startButton.setOnAction(e -> controller.showDoors());
		optionsButton.setOnAction(e -> controller.showOptions());
		helpButton.setOnAction(e -> controller.showHelp());
		quitButton.setOnAction(e -> {
			// TODO controller close
			window.close();
		});

		scene = new Scene(layout, 800, 450);

		show();
		window.show();
	}
}
