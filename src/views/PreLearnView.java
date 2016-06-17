package views;

import debug.Debugger;
import globals.Globals;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import models.StatisticsModel;
import mvc.fx.FXController;
import mvc.fx.FXViewModel;
import views.components.AppButton;


/**
 * Diese View zeigt dem User vor dem Lernen Informationen an. Zum Beispiel der
 * Fortschritt des akutellen Stacks.
 * 
 * @author miro albrecht
 *
 */
public class PreLearnView extends FXViewModel
{
	public PreLearnView (String setName, FXController controller)
	{
		super(controller);
		construct(setName);
	}

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
		// Reset the data of the learn model which forces the model to shuffle
		// again
		getFXController().getModel("learn").getDataList(null).clear();
		getFXController().getModel("learn").setString(null);

		if (getData() == null || getData().equals(""))
		{
			Debugger.out("PreLearnView has no Stack Data");
			return;
		}

		Label stackName = new Label(getData());
		stackName.setId("bold");

		int stackSize = getFXController().getModel("learn").getDataList(getData()).size();
		Label stackInfo = new Label("Anzahl Karten: " + stackSize);

		String score = "";
		if (getFXController().getModel("statistics") != null)
		{
			StatisticsModel sm = (StatisticsModel) getFXController().getModel("statistics");
			if (sm.getDoubleList(getData() + Globals.SEPARATOR + "start") != null)
			{
				Double roundDouble = ((double) ((int) (sm.getDoubleList(getData() + Globals.SEPARATOR + "start").get(0) * 100)) / 100);
				score = roundDouble.toString();
			}
		}

		Label stackScore = new Label("Fortschritt: " + score);

		AppButton goBtn = new AppButton("Go");
		goBtn.setOnAction(e ->
		{
			getFXController().setViewData("learnview", getData());
			getFXController().showView("learnview");
		});
		goBtn.setOnKeyReleased(e ->
		{
			if (e.getCode() == KeyCode.ENTER) goBtn.fire();
		});

		if (stackSize < 1)
		{
			goBtn.setDisable(true);
		}
		else
		{
			goBtn.setDisable(false);
		}

		AppButton backBtn = new AppButton("Zurück");
		backBtn.setOnAction(e -> getFXController().showView("stack"));
		
		

		VBox layout = new VBox(30);
		layout.getChildren().addAll(stackName, stackInfo, stackScore, goBtn, backBtn);

		mainLayout.setCenter(layout);
	}

}
