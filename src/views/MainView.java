package views;

import globals.Globals;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import models.GameModel;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;

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

	//Wir haben das ganze mit den BorderPanes gelöst.
	BorderPane mainLayout = new BorderPane();
	BorderPane helpbtn = new BorderPane();
	BorderPane UserBtn = new BorderPane();
	BorderPane startBtn = new BorderPane();
	BorderPane stat2Btn = new BorderPane();
	BorderPane optionsBtn = new BorderPane();
	BorderPane gameBtn = new BorderPane();
	AppButton loginBtn = new AppButton("Login");
	BorderPane quitBtn = new BorderPane();
	Label lueckenfueller1 = new Label("");
	Label lueckenfueller2 = new Label("");

	VBox menuLayout = new VBox();
	HBox loginBox = new HBox();
	
	@Override
	public Parent constructContainer() {
		String title = Globals.appTitle + " " + Globals.appVersion;
		this.getWindow().setTitle(title);

		debug.Debugger.out("constructing MainView Container with title '"+title+"'...");
		getFXController().getMyFXStage().setTitle(title);
		
		//Hier wird mit Koordinaten Psotionen angegeben. 
		//Die Lückenfüller sind da weil eine Spalte nicht Nichts enthalten kann wegen 
		//dem Abstand. Es würde es einfach ignorieren. Deswegen 2 leere Labels da.
		GridPane gridpane = new GridPane();
		gridpane.setHgap(10);
		gridpane.setVgap(10);
	    gridpane.add(startBtn, 3, 3); 	
	    gridpane.add(UserBtn, 6, 2);  
	    gridpane.add(stat2Btn, 3, 1);  
	    gridpane.add(quitBtn, 3, 5);  
	    gridpane.add(gameBtn, 1, 4); 
	    gridpane.add(optionsBtn, 6, 4);
	    gridpane.add(helpbtn, 1, 2);
	    gridpane.setAlignment(Pos.CENTER);
	    gridpane.add(lueckenfueller1 , 2 ,3);
	    gridpane.add(lueckenfueller2 , 5 ,3);
			
	    //Behavior
	    startBtn.setOnMouseClicked(e -> getFXController().showView("doorview"));
		stat2Btn.setOnMouseClicked(e -> getFXController().showView("statsview"));
		optionsBtn.setOnMouseClicked(e -> getFXController().showView("optionsview"));
		gameBtn.setOnMouseClicked(e -> getFXController().showView("gameview"));
		helpbtn.setOnMouseClicked(e -> getFXController().showView("helpview"));
		quitBtn.setOnMouseClicked(e ->
		{
			debug.Debugger.out("closing button");
			GameModel gm = (GameModel) getFXController().getModel("game");
			if (gm != null) gm.dispose();
			getWindow().close();
		});		
		
		// IDs um im CSS die Bilder einzufügen.
		startBtn.setId("startbtn");
		stat2Btn.setId("stat2btn");
		optionsBtn.setId("optionsbtn");
		gameBtn.setId("gamebtn");
		loginBtn.setId("loginBtn");
		UserBtn.setId("UserBtn");
		helpbtn.setId("helpbtn");
		quitBtn.setId("quitBtn");
		
		//Grösse muss angegeben werden oder es überschatten alles....
		loginBtn.setMinSize(90.0, 90.0);
		optionsBtn.setMinSize(100.0, 100.0);
		stat2Btn.setMinSize(100.0, 100.0);
		gameBtn.setMinSize(100.0, 100.0);
		UserBtn.setMinSize(100.0, 100.0);
		helpbtn.setMinSize(90.0, 90.0);
		startBtn.setMinSize(120.0,120.0);
		quitBtn.setMinSize(100.0,100.0);
		lueckenfueller1.setMinSize(100.0,100.0);
		lueckenfueller2.setMinSize(100.0,100.0);
		
		
		getWindow().setOnCloseRequest(e ->
		{
			debug.Debugger.out("closing window");
			GameModel gm = (GameModel) getFXController().getModel("game");
			if (gm != null) gm.dispose();
			getWindow().close();
		});
		
		//debug.Debugger.out("Set impressum...");
		
		return gridpane;
	}

	@Override
	public void refreshView ()
	{
	}
}