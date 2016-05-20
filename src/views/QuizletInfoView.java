package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;

public class QuizletInfoView extends FXView
{
	public QuizletInfoView(String newName, FXController newController) {
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}

	@Override
	public Parent constructContainer ()
	{
		//Labels (für die Infotexte)
		Label labelTitel = new Label("Quizlet Info");
		Label labelText = new Label("Hier kommt die Info und der Link zur Quizlet Seite hin.");
		labelText.setWrapText(true);
		labelText.setMaxWidth(870);
		labelTitel.setId("impressumtext");
		labelText.setId("impressumtext");
		
		AppButton backBtn = new AppButton("_Zurück");
		backBtn.setOnAction(e -> getController().showMainView());

		BorderPane headLayout = new BorderPane(labelTitel);
		headLayout.setPadding(new Insets(20));
		ScrollPane scroller = new ScrollPane();
			
		scroller.setContent(labelText);
		scroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroller.setVbarPolicy(ScrollBarPolicy.ALWAYS);  
		
		HBox controlLayout = new HBox(20);
		controlLayout.setAlignment(Pos.BOTTOM_LEFT);
		controlLayout.getChildren().addAll(backBtn);

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
