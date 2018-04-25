package views;


import globals.Globals;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;
import views.components.BackButton;


public class OwnDBConnectionView extends FXView
{

	public OwnDBConnectionView(String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}

	BorderPane bp = new BorderPane();

	VBox AllFields;
	
	TextField txtPath;
	TextField txtUser;
	PasswordField pwPassword;
	PasswordField pwToCheck;
	TextField txtDB;
	TextField txtUserTable;
	
	AppButton save;
	BackButton back;
	
	@Override
	public Parent constructContainer()
	{
		bp.setId("registerviewbg");
		
		AllFields = new VBox(50);
		AllFields.setAlignment(Pos.CENTER);
		AllFields.setMaxWidth(300);
		AllFields.setPadding(new Insets(20));
		
		txtPath = new TextField();
		txtPath.setPromptText("Pfad: Bsp. 192.168.2.2/dbname");
		pwPassword = new PasswordField();
		pwPassword.setPromptText("Passwort");
		pwToCheck = new PasswordField();
		pwToCheck.setPromptText("Passwort wiederholen");
		txtUser = new TextField();
		txtUser.setPromptText("Benutzer für Verbindung");
		txtDB = new TextField();
		txtDB.setPromptText("Datenbankname");
		txtUserTable = new TextField();
		txtUserTable.setPromptText("Benutzertabellen-Name");
		
		save = new AppButton("Abspeichern");
		back = new BackButton(getFXController(), "Zurück");
		
		save.setOnAction(e -> {
			if(pwPassword.getText().equals(pwToCheck.getText()))
			{
				Globals.mysqlpath += txtPath.getText();
				Globals.mysqluser = txtUser.getText();
				Globals.mysqlpassword = pwPassword.getText();
				Globals.mysqldb = txtDB.getText();
				Globals.user_Table = txtUserTable.getText();
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("DB-Connection");
				alert.setHeaderText("Verbindung erfolgreich abgespeichert!");
				alert.setContentText("Ihre Verbindung wurde erfolgreich abgespeichert.\nDie Datenbank bzw. die Tabellen werden nun erstellt.");
				alert.showAndWait();

				txtPath.clear();
				txtUser.clear();
				pwPassword.clear();
				pwToCheck.clear();
				txtDB.clear();
				txtUserTable.clear();
				
				getFXController().showMainView();
			} else{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fehler 0x0002");
				alert.setHeaderText("Passwörter stimmen nicht überein!");
				alert.setContentText("Bitte geben Sie das Passwort erneut ein.");
				pwPassword.clear();
				pwToCheck.clear(); 
				alert.showAndWait();
			}
		});
		
		back.setOnAction(e -> getFXController().showAndTrackView("databaseselectionview"));
		
		AllFields.getChildren().addAll(txtPath, txtDB, txtUserTable, txtUser, pwPassword, pwToCheck, save);
		
		bp.setCenter(AllFields);
		
		return bp;
	}

	@Override
	public void refreshView()
	{
		bp.setId("registerviewbg");
	}

}
