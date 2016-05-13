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
		
		//Zurück Button
		AppButton backBtn = new AppButton("_Zurück");
		backBtn.setOnAction(e -> 
		getController().getView("simpleeditorview").show());	
		
		//EditLayout
		editLayout.setPadding(new Insets(10));
		editLayout.setAlignment(Pos.TOP_CENTER);

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

		if (data != null)
		{
			headLbl.setText(data + " - " + getController().getView("boxview").getData());

			TextField front = new TextField();
			TextField back = new TextField();
			Button update = new Button("\u2713");		
			
			front.setText(data);
			back.setText(data);
			
			update.setOnAction(e -> {
				//UPDATE
			});	
			
			HBox newL = new HBox(8);
			newL.setAlignment(Pos.CENTER);
			newL.getChildren().addAll(front, back, update);

			editLayout.getChildren().addAll(newL);
			
		}
		
	}
}
