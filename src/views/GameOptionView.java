package views;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;

public class GameOptionView extends FXView {

	public GameOptionView(String newName, FXController newController) {
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}

	@Override
	public Parent constructContainer() {
		

		// Objekte
		
		AppButton BacktoGameMenu = new AppButton("Zurück");
		Label Anleitung = new Label();
		BorderPane mainLayout = new BorderPane();

		VBox itemsLayout = new VBox();
		itemsLayout.setAlignment(Pos.CENTER);

		Anleitung.setText("Anleitung");
		
		BacktoGameMenu.setOnAction(e -> getController().showView("gameview"));

		itemsLayout.getChildren().addAll(Anleitung, BacktoGameMenu);
		
		Anleitung.setAlignment(Pos.TOP_CENTER); 
		mainLayout.setCenter(itemsLayout);
	
		return mainLayout;
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub

	}

}
