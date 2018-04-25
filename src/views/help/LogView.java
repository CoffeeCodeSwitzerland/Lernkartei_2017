package views.help;

import java.io.File;
import java.io.FileNotFoundException;

import debug.Logger;
import globals.Environment;
import globals.Functions;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
public class LogView extends FXView
{
	private final String logfileName = Environment.getDatabaseLocation()+Environment.getFileSep()+"LogfileOf"+Environment.getUserName()+".txt";
	private Label  labelText;
	private final ScrollPane scroller = new ScrollPane();

	public LogView(String newName, FXController newController) {
		// this constructor is the same for all view's
		super(newController);
		construct(newName);
	}

	@Override
	public Parent constructContainer() {
		// TODO Auto-generated method stub

		try {
			labelText = new Label ( Functions.fileToString(new File(logfileName)) );
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			labelText = new Label("leer");
		}
		labelText.setWrapText(true);
		labelText.setMaxWidth(800);
		labelText.setId("impressumtext");

		Label labelTitel = new Label("Log-File ("+logfileName);
		labelTitel.setStyle("-fx-font-weight: bold; -fx-font-size: 1.4em;");
			
		AppButton backBtn = new AppButton("_Zurück");
		backBtn.setOnAction(e -> getFXController().showMainView());
		
		Button deleteLog = new Button("Lösche Log");
		deleteLog.setOnAction(e -> {

			try {
				boolean succ = new File(logfileName).delete();
				if (succ == false) {
					Logger.out("did not delete '"+logfileName+"'");
				}
			} catch (Exception e1) {
				Logger.out(e1.getMessage());
			}
			this.refreshView();
		});
		
		BorderPane headLayout = new BorderPane(labelTitel);
		headLayout.setPadding(new Insets(20));
		scroller.setMaxWidth(800);
		
				
		scroller.setContent(labelText);
		scroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroller.setVbarPolicy(ScrollBarPolicy.ALWAYS);  
			
		HBox controlLayout = new HBox(20);
		controlLayout.setAlignment(Pos.BOTTOM_CENTER);
		controlLayout.getChildren().addAll(backBtn, deleteLog);
		controlLayout.setPadding(new Insets (10));

		BorderPane mainLayout = new BorderPane();
		mainLayout.setPadding(new Insets(15));
		mainLayout.setTop(headLayout);
		mainLayout.setCenter(scroller);
		mainLayout.setBottom(controlLayout);
		//getFXController().getModel("door").registerView(this);
		return mainLayout;
	}

	@Override
	public void refreshView() {
		try {
			labelText = new Label( Functions.fileToString(new File(logfileName)) );
		} catch (FileNotFoundException e1) {
			labelText = new Label("leer");
		}
		scroller.setContent(labelText);
	}
}
