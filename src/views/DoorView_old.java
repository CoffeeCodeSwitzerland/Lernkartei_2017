package views;

import java.util.ArrayList;

import globals.Globals;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import mvc.ModelInterface.Command;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.Alert;
import views.components.AppButton;
import views.components.ControlLayout;
import views.components.HomeButton;
import views.components.MainLayout;
import views.components.StandardVBox;
import views.components.VerticalScroller;


/**
 * Zeigt alle Doors an. Erlaubt die Erstellung und das Löschen von Doors. Eine
 * Door entspricht einem Themenbereich.
 * 
 * @author miro albrecht & hugo lucca
 *
 */
public class DoorView_old extends FXView
{
	private String txtNewTheme = "Neues Fach";

	public DoorView_old (String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}

	// Zeigt Doors dynamisch an (muss im constructContainer und im refresh verfügbar sein)
	StandardVBox doorLayout;

	@Override
	public Parent constructContainer ()
	{
		doorLayout = new StandardVBox();
		VerticalScroller scroLay = new VerticalScroller(doorLayout);

		HomeButton homeBtn = new HomeButton(getFXController());
		AppButton newDoorBtn = new AppButton(txtNewTheme);
		AppButton renameBtn = new AppButton("Umbennen");

		Image trashImg = new Image("views/pictures/Papierkorb.png");
		ImageView trashImgView = new ImageView(trashImg);

		ControlLayout conLay = new ControlLayout(homeBtn, newDoorBtn, renameBtn, trashImgView);

		MainLayout maLay = new MainLayout(scroLay, conLay);

		newDoorBtn.setOnAction(e ->
		{
			newDoor();
		});

		renameBtn.setOnAction(e ->
		{
			getFXController().setViewData("rename", "door" + Globals.SEPARATOR + "doors");
			getFXController().showAndTrackView("rename");
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

		try {
			getFXController().getModel("door").registerView(this);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return maLay;
	}

	@Override
	public void refreshView ()
	{
		doorLayout.getChildren().clear();

		ArrayList<String> doorNames=null;
		try {
			doorNames = getFXController().getModel("door").getDataList("doors");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<AppButton> doors = new ArrayList<>();

		if (doorNames != null)
		{
			for (String s : doorNames)
			{
				doors.add(new AppButton(s));
			}
		}

		for (AppButton a : doors)
		{
			a.setId("DoorButtons");
			a.setMaxWidth(500);
			a.setOnAction(e ->
			{
				getFXController().setViewData("stack", a.getText());
				getFXController().showAndTrackView("stack");
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
		}

		doorLayout.getChildren().addAll(doors);

		doorLayout.setAlignment(Pos.CENTER);
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
				int succesful=-1;
				try {
					succesful = getFXController().getModel("door").doAction(Command.NEW, doorName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (succesful == -1)
				{
					Alert.simpleInfoBox("Fach wurde nicht erstellt", "Dieser Name ist schon vergeben.");
				}
			}
		}
	}
	
	private boolean deleteDoor (String door)
	{
		if (Alert.ok("Achtung", "Willst du das Fach '" + door + "' wirklich löschen?"))
		{
			try {
				getFXController().getModel("door").doAction(Command.DELETE, door);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
}
