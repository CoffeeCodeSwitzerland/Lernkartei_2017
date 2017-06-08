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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import views.components.Alert;
import views.components.AppButton;
import views.components.ControlLayout;
import views.components.HomeButton;
import views.components.MainLayout;

public class DoorView extends FXViewModel {
	
	private String txtNewTheme = "Neues Fach";
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
		AppButton newDoorBtn = new AppButton(txtNewTheme);
		
		Image trashImg = new Image("views/pictures/Papierkorb.png");
		ImageView trashImgView = new ImageView(trashImg);

		BorderPane headLayout = new BorderPane(headLbl);
		headLayout.setPadding(new Insets(0, 0, 25, 0));

		doorLayout.setPadding(new Insets(10));
		doorLayout.setAlignment(Pos.TOP_CENTER);

		scroller.setMaxWidth(600);
		scroller.setFitToWidth(true);
		scroller.setPadding(new Insets(25));
		
		ControlLayout conLay = new ControlLayout(backBtn, newDoorBtn, trashImgView);
		MainLayout maLay = new MainLayout(scroller, headLayout, conLay);
		
		newDoorBtn.setOnAction(e ->
		{
			newDoor();
		});
		
		trashImgView.setOnDragOver(event ->
		{
			if (event.getGestureSource() != trashImgView && event.getDragboard().hasString())
			{
				event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			}

			event.consume();
		});
		
		trashImgView.setOnDragDropped(event ->
		{
			Dragboard db = event.getDragboard();
			boolean success = false;
			if (db.hasString())
			{
				success = deleteDoor(db.getString());
			}

			event.setDropCompleted(success);
			event.consume();
		});
		
		getFXController().getModel("cards").registerView(this);
		return maLay;
	}

	@Override
	public void refreshView() {
		
		doorLayout.getChildren().clear();
	
		ArrayList<String> doorNames = getFXController().getModel("door").getDataList("doors");
		ArrayList<AppButton> doors = new ArrayList<>();
		ArrayList<AppButton> pencils = new ArrayList<>();
		ArrayList<HBox> zeilen = new ArrayList<>();
		
		
		if (doorNames != null)
		{
			zeilen.clear();
			for (String s : doorNames)
			{
				AppButton d = new AppButton(s);
				d.setId("DoorButtons");
				d.setMaxWidth(500);
				d.setOnAction(e ->
				{
					getFXController().setViewData("stack", d.getText());
					getFXController().showView("stack");
				});

				d.setOnDragDetected(e ->
				{
					Dragboard db = d.startDragAndDrop(TransferMode.MOVE);

					ClipboardContent content = new ClipboardContent();
					content.putString(d.getText());
					db.setContent(content);

					e.consume();
				});
				d.setOnDragDone(event ->
				{
					if (event.getTransferMode() == TransferMode.MOVE)
					{
						doors.remove(d);
					}
					event.consume();
				});
				
				doors.add(d);
				
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
				
				HBox hB = new HBox(10);
				hB.setAlignment(Pos.CENTER);
				hB.getChildren().addAll((AppButton) d, (AppButton) p);
				
				zeilen.add(hB);
			}
		}		
		
		doorLayout.setAlignment(Pos.CENTER);
		doorLayout.getChildren().addAll(zeilen);
	
		scroller.setContent(doorLayout); 
	}
	
	private void newDoor ()
	{
		String doorName = Alert.simpleString(txtNewTheme, "Wie soll das neue Fach heissen?");
		if (doorName != null && !doorName.equals(""))
		{
			int maxNameLength = Globals.maxNameLength;
			while (doorName != null && doorName.length() > maxNameLength)
			{
				doorName = Alert.simpleString("Name zu lang", "Bitte wählen Sie einen kürzeren Namen. (max "+maxNameLength+" Zeichen)", doorName.substring(0, maxNameLength), 300);
			}
			if (doorName != null && !doorName.equals(""))
			{
				int succesful = getFXController().getModel("door").doAction(Command.NEW, doorName);
				if (succesful == -1)
				{
					Alert.simpleInfoBox("Fach wurde nicht erstellt", "Dieser Name ist schon vergeben.");
				} else
				{
					refreshView();
				}
			}
		}
	}
	
	private boolean deleteDoor (String door)
	{
		if (Alert.ok("Achtung", "Willst du das Fach '" + door + "' wirklich löschen?"))
		{
			getFXController().getModel("door").doAction(Command.DELETE, door);
			refreshView();
		}
		return true;
	}
}
