package views;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

	Button createGroup;
	Button deleteGroup;
	Button modifyGroup;

	BackButton back;
	TabPane tabPane;
	
	ListView<String> list;
	ObservableList<String> items;

	@Override
	public Parent constructContainer()
	{
		bp = new BorderPane();
		bp.setId("userviewbg");
		
		tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		 
	    Tab tabPersonal = new Tab();
	    tabPersonal.setText("Pers�nlich");
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
	         
	    list = new ListView<String>();
	    items =FXCollections.observableArrayList (
	        "Gruppe1", "Gruppe2", "Gruppe3", "Gruppe4");
	    list.setItems(items);   
	    
		modifyGroup = new Button("Gruppen-Mitglieder bearbeiten");
		createGroup = new Button("+");
		deleteGroup = new Button("-");
		
		createGroup.setOnAction(e -> getFXController().showView("groupcreateview"));
		modifyGroup.setOnAction(e -> getFXController().showView("groupmemberview"));
		
		deleteGroup.setOnAction(e -> {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Mitglied l�schen");
		alert.setHeaderText("Sie sind gerade dabei ein Mitglied aus der Gruppe zu entfernen.");
		alert.setContentText("Sind Sie sich sicher, dass sie das tun wollen?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    // ... user chose OK
		} else {
		    // ... user chose CANCEL or closed the dialog
		}});
		

		//Doesn't need any onClick-Listener, because it's default
		back = new BackButton(getFXController(), "Zur�ck");
		
		HBox bottom = new HBox(50);
		bottom.getChildren().addAll(modifyGroup, createGroup, deleteGroup);
		bottom.setPadding(new Insets(0,0,20,450));
		
		bp.setTop(tabPane);
		bp.setCenter(list);
		bp.setBottom(bottom);

		return bp;
	}

	@Override
	public void refreshView()
	{

	}

}
