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
import views.components.AppButton;

/**
 *	Hilfesystem Info Anzeige
 * 
 * @author hugo-lucca
 *
 */
public class BBCodeInfo extends FXView
{

	public BBCodeInfo(String newName, FXController newController) {
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}

	@Override
	public Parent constructContainer() {
		// TODO Auto-generated method stub
		
				// Buttons
				AppButton backBtn = new AppButton("_Zurück");
					
				//Labels (für die Infotexte)
				Label labelTitel = new Label("BBCode");
				
				//Hier kommt der angezeigte Text hin:
				Label labelText = new Label("Was ist BBCode:\nBBCode ist ähnlich wie HTML-Tags nur das sie aus eckigen Klammern([]) bestehen. Dies ermöglicht uns, dass weniger Speicherplatz verschwendet wird und wir haben die voll Kontrolle!\n\nBBCode-Liste:\nFett = [b]\nItalic = [i]\nUnterstrichen = [u]\nDurchgestrichen = [s]\nHochgestellt = [sup]\nHinuntergestellt = [sub]\nTextfarbe = [color=]");
				
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
				backBtn.setOnAction(e -> getController().showLastView());
				
				// Layout
				BorderPane borderPane = new BorderPane();
				borderPane.setPadding(new Insets(15));
				borderPane.setBottom(naviBox);
				borderPane.setCenter(BoxMitText);
				borderPane.setTop(TitelBox);
				
		return borderPane;
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}
}
