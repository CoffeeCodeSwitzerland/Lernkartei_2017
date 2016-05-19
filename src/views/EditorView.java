package views;

import controls.Globals;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import mvc.fx.FXController;
import mvc.fx.FXViewModel;

public class EditorView extends FXViewModel
{
	// ArrayList<VBox> cards;

	public EditorView(String newName, FXController newController) {
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}

	VBox editLayout = new VBox(10);
	Label headLbl;

	@Override
	public Parent constructContainer() {
		headLbl = new Label("");
		headLbl.setId("bold");	
		
		BorderPane headLayout = new BorderPane(headLbl);
		headLayout.setPadding(new Insets(25));
		
		//Zurück Button
		AppButton backBtn = new AppButton("_Zurück");
		backBtn.setOnAction(e -> 
		getController().showView("simpleeditorview"));	
		
		//Info Button
		AppButton infobtn = new AppButton("");
		infobtn.setOnAction(e ->
		getController().showView("simpleeditorview"));
		
		//EditLayout
		editLayout.setPadding(new Insets(10));
		editLayout.setAlignment(Pos.TOP_CENTER);

		//Controll Layout
		HBox controlLayout = new HBox(20);
		controlLayout.setAlignment(Pos.CENTER);
		controlLayout.getChildren().addAll(backBtn);

		//Main Layout
		BorderPane mainLayout = new BorderPane();
		mainLayout.setPadding(new Insets(15));
		mainLayout.setTop(headLayout);
		mainLayout.setCenter(editLayout);
		mainLayout.setBottom(controlLayout);

		return mainLayout;
	}

	@Override
	public void refreshView() {

		editLayout.getChildren().clear();

		String data = getData();

		if (data != null)
		{
			headLbl.setText(data + " - " + getController().getViewData("stack"));
			
			Button bold = new Button("B");
			Button italic = new Button("I");
			Button crossed = new Button("U");
			Button sup = new Button("SP");
			Button sub = new Button("SB");
			TextField front = new TextField();
			TextField back = new TextField();
			Button update = new Button("\u2713");	
			WebView previewfront = new WebView();
			WebView previewback = new WebView();
			WebEngine enginefront = previewfront.getEngine();
			WebEngine engineback = previewback.getEngine();
			
			front.setMinHeight(90);
			back.setMinHeight(90);
			previewfront.setMaxHeight(90);
			previewback.setMaxHeight(90);
			previewfront.setMaxWidth(200);
			previewback.setMaxWidth(200);
			engineback.loadContent(back.getText());
			
			String[] cardSides = data.split(Globals.SEPARATOR);
			front.setText(cardSides[1]);
			back.setText(cardSides[2]);
			
			front.setOnKeyReleased(e ->
			enginefront.loadContent(front.getText()));

			update.setOnAction(e -> {
				if(back.getText() != null && !back.getText().equals("") && front.getText() != null
						&& !front.getText().equals(""))
				{
					getController().getModel("cards").doAction("edit", cardSides[0] + Globals.SEPARATOR
							+ front.getText() + Globals.SEPARATOR + back.getText());
				}
			});	
			
			HBox buttonL = new HBox(8);
			buttonL.setAlignment(Pos.TOP_CENTER);
			buttonL.getChildren().addAll(bold, italic, crossed, sup, sub);
			
			HBox eingabeL = new HBox(8);
			eingabeL.setAlignment(Pos.CENTER);
			eingabeL.getChildren().addAll(front, back, update);
			
			HBox vorschauL = new HBox(8);
			vorschauL.setAlignment(Pos.BOTTOM_CENTER);
			vorschauL.getChildren().addAll(previewfront, previewback);

			editLayout.getChildren().addAll(buttonL, eingabeL, vorschauL);
		}
	}
}
