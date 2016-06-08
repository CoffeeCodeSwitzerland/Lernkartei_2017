package views.components;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import mvc.fx.FXView;

public class HoverButton extends VBox {

	final Text lernText = new Text("");
	String myTipp = "";

	public HoverButton (FXView v, String tipp, String targetView, String id) {
		myTipp = tipp;

	    BorderPane bp = new BorderPane();
	    bp.setOnMouseEntered( e -> {
				String isTooltipActif = v.getFXController().getModel("config").getDataList("tooltip").get(0);
				if (isTooltipActif == null || !isTooltipActif.equals("no")) {
					lernText.setText(myTipp);
				};
			}
		);
		bp.setOnMouseExited( e -> lernText.setText("") );

	    bp.setOnMouseClicked(e -> v.getFXController().showView(targetView));

	    bp.setMinSize(110.0,100.0);
	    bp.setId(id);

		getChildren().addAll(bp,lernText);
	    setAlignment(Pos.CENTER);
	}
}
