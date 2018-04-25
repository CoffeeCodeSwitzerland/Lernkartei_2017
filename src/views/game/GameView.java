package views.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import database.LKDatabase;
import debug.Debugger;
import models.GameModel;
import mvc.ModelInterface.Command;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;
import views.components.MainLayout;

/**
 * Gamestartfenster ================ Zweck dieses BS ist eine Art Warte- und
 * Info-BS (zum Bsp. Anzahl Punkte) zum Gamestart, mann kann dann alternativ
 * weiter lernen.
 * 
 * @author hugo-lucca
 *
 */
public class GameView extends FXView {

	public GameView(String newName, FXController newController) {
		// this constructor is the same for all view's
		super(newController);
		construct(newName);
	}

	Text text;

	public static Text lifes = new Text("");
	public static AppButton btnJumNRun = new AppButton("Jump 'n Run");
	public static AppButton btnTutto = new AppButton("Tutto");
	VBox tutto = new VBox();
	VBox JumpNRun = new VBox();
	VBox life = new VBox();
	GridPane gridpane = new GridPane();
	AppButton btnInfoJump = new AppButton("Spielanleitung");
	AppButton btnInfoTutto = new AppButton("Spielanleitung");
	AppButton btnBacktoKartei = new AppButton("Zurück");
	public static Text grund = new Text("");
	

	@Override
	public Parent constructContainer() {
		text = new Text("Starting game...");
		text.setId("fancytext"); // CSS formatierte Meldung auf BS bringen (mit
									// div. Schrifteffekten)

		// Button for back to main:
		btnBacktoKartei.setOnAction(e -> getFXController().showMainView()); 

		// Other buttons:
		btnJumNRun.setOnAction(e -> {
			try {
				getFXController().getModel("game").doAction(Command.NEW);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}); 
		btnInfoJump.setOnAction(e -> getFXController().showAndTrackView("gameoptionview")); 
		btnTutto.setOnAction(e -> {
			try {
				getFXController().getModel("tutto").doAction(Command.NEW);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btnInfoTutto.setOnAction(e -> getFXController().showAndTrackView("tuttohelpview"));

		// Erstellt VBox Layout für beide obige Elemente:
		JumpNRun.getChildren().addAll( btnJumNRun, btnInfoJump);
		
		JumpNRun.setPadding(new Insets(10)); 
		JumpNRun.setSpacing(15);
		JumpNRun.setAlignment(Pos.CENTER_LEFT);
		
		
		tutto.getChildren().addAll( btnTutto, btnInfoTutto);
		tutto.setPadding(new Insets(10)); 
		tutto.setSpacing(15);
		tutto.setAlignment(Pos.CENTER_LEFT);
		
		life.getChildren().addAll(lifes);
		life.setAlignment(Pos.CENTER);
		 
		gridpane.setAlignment(Pos.CENTER);
		gridpane.add(tutto, 90, 2);
		gridpane.add(JumpNRun, 30, 2);
		gridpane.add(grund,35,1);
		gridpane.add( btnBacktoKartei,35,25);
		gridpane.add(life,35,2);
		
		MainLayout maLay = new MainLayout(gridpane,null);
		
		try {
			((GameModel) getFXController().getModel("game")).registerView(this, getFXController());
		} catch (Exception e) {
			Debugger.out("GamerView-constructContainer: did not found a Model named 'game'!");
		}		
		return maLay;

		// VBox in neuem Borderpane einfügen, zwingend wenn Hintergrund neu sein
		// soll
		// CSS liefert neue Darstellung:
		
		
	}
	
	
	public void refreshView() {
/*
		if (LKDatabase.myUsers.getLifecount() == 0) {
			btn.setDisable(true); 
			btnTutto.setDisable(true);
			grund.setText("Sie müssen zuerst Lernen");
			lifes.setText("Lifes: " + LKDatabase.myUsers.getLifecount());
		} else { 
*/
			grund.setText("");
			btnJumNRun.setDisable(false); 
			btnTutto.setDisable(false);
			lifes.setText("Lifes: " + LKDatabase.myUsers.getLifecount()); 
//		}

	}
}
