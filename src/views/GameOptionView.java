package views;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mvc.Controller;
import mvc.fx.FXView;

public class GameOptionView extends FXView {
	

	public GameOptionView(String newName, Controller newController) {
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}

	@Override
	public Parent constructContainer() {
		BorderPane InfoLayout = new BorderPane();
		
		//Objekte
		AppButton BacktoGameMenu = new AppButton("Zurück");
		BorderPane mainLayout = new BorderPane();
		
		VBox itemsLayout = new VBox();
		
		itemsLayout.getChildren().addAll(BacktoGameMenu);
		
		
		
	

		
		mainLayout.setCenter(itemsLayout);
		return InfoLayout;
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub

	}

}
