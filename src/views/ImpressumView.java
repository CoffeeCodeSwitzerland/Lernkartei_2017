package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
		Label labelText = new Label("Das Programm WISSLearnCards wurde programmiert im Rahmen eines Projekts durch die Informatiklernenden\n im Auftrag der\n\nStiftung Wirtschaftsinformatikschule Schweiz WISS\nOstermundigenstrasse 81\nCH-3006 Bern\nhttp://www.wiss.ch/\n\nTeilnehmer:\n\nInformatiklernende vom\n\nBundesamt für Informatik und Telekommunikation BIT\nMonbijoustrasse 74\nCH-3003 Bern\nhttps://www.bit.admin.ch/\n\n•	Yanis Weibel\n•	Joel Häberli\n•	David Schor\n•	Nina Egger\n•	Roger Schneiter\n•	Miro Albrecht\n•	Tim Leibacher\nhttp://bund2015.wiss-bern.ch/\n");
		labelText.setWrapText(true);
		labelText.setMaxWidth(800);

		labelTitel.setId("impressumtitel");
		labelText.setId("impressumtext");
			
		AppButton backBtn = new AppButton("_Zurück");
		backBtn.setOnAction(e -> getController().showMainView());

		BorderPane headLayout = new BorderPane(labelTitel);
		headLayout.setPadding(new Insets(20));
		ScrollPane scroller = new ScrollPane();
		scroller.setMaxWidth(800);
				
		scroller.setContent(labelText);
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
