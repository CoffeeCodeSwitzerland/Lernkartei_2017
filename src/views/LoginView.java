package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.ModelInterface.Command;
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
		pwPassword.setPromptText("Passwort");;
		
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
		
		reg.setOnAction(e -> getFXController().showView("registerview"));		
		
		return bp;
	}

	@Override
	public void refreshView()
	{
		bp.setId("loginviewbg");

		log.setOnAction(e -> getFXController().showView("userview"));
		
	}

}
