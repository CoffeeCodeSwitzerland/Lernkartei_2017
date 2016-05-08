package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.Controller;
import mvc.FXView;

public class ImpressumView extends FXView
{
	/*
	 * Ich nehme mal an wir werden die Texte mit Labels anzeigen.
	 * Ich hab versucht den Hintergrund etwas heller zu machen nur einen kleinen Akzent.
	 * Ich habs nicht und versuche es bald mit css. Es ist aber noch nicht geklärt ob das 
	 * wirklich Labels sein sollten oder ob es was besseres gibt.
	 * 
	 *    -------
	 *   ¦ To Do ¦
	 * 	  -------
	 * - Der Text sollte besser formatiert sein. (Andere Schrift)
	 * 
	 * - Der Hintergrund sollte ganz schwach heller sein,
	 *	 das Problem ist dass man bei Optionen vielleicht eine andere Farbe
	 *	 auswählen kann als Hintergrund und wenn ich jetzt eine feste Farbe setzte
	 *   sieht es dann schräg aus. Ich kann es ja nicht auf halb durchsichtig stellen.
	 *   
	 * - Das Formatieren könnte auch noch ein Problem sein. 
	 * 	 Mit \n kann man eine neue Zeile machen aber ich kenn die anderen nicht.
	 * 
	 */
	

	public ImpressumView(String newName, Controller newController) {
		// this constructor is the same for all view's on same stage
		super(newName, newController);
		Parent p = constructContainer();
		if (p==null) {
			p = getMainLayout();
		}
		p.setId(this.getName());
		setupScene(p);
	}

	@Override
	public Parent constructContainer() {
		// Buttons
		AppButton backBtn = new AppButton("Zurück");
		
		//Labels (für die Infotexte)
		Label labelTitel = new Label("Impressum");
		Label labelText = new Label("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. \n \n Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. \n \n Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");
		labelText.setWrapText(true);

		labelTitel.setId("impressumtext");
		labelText.setId("impressumtext");

		Double size = getController().getTheFXSettings().getOPTIMAL_WIDTH()*.95;
		size += 10;
		labelText.setPrefWidth(size);
		labelTitel.setPrefWidth(size);
		
		//Box für die Navigation
		HBox naviBox = new HBox(10);
		naviBox.getChildren().addAll(backBtn);
			
		//Box für Titel
		VBox TitelBox = new VBox(10);
		TitelBox.getChildren().addAll(labelTitel);
		TitelBox.setAlignment(Pos.CENTER);
				
		//Box für Mitte Text
		VBox BoxMitText = new VBox(20);
		BoxMitText.getChildren().addAll(labelText);
		BoxMitText.setAlignment(Pos.CENTER);
		
		// Behaviour
		backBtn.setOnAction(e -> getController().showMainView());
		
		// Layout
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(15));
		borderPane.setBottom(naviBox);
		borderPane.setCenter(BoxMitText);
		borderPane.setTop(TitelBox);
		
		// TODO Auto-generated method stub
		return borderPane;
	}

	@Override
	public void refreshView ()
	{
	}
}
