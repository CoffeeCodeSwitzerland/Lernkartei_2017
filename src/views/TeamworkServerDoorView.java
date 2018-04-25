package views;

import java.util.ArrayList;

import debug.Debugger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
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

public class TeamworkServerDoorView extends FXView
{

	public TeamworkServerDoorView(String newName, FXController newController)
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
	    btnCreate.setOnAction(e -> getFXController().showAndTrackView("createdoorview"));
	    
	    StackShowList.setSpacing(20);
	    Center.setSpacing(20);
	    
	    
	    sMain = new ScrollPane();
	    sMain.setContent(Center);
	    
	    Search.getChildren().addAll(txtSearch,btnSearch);
	    Center.getChildren().addAll(StackShowList,btnCreate);
	    
		back = new AppButton("Zurück");
		back.setOnAction(e-> getFXController().showAndTrackView("lernenselectionview"));
		
		
		Bottom = new VBox();
	
		Bottom.getChildren().addAll(back);
		Top.getChildren().addAll(Search,tabPane);
		
		bp.setTop(Top);
		bp.setCenter(sMain);
		bp.setBottom(Bottom);
		
		try {
			getFXController().getModel("serverstack").registerView(this);
		} catch (Exception e) {
			Debugger.out("TeamworkServerDoorView-constructContainer: did not found a Model named 'serverstack'!");
		}		
		
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
			AppButton btnMerge = new AppButton("Merge");
			AppButton btnInformation = new AppButton("i");
			btnInformation.setOnAction(e ->
			{
				ArrayList<String> list = new ArrayList<String>();
				list.add(names.get(counter));
				try {
					getFXController().getModel("doorstackinformationmodel").setDataList(list);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
				getFXController().showAndTrackView("doorstackinformationview");
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