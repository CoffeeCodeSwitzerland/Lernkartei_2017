package views.components;

import javafx.stage.Stage;
import mvc.fx.FXStage;

/**
 * Contains centralized GUI Settings (globals) that are used for this GUI  
 * 
 * @author hugo-lucca
 */
public class HelpViewSettings extends FXStage {
	
	public HelpViewSettings (Stage primaryStage) {
		super (primaryStage);
//		this.setWidth(800);
//		this.setHeight(640);
		//FXStage.setBackroundColor(new Color(0.0,1.0,1.0,0.5)); TODO does not work
	}
}
