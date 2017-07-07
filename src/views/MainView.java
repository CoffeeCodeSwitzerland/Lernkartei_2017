package views;

import database.LKDatabase;
import globals.Globals;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import models.GameModel;
import models.TuttoModel;
import mvc.fx.FXController;
import mvc.fx.FXSettings;
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
	
	// My graphic Hover-Buttons:
	HoverButton helpbtn = new HoverButton(this,"Hilfe","helpview","helpbtn");
	HoverButton userLoginBtn = new HoverButton(this,null ,"loginview","UserBtn");
	HoverButton lernenStartBtn = new HoverButton(this,"Lernen","lernenselectionview", "lernenbtn");
	HoverButton statisticBtn = new HoverButton(this,"Statistik","statsview", "stat2btn");
	HoverButton optionsBtn = new HoverButton(this,"Optionen","optionsview","optionsbtn");
	HoverButton gameBtn = new HoverButton(this,"Jump 'n' Run","gameview","gamebtn");
	HoverButton quitBtn = new HoverButton(this,"Beenden", null, "quitbtn");
	
	// FX needs 2 empty components filling the Gridpane as we need it:
	Label lueckenfueller1 = new Label("");
	Label lueckenfueller2 = new Label("");

	/**
	 * Close all Threads, DB Connections and remaining Windows
	 */
	private void closeAll() {
		debug.Debugger.out("closing main view...");
		GameModel gm = (GameModel) getFXController().getModel("game");
		if (gm != null) gm.dispose();
		TuttoModel tm = (TuttoModel) getFXController().getModel("tutto");
		if (tm != null) tm.dispose();
		LKDatabase.myConfigDB.closeDB();
		LKDatabase.myWLCDB.closeDB();
		getWindow().close();
	}

	@Override
	public Parent constructContainer() {
		// title settings:
		String title = Globals.appTitle + " " + Globals.appVersion;
		this.getWindow().setTitle(title);
		//debug.Debugger.out("constructing MainView Container with title '"+title+"'...");
		getFXController().getMyFXStage().setTitle(title);
		
		//Die Lückenfüller sind da weil eine Spalte nicht Nichts enthalten kann wegen 
		//dem Abstand. Es würde es einfach ignorieren. Deswegen 2 leere Labels da.
		lueckenfueller1.setMinSize(FXSettings.HoverButtonWidth,FXSettings.HoverButtonHeight);
		lueckenfueller2.setMinSize(FXSettings.HoverButtonWidth,FXSettings.HoverButtonHeight);
		//... und "Nein" man kann nicht das gleiche Label an 2 verschiedenen Stellen hinzufügen.
		
		GridPane gridpane = new GridPane();
		//Hier werden die Koordinaten der Komponenten bestimmt: 
	    gridpane.setAlignment(Pos.CENTER);
//		gridpane.setHgap(4);
//		gridpane.setVgap(4);
	    gridpane.add(lernenStartBtn, 3, 3);
	    gridpane.add(userLoginBtn, 6, 2);
	    gridpane.add(statisticBtn, 3, 1);  
	    gridpane.add(quitBtn, 3, 5);  
	    gridpane.add(gameBtn, 1, 4); 
	    gridpane.add(optionsBtn, 6, 4);
	    gridpane.add(helpbtn, 1, 2);
	    gridpane.add(lueckenfueller1 , 2 ,3);
	    gridpane.add(lueckenfueller2 , 5 ,3);
	    
		// special solution for the close button
		quitBtn.setOnMouseClicked(e ->
		{
			debug.Debugger.out("closing button");
			this.closeAll();
		});		
		// does the same as the close button, when closing window on x
		getWindow().setOnCloseRequest(e ->
		{
			debug.Debugger.out("closing window");
			this.closeAll();
		});
		
		//debug.Debugger.out("Set impressum...");
		return gridpane;
	}

	@Override
	public void refreshView () { }
}