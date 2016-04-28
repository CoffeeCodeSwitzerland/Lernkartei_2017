package gui;

import application.MainController;
import javafx.scene.Scene;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DragDropView extends View
{

	public DragDropView (String setName, Stage primary, MainController controller)
	{
		super(setName, primary, controller);
		
		final Text source = new Text(50, 100, "DRAG ME");
		final Text target = new Text(300, 100, "DROP HERE");
		
		source.setOnDragDetected(e -> {
			Dragboard db = source.startDragAndDrop(TransferMode.ANY);
	        
	        /* Put a string on a dragboard */
	        ClipboardContent content = new ClipboardContent();
	        content.putString(source.getText());
	        db.setContent(content);
	        
	        e.consume();
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
