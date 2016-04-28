package gui;

import java.util.ArrayList;

import application.Constants;
import application.MainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Gamestartfenster
 * 
 * @author miro-albrecht & hugo-lucca
 *
 */
public class DoorView extends View
{
	VBox doorLayout;
	
	public DoorView (String setName, Stage primaryStage, MainController controller)
	{
		super(setName, primaryStage, controller);

		doorLayout = new VBox(20);
		doorLayout.setAlignment(Pos.CENTER);
		
		// Buttons
		AppButton zurueckButton = new AppButton("zurück");
		AppButton neueTuer = new AppButton("Neue Tür");
		AppButton weitereTueren = new AppButton("weitere Türen");

		// Trash Image
		
		javafx.scene.image.Image trash = new javafx.scene.image.Image("gui/pictures/Papierkorb.png");
		ImageView view = new ImageView(trash);
		
		// Layout für Controls
		HBox hBox = new HBox(20);
		hBox.setAlignment(Pos.CENTER);

		hBox.getChildren().addAll(zurueckButton, neueTuer, weitereTueren, view);
		
		
		// Layout für die Scene
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(15));

		borderPane.setCenter(doorLayout);
		borderPane.setBottom(hBox);

		
		// Behaviour
		
		zurueckButton.setOnAction(e -> getController().showMain());
		
		neueTuer.setOnAction(e -> {
			String doorName = Alert.simpleString("Neue Tür", "Wie soll die neue Tür heissen?");
			if (doorName != null && !doorName.equals(""))
			{
				getController().getMyModel("door").doAction("new", doorName);
				refreshView();
			}
		});
		
		weitereTueren.setOnAction(e -> getController().show("boxview"));

		
		view.setOnDragOver(e -> {
			if (e.getGestureSource() != view &&
	                e.getDragboard().hasString()) {
	            /* allow for both copying and moving, whatever user chooses */
	            e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
	        }
	        
	        // target.setText(event.getDragboard().getString());
	        e.consume();
		});
		view.setOnDragDropped(event -> {
			Dragboard db = event.getDragboard();
	        boolean success = false;
	        if (db.hasString()) {
	           if (Alert.ok("Achtung", "Willst du " + db.getString() + " wirklich löschen?"))
	           {
	        	   getController().getMyModel("door").doAction("delete", db.getString());
	        	   refreshView();
	           }
	           success = true;
	        }
	        /* let the source know whether the string was successfully 
	         * transferred and used */
	        event.setDropCompleted(success);
	        
	        event.consume();
		});
		
		
		
		this.setupScene(new Scene(borderPane, Constants.OPTIMAL_WIDTH, Constants.OPTIMAL_HEIGHT));
	}

	@Override
	public void refreshView ()
	{
		doorLayout.getChildren().clear();
		ArrayList<String> doorNames = getController().getMyModel("door").getData("doors");
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
			a.setOnAction(e -> {
				View v = getController().show("boxview");
				v.setData(a.getText());
				v.refreshView();
			});
			a.setOnDragDetected(e -> {
				
				Dragboard db = a.startDragAndDrop(TransferMode.MOVE);
		        
		        ClipboardContent content = new ClipboardContent();
		        content.putString(a.getText());
		        db.setContent(content);
		        
		        e.consume();
			});
			a.setOnDragDone(event -> {
				if (event.getTransferMode() == TransferMode.MOVE) {
		            doors.remove(a);
		        }
		        event.consume();
			});
		}
		
		doorLayout.getChildren().addAll(doors);
		refresh();
	}
}
