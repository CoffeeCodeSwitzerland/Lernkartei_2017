package views;

import globals.Globals;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import models.StatisticsModel;
import mvc.fx.FXController;
import mvc.fx.FXViewModel;

/**
 * View um Lernergebnis und Gesamtforschritt nach einer 'Lernrunde' anzuzeigen.
 * 
 * @author miro albrecht
 *
 */
public class PostLearnView extends FXViewModel
{

	public PostLearnView (String setName, FXController newController) { super(setName, newController); construct(); }

	BorderPane mainLayout = new BorderPane();
	
	@Override
	public Parent constructContainer ()
	{
		mainLayout.setPadding(new Insets(50));
		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
		String score = "";
		String dif = "";
		if (getFXController().getModel("statistics") != null)
		{
			StatisticsModel sm = (StatisticsModel) getFXController().getModel("statistics");
			if (sm.getDoubleList(getData() + Globals.SEPARATOR + "end") != null)
			{
				Double roundDouble = ((double) ((int)(sm.getDoubleList(getData() + Globals.SEPARATOR + "end").get(0) * 100)) / 100);
				score = roundDouble.toString();
			}
			if (sm.getDoubleList(getData() + Globals.SEPARATOR + "difference") != null)
			{
				Double difDouble = ((double) ((int)(sm.getDoubleList(getData() + Globals.SEPARATOR + "difference").get(0) * 100)) / 100);
				dif = difDouble.toString();
			}
		}
		
		Label now = new Label("Jetzt: " + score);
		Label diff = new Label("Fortschritt: " + dif);
		
		AppButton backBtn = new AppButton("Zurück");
		backBtn.setOnAction(e -> getFXController().showView("stack"));
		
		VBox layout = new VBox(20);
		layout.getChildren().addAll(now, diff, backBtn);
		
		mainLayout.setCenter(layout);
	}

}
