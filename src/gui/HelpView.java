package gui;

import application.Constants;
import application.HelpController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mvc.FXSettings;
import mvc.FXView;


/**
 * Hilfefenster
 * 
 * @author miro-albrecht
 *
 */
public class HelpView extends FXView
{

	BorderPane mainLayout = new BorderPane();
	//Image helpdesk = new Image("gui/pictures/helpdesk.jpg", true);
	AppButton impressumBtn = new AppButton("Impressum");
	AppButton anleitungBtn = new AppButton("Anleitung");
	AppButton indexBtn     = new AppButton("Index");
	String subTitle = "Hilfe";
	
	public HelpView (String setName)
	{
		super (setName, new HelpController());

		this.getWindow().setTitle(Constants.appTitle+subTitle+Constants.appVersion);
		this.getWindow().setResizable(false);

		//ImageView view = new ImageView(helpdesk);

		
		VBox tempVBox = new VBox();
		tempVBox.setPadding(new Insets(10));
		tempVBox.setSpacing(10);
		tempVBox.setAlignment(Pos.CENTER);
		tempVBox.getChildren().addAll(impressumBtn, anleitungBtn, indexBtn);

		tempVBox.setId("help");
		this.setupScene(new Scene(tempVBox, FXSettings.OPTIMAL_WIDTH, 150+FXSettings.OPTIMAL_HEIGHT));
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}
	
}
