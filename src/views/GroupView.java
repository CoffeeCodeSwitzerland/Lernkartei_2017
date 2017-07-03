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
	    tabPane.getSelectionModel().getSelectedItem().setStyle("-fx-background-color:#a3a4a8");
	    
	         
	    list = new ListView<String>();
	    items =FXCollections.observableArrayList (
	        "Gruppe1", "Gruppe2", "Gruppe3", "Gruppe4");
	    list.setItems(items);   
	    
		modifyGroup = new Button("Gruppen-Mitglieder bearbeiten");
		createGroup = new Button("+");
		deleteGroup = new Button("-");
		
		createGroup.setOnAction(e -> getFXController().showView("groupcreateview"));
		modifyGroup.setOnAction(e -> getFXController().showView("groupmemberview"));
		tabPane.setOnMouseClicked(e -> {
			for(Tab actTab:tabPane.getTabs())
			{
				actTab.setStyle("-fx-background-color:#f0f0f0");
			}
			tabPane.getSelectionModel().getSelectedItem().setStyle("-fx-background-color:#a3a4a8");
		});
		
		// Make sure user wants to delete member
		deleteGroup.setOnAction(e -> {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Gruppe l�schen");
		alert.setHeaderText("Sie sind gerade dabei eine Gruppe zu entfernen.");
		alert.setContentText("Sind Sie sich sicher, dass sie das tun wollen?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    // ... user chose OK
		} else {
			Alert noDeletion = new Alert(AlertType.INFORMATION);
			noDeletion.setTitle("L�schvorgang abgebrochen");
			noDeletion.setHeaderText("Gruppe nicht gel�scht");
			noDeletion.setContentText("Der L�schvorgang wurde abgebrochen.");
			noDeletion.showAndWait();
		    alert.close();
		}});
		

		back = new BackButton(getFXController(), "Zur�ck");
		back.setOnAction(e -> getFXController().showView("managementselectionview"));
		back.setPadding(new Insets(0,100,0,0));
		
		HBox bottom = new HBox(50);
		bottom.getChildren().addAll(back, modifyGroup, createGroup, deleteGroup);
		bottom.setPadding(new Insets(0,0,20,250));
		
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
