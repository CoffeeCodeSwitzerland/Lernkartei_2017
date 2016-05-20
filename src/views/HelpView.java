package views;

import globals.Globals;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;

/**
 * Hilfefenster
 * 
 * @author miro-albrecht
 *
 */
public class HelpView extends FXView
{
	public HelpView(String newName, FXController newController) {
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}

	public Parent constructContainer () {
		
		this.getWindow().setTitle(Globals.appTitle+"Hilfe"+Globals.appVersion);
		this.getWindow().setResizable(false);

		AppButton impressumBtn = new AppButton("_Impressum");
		AppButton quizletBtn = new AppButton("_Quizlet");
		AppButton anleitungBtn = new AppButton("_Anleitung");
		AppButton indexBtn     = new AppButton("Inde_x");
		AppButton backbtn 	   = new AppButton("Zurück");
		impressumBtn.setOnAction(e -> getController().showView("impressumview"));
		quizletBtn.setOnAction(e -> getController().showView("quizletview"));
		anleitungBtn.setOnAction(e -> getController().showView("manualview"));
		indexBtn.setOnAction(e -> getController().showView("indexview"));

		VBox tempVBox = new VBox();
		tempVBox.setPadding(new Insets(10));
		tempVBox.setSpacing(10);
		tempVBox.setAlignment(Pos.CENTER);
		tempVBox.getChildren().addAll(impressumBtn, quizletBtn, anleitungBtn, indexBtn, backbtn);

		tempVBox.setId("help");
		return tempVBox;
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}
	
}
