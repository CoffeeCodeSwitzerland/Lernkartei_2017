package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;


/**
 * Zeigt die Steuerung für das Spiel an
 * 
 * @author miro albrecht
 *
 */
public class GameOptionView extends FXView
{

	public GameOptionView (String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}

	@Override
	public Parent constructContainer ()
	{
		// Objekte

		AppButton BacktoGameMenu = new AppButton("Zurück");
		BorderPane mainLayout = new BorderPane();

		VBox itemsLayout = new VBox();
		itemsLayout.setAlignment(Pos.BOTTOM_CENTER);

		BacktoGameMenu.setOnAction(e -> getFXController().showView("gameview"));

		itemsLayout.getChildren().addAll(BacktoGameMenu);

		mainLayout.setCenter(itemsLayout);
		mainLayout.setPadding(new Insets(50));
		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
		// Do nothing
	}

}
