package views;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.BackButton;
import views.components.HomeButton;

public class DoorStackInformationView extends FXView
{

	public DoorStackInformationView(String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}
	
	BorderPane bp = new BorderPane();
	
	Label topValue;
	Label user;
	Label userValue;
	Label lastChange;
	Label lastChangeValue;
	Label derive;
	Label deriveValue;
	Label teamwork;
	Label teamworkValue;

	HBox top;
	HBox ownerInformation;
	HBox lastChangeInformation;
	HBox deriveInformation;
	HBox teamworkInformation;
	VBox AllFields;
	VBox Bottom;

	BackButton back;
	
	HomeButton home;
	
	ArrayList<String> nameOfObject;
	ArrayList<Label> labelLeft = new ArrayList<Label>();
	
	@Override
	public Parent constructContainer()
	{
		bp.setId("loginviewbg");
		
		
		AllFields = new VBox(50);
		AllFields.setAlignment(Pos.CENTER);
		AllFields.setMaxWidth(300);
		AllFields.setSpacing(50);


		top = new HBox();
		top.setStyle("-fx-font: 24 'System Regular'");
		
		ownerInformation = new HBox();
		
		
		lastChangeInformation = new HBox();
		deriveInformation = new HBox();
		teamworkInformation = new HBox();
		AllFields = new VBox();
		
		Bottom = new VBox();
		
		back = new BackButton(getFXController(),"Zurück");
		
		home = new HomeButton(getFXController());
		
		topValue = new Label("");		
		user = new Label("Besitzer:");
		userValue = new Label();
		lastChange = new Label("Lezte Änderung:");
		lastChangeValue = new Label();
		derive = new Label("Derive-Gruppe");
		deriveValue = new Label();
		teamwork = new Label("Teamwork-Gruppe:");
		teamworkValue = new Label();

		
		top.getChildren().addAll(topValue);
		ownerInformation.getChildren().addAll(user,userValue);
		lastChangeInformation.getChildren().addAll(lastChange,lastChangeValue);
		deriveInformation.getChildren().addAll(derive,deriveValue);
		teamworkInformation.getChildren().addAll(teamwork,teamworkValue);
		Bottom.getChildren().addAll(ownerInformation,lastChangeInformation,deriveInformation,teamworkInformation);
		
		AllFields.getChildren().addAll(top,Bottom,back);
		
		bp.setPadding(new Insets(180,0,0,310));
		bp.setCenter(AllFields);
		
		//getFXController().getModel("doorstackinfomrationmodel").registerView(this);
		
		return bp;
	}

	@Override
	public void refreshView()
	{
		bp.setId("loginviewbg");
		try {
			nameOfObject = getFXController().getModel("doorstackinformationmodel").getDataList("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		topValue.setText(nameOfObject.get(0));
		
	}

}
