package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;
import views.components.BackButton;
import views.components.HomeButton;

public class SaveDownloadstackForeignDMOView extends FXView
{

	public SaveDownloadstackForeignDMOView(String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}
	
	BorderPane bp = new BorderPane();

	VBox AllFields;
	HBox SearchDMO;
	HBox Bottom;

	TextField txtDMO;
	
	ListView<String> list;
	ObservableList<String> items;
	
	AppButton btnSearch;
	AppButton btnContinue;
	BackButton back;
	
	TabPane tabPane;
	
	
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
		
		tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		 
	    Tab tabDerive = new Tab();
	    tabDerive.setText("Derive");
	    HBox hbox = new HBox();
	    hbox.setPadding(new Insets(20, 0, 0, 0));
	    hbox.setStyle("-fx-font: 24 'System Regular'");
	    hbox.getChildren().add(new Label(tabDerive.getText()));
	    hbox.setAlignment(Pos.CENTER);
	    tabDerive.setContent(hbox);
	         
	    Tab tabTeamwork = new Tab();
	    tabTeamwork.setText("Fremd");
	    HBox hbox2 = new HBox();
	    hbox2.setPadding(new Insets(20, 0, 0, 0));
	    hbox2.setStyle("-fx-font: 24 'System Regular'");
	    hbox2.getChildren().add(new Label(tabTeamwork.getText()));
	    hbox2.setAlignment(Pos.CENTER);
	    tabTeamwork.setContent(hbox2);
	         
	    tabPane.getTabs().addAll(tabDerive, tabTeamwork);
	    tabPane.getSelectionModel().getSelectedItem().setStyle("-fx-background-color:#a3a4a8");
	    
	    tabPane.setOnMouseClicked(e -> {
			for(Tab actTab:tabPane.getTabs())
			{
				actTab.setStyle("-fx-background-color:#f0f0f0");
			}
			tabPane.getSelectionModel().getSelectedItem().setStyle("-fx-background-color:#a3a4a8");
		});
		
		SearchDMO = new HBox();
		Bottom = new HBox();
		
		txtDMO = new TextField();
		txtDMO.setMinHeight(50);
		txtDMO.setMinWidth(700);
		txtDMO.setPromptText("Email-Adresse des Benutzers");
		
		btnSearch = new AppButton("Suchen");
		btnContinue = new AppButton("Weiter zum DMO");
		back = new BackButton(getFXController(),"Zurück");
		
		SearchDMO.getChildren().addAll(txtDMO,btnSearch);
		Bottom.getChildren().addAll(back, btnContinue);
		AllFields.getChildren().addAll(SearchDMO,list,Bottom);
		
		bp.setLeft(AllFields);
		bp.setCenter(tabPane);
		bp.setBottom(Bottom);
			
		
		return bp;
	}

	@Override
	public void refreshView()
	{
		bp.setId("loginviewbg");
	}

}
