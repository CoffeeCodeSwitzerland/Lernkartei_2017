package gui;

import java.util.ArrayList;

import javax.swing.JFrame;

import application.MainController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * Hauptfenster
 * 
 * @author miro-albrecht
 *
 */
public class MainView extends View
{
	public MainView (String setName, Stage primaryStage, MainController controller)
	{
		super (setName, primaryStage);

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
		startButton.setOnAction(e -> controller.show("doorview"));
		statButton.setOnAction(e -> controller.show("statisticsview"));
		optionsButton.setOnAction(e -> controller.show("optionsview"));
		gameButton.setOnAction(e -> controller.show("gameview"));
		helpButton.setOnAction(e -> controller.show("helpview"));
		quitButton.setOnAction(e -> {
			// TODO controller close
			getWindow().close();
		});
		getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override public void handle(WindowEvent t) {
		        System.out.println("CLOSING");
				getWindow().close();
		    }
		});

		this.setScene(new Scene(layout, 800, 450));
		this.show();
	}
}