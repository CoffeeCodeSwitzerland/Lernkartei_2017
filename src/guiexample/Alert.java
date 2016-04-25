package guiexample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * Diese Klasse zeigt mit JavaFx verschiedene Alertfenster an.
 * 
 * @author miro-albrecht
 *
 */
public final class Alert
{

	/**
	 * Zeigt ein kleines Infofenster an. Der User muss das Fenster schliessen,
	 * bevor er andere Fenster der Anwendung bedienen kann.
	 * 
	 * @param title
	 *            Der Titel des Fensters
	 * @param message
	 *            Die Narchricht, die angezeigt wird. Text wird nicht von selbst
	 *            gewrapt.
	 * @param button
	 *            Text des Buttons
	 */
	public static void simpleInfoBox (String title, String message, String button)
	{
		// Neues Fenster
		Stage window = new Stage();
		// Minimalistischer Stil ohne Buttons um das Fenster zu minimieren oder
		// zu maximieren
		window.initStyle(StageStyle.UTILITY);
		window.setResizable(false);
		// Blockiere alle anderen Fenster
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);

		// Neues Label und neuer Button
		Label l = new Label(message);
		Button b = new Button(button);
		// Schliesst Fenster wenn der User auf OK klickt
		b.setOnAction(e -> window.close());

		// Neues Layout
		VBox layout = new VBox(20);
		layout.getChildren().addAll(l, b);
		// Zentriert Elemente
		layout.setAlignment(Pos.CENTER);

		// Passt Breite des Fensters an den Text an
		int width;
		int x = 6;
		int y = 150;

		width = message.length() * x + y;

		// Zeigt Fenster an
		window.setScene(new Scene(layout, width, 150));
		window.show();
	}

	/**
	 * Zeigt ein kleines Infofenster an. Der User muss das Fenster schliessen,
	 * bevor er andere Fenster der Anwendung bedienen kann. Button zeigt "OK"
	 * an.
	 * 
	 * @param title
	 *            Der Titel des Fensters
	 * @param message
	 *            Die Narchricht, die angezeigt wird. Text wird nicht von selbst
	 *            gewrapt.
	 */
	public static void simpleInfoBox (String title, String message)
	{
		simpleInfoBox(title, message, "OK");
	}
}
