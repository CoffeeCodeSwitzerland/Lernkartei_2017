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

/**
 * Hilfe System Index-Suche
 *  
 * @author hugo-lucca
 *
 */
public class HelpSerachView extends FXView
{

	public HelpSerachView(String newName, FXController newController) {
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
				Label labelTitel = new Label("Index");
				
				//Hier kommt der angezeigte Text hin:
				Label labelText = new Label("-Türen- 			Das sind die Themen.\n-Boxen- 			Das sind die Unterthemen\n-Jump 'n' Run- 	Mit lernen kannst du dir Leben verdienen für das Spiel. Du findest es im Hauptmenü.\n-Optionen- 		Hier kannst du einstellen wie viele Kärtchen du auf einmal lernen möchtest.\n-Statistiken- 		Hier kannst du deinen Fortschritt sehen. Unter anderem wie viele Punkte du gesammelt hast beim lernen.\n-Quizlet-			Von dort kannst du Kärtchen importieren wenn du sie nicht selber schreiben möchtest.\n-Lernkarteien-		Hier drücken um die Kartei auszuwählen und sie zu lernen.\n-(?) Icon-			Hier findest du Anleitungen,Index etc...\n-Index-			Hier werden Begriffe erklärt\n-Impressum-		Wer an diesem Programm gearbeitet hat, die Lizenzen und Rechte etc...\n-Anleitung-		Kurze Info wie man WISSLearnCards benutzt.\n-etc-			\n-etc-			\n-etc-");
				
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
				
		return borderPane;
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}
}
