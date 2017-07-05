package views;

import java.util.ArrayList;

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

public class DMOModifyDoorView extends FXView
{

	public DMOModifyDoorView(String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}
	
	BorderPane bp = new BorderPane();
	BorderPane inside = new  BorderPane();
	
	HBox AllFields;
	HBox Name;
	VBox Value;
	VBox Buttons;
	
	Label name;
	Label removeDoor;
	Label removeStacks;
	Label titel;
	
	TextField inputName;

	AppButton btnRemoveDoor;
	AppButton btnRemoveStack;
	AppButton btnModify;
	
	ArrayList<String> actualDoorName = new ArrayList<String>();
	
	BackButton back;
	
	@Override
	public Parent constructContainer()
	{
		bp.setId("loginviewbg");
		
		AllFields = new HBox(50);
		AllFields.setAlignment(Pos.CENTER);
		AllFields.setMaxWidth(300);
		AllFields.setPadding(new Insets(20));
		AllFields.setPadding(new Insets(200,0,0,0));
		//AllFields.setMinWidth(740);
		
		Value = new VBox();
		Value.setMinWidth(400);
		Value.setSpacing(35);
		Buttons = new VBox();
		Buttons.setSpacing(5);
		Name = new HBox();
		
		inputName = new TextField();
		inputName.setPromptText("Names der Door");
		inputName.setAlignment(Pos.CENTER_LEFT);
		inputName.setMinWidth(50);
		
		name = new Label("Name");
		removeDoor = new Label("Door löschen");
		removeStacks = new Label("Enfert den/die Stack/s der Door");
		titel = new Label("Bearbeiten");

		btnRemoveDoor = new AppButton("Entfernen");
		btnRemoveStack = new AppButton("Entfernen");
		btnModify = new AppButton("Ändern");
		back = new BackButton(getFXController(),"Abbrechen");
		
		//Buttons.getChildren().addAll(,,btnRemoveStack,back);
		Name.getChildren().addAll(name,inputName);
		Value.getChildren().addAll(Name,removeDoor,removeStacks);
		Buttons.getChildren().addAll(btnModify,btnRemoveStack,btnRemoveDoor,back);
		AllFields.getChildren().addAll(Value,Buttons);
		
		/*inside.setLeft(AllFields);
		inside.setRight(Buttons);*/
		
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
		actualDoorName = getFXController().getModel("dmomodifydoormodel").getDataList("");
		inputName.setText(actualDoorName.get(0));
		
	}

}
