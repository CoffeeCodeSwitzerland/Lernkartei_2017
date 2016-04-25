package gui;

import application.MainController;
import application.WISSLearnCards;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StatisticsView extends View
{
	public StatisticsView(String setName, Stage window, MainController controller)
	{
		super (setName, window);
		
		AppButton b = new AppButton("Back");
		b.setOnAction(e -> controller.showMain());

		BorderPane bp = new BorderPane();
		bp.setCenter(b);
		this.setScene(new Scene(bp, WISSLearnCards.OPTIMAL_WIDTH, WISSLearnCards.OPTIMAL_HEIGHT));
	}
}
