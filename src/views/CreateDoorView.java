package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;
import views.components.BackButton;
import views.components.HomeButton;

public class CreateDoorView extends FXView
{

	public CreateDoorView(String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}
	
	BorderPane bp = new BorderPane();
	
	VBox AllFields;
	HBox Identifier;
	HBox Status;
	HBox Bottom;
	
	Label name;
	Label status;
	Label statusValue;
	
	TextField inputName;

	AppButton btnCreate;
	
	BackButton back;
	
	@Override
	public Parent constructContainer()
	{
		bp.setId("loginviewbg");
		
		AllFields = new VBox(50);
		AllFields.setAlignment(Pos.CENTER);
		AllFields.setMaxWidth(300);
		AllFields.setPadding(new Insets(20));
		
		
		Identifier = new HBox();
		Status = new HBox();
		Bottom = new HBox();
		
		inputName = new TextField();
		inputName.setPromptText("Names der Door");
		inputName.setAlignment(Pos.CENTER_LEFT);
		
		name = new Label("Name:");
		status = new Label("Status:");
		statusValue = new Label("{Door bereits vorhanden}");

		btnCreate = new AppButton("Erstellen");
		back = new BackButton(getFXController(),"Abbrechen");
		
		Identifier.getChildren().addAll(name,inputName);
		Status.getChildren().addAll(status,statusValue);
		Bottom.getChildren().addAll(back,btnCreate);
		AllFields.getChildren().addAll(Identifier,Status,Bottom);
		
		bp.setCenter(AllFields);
		
		/*
		btnCreate.setOnAction(e -> getFXController().showView("serverdoorview"));
		--Listenter to crete a door
		*/
		
		
		return bp;
	}

	@Override
	public void refreshView()
	{
		bp.setId("loginviewbg");
	}

}
