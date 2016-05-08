package mvc;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mvc.FXSettings;
import debug.*;

/**
 * Contains centralized GUI Settings (globals) that are used for this GUI  
 * 
 * @author hugo-lucca
 */
public class FXSettings {

	public static double width  = 920;
	public static double height = 570;
	public static Color backroundColor = new Color(1.0,1.0,1.0,0.5); 

	private Stage primaryStage = null;
	private double OPTIMAL_WIDTH  = 920;
	private double OPTIMAL_HEIGHT = 570;
	private Color preferredBackroundColor = new Color(1.0,1.0,1.0,0.5); 
	private String stylePath = "style.css";
	
	public void setStylePath(String stylePath) {
		this.stylePath = stylePath;
	}

	public String getStylePath() {
		return stylePath;
	}

	public Stage getPrimaryStage() {
		if (this.primaryStage == null) {
			Debugger.out("FXSettings: no primary stage defined!");
		}
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage; // returns always the actual value
	}

	public double getOPTIMAL_WIDTH() {
		return OPTIMAL_WIDTH;
	}

	public void setOPTIMAL_WIDTH(double ow) {
		OPTIMAL_WIDTH = ow;
		FXSettings.width = ow;
	}

	public double getOPTIMAL_HEIGHT() {
		return OPTIMAL_HEIGHT;
	}

	public void setOPTIMAL_HEIGHT(double oh) {
		OPTIMAL_HEIGHT = oh;
		FXSettings.height = oh;
	}

	public Color getPreferredBackroundColor() {
		return preferredBackroundColor;
	}

	public void setPreferredBackroundColor(Color bcol) {
		this.preferredBackroundColor = bcol;
		FXSettings.backroundColor = bcol;
	}

	public static void setBackroundColor(Color bcol) {
		FXSettings.backroundColor = bcol;
	}
}
