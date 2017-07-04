package views;

import java.util.ArrayList;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;
import views.components.BackButton;
import views.components.ContainerLayout;
import views.components.ControlLayout;
import views.components.VerticalScroller;

public class SaveDownloadstackOwnDMOView extends FXView
{

	public SaveDownloadstackOwnDMOView(String newName, FXController newController)
	{
		super(newController);
		construct(newName); 
	}
	
	BorderPane bp;

	AppButton btnSearch;
	AppButton btnCreate;
	AppButton back;
	
	TextField txtSearch;
	
	ScrollPane sMain;
	Group MainGroup;
	
	VBox StackShowList = new VBox();
	HBox Search;
	VBox Bottom;
	VBox Center;
	VBox Top;
	
	ArrayList<HBox> lines = new ArrayList<HBox>();
	
	TabPane tabPane;
	
	ListView<String> list;
	ObservableList<String> items;

	@Override
	public Parent constructContainer()
	{
		bp = new BorderPane();
		bp.setId("userviewbg");
		
		back = new BackButton(getFXController(),"Zurück");
		
		tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			   @Override
			   public void changed(ObservableValue<? extends Tab> ov, Tab tabOld, Tab tabNew) {
			      refreshView();
			   }
			});
		 
	    	    
	    Center = new VBox();
	    Search = new HBox();
	    Top = new VBox();
	    
	    
	    txtSearch = new TextField();
	    txtSearch.setPromptText("Suche nach Doors");
	    txtSearch.setMinWidth(740);
	    txtSearch.setMinHeight(50);;
	    btnSearch = new AppButton("Suchen");
	    btnCreate = new AppButton("++Erstellen++");
	    btnCreate.setMinWidth(590);
	    btnCreate.setOnAction(e -> getFXController().showView("createdoorview"));
	    
	    StackShowList.setSpacing(20);
	    Center.setSpacing(20);
	    
	    
	    sMain = new ScrollPane();
	    sMain.setContent(Center);
	    
	    Search.getChildren().addAll(txtSearch,btnSearch);
	    Center.getChildren().addAll(StackShowList,btnCreate);
	    
		back = new AppButton("Zurück");
		back.setOnAction(e-> getFXController().showView("savedownloadstackonserverdialogview"));
		
		
		Bottom = new VBox();
	
		Bottom.getChildren().addAll(back);
		Top.getChildren().addAll(Search,tabPane);
		
		bp.setTop(Top);
		bp.setCenter(sMain);
		bp.setBottom(Bottom);
		
		getFXController().getModel("serverstack").registerView(this);
		
		return bp;
	}

	@Override
	public void refreshView()
	{
		lines.clear();
		StackShowList.getChildren().clear();
		ArrayList<String> names = new ArrayList<String>();
		int dataLength = 8;
		
			names.clear();

			names.add("first Doorlocal");
			names.add("second Doorlocal");
			names.add("third Doorlocal");
			names.add("fourth Doorlocal");
			names.add("first Doorlocal");
			names.add("second Doorlocal");
			names.add("third Doorlocal");
			names.add("fourth Doorlocal");
			names.add("first Doorlocal");
			names.add("second Doorlocal");
			names.add("third Doorlocal");
			names.add("fourth Doorlocal");
			names.add("first Doorlocal");
			names.add("second Doorlocal");
			names.add("third Doorlocal");
			names.add("fourth Doorlocal");
		
		
		for(int i = 0; i < dataLength; i++)
		{
			int counter = i;
			HBox entry = new HBox();
			Label lblServerDoor = new Label(names.get(i));
			lblServerDoor.setPadding(new Insets(15,0,0,250));
			lblServerDoor.setMinWidth(590);
			AppButton btnMerge = new AppButton("Overwrite");
			AppButton btnInformation = new AppButton("i");
			btnInformation.setOnAction(e ->
			{
				ArrayList<String> list = new ArrayList<String>();
				list.add(names.get(counter));
				getFXController().getModel("doorstackinformationmodel").setDataList(list);				
				getFXController().showView("doorstackinformationview");
			}
			);
			

			entry.getChildren().addAll(lblServerDoor,btnInformation,btnMerge);
			lines.add(entry);
		}
		
		for(HBox l : lines)
		{
			StackShowList.getChildren().add(l);
		}
		
	}
}