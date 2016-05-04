package gui;

import application.Constants;
import debug.Debugger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	public MainView (String setName, Controller controller)
	{
		super(setName, controller);
		FXSettings.getPrimaryStage().setTitle(Constants.appTitle + " " + Constants.appVersion);

		// Buttons
		AppButton startBtn = new AppButton("Lernen");
		AppButton statBtn = new AppButton("Statistiken");
		AppButton optionsBtn = new AppButton("Optionen");
		AppButton gameBtn = new AppButton("Jump 'n' Run");
		AppButton helpBtn = new AppButton("Hilfe");
		AppButton quitBtn = new AppButton("Beenden");

		// Layout für Menu Items
		VBox menuLayout = new VBox();
		menuLayout.setPadding(new Insets(10));
		menuLayout.setSpacing(10);
		menuLayout.setAlignment(Pos.CENTER);
		menuLayout.getChildren().addAll(startBtn, statBtn, optionsBtn, gameBtn, helpBtn, quitBtn);

		// Main Layout
		BorderPane mainLayout = new BorderPane();
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
		
		// Impressum
		Image impressumImg = new Image("gui/pictures/ImpressumIcon.png");
		ImageView impImgView = new ImageView(impressumImg);
		mainLayout.setBottom(impImgView);
		impImgView.setOnMouseClicked(e -> getController().getView("impressumview").show());

		mainLayout.setId("main");

		this.setupScene(new Scene(mainLayout, FXSettings.OPTIMAL_WIDTH, FXSettings.OPTIMAL_HEIGHT));
		this.show();
	}

	@Override
	public void refreshView ()
	{
		return;
	}
}