package guiexample;

import java.util.ArrayList;

import controls.MainController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Erste Version des GUI. Noch unvollständig
 * 
 * @author miro-albrecht
 * @deprecated Wurde ersetzt durch {@link MainController} und View-Klassen
 */
public class Lernkartei extends Application
{
	// Das Fenster
	Stage		window;

	// Alle Szenen
	Scene		homeScene;
	Scene		optionsScene;

	// Alle verwendeten Layouts
	BorderPane	tempBorderPane;
	VBox		tempVBox;

	String		stylePath	= "views/style.css";

	public static void main (String[] args)
	{
		// Startet JavaFX und ruft start() auf
		launch(args);
	}

	@Override
	public void start (Stage primaryStage) throws Exception
	{
		// Fenster und Titel werden gesetzt
		window = primaryStage;
		window.setTitle("Lernkartei [Alpha]");

		// Erstellt alle Szenen
		homeScene = new Scene(home(), 800, 450);
		optionsScene = new Scene(options(), 800, 450);

		// Setzt CSS für die Szenen
		homeScene.getStylesheets().add(stylePath);
		optionsScene.getStylesheets().add(stylePath);

		// Setzt erste Szene und zeigt Fenster an
		window.setScene(homeScene);
		window.show();
	}

	/**
	 * Methode für den Startbildschirm der Lernkartei
	 * 
	 * @return BorderPane für die Erstellung der Szene
	 */
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

		tempBorderPane = new BorderPane();
		tempBorderPane.setCenter(menu);

		// Verhalten der Buttons
		menuButtons.get(2).setOnAction(e -> window.setScene(optionsScene));
		menuButtons.get(4).setOnAction(e -> help());
		menuButtons.get(5).setOnAction(e -> window.close());

		return tempBorderPane;
	}

	/**
	 * Funktion für die Optionen
	 * 
	 * @return VBox für die Erstellung der Szene
	 */
	private VBox options ()
	{
		// Contorls
		Button resetStats = new Button("Statistiken zurücksetzten");
		CheckBox enableSound = new CheckBox("Audio");
		ColorPicker col = new ColorPicker();
		Button applyColor = new Button("Farbe speichern");

		// Setzt maximale Breite
		Button back = new Button("Zurück");
		resetStats.setMaxWidth(200);
		enableSound.setMaxWidth(200);
		col.setMaxWidth(200);
		applyColor.setMaxWidth(200);
		back.setMaxWidth(200);

		// Erstellt Layout
		tempVBox = new VBox();
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

		back.setOnAction(e -> window.setScene(homeScene));

		return tempVBox;
	}

	private void help ()
	{
		Image helpdesk = new Image("views/helpdesk.jpg", true);

		ImageView view = new ImageView(helpdesk);

		VBox center = new VBox();
		center.setAlignment(Pos.CENTER);
		center.getChildren().add(view);

		Stage helpWindow = new Stage();
		helpWindow.setTitle("Lernkartei Hilfe [Alpha]");
		helpWindow.setResizable(false);
		helpWindow.setScene(new Scene(center, 800, 550));
		helpWindow.show();
	}
}
