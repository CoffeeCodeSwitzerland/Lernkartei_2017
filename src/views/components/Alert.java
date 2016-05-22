package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * Diese Klasse zeigt mit JavaFx verschiedene Alertfenster an. Die Fenster
 * können auch Daten zurückgeben.
 * 
 * @author miro albrecht
 *
 */
public final class Alert
{
	static Stage tempStage;

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
		Stage window = buildWindow(title);
		Label l = new Label(message);
		Button b = new Button(button);

		b.setOnAction(e -> window.close());

		VBox layout = new VBox(20);
		layout.getChildren().addAll(l, b);
		layout.setAlignment(Pos.CENTER);

		// Passt Breite des Fensters an den Text an
		int width;
		int x = 6;
		int y = 150;
		width = message.length() * x + y;

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
		simpleInfoBox(title, message, "_OK");
	}

	// -------------------------------------------------------------------------------------------------------------

	private static String output = null;
	private static String tempOutput = null;

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
	public static String simpleString (String title, String message, String field, double fieldWidth)
	{
		Stage window = buildWindow(title);

		Label l = new Label(message);

		TextField tf = new TextField(field);
		tf.setMaxWidth(fieldWidth);

		tf.setOnKeyReleased(e -> {
			if (e.getCode().equals(KeyCode.ENTER))
			{
				tempOutput = tf.getText();
				window.close();
			}
		});
		
		Button b = new Button("_OK");
		b.setOnAction(e ->
		{
			tempOutput = tf.getText();
			window.close();
		});

		VBox layout = new VBox(20);
		layout.getChildren().addAll(l, tf, b);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(20));

		int width;
		int x = 6;
		int y = 150;

		width = message.length() * x + y;

		window.setScene(new Scene(layout, width, 150));
		window.showAndWait();
		output = tempOutput;
		tempOutput = null;
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
	 * @param field
	 *            Setzt einen Default-Text
	 * @return String mit dem Userinput
	 */
	public static String simpleString (String title, String message, String field)
	{
		return simpleString(title, message, field, 150);
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
		return simpleString(title, message, "", 150);
	}

	// -------------------------------------------------------------------------------------------------------------

	static boolean okay = false;
	static boolean tempOkay = false;

	/**
	 * Fenster mit OK- und Abbrechen-Buttons
	 * 
	 * @param title
	 *            Der Titel des Fensters
	 * @param message
	 *            Die Narchricht, die angezeigt wird. Text wird nicht von selbst
	 *            gewrapt.
	 * @return True, wenn der User akzeptiert, sonst false
	 */
	public static boolean ok (String title, String message)
	{
		Stage window = buildWindow(title);
		Label l = new Label(message);

		Button bo = new Button("_OK");
		Button ba = new Button("_Abbrechen");

		bo.setOnAction(e ->
		{
			tempOkay = true;
			window.close();
		});

		ba.setOnAction(e ->
		{
			tempOkay = false;
			window.close();
		});

		HBox buttons = new HBox(20);
		buttons.setAlignment(Pos.CENTER);
		buttons.setPadding(new Insets(20));
		buttons.getChildren().addAll(bo, ba);

		VBox layout = new VBox(20);
		layout.getChildren().addAll(l, buttons);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(40, 20, 20, 20));

		int width;
		int x = 6;
		int y = 150;

		width = message.length() * x + y;

		window.setScene(new Scene(layout, width, 150));
		window.showAndWait();

		okay = tempOkay;
		tempOkay = false;
		
		return okay;
	}

	// -------------------------------------------------------------------------------------------------------------

	private static int tempChoise = -1;
	private static int choise = -1;

	public static int complexChoiceBox (String title, String message, String... options)
	{
		Stage window = buildWindow(title);

		Label l = new Label(message);

		AppButton[] buttons = new AppButton[options.length];

		for (int i = 0; i < options.length; i++)
		{
			final int j = i;
			buttons[j] = new AppButton(options[j]);
			buttons[j].setOnAction(e ->
			{
				tempChoise = j;
				window.close();
			});
		}
		
		VBox layout = new VBox(20);
		layout.getChildren().add(l);
		layout.getChildren().addAll(buttons);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(20));

		int width;
		int x = 6;
		int y = 150;
		width = message.length() * x + y;

		int height;
		height = options.length * 25 + 150;

		window.setScene(new Scene(layout, width, height));
		window.showAndWait();

		choise = tempChoise;
		tempChoise = -1;
		return choise;
	}

	// -------------------------------------------------------------------------------------------------------------

	private static Stage buildWindow (String title)
	{
		tempStage = new Stage();
		tempStage.initStyle(StageStyle.UTILITY);			// Einfaches Fenster
															// ohne 'minimiere'
															// und 'maximiere'
															// Buttons
		tempStage.setResizable(false);						// Verbiete Änderung
																// der Grösse
		tempStage.initModality(Modality.APPLICATION_MODAL);	// Blockiere alle
																// anderen
																// Fenster
		tempStage.setTitle(title);							// Setze Titel
		return tempStage;
	}
}
