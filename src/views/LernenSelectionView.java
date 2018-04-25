package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;
import views.components.HomeButton;

public class LernenSelectionView extends FXView
{

	public LernenSelectionView(String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}
	
	BorderPane bp = new BorderPane();


	VBox AllFields;


	AppButton btnDataMgmt;
	AppButton btnDmoMgmt;
	AppButton btnLearning;
	
	HomeButton home;
	
	@Override
	public Parent constructContainer()
	{
		bp.setId("loginviewbg");
		
		AllFields = new VBox(50);
		AllFields.setAlignment(Pos.CENTER);
		AllFields.setMaxWidth(300);
		AllFields.setPadding(new Insets(20));

		
		btnDataMgmt = new AppButton("Dateimanager");
		
		btnDmoMgmt = new AppButton("DMOManager");
		
		btnLearning = new AppButton("Lernen");
		
		home = new HomeButton(getFXController());
		
		AllFields.getChildren().addAll(btnDataMgmt, btnDmoMgmt,btnLearning, home);
		
		bp.setCenter(AllFields);
		
		btnDataMgmt.setOnAction(e -> getFXController().showAndTrackView("serverdoorview"));
		btnDmoMgmt.setOnAction(e -> getFXController().showAndTrackView("dmodoorview"));
		btnLearning.setOnAction(e -> getFXController().showAndTrackView("doorview"));
		
		return bp;
	}

	@Override
	public void refreshView()
	{
		bp.setId("lernenselectionview");
	}

}
