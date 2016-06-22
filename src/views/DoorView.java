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
import javafx.scene.layout.VBox;
import mvc.ModelInterface.Command;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.Alert;
import views.components.AppButton;
import views.components.ControlLayout;
import views.components.HomeButton;
import views.components.MainLayout;
import views.components.VerticalScroller;


/**
 * Zeigt alle Türen an. Erlaubt die Erstellung und das Löschen von Türen. Eine
 * Tür entspricht einem Themenbereich.
 * 
 * @author miro albrecht & hugo lucca
 *
 */
public class DoorView extends FXView
{
	private String txtNewTheme = "Neues Fach";

	public DoorView (String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}

	// Zeigt Türen dynamisch an (muss im construct und im refresh verfügbar
	// sein)
	VBox doorLayout;

	@Override
	public Parent constructContainer ()
	{
		// Initialisiere Layout für Türen
		doorLayout = new VBox(20);
		doorLayout.setAlignment(Pos.CENTER);
		
		VerticalScroller scroLay = new VerticalScroller(doorLayout, 25);

		// Buttons
		HomeButton homeBtn = new HomeButton(getFXController());
		AppButton newDoorBtn = new AppButton(txtNewTheme);
		AppButton renameBtn = new AppButton("Umbennen");

		// Trash Image
		Image trashImg = new Image("views/pictures/Papierkorb.png");
		ImageView trashImgView = new ImageView(trashImg);

		// Layout für Controls (Hauptsteuerung)
		ControlLayout conLay = new ControlLayout(homeBtn, newDoorBtn, renameBtn, trashImgView);

		// Main Layout
		MainLayout maLay = new MainLayout(scroLay, conLay);

		newDoorBtn.setOnAction(e ->
		{
			String doorName = Alert.simpleString("Neue Tür", "Wie soll die neue Tür heissen?");
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
						Alert.simpleInfoBox("Tür wurde nicht erstellt", "Dieser Name ist schon vergeben.");
					}
				}
			}
		});

		renameBtn.setOnAction(e ->
		{
			getFXController().setViewData("rename", "door" + Globals.SEPARATOR + "doors");
			getFXController().showView("rename");
		});

		trashImgView.setOnDragOver(e ->
		{
			if (e.getGestureSource() != trashImgView && e.getDragboard().hasString())
			{
				e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			}

			e.consume();
		});
		
		trashImgView.setOnDragDropped(event ->
		{
			Dragboard db = event.getDragboard();
			boolean success = false;
			if (db.hasString())
			{
				if (Alert.ok("Achtung", "Willst du die Tür '" + db.getString() + "' wirklich löschen?"))
				{
					getFXController().getModel("door").doAction(Command.DELETE, db.getString());
				}
				success = true;
			}

			event.setDropCompleted(success);
			event.consume();
		});

		getFXController().getModel("door").registerView(this);
		return maLay;
	}

	@Override
	public void refreshView ()
	{
		doorLayout.getChildren().clear();

		ArrayList<String> doorNames = getFXController().getModel("door").getDataList("doors");
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
		}

		doorLayout.getChildren().addAll(doors);

		doorLayout.setAlignment(Pos.CENTER);
	}
}
