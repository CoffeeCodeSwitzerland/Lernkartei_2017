package views;

import java.io.File;

import globals.Functions;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.BackButton;

/**
 * Hilfesystem Info Anzeige
 * 
 * @author hugo-lucca
 *
 */
public class TroubleShootView extends FXView {

	public TroubleShootView(String newName, FXController newController) {
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
			// To avoid strange chars like "﻿", the html -Tag is added here separately:
			webContent.loadContent("<html>"+Functions.fileToString(new File(
								   "src/views/txt/troubleshoot.htm"))+"</html>");
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

		Label labelTitel = new Label("Fehlerbehandlung");
		labelTitel.setId("anleitungstitel");

		BackButton backBtn = new BackButton(this.getFXController());

		BorderPane headLayout = new BorderPane(labelTitel);
		headLayout.setPadding(new Insets(20));

		ScrollPane scroller = new ScrollPane();
		scroller.setMaxWidth(pageWidth);

		scroller.setContent(webPage);
		scroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroller.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		scroller.setId("anleitung");

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
	public void refreshView() {
		// TODO Auto-generated method stub

	}
}
