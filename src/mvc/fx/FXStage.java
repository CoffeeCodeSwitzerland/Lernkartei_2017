package mvc.fx;

import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Contains centralized GUI Settings (globals) that are used for this GUI  
 * 
 * @author hugo-lucca
 */
public class FXStage extends Stage {

	public static double width  = 920;
	public static double height = 570;
	public static Color backroundColor = new Color(1.0,1.0,1.0,0.5); 

	private Stage primaryStage = null;
	
	private double OPTIMAL_WIDTH  = 920;
	private double OPTIMAL_HEIGHT = 570;
	private Color preferredBackroundColor = new Color(1.0,1.0,1.0,0.5); 

	public FXStage (Stage refToPrimaryStage) {
		primaryStage = refToPrimaryStage;
	}
	
	public Stage getStage() {
		if (this.primaryStage == null) {
			return this;
		}
		return this.primaryStage;
	}

	public double getOPTIMAL_WIDTH() {
		return this.OPTIMAL_WIDTH;
	}

	public void setOPTIMAL_WIDTH(double ow) {
		this.OPTIMAL_WIDTH = ow;
		FXStage.width = ow;
	}

	public double getOPTIMAL_HEIGHT() {
		return this.OPTIMAL_HEIGHT;
	}

	public void setOPTIMAL_HEIGHT(double oh) {
		this.OPTIMAL_HEIGHT = oh;
		FXStage.height = oh;
	}

	public Color getPreferredBackroundColor() {
		return this.preferredBackroundColor;
	}

	public void setPreferredBackroundColor(Color bcol) {
		this.preferredBackroundColor = bcol;
		FXStage.backroundColor = bcol;
	}

	public static void setBackroundColor(Color bcol) {
		FXStage.backroundColor = bcol;
	}
}
