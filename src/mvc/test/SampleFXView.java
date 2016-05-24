package mvc.test;

import globals.Globals;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import mvc.fx.FXController;
import mvc.fx.FXViewModel;

/**
 * MUSTER-Hauptfenster für Java FX
 * 
 * @author hugo-lucca
 *
 */
public class SampleFXView extends FXViewModel
{
	public SampleFXView(String setName, FXController controller) {
		super(controller);
		construct(setName);
	}

	private final String 			subTitle = "(Java FX Demo)";

	@Override
	public Parent constructContainer() {
		this.getWindow().initStyle (StageStyle.TRANSPARENT);
		this.getWindow().setTitle ( Globals.appTitle + " " + subTitle );

		// Buttons ( Dekoration im CSS mit: .button {...} )
		Button startBtn   = new Button("Simulieren");
		Button optBtn 	  = new Button("Optionen");
		Button demoBtn    = new Button("Demo");
		Button helpBtn    = new Button("Hilfe");
		Button quitBtn    = new Button("Beenden");

		// Verhalten der Button bestimmen (View-Controller's):
		startBtn.setOnAction ( e -> this.getFXController().showView("simview") );
		optBtn.setOnAction   ( e -> getFXController().showView("optview") );
		demoBtn.setOnAction  ( e -> getFXController().showView("demoview") );
		helpBtn.setOnAction  ( e -> getFXController().showView("helpview") );
		quitBtn.setOnAction  ( e -> { getWindow().close(); } );

		// Zum Bsp. eine VBox als Layout und Container für die Buttons:
		VBox layout = new VBox();
		layout.getChildren().addAll(startBtn, optBtn, demoBtn, helpBtn,quitBtn); 

		// Neuer Container für das Haupt-Fenster (fakultativ, kann aber so eigens gestaltet werden):
		BorderPane bp = new BorderPane();
		bp.setCenter(layout);
		bp.setId("mainBS"); 	// CSS ID für eine allfällig eigene Darstellung
		
		return bp;
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
	}

}