package gui;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.Controller;
import mvc.FXSettings;
import mvc.FXView;
import mvc.View;


/**
 * Zeigt alle Türen an. Erlaubt die Erstellung und das Löschen von Türen.
 * 
 * @author miro albrecht & hugo lucca
 *
 */
public class DoorView extends FXView
{
	// Zeigt Türen dynamisch an
	VBox doorLayout;

	public DoorView (String setName, Controller controller)
	{
		super(setName, controller);

		// Initialisiere Layout für Türen
		doorLayout = new VBox(20);
		doorLayout.setAlignment(Pos.CENTER);

		// Buttons
		AppButton backBtn = new AppButton("Zurück");
		AppButton newDoorBtn = new AppButton("Neue Tür");

		// Trash Image
		Image trashImg = new Image("gui/pictures/Papierkorb.png");
		ImageView trashImgView = new ImageView(trashImg);

		// Layout für Controls (Hauptsteuerung)
		HBox controlsLayout = new HBox(20);
		controlsLayout.setAlignment(Pos.CENTER);
		controlsLayout.getChildren().addAll(backBtn, newDoorBtn, trashImgView);

		// Main Layout
		BorderPane mainLayout = new BorderPane();
		mainLayout.setPadding(new Insets(15));

		mainLayout.setCenter(doorLayout);
		mainLayout.setBottom(controlsLayout);

		
		// Behaviour
		backBtn.setOnAction(e -> getController().showMain());

		newDoorBtn.setOnAction(e ->
		{
			String doorName = Alert.simpleString("Neue Tür", "Wie soll die neue Tür heissen?");
			if (doorName != null && !doorName.equals(""))
			{
				getController().getModel("door").doAction("new", doorName);
			}
		});

		trashImgView.setOnDragOver(e ->
		{
			if (e.getGestureSource() != trashImgView &&
					e.getDragboard().hasString())
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
					getController().getModel("door").doAction("delete", db.getString());
				}
				success = true;
			}
			
			event.setDropCompleted(success);
			event.consume();
		});

		this.setupScene(new Scene(mainLayout, FXSettings.OPTIMAL_WIDTH, FXSettings.OPTIMAL_HEIGHT));
		getController().getModel("door").registerView(this);
	}

	@Override
	public void refreshView ()
	{
		doorLayout.getChildren().clear();
		
		ArrayList<String> doorNames = getController().getModel("door").getDataList("doors");
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
			a.setOnAction(e ->
			{
				View v = getController().getView("boxview");
				v.setData(a.getText());
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
	}
}
