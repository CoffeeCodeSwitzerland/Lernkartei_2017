package views;

import debug.Debugger;
import debug.Logger;
import globals.Globals;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import models.GameModel;
import mvc.fx.FXController;
import mvc.fx.FXView;

/**
 * Hauptfenster
 * 
 * @author miro-albrecht
 *
 */
public class MainView extends FXView
{
	public MainView(String newName, FXController newController) {
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}

	BorderPane mainLayout = new BorderPane();
	AppButton startBtn = new AppButton("_Lernkarteien");
//	AppButton statBtn = new AppButton("Statistiken");
	AppButton stat2Btn = new AppButton("Statistiken");
	AppButton optionsBtn = new AppButton("_Optionen");
	AppButton gameBtn = new AppButton("_Jump 'n' Run");
	//AppButton helpBtn = new AppButton("_Hilfe");
	AppButton quitBtn = new AppButton("B_eenden");
	VBox menuLayout = new VBox();

	
	
	
	@Override
	public Parent constructContainer() {
		String title = Globals.appTitle + " " + Globals.appVersion;
		getController().getMyFXStage().setTitle(title);

		// Buttons
		startBtn.setId("startbtn");
//		statBtn.setId("statbtn");
		stat2Btn.setId("stat2btn");
		optionsBtn.setId("optionsbtn");
		gameBtn.setId("gamebtn");
		//helpBtn.setId("helpbtn");
		quitBtn.setId("quitbtn");
		
		Logger.log("Instanziere Div....");
		// Layout für Menu Items
		menuLayout.setPadding(new Insets(10));
		menuLayout.setSpacing(15);
		menuLayout.setAlignment(Pos.CENTER);
		menuLayout.getChildren().addAll(startBtn,/* statBtn,*/ stat2Btn, optionsBtn, gameBtn, /*helpBtn,*/ quitBtn);

		// Main Layout
		mainLayout.setPadding(new Insets(5));
		mainLayout.setCenter(menuLayout);

		// Behaviour
		startBtn.setOnAction(e -> getController().showView("doorview"));
		//statBtn.setOnAction(e -> getController().showView("statisticsview"));
		stat2Btn.setOnAction(e -> getController().showView("statsview"));
		optionsBtn.setOnAction(e -> getController().showView("optionsview"));
		gameBtn.setOnAction(e -> getController().showView("gameview"));
		//helpBtn.setOnAction(e -> getController().getView("helpview").show());

		quitBtn.setOnAction(e ->
		{
			Debugger.out("closing 1 (Beenden Button)");
			GameModel gm = (GameModel) getController().getModel("game");
			if (gm != null) gm.dispose();
			getWindow().close();
		});

		getWindow().setOnCloseRequest(e ->
		{
			Debugger.out("closing 2");
			GameModel gm = (GameModel) getController().getModel("game");
			if (gm != null) gm.dispose();
			getWindow().close();
		});
		
		Logger.log("Set impressum....");

		// Impressum Leerbox (IMG in CSS eingefügt)
		BorderPane imgPane = new BorderPane();
		imgPane.setId("helpbtn");
		imgPane.setOnMouseClicked(e -> getController().showView("helpview"));
		imgPane.setMinSize(20.0, 50.0);
		mainLayout.setBottom(imgPane);

		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
	}
}