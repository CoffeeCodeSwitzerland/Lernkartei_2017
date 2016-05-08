package views;

import controls.Constants;
import controls.HelpController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mvc.FXView;

/**
 * Hilfefenster
 * 
 * @author miro-albrecht
 *
 */
public class HelpView extends FXView
{
	BorderPane mainLayout  = new BorderPane();
	AppButton impressumBtn = new AppButton("Impressum");
	AppButton anleitungBtn = new AppButton("Anleitung");
	AppButton indexBtn     = new AppButton("Index");
	String subTitle = "Hilfe";
	
	public HelpView (String setName)
	{
		// special constructor for a new Stage
		super (setName, new HelpController());
		
		// add myself as mainView to the new stage:
		this.getController().addUniqueView(this);
		this.getController().setMainViewName(this.getName());
		//
		setupScene(constructContainer());
	}

	private Parent constructContainer () {
		this.getWindow().setTitle(Constants.appTitle+subTitle+Constants.appVersion);
		this.getWindow().setResizable(false);

		impressumBtn.setOnAction(e -> getController().getView("impressum").show());
		anleitungBtn.setOnAction(e -> getController().getView("manual").show());
		indexBtn.setOnAction(e -> getController().getView("index").show());

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
