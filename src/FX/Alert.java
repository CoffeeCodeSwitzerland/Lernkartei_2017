package FX;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Alert
{
	public static void simpleInfoBox (String title, String message)
	{
		Stage window = new Stage();
		window.initStyle(StageStyle.UTILITY);
		window.setResizable(false);
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		
		Label l = new Label(message);
		Button b = new Button("OK");
		b.setOnAction(e -> window.close());
		
		VBox layout = new VBox(20);
		layout.getChildren().addAll(l, b);
		layout.setAlignment(Pos.CENTER);
		
		int width;
		int x = 6;
		int y = 150;
		
		width = message.length() * x + y;
		
		window.setScene(new Scene(layout, width, 150));
		window.show();
	}
}
