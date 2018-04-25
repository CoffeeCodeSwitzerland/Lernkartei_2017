package views;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;
import views.components.MainLayout;


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
		AppButton backToGameMenu = new AppButton("Zurück");

		VBox itemsLayout = new VBox();
		itemsLayout.setAlignment(Pos.BOTTOM_CENTER);

		backToGameMenu.setOnAction(e -> getFXController().showAndTrackView("gameview"));

		itemsLayout.getChildren().addAll(backToGameMenu);

		MainLayout maLay = new MainLayout(itemsLayout);
		return maLay;
	}

	@Override
	public void refreshView ()
	{
		// Does nothing
	}

}
