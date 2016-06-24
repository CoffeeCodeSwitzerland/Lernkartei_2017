package views;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;
import views.components.ContainerLayout;
import views.components.ControlLayout;

public class UserView extends FXView
{

	public UserView(String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}
	
	BorderPane bp;

	// Der Header Bereich setzt sich aus den Buttons zum ändern der
	// Benutzerdaten und dem Username zusammen.
	ContainerLayout header;
	ControlLayout changeButtons;
	ControlLayout uName;

	Label username;
	AppButton changeName;
	AppButton changePasswort;
	AppButton changeEmail;

	// Der Body setzte sich aus zwei ContainerLAyouts zusammen (Links und
	// Rechts) darin sind dann nochmals mehrere Elemente vorhanden. Vorgesehen
	// ist eine Statistik und eine Auflistung aller bereits gelernten Karteien
	
	//Rechts
	ContainerLayout right;

	Label hinweis;
	
	//Links
	ContainerLayout left;
	
	ListView<String> stacks;
	AppButton learn;

	// Der Footer enthält die Buttons, um ins Hauptmenü zurück zu gelangen oder
	// andere Aktionen durchzuführen. Er entspricht der Hbox "Controls"
	ControlLayout footer;

	AppButton back;

	@Override
	public Parent constructContainer()
	{
		bp = new BorderPane();
		bp.setId("userviewbg");
		
		header = new ContainerLayout();
		
		changeButtons = new ControlLayout();
		changeButtons.setPadding(new Insets(5));
		
		uName = new ControlLayout();
		uName.setPadding(new Insets(15));

		username = new Label("Here's the name of the user");
		username.setId("username");
		changeName = new AppButton("Namen ändern");
		changePasswort = new AppButton("Passwort ändern");
		changeEmail = new AppButton("E-Mail ändern");

		right = new ContainerLayout();
		hinweis = new Label("Hinweis : Hier wird später eine Statistik angezeigt");
		
		left = new ContainerLayout();
		stacks = new ListView<String>();
		learn = new AppButton("lernen");

		footer = new ControlLayout();
		footer.setPadding(new Insets(15));

		back = new AppButton("Zurück");
		
		footer.getChildren().addAll(back);
		left.getChildren().addAll(stacks, learn);
		right.getChildren().addAll(hinweis);
		
		uName.getChildren().addAll(username);
		changeButtons.getChildren().addAll(changeName, changeEmail, changePasswort);
		
		header.getChildren().addAll(uName, changeButtons);

		bp.setBottom(footer);
		bp.setLeft(left);
		bp.setRight(right);
		bp.setTop(header);

		back.setOnAction(e -> getFXController().showMainView());

		return bp;
	}

	@Override
	public void refreshView()
	{
		//username = new Label(getFXController().getFXModel("usersecuritymodel")));

	}

}
