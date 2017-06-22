package views;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;
import views.components.MainLayout;

public class TuttoHelpView  extends FXView{
	public TuttoHelpView (String newName, FXController newController)
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

		backToGameMenu.setOnAction(e -> getFXController().showView("gameview"));

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
