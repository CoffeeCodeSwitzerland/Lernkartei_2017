package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;
import views.components.BackButton;

public class UserListView extends FXView
{

	public UserListView(String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}
	
	BorderPane bp = new BorderPane();

	VBox AllFields;
	HBox SearchUser;
	HBox Bottom;

	TextField txtUserName;
	
	ListView<String> list;
	ObservableList<String> items;
	
	AppButton btnSearch;
	AppButton btnAdd;
	BackButton back;
	
	
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
		
		SearchUser = new HBox();
		Bottom = new HBox();
		
		txtUserName = new TextField();
		txtUserName.setMinHeight(50);
		txtUserName.setMinWidth(700);
		txtUserName.setPromptText("Email-Adresse des Benutzers");
		
		btnSearch = new AppButton("Suchen");
		btnAdd = new AppButton("Hinzufügen");
		back = new BackButton(getFXController(),"Zurück");
		
		SearchUser.getChildren().addAll(txtUserName,btnSearch);
		Bottom.getChildren().addAll(back,btnAdd);
		AllFields.getChildren().addAll(SearchUser,list,Bottom);
		
		bp.setLeft(AllFields);
		
		//btnSearch.setOnAction(e -> getFXController().showView("userlist"));
		
		
		return bp;
	}

	@Override
	public void refreshView()
	{
		bp.setId("loginviewbg");
	}

}
