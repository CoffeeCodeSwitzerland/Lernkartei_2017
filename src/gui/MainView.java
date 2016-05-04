package gui;

import application.Constants;
import debug.Debugger;
import debug.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import models.GameModel;
import mvc.Controller;
import mvc.FXSettings;
import mvc.FXView;


/**
 * Hauptfenster
 * 
 * @author miro-albrecht
 *
 */
public class MainView extends FXView
{
//	Image impressumImg = new Image("gui/pictures/ImpressumIcon.png");
	AppButton startBtn = new AppButton("Lernen");
	AppButton statBtn = new AppButton("Statistiken");
	AppButton optionsBtn = new AppButton("Optionen");
	AppButton gameBtn = new AppButton("Jump 'n' Run");
	AppButton helpBtn = new AppButton("Hilfe");
	AppButton quitBtn = new AppButton("Beenden");
	VBox menuLayout = new VBox();
	BorderPane mainLayout = new BorderPane();

	public MainView (String setName, Controller controller)
	{
		super(setName, controller);
		FXSettings.getPrimaryStage().setTitle(Constants.appTitle + " " + Constants.appVersion);

		// Buttons
	
		Logger.log("Instanziere Div....");
		// Layout für Menu Items
		menuLayout.setPadding(new Insets(10));
		menuLayout.setSpacing(10);
		menuLayout.setAlignment(Pos.CENTER);
		menuLayout.getChildren().addAll(startBtn, statBtn, optionsBtn, gameBtn, helpBtn, quitBtn);

		// Main Layout
		mainLayout.setPadding(new Insets(5));
		mainLayout.setCenter(menuLayout);

		// Behaviour
		startBtn.setOnAction(e -> getController().getView("doorview").show());
		statBtn.setOnAction(e -> getController().getView("statisticsview").show());
		optionsBtn.setOnAction(e -> getController().getView("optionsview").show());
		gameBtn.setOnAction(e -> getController().getView("gameview").show());
		helpBtn.setOnAction(e -> getController().getView("helpview").show());

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
		// Impressum
//		ImageView impImgView = new ImageView(impressumImg);
//		mainLayout.setBottom(impImgView);
//		impImgView.setOnMouseClicked(e -> getController().getView("impressumview").show());

		mainLayout.setId("main");

		Logger.log("Set scene....");

		setupScene(new Scene(mainLayout, FXSettings.OPTIMAL_WIDTH, FXSettings.OPTIMAL_HEIGHT));

		Logger.log("Show....");
		show();
	}

	@Override
	public void refreshView ()
	{
		return;
	}
}