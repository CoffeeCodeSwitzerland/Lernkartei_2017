package application;

public abstract class Constants {

	private static double MinWidth  = 400;
	private static double MinHeight = 300;
	public static final double OPTIMAL_WIDTH  = 800;
	public static final double OPTIMAL_HEIGHT = 450;
	public static final String appTitle = "WISSLearnCards 2016";
	public static final String appVersion = "[V0.1 alpha]";

	// Getters/Setters for non final "constants":
	public static double getMinWidth() {
		return MinWidth;
	}
	public static void setMinWidth(double minWidth) {
		MinWidth = minWidth;
	}
	public static double getMinHeight() {
		return MinHeight;
	}
	public static void setMinHeight(double minHeight) {
		MinHeight = minHeight;
	}
}
