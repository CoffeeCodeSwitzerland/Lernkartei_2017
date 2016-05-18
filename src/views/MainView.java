package views;

import controls.Globals;
import debug.Debugger;
import debug.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	Image impressumImg = new Image("views/pictures/ImpressumIcon.png");
	AppButton startBtn = new AppButton("_Lernkarteien");
	AppButton statBtn = new AppButton("Statistiken");
	AppButton stat2Btn = new AppButton("Statistiken-2");
	AppButton optionsBtn = new AppButton("_Optionen");
	AppButton gameBtn = new AppButton("_Jump 'n' Run");
	AppButton helpBtn = new AppButton("_Hilfe");
	AppButton quitBtn = new AppButton("B_eenden");
	VBox menuLayout = new VBox();

	
	
	
	@Override
	public Parent constructContainer() {
		String title = Globals.appTitle + " " + Globals.appVersion;
		getController().getMyFXStage().setTitle(title);

		// Buttons
		startBtn.setId("startbtn");
		statBtn.setId("statbtn");
		stat2Btn.setId("stat2btn");
		optionsBtn.setId("optionsbtn");
		gameBtn.setId("gamebtn");
		helpBtn.setId("helpbtn");
		quitBtn.setId("quitbtn");
		
		Logger.log("Instanziere Div....");
		// Layout für Menu Items
		menuLayout.setPadding(new Insets(10));
		menuLayout.setSpacing(10);
		menuLayout.setAlignment(Pos.CENTER);
		menuLayout.getChildren().addAll(startBtn, statBtn, stat2Btn, optionsBtn, gameBtn, helpBtn, quitBtn);

		// Main Layout
		mainLayout.setPadding(new Insets(5));
		mainLayout.setCenter(menuLayout);

		// Behaviour
		startBtn.setOnAction(e -> getController().getView("doorview").show());
		statBtn.setOnAction(e -> getController().getView("statisticsview").show());
		stat2Btn.setOnAction(e -> getController().getView("statsview").show());
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
		ImageView impImgView = new ImageView(impressumImg);
		mainLayout.setBottom(impImgView);
		impImgView.setOnMouseClicked(e -> getController().getView("impressumview").show());

		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
	}
}