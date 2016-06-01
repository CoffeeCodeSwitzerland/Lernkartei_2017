package views;

import java.io.File;

import globals.Functions;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
	WebView		webPage		= new WebView();
	WebEngine	webContent	= webPage.getEngine();

	@Override
	public Parent constructContainer() {
		webContent.loadContent("<html><body><b>Missing a manual</b></body></html>");
		try {
			// To avoid strange chars like "ï»¿", the html -Tag is added here separately:
			webContent.loadContent("<html>"+Functions.fileToString(new File(
								   "src/views/txt/impressum.htm"))+"</html>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		double pageWidth = this.getFXController().getMyFXStage().getOPTIMAL_WIDTH();
		double pageHeight = this.getFXController().getMyFXStage().getOPTIMAL_HEIGHT();
		debug.Debugger.out("ManualView sizes: w:"+pageWidth+" h:"+pageHeight);
		
		//webPage.setPrefHeight(pageHeight);
		webPage.setPrefWidth(pageWidth*.93);
		//webContent.setJavaScriptEnabled(true);
		webPage.applyCss();
		webPage.setId("anleitung");

		Label labelTitel = new Label("Impressum");
		labelTitel.setId("impressumtitel");

		AppButton backBtn = new AppButton("_Zurück");
		backBtn.setOnAction(e -> getFXController().showMainView());

		BorderPane headLayout = new BorderPane(labelTitel);
		headLayout.setPadding(new Insets(20));
		//ScrollPane scroller = new ScrollPane();
		//scroller.setMaxWidth(800);
		
		Hyperlink WISSlink = new Hyperlink("WISS Webseite");
		WISSlink.setOnAction(e -> Functions.openWebpage("http://www.wiss.ch/"));
			
		Hyperlink BITLink = new Hyperlink("BIT Webseite");
		BITLink.setOnAction(e -> Functions.openWebpage("https://www.bit.admin.ch/"));
				
		Hyperlink LehrlingeLink = new Hyperlink("Unsere Webseite");
		LehrlingeLink.setOnAction(e -> Functions.openWebpage("http://bund2015.wiss-bern.ch/"));


		//scroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		//scroller.setVbarPolicy(ScrollBarPolicy.ALWAYS);

		VBox contentLayout = new VBox(20);
		
		//scroller.setContent(contentLayout);
		
		
		//scroller.setContent(contentLayout);
		contentLayout.setMinHeight(pageHeight*0.8);
		contentLayout.setPrefWidth(pageWidth*.93);		
		contentLayout.getChildren().addAll(webPage,WISSlink, BITLink, LehrlingeLink);
		
		HBox controlLayout = new HBox(20);
		controlLayout.setAlignment(Pos.BOTTOM_CENTER);
		controlLayout.getChildren().addAll(backBtn);
		controlLayout.setPadding(new Insets(10));

		BorderPane mainLayout = new BorderPane();
		mainLayout.setPadding(new Insets(15));
		mainLayout.setTop(headLayout);
		mainLayout.setCenter(contentLayout);
		mainLayout.setBottom(controlLayout);

		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
	}
}
