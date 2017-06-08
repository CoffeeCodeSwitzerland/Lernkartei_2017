package views;

import java.util.ArrayList;

import debug.Debugger;
import globals.Globals;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.ModelInterface.Command;
import mvc.fx.FXController;
import mvc.fx.FXViewModel;
import views.components.AppButton;
import views.components.ControlLayout;
import views.components.HomeButton;
import views.components.MainLayout;

public class DoorView extends FXViewModel {
	// ArrayList<VBox> cards;

	public DoorView(String newName, FXController newController) {
		// this constructor is the same for all view's
		super(newController);
		construct(newName);
	}

	VBox doorLayout = new VBox(10);
	Label headLbl;
	ScrollPane scroller = new ScrollPane() {
		public void requestFocus() {
		}
	};

	boolean justCreatedCard = false;

	@Override
	public Parent constructContainer() {
		headLbl = new Label("");
		headLbl.setId("bold");

		HomeButton backBtn = new HomeButton(getFXController(), "_Zurück");

		BorderPane headLayout = new BorderPane(headLbl);
		headLayout.setPadding(new Insets(0, 0, 25, 0));

		doorLayout.setPadding(new Insets(10));
		doorLayout.setAlignment(Pos.TOP_CENTER);

		scroller.setMaxWidth(600);
		scroller.setFitToWidth(true);
		scroller.setPadding(new Insets(25));

		MainLayout maLay = new MainLayout(scroller, headLayout, new ControlLayout(backBtn));
		getFXController().getModel("cards").registerView(this);
		return maLay;
	}

	@Override
	public void refreshView() {
		
		doorLayout.getChildren().clear();
	
		ArrayList<String> doorNames = getFXController().getModel("door").getDataList("doors");
		ArrayList<AppButton> doors = new ArrayList<>();
		ArrayList<AppButton> pencils = new ArrayList<>();
		ControlLayout doorsAndPencils = new ControlLayout();

		if (doorNames != null)
		{
			for (String s : doorNames)
			{
				doors.add(new AppButton(s));	
				
				AppButton p = new AppButton("\u270E");
				
				p.setId("small");
				
				p.setOnAction(e -> {
				getFXController().setViewData("rename",s);
				getFXController().showView("rename");
				});
				p.setOnKeyReleased(e -> {
				if (e.getCode() == KeyCode.ENTER)
				p.fire();
				});
				
				pencils.add((AppButton) p);
			}
		}

		for (AppButton a : doors)
		{
			a.setId("DoorButtons");
			a.setMaxWidth(500);
			a.setOnAction(e ->
			{
				getFXController().setViewData("stack", a.getText());
				getFXController().showView("stack");
			});

			a.setOnDragDetected(e ->
			{
				Dragboard db = a.startDragAndDrop(TransferMode.MOVE);

				ClipboardContent content = new ClipboardContent();
				content.putString(a.getText());
				db.setContent(content);

				e.consume();
			});
			a.setOnDragDone(event ->
			{
				if (event.getTransferMode() == TransferMode.MOVE)
				{
					doors.remove(a);
				}
				event.consume();
			});
			
			doorsAndPencils.add(a);
		}
		
		for(AppButton p: pencils)
		{
			doorsAndPencils.add(p);
		}
		
		doorLayout.getChildren().addAll(doorsAndPencils);
		
		doorLayout.setAlignment(Pos.CENTER);

		scroller.setContent(doorLayout); 
	}
}
