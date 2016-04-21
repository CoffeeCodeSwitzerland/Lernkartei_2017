package FX;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Sandbox extends Application
{
	Stage window;

	public static void main (String[] args)
	{
		launch(args);
	}

	@Override
	public void start (Stage primaryStage) throws Exception
	{
		window = primaryStage;
		window.setTitle("Lernkartei [Alpha]");
		
		TextField input = new TextField();
		Button alertButton = new Button("Alert");
		Button edit = new Button("Edit");
		Button closeButton = new Button("Close");

		GridPane.setConstraints(alertButton, 0, 0);
		GridPane.setConstraints(edit, 1, 0);
		GridPane.setConstraints(closeButton, 2, 0);
		
		Button printButton = new Button("Print");
		Button clearButton = new Button("Clear");
		Button c = new Button("C");

		
		Label outp = new Label();
		
		ChoiceBox<String> outputOption = new ChoiceBox<String>();
		outputOption.getItems().addAll("Alert", "Label", "None");
		outputOption.setValue("None");
		
		alertButton.setCursor(Cursor.HAND);
		alertButton.setOnAction(e -> {
			String infoMessage = input.getText().length() == 0 ? "*Kein Text*" : input.getText();
			Alert.simpleInfoBox("Info", infoMessage);
		});
		
		printButton.setOnAction(e -> putTextOut(outputOption, input, outp));
		clearButton.setOnAction(e -> outp.setText(""));
		closeButton.setOnAction(e -> window.close());

		edit.setDisable(true);
		printButton.setDisable(true);
		
		input.setOnKeyTyped(e -> {
			if (input.getText().length() > 0)
			{
				printButton.setDisable(false);
			}
			else
			{
				printButton.setDisable(true);
			}
		});
		
		
		MenuItem newFile = new MenuItem("_New File");
		MenuItem saveFile = new MenuItem("_Save File...");
		MenuItem printText = new MenuItem("_Print");
		
		printText.setOnAction(e -> putTextOut(outputOption, input, outp));
		
		Menu menuFile = new Menu("_File");
		menuFile.getItems().addAll(newFile, saveFile, new SeparatorMenuItem(), printText);
		
		MenuBar bar = new MenuBar();
		bar.getMenus().addAll(menuFile);
		
		VBox form = new VBox();
		form.setAlignment(Pos.TOP_CENTER);
		form.getChildren().addAll(input, outp);
		
		GridPane.setConstraints(printButton, 0, 1);
		GridPane.setConstraints(clearButton, 1, 1);
		GridPane.setConstraints(c, 2, 1);
		GridPane.setConstraints(outputOption, 3, 1);
		
		GridPane top = new GridPane();
		top.setPadding(new Insets(10));
		top.setVgap(5);
		top.setHgap(5);
		top.getChildren().addAll(alertButton, edit, closeButton, printButton, clearButton, c, outputOption);
		
		BorderPane border = new BorderPane();
		border.setCenter(form);
		border.setTop(top);

		
		border.setPadding(new Insets(30));
		
		VBox menu = new VBox();
		menu.getChildren().addAll(bar, border);
		
		outp.setPadding(new Insets(30));
		form.setPadding(new Insets(30));
		
		window.setScene(new Scene(menu, 800, 450));
		window.show();
	}
	
	private void putTextOut (ChoiceBox<String> cBox, TextField tf, Label l)
	{
		switch (cBox.getValue())
		{
			case "Alert":
				Alert.simpleInfoBox("Your Message", tf.getText());
				break;
			case "Label":
				l.setText(tf.getText());
				break;
			default:
				break;
		}
	}
}
