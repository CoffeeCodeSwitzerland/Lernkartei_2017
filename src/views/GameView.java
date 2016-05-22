package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.GameModel;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;

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
		super(newName, newController);
		construct();
	}

	Text text;

	Text lifes = new Text("");
	BorderPane mainLayout = new BorderPane();
	AppButton btn = new AppButton("Spiel starten");
	VBox menuLayout = new VBox();
	AppButton btnInfo = new AppButton("Info");
	AppButton btnBacktoKartei = new AppButton("Zurück");
	Text grund = new Text("");

	@Override
	public Parent constructContainer() {
		text = new Text("Starting game...");
		text.setId("fancytext"); // CSS formatierte Meldung auf BS bringen (mit
									// div. Schrifteffekten)

		// Button für Zurück zum Hauptmenue:

		btn.setOnAction(e -> getController().getModel("game").doAction("start"));

		btnInfo.setOnAction(e -> getController().showView("gameoptionview"));

		btnBacktoKartei.setOnAction(e -> getController().showMainView());

		// Erstellt VBox Layout für beide obige Elemente:
		menuLayout.getChildren().addAll(grund, btn, btnInfo, btnBacktoKartei);

		menuLayout.setPadding(new Insets(10));
		menuLayout.setSpacing(10);
		menuLayout.setAlignment(Pos.CENTER);
		mainLayout.setTop(lifes);

		mainLayout.setPadding(new Insets(5));
		mainLayout.setCenter(menuLayout);
		((GameModel) getController().getModel("game")).registerView(this, getController());
		return mainLayout;

		// VBox in neuem Borderpane einfügen, zwingend wenn Hintergrund neu sein
		// soll
		// CSS liefert neue Darstellung:

	}

	@Override
	public void refreshView() {

		if (database.Score.getLifecount() == 0) {
			btn.setDisable(true);
			grund.setText("Sie müssen zuerst Lernen!");
			lifes.setText("Lifes: " + database.Score.getLifecount());
		} else {
			btn.setDisable(false);
			lifes.setText("Lifes: " + database.Score.getLifecount());
		}

	}
}
