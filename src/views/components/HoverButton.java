package views.components;

import debug.Debugger;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import mvc.ModelInterface.Command;
import mvc.fx.FXSettings;
import mvc.fx.FXView;

public class HoverButton extends VBox {

	final Text lernText = new Text("");
	String myTipp = "";
	private static String tooltipEnable = null;
	private static boolean isTooltipActif = true;
	
	public static void clearSettings () {
		tooltipEnable = null;
	}
	
	public HoverButton (FXView v, String tipp, String targetView, String id) {
		myTipp = tipp;
		if (tooltipEnable == null) {
			/**
			 *  this all is to be more efficient when handling the event below
			 */
// TODO move to model
			try {
				tooltipEnable = v.getFXController().getModel("config").getDataList("tooltipp").get(0);
			} catch (Exception e1) {
				Debugger.out("HooverButton Konstructor 1: did not found a FXModel named 'config'!");
			}
			isTooltipActif = true;
			if (tooltipEnable == null) {
				try {
					v.getFXController().getModel("config").doAction(Command.SET,"tooltipp","false");
				} catch (Exception e1) {
					Debugger.out("HooverButton Konstructor 2: did not found a FXModel named 'config'!");
				}
			}
			if (tooltipEnable != null && tooltipEnable.equals("true")) {
				isTooltipActif = false;
			}
		}
	    BorderPane bp = new BorderPane();
	    bp.setOnMouseEntered( e -> {
				if (isTooltipActif && myTipp != null) {
					lernText.setText(myTipp);
				};
			}
		);
		bp.setOnMouseExited( e -> lernText.setText("") );
		if (targetView != null) {
			bp.setOnMouseClicked( e -> v.getFXController().showAndTrackView(targetView) );
		}
	    bp.setMinSize(FXSettings.HoverButtonWidth,FXSettings.HoverButtonHeight);
	    if (id != null) bp.setId(id);
		getChildren().addAll(bp,lernText);
	    setAlignment(Pos.CENTER);
	}
}
