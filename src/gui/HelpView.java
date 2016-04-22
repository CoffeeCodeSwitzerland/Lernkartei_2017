package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Hilfefenster
 * 
 * @author miro-albrecht
 *
 */
public class HelpView extends View
{

	public HelpView ()
	{
		name = "helpview";
		window = new Stage();
		
		Image helpdesk = new Image("gui/helpdesk.jpg", true);

		ImageView view = new ImageView(helpdesk);

		VBox center = new VBox();
		center.setAlignment(Pos.CENTER);
		center.getChildren().add(view);

		window.setTitle("Lernkartei Hilfe [Alpha]");
		window.setResizable(false);
		scene = new Scene(center, 800, 600);
		window.setScene(scene);
	}
	
	@Override
	public void show()
	{
		window.show();
	}
	
}
