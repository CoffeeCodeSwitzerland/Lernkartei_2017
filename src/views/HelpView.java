package views;

import controls.Constants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import mvc.Controller;
import mvc.FXView;

/**
 * Hilfefenster
 * 
 * @author miro-albrecht
 *
 */
public class HelpView extends FXView
{
	public HelpView(String newName, Controller newController) {
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}

	public Parent constructContainer () {
		
		this.getWindow().setTitle(Constants.appTitle+"Hilfe"+Constants.appVersion);
		this.getWindow().setResizable(false);

		AppButton impressumBtn = new AppButton("Impressum");
		AppButton anleitungBtn = new AppButton("Anleitung");
		AppButton indexBtn     = new AppButton("Index");
		impressumBtn.setOnAction(e -> getController().showView("impressumview"));
		anleitungBtn.setOnAction(e -> getController().showView("manualview"));
		indexBtn.setOnAction(e -> getController().showView("indexview"));

		VBox tempVBox = new VBox();
		tempVBox.setPadding(new Insets(10));
		tempVBox.setSpacing(10);
		tempVBox.setAlignment(Pos.CENTER);
		tempVBox.getChildren().addAll(impressumBtn, anleitungBtn, indexBtn);

		tempVBox.setId("help");
		return tempVBox;
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}
	
}
