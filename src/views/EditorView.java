package views;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import mvc.fx.FXController;
import mvc.fx.FXViewModel;
import views.components.AppButton;

public class EditorView extends FXViewModel
{
	// ArrayList<VBox> cards;

	public EditorView(String newName, FXController newController) {
		// this constructor is the same for all view's
		super(newController);
		construct(newName);
	}

	VBox editLayout = new VBox(10);
	Label headLbl;
	AppButton backBtn;
	TextField front = new TextField();
	TextField back = new TextField();
	WebView previewfront = new WebView();
	WebView previewback = new WebView();
	WebEngine enginefront = previewfront.getEngine();
	WebEngine engineback = previewback.getEngine();

	@Override
	public Parent constructContainer() {
		headLbl = new Label("");
		headLbl.setId("bold");	
		
		BorderPane headLayout = new BorderPane(headLbl);
		headLayout.setPadding(new Insets(25));
		
		//Zurück Button
		backBtn = new AppButton("_Zurück");
		backBtn.setOnAction(e -> 
		getController().showView("simpleeditorview"));	
		
		//Info Button
		AppButton infobtn = new AppButton("Hilfe");
		infobtn.setOnAction(e ->
		getController().showView("bbcodeinfo"));
		
		//EditLayout
		editLayout.setPadding(new Insets(10));
		editLayout.setAlignment(Pos.TOP_CENTER);

		//Controll Layout
		HBox controlLayout = new HBox(20);
		controlLayout.setAlignment(Pos.CENTER);
		controlLayout.getChildren().addAll(backBtn, infobtn);

		//Main Layout
		BorderPane mainLayout = new BorderPane();
		mainLayout.setPadding(new Insets(15));
		mainLayout.setTop(headLayout);
		mainLayout.setCenter(editLayout);
		mainLayout.setBottom(controlLayout);

		return mainLayout;
	}

	@Override
	public void refreshView() {

		editLayout.getChildren().clear();
		String data = getData();

		if (data != null)
		{
			headLbl.setText(data + " - " + getController().getViewData("stack"));
			
			Button bold = new Button("B");
			Button italic = new Button("I");
			Button crossed = new Button("S");
			Button under = new Button("U");
			Button sup = new Button("SP");
			Button sub = new Button("SB");
			Button cp = new Button("C");
			Button bold1 = new Button("B");
			Button italic1 = new Button("I");
			Button crossed1 = new Button("S");
			Button under1 = new Button("U");
			Button sup1 = new Button("SP");
			Button sub1 = new Button("SB");
			Button cp1 = new Button("C");
			
			front.setMinHeight(150);
			back.setMinHeight(150);
			
			String[] cardSides = data.split(Globals.SEPARATOR);
				front.setText(cardSides[1]);
				back.setText(cardSides[2]);
				
				UpdatePreview();

			back.setOnMouseReleased(e ->{
				int start = back.getSelection().getStart();
				int end = back.getSelection().getEnd();
				String codeBefore = back.getText().substring(0, start);
				String codeAfter = back.getText().substring(end);
				String isolatedWord = back.getText().substring(start, end);
					bold.setOnMouseClicked(a ->{
						back.setText(codeBefore+"[b]"+isolatedWord+"[/b]"+codeAfter);
					});
					italic.setOnMouseClicked(a ->{
						back.setText(codeBefore+"[i]"+isolatedWord+"[/i]"+codeAfter);
					});
					crossed.setOnMouseClicked(a ->{
						back.setText(codeBefore+"[s]"+isolatedWord+"[/s]"+codeAfter);
					});
					under.setOnMouseClicked(a ->{
						back.setText(codeBefore+"[u]"+isolatedWord+"[/u]"+codeAfter);
					});
					sup.setOnMouseClicked(a ->{
						back.setText(codeBefore+"[sup]"+isolatedWord+"[/sup]"+codeAfter);
					});
					sub.setOnMouseClicked(a ->{
						back.setText(codeBefore+"[sub]"+isolatedWord+"[/sub]"+codeAfter);
					});
					cp.setOnMouseClicked(a ->{
						ColorPicker colorPicker = new ColorPicker();
						Button save = new Button("Save");
						final Stage dialog = new Stage();
			            dialog.initModality(Modality.APPLICATION_MODAL);
			            VBox dialogVbox = new VBox(20);
			            dialogVbox.getChildren().addAll(colorPicker, save);
			            Scene dialogScene = new Scene(dialogVbox);
			            dialog.setScene(dialogScene);
			            dialog.show();
			                
			                save.setOnAction(b ->{
			                	String cstrg = Integer.toHexString(colorPicker.getValue().hashCode()).substring(0, 6).toUpperCase();
			                	back.setText(codeBefore+"[color=" + "(" + cstrg + ")" + "]"+isolatedWord+"[/color]"+codeAfter);
			                	dialog.close();
			               });
					});
			});
			
			front.setOnMouseReleased(e ->{
				int start = front.getSelection().getStart();
				int end = front.getSelection().getEnd();
				String codeBefore = front.getText().substring(0, start);
				String codeAfter = front.getText().substring(end);
				String isolatedWord = front.getText().substring(start, end);
					bold1.setOnMouseClicked(a ->{
						front.setText(codeBefore+"[b]"+isolatedWord+"[/b]"+codeAfter);
					});
					italic1.setOnMouseClicked(a ->{
						front.setText(codeBefore+"[i]"+isolatedWord+"[/i]"+codeAfter);
					});
					crossed1.setOnMouseClicked(a ->{
						front.setText(codeBefore+"[s]"+isolatedWord+"[/s]"+codeAfter);
					});
					under1.setOnMouseClicked(a ->{
						front.setText(codeBefore+"[u]"+isolatedWord+"[/u]"+codeAfter);
					});
					sup1.setOnMouseClicked(a ->{
						front.setText(codeBefore+"[sup]"+isolatedWord+"[/sup]"+codeAfter);
					});
					sub1.setOnMouseClicked(a ->{
						front.setText(codeBefore+"[sub]"+isolatedWord+"[/sub]"+codeAfter);
					});
					cp1.setOnMouseClicked(a ->{
						ColorPicker colorPicker = new ColorPicker();
						Button save = new Button("Save");
						final Stage dialog = new Stage();
			            dialog.initModality(Modality.APPLICATION_MODAL);
			            VBox dialogVbox = new VBox(20);
			            dialogVbox.getChildren().addAll(colorPicker, save);
			            Scene dialogScene = new Scene(dialogVbox);
			            dialog.setScene(dialogScene);
			            dialog.show();
			                
			                save.setOnAction(b ->{
			                	String cstrg = Integer.toHexString(colorPicker.getValue().hashCode()).substring(0, 6).toUpperCase();
			                	front.setText(codeBefore+"[color=" + "(" + cstrg + ")" + "]"+isolatedWord+"[/color]"+codeAfter);
			                	dialog.close();
			                });
					});
			});
			
			front.setOnKeyReleased(e ->{
				UpdatePreview();
			});
			back.setOnKeyReleased(e ->{
				UpdatePreview();
			});
			
			front.focusedProperty().addListener(e ->
			{
				if (!front.isFocused())
				{
					if (back.getText() != null && !back.getText().equals("") && front.getText() != null
							&& !front.getText().equals(""))
					{
						getController().getModel("cards").doAction("edit", cardSides[0] + Globals.SEPARATOR
								+ front.getText() + Globals.SEPARATOR + back.getText());
					}
				}
			});

			back.focusedProperty().addListener(e ->
			{
				if (!back.isFocused())
				{
					if (back.getText() != null && !back.getText().equals("") && front.getText() != null
							&& !front.getText().equals(""))
					{
						getController().getModel("cards").doAction("edit", cardSides[0] + Globals.SEPARATOR
								+ front.getText() + Globals.SEPARATOR + back.getText());
					}
				}
			});
			
			backBtn.setOnAction(e -> {
				if (back.getText() != null && !back.getText().equals("") && front.getText() != null
						&& !front.getText().equals(""))
				{
					getController().getModel("cards").doAction("edit", cardSides[0] + Globals.SEPARATOR
							+ front.getText() + Globals.SEPARATOR + back.getText());
					getController().showView("simpleeditorview");
				}
			});	
			
			HBox buttonLL = new HBox(8);
			buttonLL.setAlignment(Pos.TOP_LEFT);
			buttonLL.getChildren().addAll(bold1, italic1, crossed1, under1, sup1, sub1, cp1);
			
			HBox buttonLR = new HBox(8);
			buttonLR.setAlignment(Pos.TOP_LEFT);
			buttonLR.getChildren().addAll(bold, italic, crossed, under, sup, sub, cp);
			
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
			mainL.getChildren().addAll(leftL, rightL);

			editLayout.getChildren().addAll(mainL);
		}
	}
	
	public void UpdatePreview(){
		
			String text = back.getText();
			text = Functions.AntiHTMLTags(text);
			text = Functions.ColorBBCode(text);
			text = Functions.simpleBbCode2HTML(text, Globals.evenTags);
			text = Functions.realBbCode2HTML(text, Globals.pairedTags); 
			engineback.loadContent(text);
		
			String text2 = front.getText();
			text2 = Functions.AntiHTMLTags(text2);
			text2 = Functions.ColorBBCode(text2);
			text2 = Functions.simpleBbCode2HTML(text2, Globals.evenTags);
			text2 = Functions.realBbCode2HTML(text2, Globals.pairedTags);			
			enginefront.loadContent(text2);
		
	}
}
