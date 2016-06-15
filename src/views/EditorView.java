package views;

import java.io.File;
import globals.Functions;
import globals.Globals;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mvc.ModelInterface.Command;
import mvc.fx.FXController;
import mvc.fx.FXViewModel;
import views.components.AppButton;

public class EditorView extends FXViewModel
{
	// ArrayList<VBox> cards;

	public EditorView (String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}

	VBox					editLayout		= new VBox(10);
	Label					headLbl;
	AppButton				backBtn, infobtn;
	public static TextField	front			= new TextField();
	public static TextField	back			= new TextField();
	public static WebView	previewfront	= new WebView();
	public static WebView	previewback		= new WebView();
	public static WebEngine	enginefront		= previewfront.getEngine();
	public static WebEngine	engineback		= previewback.getEngine();
	public static String	frontinfo;
	public static String	backinfo;

	@Override
	public Parent constructContainer ()
	{
		headLbl = new Label("");
		headLbl.setId("bold");

		BorderPane headLayout = new BorderPane(headLbl);
		headLayout.setPadding(new Insets(25));

		// Zurück Button
		backBtn = new AppButton("_Zurück");
		backBtn.setOnAction(e -> getFXController().showView("simpleeditorview"));

		// Info Button
		infobtn = new AppButton("Hilfe");
		infobtn.setOnAction(e -> getFXController().showView("bbcodeinfo"));

		// EditLayout
		editLayout.setPadding(new Insets(10));
		editLayout.setAlignment(Pos.TOP_CENTER);

		// Controll Layout
		HBox controlLayout = new HBox(20);
		controlLayout.setAlignment(Pos.CENTER);
		controlLayout.getChildren().addAll(backBtn, infobtn);

		// Main Layout
		BorderPane mainLayout = new BorderPane();
		mainLayout.setPadding(new Insets(15));
		mainLayout.setTop(headLayout);
		mainLayout.setCenter(editLayout);
		mainLayout.setBottom(controlLayout);

		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
		editLayout.getChildren().clear();

		String data = getData();

		if (data != null)
		{
			headLbl.setText("Erweiterter Editor");

			// TODO Keine doppelten Buttons
			Button bold = new Button("B");
			bold.setId("boldbtn");
			Button italic = new Button("I");
			italic.setId("italicbtn");
			Button crossed = new Button("S");
			crossed.setId("crossedbtn");
			Button under = new Button("U");
			under.setId("underbtn");
			Button sup = new Button("SP");
			sup.setId("supbtn");
			Button sub = new Button("SB");
			sub.setId("subbtn");
			Button cp = new Button("C");
			cp.setId("cpbtn");
			Button explorer = new Button("Img");
			explorer.setId("imgbtn");
			Button bold1 = new Button("B");
			Button italic1 = new Button("I");
			Button crossed1 = new Button("S");
			Button under1 = new Button("U");
			Button sup1 = new Button("SP");
			Button sub1 = new Button("SB");
			Button cp1 = new Button("C");
			Button explorer1 = new Button("Img");
			Button leftbtn = new Button("<");
			Button rightbtn = new Button(">");

			front.setMinHeight(150);
			back.setMinHeight(150);

			String[] cardSides = data.split(Globals.SEPARATOR);
			front.setText(cardSides[1]);
			back.setText(cardSides[2]);

			UpdatePreview();

			back.setOnMouseReleased(e ->
			{
				int start = back.getSelection().getStart();
				int end = back.getSelection().getEnd();
				String codeBefore = back.getText().substring(0, start);
				String codeAfter = back.getText().substring(end);
				String isolatedWord = back.getText().substring(start, end);
				// TODO Create a function for 'back.setText(codeBefore + "[b]" +
				// isolatedWord + "[/b]" + codeAfter);'
				bold.setOnMouseClicked(a ->
				{
					back.setText(codeBefore + "[b]" + isolatedWord + "[/b]" + codeAfter);
					UpdatePreview();
				});
				italic.setOnMouseClicked(a ->
				{
					back.setText(codeBefore + "[i]" + isolatedWord + "[/i]" + codeAfter);
					UpdatePreview();
				});
				crossed.setOnMouseClicked(a ->
				{
					back.setText(codeBefore + "[s]" + isolatedWord + "[/s]" + codeAfter);
					UpdatePreview();
				});
				under.setOnMouseClicked(a ->
				{
					back.setText(codeBefore + "[u]" + isolatedWord + "[/u]" + codeAfter);
					UpdatePreview();
				});
				sup.setOnMouseClicked(a ->
				{
					back.setText(codeBefore + "[sup]" + isolatedWord + "[/sup]" + codeAfter);
					UpdatePreview();
				});
				sub.setOnMouseClicked(a ->
				{
					back.setText(codeBefore + "[sub]" + isolatedWord + "[/sub]" + codeAfter);
					UpdatePreview();
				});
				cp.setOnMouseClicked(a ->
				{
					ColorPicker colorPicker = new ColorPicker();
					Button save = new Button("Save");
					final Stage dialog = new Stage();
					dialog.initModality(Modality.APPLICATION_MODAL);
					VBox dialogVbox = new VBox(20);
					dialogVbox.getChildren().addAll(colorPicker, save);
					Scene dialogScene = new Scene(dialogVbox);
					dialog.setScene(dialogScene);
					dialog.show();

					save.setOnAction(b ->
					{
						String cstrg = Integer.toHexString(colorPicker.getValue().hashCode()).substring(0, 6).toUpperCase();
						back.setText(codeBefore + "[color=" + "(" + cstrg + ")" + "]" + isolatedWord + "[/color]" + codeAfter);
						UpdatePreview();
						dialog.close();
					});
				});
			});
			
			explorer1.setOnAction(e ->{
				FileChooser fileChooser = new FileChooser();
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.jpeg", "*.gif");
				fileChooser.getExtensionFilters().add(extFilter);
				File selectedFile = fileChooser.showOpenDialog(null);
				front.setText("[img=(" + selectedFile.getAbsolutePath() + ")][/img]");
				UpdatePreview();
			});
			
			explorer.setOnAction(e ->{
				FileChooser fileChooser = new FileChooser();
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.jpeg", "*.gif");
				fileChooser.getExtensionFilters().add(extFilter);
				File selectedFile = fileChooser.showOpenDialog(null);
				back.setText("[img=(" + selectedFile.getAbsolutePath() + ")][/img]");
				UpdatePreview();
			});

			front.setOnMouseReleased(e ->
			{
				int start = front.getSelection().getStart();
				int end = front.getSelection().getEnd();
				String codeBefore = front.getText().substring(0, start);
				String codeAfter = front.getText().substring(end);
				String isolatedWord = front.getText().substring(start, end);
				bold1.setOnMouseClicked(a ->
				{
					// TODO siehe oben
					front.setText(codeBefore + "[b]" + isolatedWord + "[/b]" + codeAfter);
					UpdatePreview();
				});
				italic1.setOnMouseClicked(a ->
				{
					front.setText(codeBefore + "[i]" + isolatedWord + "[/i]" + codeAfter);
					UpdatePreview();
				});
				crossed1.setOnMouseClicked(a ->
				{
					front.setText(codeBefore + "[s]" + isolatedWord + "[/s]" + codeAfter);
					UpdatePreview();
				});
				under1.setOnMouseClicked(a ->
				{
					front.setText(codeBefore + "[u]" + isolatedWord + "[/u]" + codeAfter);
					UpdatePreview();
				});
				sup1.setOnMouseClicked(a ->
				{
					front.setText(codeBefore + "[sup]" + isolatedWord + "[/sup]" + codeAfter);
					UpdatePreview();
				});
				sub1.setOnMouseClicked(a ->
				{
					front.setText(codeBefore + "[sub]" + isolatedWord + "[/sub]" + codeAfter);
					UpdatePreview();
				});
				cp1.setOnMouseClicked(a ->
				{
					ColorPicker colorPicker = new ColorPicker();
					Button save = new Button("Save");
					final Stage dialog = new Stage();
					dialog.initModality(Modality.APPLICATION_MODAL);
					VBox dialogVbox = new VBox(20);
					dialogVbox.getChildren().addAll(colorPicker, save);
					Scene dialogScene = new Scene(dialogVbox);
					dialog.setScene(dialogScene);
					dialog.show();

					save.setOnAction(b ->
					{
						String cstrg = Integer.toHexString(colorPicker.getValue().hashCode()).substring(0, 6).toUpperCase();
						front.setText(codeBefore + "[color=" + "(" + cstrg + ")" + "]" + isolatedWord + "[/color]" + codeAfter);
						UpdatePreview();
						dialog.close();
					});
				});
			});

			front.setOnKeyReleased(e ->
			{
				UpdatePreview();
			});
			back.setOnKeyReleased(e ->
			{
				UpdatePreview();
			});

			

			backBtn.setOnAction(e ->
			{
				if (back.getText() != null && !back.getText().equals("") && front.getText() != null && !front.getText().equals(""))
				{
					getFXController().getModel("cards").doAction(Command.UPDATE, cardSides[0], front.getText(), back.getText());
					getFXController().showView("simpleeditorview");
				}
			});
			
			rightbtn.setOnAction(e ->{
				for(int i = 0; i < cardSides.length; i++) {
					System.out.print(cardSides[i] + ":");
				}
					  front.setText(cardSides[1]);
						back.setText(cardSides[2]);
			});

			infobtn.setOnAction(e ->
			{

				getFXController().showView("bbcodeinfo");
				frontinfo = front.getText();
				backinfo = back.getText();
				engineback.loadContent(back.getText());
				enginefront.loadContent(front.getText());
			});

			HBox buttonLL = new HBox(8);
			buttonLL.setAlignment(Pos.TOP_LEFT);
			buttonLL.getChildren().addAll(bold1, italic1, crossed1, under1, sup1, sub1, cp1, explorer1);

			HBox buttonLR = new HBox(8);
			buttonLR.setAlignment(Pos.TOP_LEFT);
			buttonLR.getChildren().addAll(bold, italic, crossed, under, sup, sub, cp, explorer);
			
			VBox leftbtnL = new VBox(8);
			leftbtnL.setAlignment(Pos.CENTER_LEFT);
			leftbtnL.getChildren().addAll(leftbtn);
			
			VBox rightbtnR = new VBox(8);
			rightbtnR.setAlignment(Pos.CENTER_RIGHT);
			rightbtnR.getChildren().addAll(rightbtn);

			HBox eingabeL = new HBox(8);
			eingabeL.setAlignment(Pos.CENTER);
			eingabeL.getChildren().addAll(front, back);

			HBox vorschauL = new HBox(8);
			vorschauL.setAlignment(Pos.BOTTOM_CENTER);
			vorschauL.getChildren().addAll(previewfront, previewback);

			VBox leftL = new VBox(8);
			leftL.setAlignment(Pos.CENTER_LEFT);
			leftL.getChildren().addAll(buttonLL, front, previewfront);

			VBox rightL = new VBox(8);
			rightL.setAlignment(Pos.CENTER_RIGHT);
			rightL.getChildren().addAll(buttonLR, back, previewback);

			HBox mainL = new HBox(8);
			mainL.setAlignment(Pos.BOTTOM_CENTER);
			mainL.getChildren().addAll(leftbtnL, leftL, rightL, rightbtnR);

			editLayout.getChildren().addAll(mainL);
		}
	}

	public static void UpdatePreview ()
	{

		engineback.loadContent(Functions.FullBb2HTML(back.getText()));
		enginefront.loadContent(Functions.FullBb2HTML(front.getText()));

	}
}
