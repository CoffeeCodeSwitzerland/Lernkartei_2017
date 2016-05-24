package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;

/**
 *	Hilfesystem Info Anzeige
 * 
 * @author hugo-lucca
 *
 */
public class HelpInfoView extends FXView
{

	public HelpInfoView(String newName, FXController newController) {
		// this constructor is the same for all view's
		super(newController);
		construct(newName);
	}

	@Override
	public Parent constructContainer() {
		// TODO Auto-generated method stub
		
		Label labelTitel = new Label("Anleitung");
		Label labelText = new Label("Erste Schritte:\n\nWenn du das Programm gestartet hast siehst du das Menü. Drücke den obersten Button Lernen. Dannach bist du bei den Türen. Das sind die Oberthemen. Unten siest du einen Button Erstelle neue Tür. Klicke auf ihn und dann kannst du der Tür einen Namen geben. Zum Biespiel Mathe.\n Klicke nun auf die neue Tür die du erstellt hast und du landest nun bei den Boxen. Das sind die Unterthemen wie zum Beispiel Algebra und Geometrie in unserem Fall. Klicke auf Neuer Stapel und ein kleines Fenster mit einer Auswahl erscheint. Du kannst selber Kärtchen erstellen oder sie von Quizlet importieren. Du entschiedest.\n\nWenn du fertig bist klicke auf die Box die du erstellt hast und nun kannst du Kärtchen hinzufügen wenn du auf Bearbeiten klickst. Hast du dies getan klicke auf Lernen und das erste Kärtchen erscheint. Drücke auf den drehen Button um die Rückseite anzeigen zu lassen. Anschliessen klicke auf Richtig oder Falsch um dem Programm zu sagen ob du das Kärtchen wusstest oder nicht.\n\nKärtchen bearbeiten/hinzufügen:\n\nUm ein Kärtchen zu erstellen, bearbeiten oder hunzuzufügen gehe bei den Stapeln(Karteikästchen) auf bearbeiten. Nun hast du eine Liste der Kärtchen sofern du schon welche erstellt hast. Nun kannst du die Frage und die Antwort eintippen in die Felder. Klicke dann auf das Häkchen am Ende der Zeile um zu bestätigen. Wenn du auf den Stift neben dem Häckchen klickst kannst du bereits erstellte Kärtchen bearbeiten.\netc...");
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
