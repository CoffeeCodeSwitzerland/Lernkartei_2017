package mvc;

import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Contains centralized GUI Settings (globals) that are used for this GUI  
 * 
 * @author hugo-lucca
 */
public class FXSettings {

	private Stage primaryStage;
	
	public double OPTIMAL_WIDTH  = 800;
	public double OPTIMAL_HEIGHT = 450;
	
	private Color preferredBackroundColor = new Color(1.0,1.0,1.0,0.5); 
	
	public double getMinWidth() {
		if (primaryStage != null)
			return primaryStage.getMaxWidth();
		return this.OPTIMAL_WIDTH;
	}

	public double getMinHeight() {
		if (primaryStage != null)
			return primaryStage.getMaxHeight(); // returns always the actual value
		return this.OPTIMAL_HEIGHT;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage; // returns always the actual value
	}

	public Color getBackroundColor() {
		return preferredBackroundColor;
	}

	public void setBackroundColor(Color preferredBackroundColor) {
		this.preferredBackroundColor = preferredBackroundColor;
	}
}
