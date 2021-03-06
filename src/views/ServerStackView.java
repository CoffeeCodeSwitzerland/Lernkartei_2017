package views;

import java.util.ArrayList;

import debug.Debugger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;
import views.components.BackButton;

public class ServerStackView extends FXView
{

	public ServerStackView(String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}
	
	BorderPane bp;
	ScrollPane sMain;

	AppButton btnSearch;
	
	BackButton back;
	
	TextField txtSearch;
	
	VBox StackShowList = new VBox();
	HBox Search;
	VBox Center;
	
	ArrayList<HBox> lines = new ArrayList<HBox>();
	
	TabPane tabPane;
	
	ListView<String> list;
	ObservableList<String> items;

	@Override
	public Parent constructContainer()
	{
		bp = new BorderPane();
		bp.setId("userviewbg");
		
		back = new BackButton(getFXController(),"Zur�ck");
		
		tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			   @Override
			   public void changed(ObservableValue<? extends Tab> ov, Tab tabOld, Tab tabNew) {
			      tabAuswaehlen();
			   }
			});
		 
	    Tab tabPersonal = new Tab();
	    tabPersonal.setText("Lokal");
	    HBox hbox = new HBox();
	    hbox.setPadding(new Insets(20, 0, 0, 0));
	    hbox.setStyle("-fx-font: 24 'System Regular'");
	    hbox.getChildren().add(new Label(tabPersonal.getText()));
	    hbox.setAlignment(Pos.CENTER);
	    
	    tabPane.setOnMouseClicked(e -> {
			for(Tab actTab:tabPane.getTabs())
			{
				actTab.setStyle("-fx-background-color:#f0f0f0");
			}
			tabPane.getSelectionModel().getSelectedItem().setStyle("-fx-background-color:#a3a4a8");
		});
	       
	    Center = new VBox();
	    Search = new HBox();
	    sMain = new ScrollPane();
	    
	    txtSearch = new TextField();
	    txtSearch.setPromptText("Suche nach Stacks");
	    txtSearch.setMinWidth(740);
	    txtSearch.setMinHeight(50);
	    btnSearch = new AppButton("Suchen");
	    
	    StackShowList.setSpacing(20);
	    Center.setSpacing(20);
	    
	    sMain.setContent(StackShowList);
	    Search.getChildren().addAll(txtSearch,btnSearch);
	    Center.getChildren().addAll(Search,sMain);
	    
	    
	    tabPersonal.setContent(hbox);
	         
	    Tab tabForeign = new Tab();
	    tabForeign.setText("Server");
	    HBox hbox2 = new HBox();
	    hbox2.setPadding(new Insets(20, 0, 0, 0));
	    hbox2.setStyle("-fx-font: 24 'System Regular'");
	    hbox2.getChildren().add(new Label(tabForeign.getText()));
	    hbox2.setAlignment(Pos.CENTER);
	    tabForeign.setContent(hbox2); 
	         
	    tabPane.getTabs().addAll(tabPersonal, tabForeign); 
	    
	    tabPane.setOnMouseClicked(e -> {
			for(Tab actTab:tabPane.getTabs())
			{
				actTab.setStyle("-fx-background-color:#f0f0f0");
			}
			tabPane.getSelectionModel().getSelectedItem().setStyle("-fx-background-color:#a3a4a8");
		});
	    tabPane.getSelectionModel().getSelectedItem().setStyle("-fx-background-color:#a3a4a8");
	         
	    /*list = new ListView<String>();
	    items =FXCollections.observableArrayList (
	        "Gruppe1", "Gruppe2", "Gruppe3", "Gruppe4");
	    list.setItems(items);   */
	    
		
		
		/*createGroup.setOnAction(e -> getFXController().showView("groupcreateview"));
		modifyGroup.setOnAction(e -> geytFXController().showView("groupmemberview"));*/
		
		back = new BackButton(getFXController(), "Zur�ck");
		back.setOnAction(e -> getFXController().showAndTrackView("serverdoorview"));
		
		
		HBox bottom = new HBox(50);
		//bottom.getChildren().addAll(modifyGroup, createGroup, deleteGroup);
		bottom.setPadding(new Insets(0,0,20,450));
		bottom.getChildren().add(back);
		
		
		
		bp.setTop(tabPane);
		bp.setCenter(Center);
		bp.setBottom(bottom);

		try {
			getFXController().getModel("serverstack").registerView(this);
		} catch (Exception e) {
			Debugger.out("ServerStackView-constructContainer: did not found a Model named 'serverstack'!");
		}		
		
		return bp;
	}

	@Override
	public void refreshView()
	{
		ArrayList<String> givenData;
		try {
			givenData = getFXController().getModel("serverstack").getDataList("");
			int selected = Integer.parseInt(givenData.get(1));
			tabPane.getSelectionModel().select(selected);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void tabAuswaehlen()
	{
		lines.clear();
		StackShowList.getChildren().clear();
		tabPane.getSelectionModel().getSelectedItem().setStyle("-fx-backgound-color:#800000");
		ArrayList<String> names = new ArrayList<String>();
		int dataLength = 4;
				
		
		
		if(tabPane.getSelectionModel().getSelectedIndex() == 0)
		{
			
			names.clear();
	
		//ArrayList<AppButton> btnList = new ArrayList<AppButton>();
			names.add("first Stacklocal");
			names.add("second Stacklocal");
			names.add("third Stacklocal");
			names.add("fourth Stacklocal");
		
		
		for(int i = 0; i < dataLength; i++)
		{
			HBox entry = new HBox();
			Label lblServerStack = new Label(names.get(i));
			lblServerStack.setMinWidth(590);
			lblServerStack.setPadding(new Insets(15,0,0,250));
			AppButton btnDownload = new AppButton("Hochladen");
			AppButton btnInformation = new AppButton("i");
			btnInformation.setOnAction(e -> getFXController().showAndTrackView("doorstackinformationview"));
			btnDownload.setOnAction(e -> getFXController().showAndTrackView("saveuploadstackonserverdialogview"));
			
			entry.getChildren().addAll(lblServerStack,btnDownload,btnInformation);
			lines.add(entry);
		}
		}
		else
		{
			names.clear();
			
			names.add("first Stackserver");
			names.add("second Stackserver");
			names.add("third Stackserver");
			names.add("fourth Stackserver");
			
			//ArrayList<AppButton> btnList = new ArrayList<AppButton>();
			
			
			
			for(int i = 0; i < dataLength; i++)
			{
				HBox entry = new HBox();
				Label lblServerStack = new Label(names.get(i));
				lblServerStack.setMinWidth(590);
				lblServerStack.setPadding(new Insets(15,0,0,250));
				AppButton btnDownload = new AppButton("Herunterladen");
				AppButton btnInformation = new AppButton("i");
				btnInformation.setOnAction(e -> getFXController().showAndTrackView("serverdoorstackinformationview"));
				btnDownload.setOnAction(e -> getFXController().showAndTrackView("stacksavepathselectionview"));
				
				entry.getChildren().addAll(lblServerStack,btnDownload,btnInformation);
				lines.add(entry);
			}
		}
		
		for(HBox l : lines)
		{
			StackShowList.getChildren().add(l);
		}
	}
}
