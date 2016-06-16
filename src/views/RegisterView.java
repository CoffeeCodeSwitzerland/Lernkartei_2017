package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;
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
	
	TextField txtName;
	PasswordField pwPassword;
	PasswordField pwToCheck;
	TextField txtMail;
	
	HomeButton home;
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
		
		txtName = new TextField();
		txtName.setPromptText("Username");
		pwPassword = new PasswordField();
		pwPassword.setPromptText("Passwort");
		pwToCheck = new PasswordField();
		pwToCheck.setPromptText("Passwort wiederholen");
		txtMail = new TextField();
		txtMail.setPromptText("E-Mail");
		
		reg = new AppButton("Registrieren");
		home = new HomeButton(getFXController());
		
		AllFields.getChildren().addAll(txtName, pwPassword, pwToCheck, txtMail, reg);
		Controls.getChildren().addAll(home);
		
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
