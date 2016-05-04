package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import mvc.Controller;
import mvc.FXViewModel;

public class LearnView extends FXViewModel
{
	AppButton successfulBtn = new AppButton("Richtig");
	AppButton wrongBtn = new AppButton("Falsch");
	Label headLbl = new Label("");
	AppButton card = new AppButton("Front");
	
	public LearnView (String setName, Controller controller)
	{
		super(setName, controller);

		AppButton backBtn = new AppButton("Zurück");
		backBtn.setOnAction(e -> getController().getView("boxview").show());
		
		headLbl.setId("bold");
		
		card.setMinWidth(160);
		card.setMinHeight(90);
		card.setId("bold");
		card.setOnAction(e -> card.setText(card.getText() == "Test" ? "Nope" : "Test"));
		
		HBox controlLayout = new HBox(20);
		controlLayout.setAlignment(Pos.CENTER);
		controlLayout.getChildren().addAll(backBtn, successfulBtn, wrongBtn);
		
		BorderPane mainLayout = new BorderPane();
		mainLayout.setPadding(new Insets(15));
		mainLayout.setTop(headLbl);
		mainLayout.setCenter(card);
		mainLayout.setBottom(controlLayout);
		
		setupScene(new Scene(mainLayout, 800, 450));
	}

	@Override
	public void refreshView ()
	{
		if (getData() == null || getData().equals(""))
		{
			successfulBtn.setDisable(false);
			wrongBtn.setDisable(false);
		}
		else
		{
			headLbl.setText(getData());
		}
	}
}
