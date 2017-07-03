package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;
import views.components.BackButton;
import views.components.ContainerLayout;
import views.components.ControlLayout;

public class GroupView extends FXView
{

	public GroupView(String newName, FXController newController)
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

	BackButton back;
	TabPane tabPane;

	@Override
	public Parent constructContainer()
	{
		bp = new BorderPane();
		bp.setId("userviewbg");
		
		tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		 
	         Tab tabPersonal = new Tab();
	         tabPersonal.setText("Persönlich");
	         HBox hbox = new HBox();
	         hbox.setPadding(new Insets(20, 0, 0, 0));
	         hbox.setStyle("-fx-font: 24 'System Regular'");
	         hbox.getChildren().add(new Label(tabPersonal.getText()));
	         hbox.setAlignment(Pos.CENTER);
	         tabPersonal.setContent(hbox);
	         
	         Tab tabForeign = new Tab();
	         tabForeign.setText("Fremd");
	         HBox hbox2 = new HBox();
	         hbox2.setPadding(new Insets(20, 0, 0, 0));
	         hbox2.setStyle("-fx-font: 24 'System Regular'");
	         hbox2.getChildren().add(new Label(tabForeign.getText()));
	         hbox2.setAlignment(Pos.CENTER);
	         tabForeign.setContent(hbox2);
	         
	         tabPane.getTabs().addAll(tabPersonal, tabForeign); 
		
		header = new ContainerLayout();
		
		changeButtons = new ControlLayout();
		changeButtons.setPadding(new Insets(5));
		
		uName = new ControlLayout();
		uName.setPadding(new Insets(15));

//		username = new Label("Here's the name of the user");
//		username.setId("username");
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

		back = new BackButton(getFXController(), "Zurück");
		
		footer.getChildren().addAll(back);
		left.getChildren().addAll(stacks, learn);
		right.getChildren().addAll(hinweis);
		
//		uName.getChildren().addAll(username);
		changeButtons.getChildren().addAll(changeName, changeEmail, changePasswort);
		
		header.getChildren().addAll(uName, changeButtons);

		bp.setBottom(footer);
		bp.setLeft(left);
		bp.setRight(right);
		bp.setTop(tabPane);

		return bp;
	}

	@Override
	public void refreshView()
	{
		username = new Label(getFXController().getFXModel("usersecuritymodel").getProperty("name"));

	}

}
