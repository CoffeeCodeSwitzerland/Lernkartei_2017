package views;

import java.util.Optional;

import globals.Globals;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;
import views.components.BackButton;

public class UserView extends FXView
{

	public UserView(String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}
	
	BorderPane bp;

	Label username;
	Label nachfolger;
	Label passwordText;
	AppButton changeNachfolger;
	AppButton deleteAccount;
	AppButton logOut;
	PasswordField password; 
	
	VBox AllFields;
	HBox Top;
	HBox Bottom;
	VBox TopRight;
	VBox BottomRight;
	
	VBox logout;
	

	BackButton back;

	@Override
	public Parent constructContainer()
	{
		bp = new BorderPane();
		bp.setId("userviewbg");
		
		AllFields = new VBox(50);
		Top = new HBox(50);
		Bottom = new HBox(50);
		TopRight = new VBox(50);
		BottomRight = new VBox(50);
		logout = new VBox(50);
		
		Top.setId("Top");
		Bottom.setId("Bottom");
		AllFields.setId("AllFields");
		
		logout.setPadding(new Insets(250, 100, 0, 0));
		
//		username = new Label("Here's the name of the user");
//		username.setId("username");
		changeNachfolger = new AppButton("Nachfolger ändern");
		deleteAccount = new AppButton("Konto entfernen");
		logOut = new AppButton("Ausloggen");
		
		username = new Label("{dein Nachfolger}");
		nachfolger = new Label("Nachfolger für das DMO:");
		passwordText = new Label("Passwort bestätigen:");
		
		password = new PasswordField();
		password.setPromptText("Passwort eingeben...");
		

		back = new BackButton(getFXController(),"Zurück");

		TopRight.getChildren().addAll(username, changeNachfolger);
		BottomRight.getChildren().addAll(password, deleteAccount, back);
		Top.getChildren().addAll(nachfolger, TopRight);
		Bottom.getChildren().addAll(passwordText, BottomRight);
		
		AllFields.getChildren().addAll(Top, Bottom);
		logout.getChildren().addAll(logOut);
		
		changeNachfolger.setOnAction(e -> getFXController().showView("userlistview"));
		back.setOnAction(e -> getFXController().showView("managementselectionview"));
		
		logOut.setOnAction(e -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Logout");
			alert.setHeaderText("Sie sind gerade dabei sich auszuloggen.");
			alert.setContentText("Sind Sie sich sicher, dass sie das tun wollen?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				Alert info = new Alert(AlertType.INFORMATION);
				info.setTitle("Logout");
				info.setHeaderText("Sie wurden erfolgreich ausgeloggt!");
				info.setContentText("Um sich erneut anzumelden, klicken Sie bitte im Hauptmenü auf Benutzer.");
				info.showAndWait();
				Globals.username = "";
				getFXController().showMainView();
			} else {
				Alert noDeletion = new Alert(AlertType.INFORMATION);
				noDeletion.setTitle("Logout");
				noDeletion.setHeaderText("Abmeldung abgebrochen!");
				noDeletion.setContentText("Sie wurden nicht ausgeloggt und bleiben mit dem Benutzer "+"'"+Globals.username+"'"+" eingeloggt.");
				noDeletion.showAndWait();
			    alert.close();
			}
		});
		
		deleteAccount.setOnAction(e -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Account löschen");
			alert.setHeaderText("Sie sind gerade dabei Ihren Account zu löschen.");
			alert.setContentText("Sind Sie sich sicher, dass sie das tun wollen? \nDieser Schritt ist unwiderruflich!");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
			    // ... user chose OK
			} else {
				Alert noDeletion = new Alert(AlertType.INFORMATION);
				noDeletion.setTitle("Löschvorgang abgebrochen");
				noDeletion.setHeaderText("Account nicht gelöscht");
				noDeletion.setContentText("Der Löschvorgang wurde abgebrochen.");
				noDeletion.showAndWait();
			    alert.close();
			}});
		
		bp.setCenter(AllFields);
		bp.setRight(logout);
		
		return bp;
	}

	@Override
	public void refreshView()
	{
		username = new Label(getFXController().getFXModel("usersecuritymodel").getProperty("name"));

	}

}
