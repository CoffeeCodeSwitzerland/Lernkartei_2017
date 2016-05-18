package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import mvc.Controller;
import mvc.fx.FXView;

/**
 * Gamestartfenster ================ Zweck dieses BS ist eine Art Warte- und
 * Info-BS (zum Bsp. Anzahl Punkte) zum Gamestart, mann kann dann alternativ
 * weiter lernen.
 * 
 * @author hugo-lucca
 *
 */
public class GameView extends FXView {
	public GameView(String newName, Controller newController) {
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}

	Text text;

	@Override
	public Parent constructContainer() {
		text = new Text("Starting game...");
		text.setId("fancytext"); // CSS formatierte Meldung auf BS bringen (mit
									// div. Schrifteffekten)

		// Button für Zurück zum Hauptmenue:
		Image impressumImg = new Image("views/pictures/ImpressumIcon.png");

		AppButton btn = new AppButton("Spiel starten");
		AppButton btnInfo = new AppButton("Info");
		AppButton btnBacktoKartei = new AppButton("Zurück zur Lernkartei");

		BorderPane mainLayout = new BorderPane();
		VBox menuLayout = new VBox();

		btn.setOnAction(e -> getController().getModel("game").doAction("start"));

		btnInfo.setOnAction(e -> getController().getView("gameoptionview").show());

		btnBacktoKartei.setOnAction(e -> getController().showMainView());

		// Erstellt VBox Layout für beide obige Elemente:

		menuLayout.setPadding(new Insets(10));
		menuLayout.setSpacing(10);
		menuLayout.setAlignment(Pos.CENTER);
		menuLayout.getChildren().addAll(btn, btnInfo, btnBacktoKartei);

		mainLayout.setPadding(new Insets(5));
		mainLayout.setCenter(menuLayout);

		ImageView impImgView = new ImageView(impressumImg);
		mainLayout.setBottom(impImgView);
		impImgView.setOnMouseClicked(e -> getController().getView("impressumview").show());

		return mainLayout;

		// VBox in neuem Borderpane einfügen, zwingend wenn Hintergrund neu sein
		// soll
		// CSS liefert neue Darstellung:

	}

	@Override
	public void refreshView() {
	}
}
