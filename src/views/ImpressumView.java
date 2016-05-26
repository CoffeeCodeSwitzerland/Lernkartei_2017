package views;

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
		Label labelTitel = new Label("Impressum");
		Label labelText = new Label("Das Programm WISSLearnCards wurde programmiert im Rahmen eines Projekts"
				+ " durch die Informatiklernenden\n im Auftrag der\n\nStiftung Wirtschaftsinformatikschule Schweiz "
				+ "WISS\nOstermundigenstrasse 81\nCH-3006 Bern\n\n\nTeilnehmer:\n\nInformatiklernende "
				+ "vom\n\nBundesamt für Informatik und Telekommunikation BIT\nMonbijoustrasse 74\nCH-3003 Bern\n\n\n•	"
				+ "Yanis Weibel\n•	Joel Häberli\n•	David Schor\n•	Nina Egger\n•	Roger Schneiter\n•	Miro Albrecht\n•	"
				+ "Tim Leibacher\n\n");
		labelText.setWrapText(true);
		labelText.setMaxWidth(800);

		labelTitel.setId("impressumtitel");
		labelText.setId("impressumtext");
		
		Hyperlink WISSlink = new Hyperlink("WISS Webseite");
		WISSlink.setOnAction(e -> Functions.openWebpage("http://www.wiss.ch/"));
		
		Hyperlink BITLink = new Hyperlink("BIT Webseite");
		BITLink.setOnAction(e -> Functions.openWebpage("https://www.bit.admin.ch/"));
		
		Hyperlink LehrlingeLink = new Hyperlink("Unsere Webseite");
		LehrlingeLink.setOnAction(e -> Functions.openWebpage("http://bund2015.wiss-bern.ch/"));
		
		
		AppButton backBtn = new AppButton("_Zurück");
		backBtn.setOnAction(e -> getController().showMainView());

		BorderPane headLayout = new BorderPane(labelTitel);
		headLayout.setPadding(new Insets(20));
		ScrollPane scroller = new ScrollPane();
		scroller.setMaxWidth(800);
				
		VBox contentLayout = new VBox(20);
		contentLayout.getChildren().addAll(labelText, WISSlink, BITLink, LehrlingeLink);
		
		scroller.setContent(contentLayout);
		scroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroller.setVbarPolicy(ScrollBarPolicy.ALWAYS);  
			
		HBox controlLayout = new HBox(20);
		controlLayout.setAlignment(Pos.BOTTOM_CENTER);
		controlLayout.getChildren().addAll(backBtn);
		controlLayout.setPadding(new Insets (10));

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
