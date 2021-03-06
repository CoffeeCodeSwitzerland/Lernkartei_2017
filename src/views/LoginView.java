package views;

import org.mindrot.jbcrypt.BCrypt;

import globals.Globals;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;
import views.components.HomeButton;

public class LoginView extends FXView
{

	public LoginView(String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}

	BorderPane bp = new BorderPane();

	VBox AllFields;
	HBox Controls;
	HBox Errorbox;

	Label errorText;

	TextField txtName;
	PasswordField pwPassword;

	HomeButton home;
	AppButton reg;
	AppButton log;

	@Override
	public Parent constructContainer()
	{
		bp.setId("loginviewbg");

		Errorbox = new HBox(50);
		Errorbox.setAlignment(Pos.CENTER);
		Errorbox.setPadding(new Insets(20));

		AllFields = new VBox(50);
		AllFields.setAlignment(Pos.CENTER);
		AllFields.setMaxWidth(300);
		AllFields.setPadding(new Insets(20));

		Controls = new HBox(50);
		Controls.setAlignment(Pos.CENTER);
		Controls.setPadding(new Insets(20));

		txtName = new TextField();
		txtName.setPromptText("Username");

		pwPassword = new PasswordField();
		pwPassword.setPromptText("Passwort");
		;

		home = new HomeButton(getFXController());
		reg = new AppButton("Noch kein Profil?");
		log = new AppButton("Login");

		errorText = new Label("");
		Errorbox.getChildren().addAll(errorText);

		Controls.getChildren().addAll(home, reg);

		AllFields.getChildren().addAll(txtName, pwPassword, log);

		bp.setTop(Errorbox);
		bp.setCenter(AllFields);
		bp.setBottom(Controls);

		reg.setOnAction(e -> getFXController().showAndTrackView("registerview"));
		log.setOnAction(e ->
		{

			// So that you can always check again who was logged in last,
			// but if user logged in, it should be set as ""
			Globals.lastRegisteredUser = "";

			String name = txtName.getText();
			String password = pwPassword.getText();

			// So that entries don't exist after leaving the view
			txtName.clear();
			pwPassword.clear();

			if (name.equals("user@mail.com") && BCrypt.checkpw(password, "$2a$12$8hcwdnkJ7uO3FvDEG/1fv.vaSNI/FfZqEzfUZFUYRUHLzRjkV/Z5.")/*plaintext-password: "gibb"*/)
			{
				Globals.username = name;
				getFXController().showAndTrackView("managementselectionview");
			} else
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fehler 0x0001");
				alert.setHeaderText("Login-Daten inkorrekt.");
				alert.setContentText(
						"Benutzername und Passwort stimmen nicht �berein oder existieren nicht.\nStellen Sie sicher, dass Ihre Eingaben korrekt sind.");
				pwPassword.clear();
				txtName.clear();
				alert.showAndWait();
			}
		});

		return bp;
	}

	@Override
	public void refreshView()
	{
		if (Globals.username.equals(""))
		{
			bp.setId("loginviewbg");

			if (!Globals.lastRegisteredUser.equals(""))
			{
				txtName.setText(Globals.lastRegisteredUser);
			}

			// Zuerst �berpr�fen ob Felder gef�llt
			// Dann �berpr�fen ob Eingaben korrekt
			// Als drittes �berpr�fen, ob der User existiert oder nicht
			// Wenn alles klappt noch in die Datenbank speichern und zum Schluss
			// in den UserView wechseln
			if (!(txtName.equals(null) && txtName.equals("") && pwPassword.equals(null) && pwPassword.equals("")))
			{
				if (txtName.equals("mama"))
				{
					getFXController().showAndTrackView("userview");
				} else
				{
					errorText = new Label("Die Eingaben entsprechen nicht unseren Richtlinien");
				}
			} else
			{
				errorText = new Label("Sie m�ssen die Felder ausf�llen");
			}
		} else
		{
			getFXController().showAndTrackView("managementselectionview");
		}

	}

}
