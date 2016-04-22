package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelpView
{
	Stage helpWindow = new Stage();
	
	public HelpView ()
	{
		Image helpdesk = new Image("gui/helpdesk.jpg", true);
		
		ImageView view = new ImageView(helpdesk);
		
		VBox center = new VBox();
		center.setAlignment(Pos.CENTER);
		center.getChildren().add(view);
		
		
		helpWindow.setTitle("Lernkartei Hilfe [Alpha]");
		helpWindow.setResizable(false);
		helpWindow.setScene(new Scene(center, 800, 600));
	}
	
	public void show()
	{
		helpWindow.show();
	}
}
