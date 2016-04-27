package gui;

import application.Constants;
import application.MainController;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StatisticsView extends View
{
	public StatisticsView(String setName, Stage window, MainController controller)
	{
		super (setName, window, controller);
		
		AppButton b = new AppButton("Back");
		b.setOnAction(e -> controller.showMain());

		BorderPane bp = new BorderPane();
		bp.setCenter(b);
		this.setScene(new Scene(bp, Constants.OPTIMAL_WIDTH, Constants.OPTIMAL_HEIGHT));
	}

	@Override
	public void refreshView() {
		refresh();
		// TODO Auto-generated method stub
		
	}
}
