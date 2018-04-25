package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import views.components.AppButton;
import views.components.BackButton;
import views.components.HomeButton;

public class DoorSavepathSelectionView extends BasicFXView
{

	public DoorSavepathSelectionView(String newName, FXController newController) {
		super(newName, newController);
	}

	HBox bottom;
	VBox AllFields;
	AppButton btnLocal;
	AppButton btnServer;
	HomeButton home;
	
	@Override
	public Parent constructContainer()
	{
		bp.setId("loginviewbg");
		
		bottom = new HBox(50);
		bottom.setAlignment(Pos.CENTER);
		bottom.setMaxWidth(300);
		bottom.setPadding(new Insets(20,50,150,280));
		
		AllFields = new VBox(50);
		AllFields.setAlignment(Pos.CENTER);
		AllFields.setMaxWidth(300);
		AllFields.setPadding(new Insets(100,20,20,20));
		
		btnLocal = new AppButton("lokal speichern");
		btnServer = new AppButton("auf dem Server speichern");
		btnBack = new BackButton(getFXController(), "Zurück");
		
		home = new HomeButton(getFXController());
		
		AllFields.getChildren().addAll(btnLocal, btnServer);
		bottom.getChildren().addAll(btnBack, home);
		
		bp.setCenter(AllFields);
		bp.setBottom(bottom);
		
		btnLocal.setOnAction(e -> getFXController().showAndTrackView("savedownloadstacklocalview"));
		btnServer.setOnAction(e -> getFXController().showAndTrackView("savedownloaddooronserverdialogview"));
		btnBack.setOnAction(e -> getFXController().showAndTrackView("serverdoorview"));
		
		return bp;
	}

	@Override
	public void refreshView()
	{
		bp.setId("loginviewbg");
	}

}
