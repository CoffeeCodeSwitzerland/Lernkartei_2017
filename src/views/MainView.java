package views;

import globals.Globals;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import models.GameModel;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;
import views.components.CloseButton;

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
		super(newController);
		construct(newName);
	}

	BorderPane mainLayout = new BorderPane();
	AppButton startBtn = new AppButton("_Lernkarteien");
	AppButton stat2Btn = new AppButton("Statistiken");
	AppButton optionsBtn = new AppButton("_Optionen");
	AppButton gameBtn = new AppButton("_Jump 'n' Run");
	VBox menuLayout = new VBox();
	
	@Override
	public Parent constructContainer() {
		String title = Globals.appTitle + " " + Globals.appVersion;
		debug.Debugger.out("constructing MainView Container with title '"+title+"'...");
		getController().getMyFXStage().setTitle(title);

		// Buttons
		startBtn.setId("startbtn");
		stat2Btn.setId("stat2btn");
		optionsBtn.setId("optionsbtn");
		gameBtn.setId("gamebtn");
		
		debug.Debugger.out("Instanziere Div....");
		// Layout für Menu Items
		menuLayout.setPadding(new Insets(10));
		menuLayout.setSpacing(15);
		menuLayout.setAlignment(Pos.CENTER);

		CloseButton quitBtn = new CloseButton(getController());
		menuLayout.getChildren().addAll(startBtn,/* statBtn,*/ stat2Btn, optionsBtn, gameBtn, /*helpBtn,*/ quitBtn);

		// Main Layout
		mainLayout.setPadding(new Insets(5));
		mainLayout.setCenter(menuLayout);

		// Behaviour
		startBtn.setOnAction(e -> getController().showView("doorview"));
		stat2Btn.setOnAction(e -> getController().showView("statsview"));
		optionsBtn.setOnAction(e -> getController().showView("optionsview"));
		gameBtn.setOnAction(e -> getController().showView("gameview"));

		getWindow().setOnCloseRequest(e ->
		{
			debug.Debugger.out("closing window");
			GameModel gm = (GameModel) getController().getModel("game");
			if (gm != null) gm.dispose();
			getWindow().close();
		});
		
		debug.Debugger.out("Set impressum...");

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