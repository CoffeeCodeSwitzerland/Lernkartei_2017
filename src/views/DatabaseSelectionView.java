package views;

import globals.Globals;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;

public class DatabaseSelectionView extends FXView
{

	public DatabaseSelectionView(String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}
	
	BorderPane bp = new BorderPane();

	VBox AllFields;
	
	Label infoText;

	AppButton btnWISSDB;
	AppButton btnOwnDB;
	
	@Override
	public Parent constructContainer()
	{
		bp.setId("loginviewbg");
		
		AllFields = new VBox(50);
		AllFields.setAlignment(Pos.CENTER);
		AllFields.setMaxWidth(300);
		AllFields.setPadding(new Insets(20));

		
		btnWISSDB = new AppButton("WISS-Datenbank");
		
		btnOwnDB = new AppButton("eigene Datenbankverbindung");
		
		infoText = new Label("Bitte geben Sie hier Ihre gewünschte Datenbankverbindung an.\nSie können nicht weiterfahren, bis Sie dies abgeschlossen haben.\nAlternativ können Sie auch zurück gehen und die WISS-Datenbank als Verbindungsobjekt auswählen.");
				
		AllFields.getChildren().addAll(btnWISSDB, btnOwnDB);
		
		bp.setCenter(AllFields);
		
		btnWISSDB.setOnAction(e -> {
			Globals.mysqldriver = Globals.mysqldriver_wiss;
			Globals.mysqlpath = Globals.mysqlpath_wiss;
			Globals.mysqluser = Globals.mysqluser_wiss;
			Globals.mysqlpassword = Globals.mysqlpassword_wiss;
			Globals.mysqldb = Globals.mysqldb_wiss;
			Globals.user_Table = Globals.user_Table_wiss;
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("DB-Connection");
			alert.setHeaderText("Verbindung erfolgreich abgespeichert!");
			alert.setContentText("Die Verbindung zum WISS-Server wurde erfolgreich abgespeichert.\nDie Datenbank bzw. die Tabellen werden nun erstellt, sollten sie noch nicht existieren.");
			alert.showAndWait();
			System.out.println("create database");
			try {
				getFXController().getModel("databaseselectionmodel").buildWissDatabase();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			getFXController().showMainView();
		});
		btnOwnDB.setOnAction(e -> {

			getFXController().showAndTrackView("owndbconnectionview");
		});
		
		return bp;
	}

	@Override
	public void refreshView()
	{
		bp.setId("loginviewbg");
	}

}
