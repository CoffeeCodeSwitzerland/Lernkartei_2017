package mvc.test;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import mvc.fx.FXController;
import mvc.fx.FXView;

/**
 * Gamestartfenster
 * Zweck dieses BS ist eine Art Warte- und Info-BS (zum Bsp. Anzahl Punkte) zum Gamestart, 
 * mann kann dann alternativ weiter lernen.
 * 
 * @author hugo-lucca
 */
public class TestView extends FXView
{
	public TestView(String newName, FXController newController) {
		super(newController);
		construct(newName);
	}

	private final VBox myLayout = new VBox();

	@Override
	public Parent constructContainer() {
		// CSS formatierte Meldung auf BS bringen (mit div. Schrifteffekten)
		Text t = new Text("Starting Demo (please wait)...");
		t.setId("fancytext");

		// Button für Zurück zum Hauptmenue:
		Button b = new Button("Zurück...");
		b.setOnAction(e -> getFXController().showMainView());

		// Erstellt VBox Layout für beide obige Elemente:
		myLayout.setSpacing(50);
		myLayout.getChildren().addAll(t, b);
		
		// VBox in neuem Borderpane einfügen, zwingend wenn Hintergrund neu sein soll
		// CSS liefert neue Darstellung:
		BorderPane bp = new BorderPane();
		bp.setCenter(myLayout);
		bp.setId("gamebg");
		
		// BP in Scene einfügen:
		// TODO Auto-generated method stub
		return bp;
	}


	@Override
	public void refreshView() {

	}

}
