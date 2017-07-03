package views;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
	HBox GroupName;
	HBox Option;
	
	AppButton btnAdd;
	AppButton btnRemove;
	
	BackButton back;
	
	Label name;
	Label groupname;
	
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
		
		GroupName = new HBox(50);
		Option = new HBox(50);
		
		name = new Label("Name:");
		groupname = new Label("{Gruppenname}");
		
		btnAdd = new AppButton("Hinzufügen");
		btnRemove = new AppButton("Entfernen");
		back = new BackButton(getFXController(),"Zurück");
		
		
		
		GroupName.getChildren().addAll(name,groupname);
		Option.getChildren().addAll(back,btnAdd,btnRemove);
		
		AllFields.getChildren().addAll(GroupName,Option,list);
		
		
		bp.setCenter(AllFields);
		
		btnAdd.setOnAction(e -> getFXController().showView("userlist"));
		btnRemove.setOnAction(e -> {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Mitglied löschen");
		alert.setHeaderText("Sie sind gerade dabei ein Mitglied aus der Gruppe zu entfernen.");
		alert.setContentText("Sind Sie sich sicher, dass sie das tun wollen?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    // ... user chose OK
		} else {
			Alert noDeletion = new Alert(AlertType.INFORMATION);
			noDeletion.setTitle("Löschvorgang abgebrochen");
			noDeletion.setHeaderText("Mitglied nicht gelöscht");
			noDeletion.setContentText("Der Löschvorgang wurde abgebrochen.");
			noDeletion.showAndWait();
		    alert.close();
		}});
		
		return bp;
	}

	@Override
	public void refreshView()
	{
		bp.setId("loginviewbg");
	}

}
