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
		this.setWidth(850);
		this.setHeight(540);
		//FXStage.setBackroundColor(new Color(0.0,1.0,1.0,0.5)); TODO does not work
	}
}
