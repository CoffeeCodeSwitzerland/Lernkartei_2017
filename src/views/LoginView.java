package views;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
	
	BorderPane bp;

	HBox Fields;
	HBox Controls;
	
	Label lblName;
	Label lblPassword;
	
	TextField txtName;
	PasswordField pwPassword;
	
	HomeButton home;
	AppButton reg;
	AppButton log;
	
	@Override
	public Parent constructContainer()
	{
		bp = new BorderPane();

		Fields = new HBox(50);
		Controls = new HBox(50);
		
		lblName = new Label("Gib deinen Usernamen ein");
		lblPassword = new Label("Gib dein Passwort ein");
		
		txtName = new TextField();
		pwPassword = new PasswordField();
		
		home = new HomeButton(getFXController());
		reg = new AppButton("Registrieren");
		log = new AppButton("Login");
		
		Fields.getChildren().addAll(lblName, txtName, lblPassword, pwPassword);
		Controls.getChildren().addAll(home, log, reg);
		
		bp.setCenter(Fields);
		bp.setBottom(Controls);
		
		return bp;
	}

	@Override
	public void refreshView()
	{
		// TODO Auto-generated method stub
		
	}

}
