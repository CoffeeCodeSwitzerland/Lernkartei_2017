package views;

import globals.Globals;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;
import views.components.CloseButton;

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
		super(newController);
		construct(newName);
	}

	final   VBox tempVBox = new VBox();
	final 	AppButton impressumBtn = new AppButton  ("_Impressum");
	final 	AppButton quizletBtn   = new AppButton  ("_Quizlet");
	final 	AppButton anleitungBtn = new AppButton  ("_Anleitung");
	final 	AppButton indexBtn     = new AppButton  ("_Trouble Shoot");
	final 	AppButton logBtn       = new AppButton  ("_Logger");
	final 	CloseButton closeBtn   = new CloseButton("Hilfe _Beenden");

	public Parent constructContainer () {
		
		this.getWindow().setTitle(Globals.appTitle+"Hilfe"+Globals.appVersion);
		this.getWindow().setResizable(false);

		
		impressumBtn.setOnAction(e -> getFXController().showAndTrackView("impressumview"));
		quizletBtn.setOnAction(e -> getFXController().showAndTrackView("quizletview"));
		anleitungBtn.setOnAction(e -> getFXController().showAndTrackView("manualview"));
		indexBtn.setOnAction(e -> getFXController().showAndTrackView("troubleshootview"));
		logBtn.setOnAction(e -> getFXController().showAndTrackView("logview"));

		tempVBox.setId("helpbox");
		tempVBox.setPadding(new Insets(10));
		tempVBox.setSpacing(5);
		tempVBox.setAlignment(Pos.CENTER);
		tempVBox.getChildren().addAll(impressumBtn, quizletBtn, anleitungBtn, indexBtn, logBtn, closeBtn);
		VBox.setMargin(impressumBtn, new Insets(50,0,0,0));

		return tempVBox;
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}
	
}
