package views;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import mvc.Controller;
import mvc.FXView;

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
		@SuppressWarnings ("unused")
		AppButton BacktoGameMenu = new AppButton("Zurück");
		
		
	

		
		
		return InfoLayout;
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub

	}

}
