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
 * @author nina-egger
 *
 */
public class BoxView extends View
{
	VBox boxLayout;
	
	public BoxView (String setName, Stage primaryStage, MainController controller)
	{
		super(setName, primaryStage, controller);

		// Buttons
		AppButton zurueckButton = new AppButton("zurück");
		AppButton newBox = new AppButton("Neuer Kasten");
		AppButton bearbeitenKasten = new AppButton("Bearbeiten");
		AppButton weitereKasten = new AppButton("weitere Kasten");

		javafx.scene.image.Image trash = new javafx.scene.image.Image("gui/pictures/Papierkorb.png");
		ImageView view = new ImageView(trash);

		// Layout für Controls
		HBox hBox = new HBox(20);
		hBox.setAlignment(Pos.CENTER);

		hBox.getChildren().addAll(zurueckButton, newBox, bearbeitenKasten, view);

		boxLayout = new VBox(20);
		boxLayout.setAlignment(Pos.CENTER);

		// Laayout für die Scene
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(15));

		borderPane.setCenter(boxLayout);
		borderPane.setBottom(hBox);

		zurueckButton.setOnAction(e -> getController().show("doorview"));
		newBox.setOnAction(e -> 
		{
			final String boxName = Alert.simpleString("Neue Box", "Wie soll die neue Box heissen?");
			if (setName != null || !boxName.equals(""))
			{
				System.out.println(getController().getMyModel("set").doAction("new", getData() + application.Constants.SEPARATOR + boxName));
			}
		});
		
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
		
		weitereKasten.setOnAction(e -> getController().show("karteiview"));
		weitereKasten.setDisable(true);

		this.setupScene(new Scene(borderPane, Constants.OPTIMAL_WIDTH, Constants.OPTIMAL_HEIGHT));
	}

	@Override
	public void refreshView ()
	{
		boxLayout.getChildren().clear();
		String data = getData();
		System.out.println(data);
		if (data != null)
		{
			ArrayList<String> setData = getController().getMyModel("set").getData(data);
			ArrayList<AppButton> sets = new ArrayList<AppButton>();
			
			for (String s : setData)
			{
				AppButton a = new AppButton(s);
				
				sets.add(a);
			}
			
			for (AppButton a : sets)
			{
				a.setOnAction(e -> {
					System.out.println("Clicked on Set");
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
			            sets.remove(a);
			        }
			        event.consume();
				});
			}
			
			boxLayout.getChildren().addAll(sets);
		}
	}
}
