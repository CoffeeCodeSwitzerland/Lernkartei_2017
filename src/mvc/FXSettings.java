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

	public void setOPTIMAL_WIDTH(double oPTIMAL_WIDTH) {
		OPTIMAL_WIDTH = oPTIMAL_WIDTH;
	}

	public double getOPTIMAL_HEIGHT() {
		return OPTIMAL_HEIGHT;
	}

	public void setOPTIMAL_HEIGHT(double oPTIMAL_HEIGHT) {
		OPTIMAL_HEIGHT = oPTIMAL_HEIGHT;
	}

	public Color getPreferredBackroundColor() {
		return preferredBackroundColor;
	}

	public void setPreferredBackroundColor(Color preferredBackroundColor) {
		this.preferredBackroundColor = preferredBackroundColor;
	}
}
