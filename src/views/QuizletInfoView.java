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


public class QuizletInfoView extends FXView
{
	public QuizletInfoView (String newName, FXController newController)
	{
		// this constructor is the same for all view's
		super(newController);
		construct(newName);
	}

	@Override
	public Parent constructContainer ()
	{
		// Labels (für die Infotexte)
		Label labelTitel = new Label("Quizlet Info");
		Label labelText = new Label("Quizlet ist eine Online-Lernplatform. "
				+ "User können Flashcards (Kartei- bzw. Lernkarten) erfassen und lernen."
				+ "\nWir ermöglichen den Import von Stapeln, "
				+ "da Quizlet als grosse Platform mit Millionen von Stapeln die Arbeit mit Flashcards erheblich erleichtert.");
		
		labelText.setWrapText(true);
		labelText.setMaxWidth(800);
		labelTitel.setId("impressumtitel");
		labelText.setId("impressumtext");
		
		Hyperlink link = new Hyperlink("Quizlet");
		link.setOnAction(e -> Functions.openWebpage("http://quizlet.com/"));

		AppButton backBtn = new AppButton("_Zurück");
		backBtn.setOnAction(e -> getController().showMainView());

		BorderPane headLayout = new BorderPane(labelTitel);
		headLayout.setPadding(new Insets(20));
		
		VBox contentLayout = new VBox(20);
		contentLayout.getChildren().addAll(labelText, link);
		
		ScrollPane scroller = new ScrollPane();
		scroller.setMaxWidth(800);
		scroller.setContent(contentLayout);
		scroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroller.setVbarPolicy(ScrollBarPolicy.ALWAYS);

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
