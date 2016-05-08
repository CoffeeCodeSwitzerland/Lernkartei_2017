package views;

import javafx.scene.paint.Color;
import mvc.FXSettings;

/**
 * Contains centralized GUI Settings (globals) that are used for this GUI  
 * 
 * @author hugo-lucca
 */
public class MainViewSettings extends FXSettings {
	
	public MainViewSettings () {
		this.setOPTIMAL_HEIGHT(920);
		this.setOPTIMAL_HEIGHT(570);
		this.setPreferredBackroundColor(new Color(1.0,1.0,1.0,0.5));
	}
}
