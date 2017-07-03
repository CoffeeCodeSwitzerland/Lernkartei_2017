package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;
import views.components.BackButton;
import views.components.HomeButton;

public class GroupMemberView extends FXView
{

	public GroupMemberView(String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}
	
	BorderPane bp = new BorderPane();

	VBox AllFields;
	HBox CheckGroup;
	HBox ShowStatus;
	HBox Option;
	
	AppButton btnCheck;
	AppButton btnAdd;
	AppButton btnRemove;
	
	BackButton back;
	
	Label name;
	Label status;
	Label statusValue;
	
	TextField txtGroupName;
	
	ListView<String> list;
	ObservableList<String> items;
	
	@Override
	public Parent constructContainer()
	{
		bp.setId("loginviewbg");
		
		list = new ListView<String>();
		items = FXCollections.observableArrayList("Philippe Krüttli","Irina Deck","Javier Martinez Alvarez","Frithjof Hoppe");
		list.setItems(items);		
		
		AllFields = new VBox(50);
		AllFields.setAlignment(Pos.CENTER);
		AllFields.setMaxWidth(300);
		AllFields.setPadding(new Insets(20));
		
		CheckGroup = new HBox(50);
		ShowStatus = new HBox(50);
		Option = new HBox(50);
		
		txtGroupName = new TextField();
		txtGroupName.setPromptText("Gruppenname");
		
		name = new Label("Name:");
		status = new Label("Status");
		statusValue = new Label("{Verfügbarkeitsstatus}");
		
		btnCheck = new AppButton("Prüfen");
		btnAdd = new AppButton("Hinzufügen");
		btnRemove = new AppButton("Entfernen");
		back = new BackButton(getFXController(),"Zurück");
		
		AllFields.getChildren().addAll(CheckGroup,ShowStatus,Option,list);
		
		CheckGroup.getChildren().addAll(name,txtGroupName,btnCheck);
		ShowStatus.getChildren().addAll(status,statusValue);
		Option.getChildren().addAll(back,btnAdd,btnRemove);
		
		
		bp.setCenter(AllFields);
		
		btnAdd.setOnAction(e -> getFXController().showView("userlist"));
		btnRemove.setOnAction(e -> getFXController().showView("groupview"));
		back.setOnAction(e -> getFXController().showView("groupview"));
		btnCheck.setOnAction(e -> getFXController().showView(""));
		
		return bp;
	}

	@Override
	public void refreshView()
	{
		bp.setId("loginviewbg");
	}

}
