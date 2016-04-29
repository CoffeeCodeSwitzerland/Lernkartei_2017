package gui;

import javafx.geometry.Insets;
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
 * @author miro albrecht
 *
 */
public final class Alert
{
	/**
	 * Zeigt ein kleines Infofenster an. Der User muss das Fenster schliessen,
	 * bevor er andere Fenster der Anwendung bedienen kann. Die Breite des
	 * Fensters passt sich dem Text an.
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

	private static String output = "";

	/**
	 * Kleines Fenster, dass Input (String) vom User abfragt.
	 * 
	 * @param title
	 *            Der Titel des Fensters
	 * @param message
	 *            Die Narchricht, die angezeigt wird. Text wird nicht von selbst
	 *            gewrapt.
	 * @param fieldWidth
	 *            Setzt die Breite des Textfeldes
	 * @return String mit dem Userinput
	 */
	public static String simpleString (String title, String message, double fieldWidth)
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
		TextField tf = new TextField();
		tf.setMaxWidth(fieldWidth);
		Button b = new Button("OK");
		// Schliesst Fenster wenn der User auf OK klickt
		b.setOnAction(e -> {
			output = tf.getText();
			window.close();
		});

		// Neues Layout
		VBox layout = new VBox(20);
		layout.getChildren().addAll(l, tf, b);
		// Zentriert Elemente
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(20));

		int width;
		int x = 6;
		int y = 150;

		width = message.length() * x + y;

		// Zeigt Fenster an
		window.setScene(new Scene(layout, width, 150));
		window.showAndWait();

		return output;
	}

	/**
	 * Kleines Fenster, dass Input (String) vom User abfragt.
	 * 
	 * @param title
	 *            Der Titel des Fensters
	 * @param message
	 *            Die Narchricht, die angezeigt wird. Text wird nicht von selbst
	 *            gewrapt.
	 * @return String mit dem Userinput
	 */
	public static String simpleString (String title, String message)
	{
		return simpleString(title, message, 150);
	}

	static boolean okay = false;

	public static boolean ok (String title, String message)
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

		Button bo = new Button("OK");
		Button ba = new Button("Abbrechen");
		// Schliesst Fenster wenn der User auf OK klickt
		bo.setOnAction(e -> {
			okay = true;
			window.close();
		});

		ba.setOnAction(e -> {
			okay = false;
			window.close();
		});

		HBox buttons = new HBox(20);
		buttons.setAlignment(Pos.CENTER);
		buttons.setPadding(new Insets(20));

		buttons.getChildren().addAll(bo, ba);

		// Neues Layout
		VBox layout = new VBox(20);
		layout.getChildren().addAll(l, buttons);
		// Zentriert Elemente
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(40, 20, 20, 20));

		int width;
		int x = 6;
		int y = 150;

		width = message.length() * x + y;

		// Zeigt Fenster an
		window.setScene(new Scene(layout, width, 150));
		window.showAndWait();

		return okay;
	}
}
