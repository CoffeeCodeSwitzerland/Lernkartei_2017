package views;

import org.mindrot.jbcrypt.BCrypt;

import globals.Globals;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;
import serverdb.usermgmt.User;
import views.components.AppButton;
import views.components.BackButton;
import views.components.HomeButton;

public class RegisterView extends FXView
{

	public RegisterView(String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}

	BorderPane bp = new BorderPane();

	VBox AllFields;
	HBox Controls;
	
	TextField txtFirstName;
	TextField txtLastName;
	PasswordField pwPassword;
	PasswordField pwToCheck;
	TextField txtMail;
	
	HomeButton home;
	BackButton back;
	AppButton reg;
	
	@Override
	public Parent constructContainer()
	{
		bp.setId("registerviewbg");
		
		AllFields = new VBox(50);
		AllFields.setAlignment(Pos.CENTER);
		AllFields.setMaxWidth(300);
		AllFields.setPadding(new Insets(20));
		
		Controls = new HBox(50);
		Controls.setAlignment(Pos.CENTER);
		Controls.setPadding(new Insets(20));
		
		txtFirstName = new TextField();
		txtFirstName.setPromptText("Vorname");
		txtLastName = new TextField();
		txtLastName.setPromptText("Nachname");
		pwPassword = new PasswordField();
		pwPassword.setPromptText("Passwort");
		pwToCheck = new PasswordField();
		pwToCheck.setPromptText("Passwort wiederholen");
		txtMail = new TextField();
		txtMail.setPromptText("E-Mail");
		
		reg = new AppButton("Registrieren");
		home = new HomeButton(getFXController());
		back = new BackButton(getFXController(), "Zurück");
		
		reg.setOnAction(e -> {
			if(pwPassword.getText().equals(pwToCheck.getText()))
			{
				User u = new User();
				String hashedPW = BCrypt.hashpw(pwPassword.getText(), BCrypt.gensalt(12));
				u.registerUser(txtFirstName.getText(),txtLastName.getText(),hashedPW,txtMail.getText());
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Registrierung");
				alert.setHeaderText("Registrierung erfolgreich abgeschlossen!");
				alert.setContentText("Sie wurden erfolgreich bei WISSLearncards registriert und können sich nun einloggen.");
				alert.showAndWait();
				//So that user doesn't need to enter his username (mail)
				Globals.lastRegisteredUser = txtMail.getText();
				
				//So that entries don't exist after leaving the view
				txtFirstName.clear();
				txtLastName.clear();
				pwPassword.clear();
				pwToCheck.clear();
				txtMail.clear();
				
				getFXController().showAndTrackView("loginview");
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
		
		AllFields.getChildren().addAll(txtFirstName,txtLastName, pwPassword, pwToCheck, txtMail, reg);
		Controls.getChildren().addAll(back, home);
		
		bp.setBottom(Controls);
		bp.setCenter(AllFields);
		
		return bp;
	}

	@Override
	public void refreshView()
	{
		bp.setId("registerviewbg");
	}

}
