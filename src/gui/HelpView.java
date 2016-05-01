package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import mvc.FXSettings;
import mvc.FXView;


/**
 * Hilfefenster
 * 
 * @author miro-albrecht
 *
 */
public class HelpView extends FXView
{

	public HelpView (String setName)
	{
		super (setName, null);
		
		Image helpdesk = new Image("gui/pictures/helpdesk.jpg", true);

		ImageView view = new ImageView(helpdesk);

		VBox center = new VBox();
		center.setAlignment(Pos.CENTER);
		center.getChildren().add(view);

		this.getWindow().setTitle("Lernkartei Hilfe [Alpha]");
		this.getWindow().setResizable(false);
		this.setupScene(new Scene(center, FXSettings.OPTIMAL_WIDTH, 150+FXSettings.OPTIMAL_HEIGHT));
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}
	
}
