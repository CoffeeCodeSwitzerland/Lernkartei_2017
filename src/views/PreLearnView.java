package views;

import debug.Debugger;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mvc.View;
import mvc.fx.FXController;
import mvc.fx.FXViewModel;

/**
 * Diese View zeigt dem User vor dem Lernen Informationen an.
 * Zum Beispiel der Fortschritt des akutellen Stacks.
 * 
 * @author miro albrecht
 *
 */
public class PreLearnView extends FXViewModel
{
	public PreLearnView (String setName, FXController controller)
	{
		super(setName, controller);
		construct();
	}

	
	BorderPane mainLayout = new BorderPane();
	
	@Override
	public Parent constructContainer ()
	{
		// Reset the data of the learn model which forces the model to shuffle again
		getController().getModel("learn").getDataList(null).clear();
		getController().getModel("learn").setString(null);
		mainLayout.setPadding(new Insets(50));
		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
		if (getData() == null || getData().equals(""))
		{
			Debugger.out("PreLearnView has no Stack Data");
			return;
		}
		
		Label stackName = new Label(getData());
		stackName.setId("bold");
		
		Label stackInfo = new Label("Anzahl Karten: " + getFXController().getModel("learn").getDataList(getData()).size());
		
		String score = "";
		if (getFXController().getModel("statistics") != null)
		{
			if (getFXController().getModel("statistics").getDataList(getData()) != null)
			{
				score = getFXController().getModel("statistics").getDataList(getData()).get(0);
			}
		}
		
		Label stackScore = new Label("Fortschritt: " + score);
		
		AppButton goBtn = new AppButton("Go");
		goBtn.setOnAction(e -> {
			View v = getFXController().getView("learnview");
			v.setData(getData());
			v.show();
		});
		
		AppButton backBtn = new AppButton("Zurück");
		backBtn.setOnAction(e -> getFXController().getView("stack").show());
		
		VBox layout = new VBox(30);
		layout.getChildren().addAll(stackName, stackInfo, stackScore, goBtn, backBtn);
		
		mainLayout.setCenter(layout);
	}

}
