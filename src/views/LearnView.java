package views;

import controls.Constants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import mvc.Controller;
import mvc.FXViewModel;

public class LearnView extends FXViewModel
{
	public LearnView(String newName, Controller newController) {
		// this constructor is the same for all view's on same stage
		super(newName, newController);
		Parent p = constructContainer();
		if (p==null) {
			p = getMainLayout();
		}
		p.setId(this.getName());
		setupScene(p);
	}

	AppButton successfulBtn = new AppButton("Richtig");
	AppButton wrongBtn = new AppButton("Falsch");
	Label headLbl = new Label("");
	AppButton card = new AppButton("Front");
	
	int counter = 0;
	
	String[] cardData = new String[3];
	

	@Override
	public Parent constructContainer() {
		AppButton backBtn = new AppButton("Zurück");
		backBtn.setOnAction(e -> getController().getView("boxview").show());
		
		headLbl.setId("bold");
		
		successfulBtn.setOnAction(e ->
		{
			counter++;
			refreshView(); // TODO move to model
		}); 
		
		wrongBtn.setOnAction(e ->
		{
			counter++;
			refreshView(); // TODO move to model
		});
		
		card.setMinWidth(160);
		card.setMinHeight(90);
		card.setId("bold");
		card.setOnAction(e -> card.setText(card.getText().equals(cardData[1]) ? cardData[2] : cardData[1]));
		
		HBox controlLayout = new HBox(20);
		controlLayout.setAlignment(Pos.CENTER);
		controlLayout.getChildren().addAll(backBtn, successfulBtn, wrongBtn);
		
		BorderPane mainLayout = new BorderPane();
		mainLayout.setPadding(new Insets(15));
		mainLayout.setTop(headLbl);
		mainLayout.setCenter(card);
		mainLayout.setBottom(controlLayout);
		
		return mainLayout;
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
			String d = getController().getModel("cards").getDataList(getData()).get(counter); // Ensure valid counter variable
			cardData = d.split(Constants.SEPARATOR);
			card.setText(cardData[1]);
		}
		
		
	}
}
