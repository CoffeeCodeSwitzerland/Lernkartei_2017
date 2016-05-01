package mvc;

import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Contains centralized GUI Settings (globals) that are used for this GUI  
 * 
 * @author hugo-lucca
 */
public abstract class FXSettings {

	private static Stage primaryStage;
	
	public static final double OPTIMAL_WIDTH  = 800;
	public static final double OPTIMAL_HEIGHT = 450;
	
	private static Color preferredBackroundColor = new Color(1.0,1.0,1.0,0.5); 
	
	public static double getMinWidth() {
		if (primaryStage != null)
			return primaryStage.getMaxWidth();
		return FXSettings.OPTIMAL_WIDTH;
	}

	public static double getMinHeight() {
		if (primaryStage != null)
			return primaryStage.getMaxHeight(); // returns always the actual value
		return FXSettings.OPTIMAL_HEIGHT;
	}

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void setPrimaryStage(Stage primaryStage) {
		FXSettings.primaryStage = primaryStage; // returns always the actual value
	}

	public static Color getBackroundColor() {
		return preferredBackroundColor;
	}

	public static void setBackroundColor(Color preferredBackroundColor) {
		FXSettings.preferredBackroundColor = preferredBackroundColor;
	}
}
