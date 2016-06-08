package views;

import globals.Globals;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
		super(newController);
		construct(newName);
	}

	//Wir haben das ganze mit den BorderPanes gelöst.
	//BorderPane mainLayout = new BorderPane();
	// Graphic Buttons:
	BorderPane helpbtn = new BorderPane();
	BorderPane userLoginBtn = new BorderPane();
	BorderPane lernenStartBtn = new BorderPane();
	BorderPane statisticBtn = new BorderPane();
	BorderPane optionsBtn = new BorderPane();
	BorderPane gameBtn = new BorderPane();
	BorderPane quitBtn = new BorderPane();
	
	// FX braucht 2 Lückenfüller für das Gridpane zu füllen:
	Label lueckenfueller1 = new Label("");
	Label lueckenfueller2 = new Label("");

	VBox menuLayout = new VBox();
	HBox loginBox = new HBox();
	
	Text lernText = new Text("");
	
	@Override
	public Parent constructContainer() {
		String title = Globals.appTitle + " " + Globals.appVersion;
		this.getWindow().setTitle(title);
		
		lernenStartBtn.setOnMouseEntered( e -> {
				String isTooltipActif = getFXController().getModel("config").getDataList("tooltip").get(0);
				if (isTooltipActif != null && !isTooltipActif.equals("no")) {
					lernText.setText("Lernen...");
				} else if (isTooltipActif == null) {
					lernText.setText("Lernen...");
				};
			} 
		);
		lernenStartBtn.setOnMouseExited( e -> lernText.setText("") );

		debug.Debugger.out("constructing MainView Container with title '"+title+"'...");
		getFXController().getMyFXStage().setTitle(title);
		
		VBox lernenVBox = new VBox();
		lernenVBox.getChildren().addAll(lernenStartBtn,lernText);
	    lernenVBox.setAlignment(Pos.CENTER);

		//Hier wird mit Koordinaten Psotionen angegeben. 
		//Die Lückenfüller sind da weil eine Spalte nicht Nichts enthalten kann wegen 
		//dem Abstand. Es würde es einfach ignorieren. Deswegen 2 leere Labels da.
		GridPane gridpane = new GridPane();
	    gridpane.setAlignment(Pos.CENTER);
		gridpane.setHgap(4);
		gridpane.setVgap(4);
	    gridpane.add(lernenVBox, 3, 3); 	
	    //gridpane.add(lernText, 3, 4); 	
	    gridpane.add(userLoginBtn, 6, 2);  
	    gridpane.add(statisticBtn, 3, 1);  
	    gridpane.add(quitBtn, 3, 5);  
	    gridpane.add(gameBtn, 1, 4); 
	    gridpane.add(optionsBtn, 6, 4);
	    gridpane.add(helpbtn, 1, 2);
	    gridpane.add(lueckenfueller1 , 2 ,3);
	    gridpane.add(lueckenfueller2 , 5 ,3);
	    
	    //Behavior
	    lernenStartBtn.setOnMouseClicked(e -> getFXController().showView("doorview"));
		statisticBtn.setOnMouseClicked(e -> getFXController().showView("statsview"));
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
		lernenStartBtn.setId("lernenbtn");
		statisticBtn.setId("stat2btn");
		optionsBtn.setId("optionsbtn");
		gameBtn.setId("gamebtn");
		//loginBtn.setId("loginBtn");
		userLoginBtn.setId("UserBtn");
		helpbtn.setId("helpbtn");
		quitBtn.setId("quitBtn");
		
		//Grösse muss angegeben werden oder es überschatten alles....
		//loginBtn.setMinSize(100.0, 90.0);
		optionsBtn.setMinSize(100.0, 90.0);
		statisticBtn.setMinSize(110.0, 100.0);
		gameBtn.setMinSize(100.0, 90.0);
		userLoginBtn.setMinSize(100.0, 90.0);
		helpbtn.setMinSize(100.0, 90.0);
		lernenStartBtn.setMinSize(110.0,100.0);
		quitBtn.setMinSize(100.0,90.0);
		
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