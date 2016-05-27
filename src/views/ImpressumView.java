package views;

import java.io.File;

import globals.Functions;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;

public class ImpressumView extends FXView
{
	
	public ImpressumView(String newName, FXController newController) {
		// this constructor is the same for all view's
		super(newController);
		construct(newName);
	}

	@Override
	public Parent constructContainer() {
		// TODO Auto-generated method stub

		Label labelText;
		try {
			labelText = new Label (Functions.fileToString(new File(
					"src\\views\\txt\\impressum.txt")) );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			labelText = new Label("leer");
		}
		labelText.setWrapText(true);
		labelText.setMaxWidth(800);
		labelText.setId("impressumtext");

		Label labelTitel = new Label("Impressum");
		labelTitel.setId("impressumtitel");

		AppButton backBtn = new AppButton("_Zurück");
		backBtn.setOnAction(e -> getController().showMainView());

		BorderPane headLayout = new BorderPane(labelTitel);
		headLayout.setPadding(new Insets(20));
		ScrollPane scroller = new ScrollPane();
		scroller.setMaxWidth(800);
		
		Hyperlink WISSlink = new Hyperlink("WISS Webseite");
		WISSlink.setOnAction(e -> Functions.openWebpage("http://www.wiss.ch/"));
			
		Hyperlink BITLink = new Hyperlink("BIT Webseite");
		BITLink.setOnAction(e -> Functions.openWebpage("https://www.bit.admin.ch/"));
				
		Hyperlink LehrlingeLink = new Hyperlink("Unsere Webseite");
		LehrlingeLink.setOnAction(e -> Functions.openWebpage("http://bund2015.wiss-bern.ch/"));


		scroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroller.setVbarPolicy(ScrollBarPolicy.ALWAYS);

		VBox contentLayout = new VBox(20);
		contentLayout.getChildren().addAll(labelText, WISSlink, BITLink, LehrlingeLink);
		scroller.setContent(contentLayout);
		
		HBox controlLayout = new HBox(20);
		controlLayout.setAlignment(Pos.BOTTOM_CENTER);
		controlLayout.getChildren().addAll(backBtn);
		controlLayout.setPadding(new Insets(10));

		BorderPane mainLayout = new BorderPane();
		mainLayout.setPadding(new Insets(15));
		mainLayout.setTop(headLayout);
		mainLayout.setCenter(scroller);
		mainLayout.setBottom(controlLayout);

		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
	}
}
