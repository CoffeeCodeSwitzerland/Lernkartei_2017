package views;

import java.util.ArrayList;

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
import mvc.Controller;
import mvc.FXViewModel;


public class EditorView1 extends FXViewModel
{
	// ArrayList<VBox> cards;

	public EditorView1(String newName, Controller newController) {
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}

	VBox editLayout = new VBox(10);
	Label headLbl;
	HTMLEditor front, back;
	Button update = new Button("\u2713");
	

	@Override
	public Parent constructContainer() {
		headLbl = new Label("");
		headLbl.setId("bold");	
		
		BorderPane headLayout = new BorderPane(headLbl);
		headLayout.setPadding(new Insets(25));

		//TODO: muss in update gewandelt werde
		update.setOnAction(e -> getController().getModel("cards").doAction("edit"));
		
		//Editor Vorderseite
		HTMLEditor front = new HTMLEditor();
		front.setPrefHeight(245);
		front.setMaxWidth(600);
		
		//Editor Rückseite
		HTMLEditor back = new HTMLEditor();
		back.setPrefHeight(245);
		back.setMaxWidth(600);
		
		//Zurück Button
		AppButton backBtn = new AppButton("Zurück");
		backBtn.setOnAction(e -> 
		getController().getView("simpleeditorview").show());	
		
		//EditLayout
		editLayout.setPadding(new Insets(10));
		editLayout.setAlignment(Pos.TOP_CENTER);
		editLayout.getChildren().addAll(front, back);

		//Controll Layout
		HBox controlLayout = new HBox(20);
		controlLayout.setAlignment(Pos.CENTER);
		controlLayout.getChildren().addAll(backBtn, update);

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

			ArrayList<HBox> cards = new ArrayList<>();
			
			HTMLEditor front = new HTMLEditor();
			HTMLEditor back = new HTMLEditor();
			
			
			Button addBtn = new Button("\u2713");
			addBtn.setMaxWidth(35);

			//TODO:Update darausmachen
			addBtn.setOnAction(e ->
			{
				if (back.getHtmlText() != null && !back.getHtmlText().equals("") && front.getHtmlText() != null
						&& !front.getHtmlText().equals(""))
				{
					getController().getModel("cards").doAction("new",
							front.getHtmlText() + Globals.SEPARATOR + back.getHtmlText() + Globals.SEPARATOR + data);
				}
			});

			HBox v = new HBox(8);

			v.setAlignment(Pos.CENTER);
			v.getChildren().addAll(front, back, addBtn);
			cards.add(v);

			editLayout.getChildren().addAll(cards);
	}
}
