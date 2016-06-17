package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
	public static AppButton btn = new AppButton("Spiel starten");
	VBox menuLayout = new VBox();
	AppButton btnInfo = new AppButton("Info");
	AppButton btnBacktoKartei = new AppButton("Zurück");
	public static Text grund = new Text("");

	@Override
	public Parent constructContainer() {
		text = new Text("Starting game...");
		text.setId("fancytext"); // CSS formatierte Meldung auf BS bringen (mit
									// div. Schrifteffekten)

		// Button für Zurück zum Hauptmenue:

		btn.setOnAction(e -> getFXController().getModel("game").doAction(Command.NEW)); 

		btnInfo.setOnAction(e -> getFXController().showView("gameoptionview")); 

		btnBacktoKartei.setOnAction(e -> getFXController().showMainView()); 

		// Erstellt VBox Layout für beide obige Elemente:
		menuLayout.getChildren().addAll(grund, btn, btnInfo, btnBacktoKartei); 

		menuLayout.setPadding(new Insets(10)); 
		menuLayout.setSpacing(10);
		menuLayout.setAlignment(Pos.CENTER);
		
		MainLayout maLay = new MainLayout(menuLayout, lifes, null);
		
		((GameModel) getFXController().getModel("game")).registerView(this, getFXController());
		return maLay;

		// VBox in neuem Borderpane einfügen, zwingend wenn Hintergrund neu sein
		// soll
		// CSS liefert neue Darstellung:

	}

	public void refreshView() {

		if (database.UserEntity.getLifecount() == 0) {
			btn.setDisable(true);  
			grund.setText("Sie müssen zuerst Lernen!");
			lifes.setText("Lifes: " + database.UserEntity.getLifecount());
		} else { 
			btn.setDisable(false); 
			lifes.setText("Lifes: " + database.UserEntity.getLifecount()); 
		}

	}
}
