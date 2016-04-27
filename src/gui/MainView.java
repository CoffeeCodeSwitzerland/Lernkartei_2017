package gui;

import application.Constants;
import application.MainController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
	private final String appTitle = "WISS Learn Cards [Alpha]";

	public MainView (String setName, Stage primaryStage, MainController controller)
	{
		super (setName, primaryStage);
		this.setMyController(controller);
		primaryStage.setTitle(appTitle);

		// Buttons
		AppButton startBtn 	 = new AppButton("Lernen");
		AppButton statBtn 	 = new AppButton("Statistiken");
		AppButton optionsBtn = new AppButton("Optionen");
		AppButton gameBtn 	 = new AppButton("Jump 'n' Run");
		AppButton helpBtn 	 = new AppButton("Hilfe");
		AppButton quitBtn 	 = new AppButton("Beenden");

		// Layout
		VBox menu = new VBox();
		menu.setPadding(new Insets(10));
		menu.setSpacing(10);
		menu.setAlignment(Pos.CENTER);

		// Fügt Buttons hinzu
		menu.getChildren().addAll(startBtn, statBtn, optionsBtn, gameBtn, helpBtn, quitBtn);
		menu.setId("mainlayout");
		BorderPane layout = new BorderPane();
		layout.setCenter(menu);

		// Behaviour
		startBtn.setOnAction(e -> controller.show("doorview"));
		statBtn.setOnAction(e -> controller.show("statisticsview"));
		optionsBtn.setOnAction(e -> controller.show("optionsview"));
		gameBtn.setOnAction(e -> controller.show("gameview"));
		//gameBtn.setDisable(true); // TODO verbessere Spielintegration
		helpBtn.setOnAction(e -> controller.show("helpview"));
		quitBtn.setOnAction(e -> {
			// TODO controller close
			getWindow().close();
		});
		getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override public void handle(WindowEvent t) {
		        System.out.println("CLOSING");
				getWindow().close();
		    }
		});

		layout.setId("main");
		
		this.setScene(new Scene(layout, Constants.OPTIMAL_WIDTH, Constants.OPTIMAL_HEIGHT));
		this.show();
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}
}