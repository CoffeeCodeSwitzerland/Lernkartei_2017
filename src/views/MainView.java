package views;

import globals.Globals;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.GameModel;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.HoverButton;

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

	
	
	// Graphic Buttons:
	HoverButton helpbtn = new HoverButton(this,"Hilfe...","helpview","helpbtn");
	HoverButton userLoginBtn = new HoverButton(this,"Login","","UserBtn");
	HoverButton lernenStartBtn = new HoverButton(this,"Lernen...","doorview", "lernenbtn");
	HoverButton statisticBtn = new HoverButton(this,"Statistik...","statsview", "stat2btn");
	HoverButton optionsBtn = new HoverButton(this,"Optionen...","optionsview","optionsbtn");
	HoverButton gameBtn = new HoverButton(this,"Jumo 'n' Run...","gameview","gamebtn");
	BorderPane quitBtn = new BorderPane();
	
	// FX braucht 2 Lückenfüller für das Gridpane zu füllen:
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
		
		//Und "Nein" man kann nicht das gleiche Label an 2 verschiedenen Stellen
		//hinzufügen. 
		GridPane gridpane = new GridPane();
	    gridpane.setAlignment(Pos.CENTER);
		gridpane.setHgap(4);
		gridpane.setVgap(4);
	    gridpane.add(lernenStartBtn, 3, 3); 	
	    gridpane.add(userLoginBtn, 6, 2);  
	    gridpane.add(statisticBtn, 3, 1);  
	    gridpane.add(quitBtn, 3, 5);  
	    gridpane.add(gameBtn, 1, 4); 
	    gridpane.add(optionsBtn, 6, 4);
	    gridpane.add(helpbtn, 1, 2);
	    gridpane.add(lueckenfueller1 , 2 ,3);
	    gridpane.add(lueckenfueller2 , 5 ,3);
	    
	    //Behavior
		//optionsBtn.setOnMouseClicked(e -> getFXController().showView("optionsview"));
		//gameBtn.setOnMouseClicked(e -> getFXController().showView("gameview"));
		//helpbtn.setOnMouseClicked(e -> getFXController().showView("helpview"));

		quitBtn.setOnMouseClicked(e ->
		{
			debug.Debugger.out("closing button");
			GameModel gm = (GameModel) getFXController().getModel("game");
			if (gm != null) gm.dispose();
			getWindow().close();
		});		
		
		// IDs um im CSS die Bilder einzufügen.
	 
		//optionsBtn.setId("optionsbtn");
		//gameBtn.setId("gamebtn");
		//loginBtn.setId("loginBtn");
		//userLoginBtn.setId("UserBtn");
		//helpbtn.setId("helpbtn");
		quitBtn.setId("quitBtn");
		
		
		//loginBtn.setMinSize(100.0, 90.0);
		//optionsBtn.setMinSize(100.0, 90.0);
		//gameBtn.setMinSize(100.0, 90.0);
		//userLoginBtn.setMinSize(100.0, 90.0);
		//helpbtn.setMinSize(100.0, 90.0);
		quitBtn.setMinSize(100.0, 90.0);
		
		lueckenfueller1.setMinSize(100.0,90.0);
		lueckenfueller2.setMinSize(100.0,90.0);
		
		
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