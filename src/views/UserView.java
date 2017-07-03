package views;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
	PasswordField password; 
	
	VBox AllFields;
	HBox Top;
	HBox Bottom;
	VBox TopRight;
	VBox BottomRight;
	

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
		
		Top.setId("Top");
		Bottom.setId("Bottom");
		AllFields.setId("AllFields");
		
//		username = new Label("Here's the name of the user");
//		username.setId("username");
		changeNachfolger = new AppButton("Nachfolger ändern");
		deleteAccount = new AppButton("Konto entfernen");
		
		username = new Label("{dein Nachfolger}");
		nachfolger = new Label("Nachfolger für das DMO:");
		passwordText = new Label("Passwort bestätigen:");
		
		password = new PasswordField();
		password.setPromptText("Passwort eingeben...");
		

		back = new BackButton(getFXController(), "Zurück");

		TopRight.getChildren().addAll(username, changeNachfolger);
		BottomRight.getChildren().addAll(password, deleteAccount, back);
		Top.getChildren().addAll(nachfolger, TopRight);
		Bottom.getChildren().addAll(passwordText, BottomRight);
		
		AllFields.getChildren().addAll(Top, Bottom);
		
		changeNachfolger.setOnAction(e -> getFXController().showView("userlistview"));
		
		bp.setCenter(AllFields);
		
		return bp;
	}

	@Override
	public void refreshView()
	{
		username = new Label(getFXController().getFXModel("usersecuritymodel").getProperty("name"));

	}

}
