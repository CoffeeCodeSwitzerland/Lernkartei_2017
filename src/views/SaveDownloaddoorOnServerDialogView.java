package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;
import views.components.BackButton;
import views.components.HomeButton;

public class SaveDownloaddoorOnServerDialogView extends FXView
{

	public SaveDownloaddoorOnServerDialogView(String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}

	BorderPane bp = new BorderPane();

	VBox AllFields;
	HBox bottom;


	AppButton btnOwnDMO;
	AppButton btnForeignDMO;
	BackButton back;

	HomeButton home;

	@Override
	public Parent constructContainer()
	{
		bp.setId("loginviewbg");

		bottom = new HBox(50);
		bottom.setAlignment(Pos.CENTER);
		bottom.setMaxWidth(300);
		bottom.setPadding(new Insets(20,50,150,280));

		AllFields = new VBox(50);
		AllFields.setAlignment(Pos.CENTER);
		AllFields.setMaxWidth(300);
		AllFields.setPadding(new Insets(100,20,20,20));


		btnOwnDMO = new AppButton("Eigenes DMO");
		btnForeignDMO = new AppButton("Fremdes DMO");
		back = new BackButton(getFXController(), "Zur�ck");

		home = new HomeButton(getFXController());

		AllFields.getChildren().addAll(btnOwnDMO, btnForeignDMO, back, home);
		bottom.getChildren().addAll(back, home);

		bp.setCenter(AllFields);
		bp.setBottom(bottom);

		btnOwnDMO.setOnAction(e -> getFXController().showView("savedownloadstackowndmoview"));
		btnForeignDMO.setOnAction(e -> getFXController().showView("savedownloadstackforeigndmoview"));
		back.setOnAction(e -> getFXController().showView("doorsavepathselectionview"));

		return bp;
	}

	@Override
	public void refreshView()
	{
		bp.setId("loginviewbg");
	}

}
