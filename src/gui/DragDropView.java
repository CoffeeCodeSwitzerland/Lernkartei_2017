package gui;

import application.MainController;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DragDropView extends View
{

	public DragDropView (String setName, Stage primary, MainController controller)
	{
		super(setName, primary, controller);
		
		final Text source = new Text(50, 100, "DRAG ME");
		final Text target = new Text(300, 100, "DROP HERE");
		
		source.setOnDragDetected(new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent event) {
		        /* drag was detected, start a drag-and-drop gesture*/
		        /* allow any transfer mode */
		        Dragboard db = source.startDragAndDrop(TransferMode.ANY);
		        
		        /* Put a string on a dragboard */
		        ClipboardContent content = new ClipboardContent();
		        content.putString(source.getText());
		        db.setContent(content);
		        
		        event.consume();
		    }
		});
		
		target.setOnDragOver(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		        /* data is dragged over the target */
		        /* accept it only if it is not dragged from the same node 
		         * and if it has a string data */
		        if (event.getGestureSource() != target &&
		                event.getDragboard().hasString()) {
		            /* allow for both copying and moving, whatever user chooses */
		            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
		        }
		        
		        // target.setText(event.getDragboard().getString());
		        event.consume();
		    }
		});
		
		target.setOnDragEntered(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		    /* the drag-and-drop gesture entered the target */
		    /* show to the user that it is an actual gesture target */
		         if (event.getGestureSource() != target &&
		                 event.getDragboard().hasString()) {
		             target.setFill(Color.GREEN);
		         }
		                
		         event.consume();
		    }
		});
		
		target.setOnDragExited(new EventHandler<DragEvent>() {
			public void handle(DragEvent event){
				if (event.getGestureSource() != target &&
		                 event.getDragboard().hasString()) {
		             target.setFill(Color.BLACK);
		         }
		                
		         event.consume();
			}
		});
		
		target.setOnDragDropped(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		        /* data dropped */
		        /* if there is a string data on dragboard, read it and use it */
		        Dragboard db = event.getDragboard();
		        boolean success = false;
		        if (db.hasString()) {
		           target.setText(db.getString());
		           success = true;
		        }
		        /* let the source know whether the string was successfully 
		         * transferred and used */
		        event.setDropCompleted(success);
		        
		        event.consume();
		     }
		});
		
		source.setOnDragDone(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		        /* the drag and drop gesture ended */
		        /* if the data was successfully moved, clear it */
		        if (event.getTransferMode() == TransferMode.MOVE) {
		            source.setText("");
		        }
		        event.consume();
		    }
		});
		
		VBox layout = new VBox(20);
		layout.getChildren().addAll(source, target);
		
		setupScene(new Scene(layout, 800, 450));
	}

	@Override
	public void refreshView ()
	{
		refresh();
	}

}
