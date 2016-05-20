package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;

public class ImpressumView extends FXView
{
	
	public ImpressumView(String newName, FXController newController) {
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}

	@Override
	public Parent constructContainer() {
		// Buttons
		AppButton backBtn = new AppButton("_Zurück");
			
		//Labels (für die Infotexte)
		Label labelTitel = new Label("Impressum");
		
		//Hier kommt der angezeigte Text hin:
		Label labelText = new Label("Das Programm WISSLearnCards wurde programmiert im Rahmen eines Projekts durch die Informatiklernenden im Auftrag der\n\nStiftung Wirtschaftsinformatikschule Schweiz WISS\nOstermundigenstrasse 81\nCH-3006 Bern\n\nTeilnehmer:\n\nInformatiklernende vom\n\nBundesamt für Informatik und Telekommunikation BIT\nMonbijoustrasse 74\nCH-3003 Bern\n\n•	Yanis Weibel\n•	Joel Häberli\n•	David Schor\n•	Nina Egger\n•	Roger Schneiter\n•	Miro Albrecht\n•	Tim Leibacher\n");
		
		//Zeilenumbruch am Fensterrand
		labelText.setWrapText(true);

		//IDs für CSS
		labelTitel.setId("impressumtext");
		labelText.setId("impressumtext");

		//Damit der Text nicht bis zum Fensterrand geht sondern noch etwas abstand hat
		Double size = getController().getMyFXStage().getOPTIMAL_WIDTH()*.95;
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
