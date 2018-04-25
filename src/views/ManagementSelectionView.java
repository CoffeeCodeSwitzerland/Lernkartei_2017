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

public class ManagementSelectionView extends FXView
{

	public ManagementSelectionView(String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}
	
	BorderPane bp = new BorderPane();

	VBox AllFields;


	AppButton btnUserMgmt;
	AppButton btnGroupMgmt;
	
	HomeButton home;
	
	@Override
	public Parent constructContainer()
	{
		bp.setId("loginviewbg");
		
		AllFields = new VBox(50);
		AllFields.setAlignment(Pos.CENTER);
		AllFields.setMaxWidth(300);
		AllFields.setPadding(new Insets(20));

		
		btnUserMgmt = new AppButton("Benutzerverwaltung");
		
		btnGroupMgmt = new AppButton("Gruppenverwaltung");
		
		home = new HomeButton(getFXController());
		
		AllFields.getChildren().addAll(btnUserMgmt, btnGroupMgmt, home);
		
		bp.setCenter(AllFields);
		
		btnUserMgmt.setOnAction(e -> getFXController().showAndTrackView("userview"));
		btnGroupMgmt.setOnAction(e -> getFXController().showAndTrackView("groupview"));
		
		return bp;
	}

	@Override
	public void refreshView()
	{
		bp.setId("loginviewbg");
	}

}
