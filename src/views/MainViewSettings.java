package views;

import javafx.stage.Stage;
import mvc.fx.FXStage;

/**
 * Contains centralized GUI Settings (globals) that are used for this GUI  
 * 
 * @author hugo-lucca
 */
public class MainViewSettings extends FXStage {
	
	public MainViewSettings (Stage primaryStage) {
		super (primaryStage);
		
		// TODO: the following does not work
//		this.setWidth(920);
//		this.setHeight(620);
		
		// TODO: the follwing does not work
//		FXStage.setBackroundColor(new Color(0.0,1.0,1.0,0.5));
	}
}
