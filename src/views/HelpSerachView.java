package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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
				
		Label labelTitel = new Label("Index");
		Label labelText = new Label("-Türen- 			Das sind die Themen.\n-Boxen- 			Das sind die Unterthemen\n-Jump 'n' Run- 	Mit lernen kannst du dir Leben verdienen für das Spiel. Du findest es im Hauptmenü.\n-Optionen- 		Hier kannst du einstellen wie viele Kärtchen du auf einmal lernen möchtest.\n-Statistiken- 		Hier kannst du deinen Fortschritt sehen. Unter anderem wie viele Punkte du gesammelt hast beim lernen.\n-Quizlet-			Von dort kannst du Kärtchen importieren wenn du sie nicht selber schreiben möchtest.\n-Lernkarteien-		Hier drücken um die Kartei auszuwählen und sie zu lernen.\n-(?) Icon-			Hier findest du Anleitungen, Infos, Index und anderes.\n-Index-			Hier werden Begriffe erklärt\n-Impressum-		Wer an diesem Programm gearbeitet hat, die Lizenzen und Rechte etc.\n-Anleitung-		Kurze Info wie man WISSLearnCards benutzt.\n\nWichtiges:			...\n-etc-			\n-etc-			\n-etc-			\n-etc-			\n-etc-			\n-etc-			\n-etc-			\n-etc-			\n-etc-			\n-etc-			\n-etc-			");
		labelText.setWrapText(true);
		labelText.setMaxWidth(800);

		labelTitel.setId("impressumtitel");
		labelText.setId("impressumtext");
			
		AppButton backBtn = new AppButton("_Zurück");
		backBtn.setOnAction(e -> getController().showMainView());

		BorderPane headLayout = new BorderPane(labelTitel);
		headLayout.setPadding(new Insets(20));
		ScrollPane scroller = new ScrollPane();
		scroller.setMaxWidth(800);
		
				
		scroller.setContent(labelText);
		scroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroller.setVbarPolicy(ScrollBarPolicy.ALWAYS);  
			
		HBox controlLayout = new HBox(20);
		controlLayout.setAlignment(Pos.BOTTOM_CENTER);
		controlLayout.getChildren().addAll(backBtn);
		controlLayout.setPadding(new Insets (10));

		BorderPane mainLayout = new BorderPane();
		mainLayout.setPadding(new Insets(15));
		mainLayout.setTop(headLayout);
		mainLayout.setCenter(scroller);
		mainLayout.setBottom(controlLayout);

		return mainLayout;	
				
				
	
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}
}
