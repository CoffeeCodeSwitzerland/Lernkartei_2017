package views;

import globals.Globals;
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
import views.components.HomeButton;

public class DatabaseSelectionView extends FXView
{

	public DatabaseSelectionView(String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}
	
	BorderPane bp = new BorderPane();

	VBox AllFields;


	AppButton btnWISSDB;
	AppButton btnOwnDB;
	
	@Override
	public Parent constructContainer()
	{
		bp.setId("loginviewbg");
		
		AllFields = new VBox(50);
		AllFields.setAlignment(Pos.CENTER);
		AllFields.setMaxWidth(300);
		AllFields.setPadding(new Insets(20));

		
		btnWISSDB = new AppButton("WISS-Datenbank");
		
		btnOwnDB = new AppButton("eigene Datenbankverbindung");
				
		AllFields.getChildren().addAll(btnWISSDB, btnOwnDB);
		
		bp.setCenter(AllFields);
		
		btnWISSDB.setOnAction(e -> {
			Globals.mysqldriver = Globals.mysqldriver_wiss;
			Globals.mysqlpath = Globals.mysqlpath_wiss;
			Globals.mysqluser = Globals.mysqluser_wiss;
			Globals.mysqlpassword = Globals.mysqlpassword_wiss;
			Globals.mysqldb = Globals.mysqldb_wiss;
			Globals.user_Table = Globals.user_Table_wiss;
			getFXController().showMainView();
		});
		btnOwnDB.setOnAction(e -> {
			getFXController().showView("owndbconnectionview");
			Globals.mysqldriver = Globals.mysqldriver_wiss;
			Globals.mysqlpath = Globals.mysqlpath_wiss;
			Globals.mysqluser = Globals.mysqluser_wiss;
			Globals.mysqlpassword = Globals.mysqlpassword_wiss;
			Globals.mysqldb = Globals.mysqldb_wiss;
			Globals.user_Table = Globals.user_Table_wiss;
			getFXController().showMainView();
		});
		
		return bp;
	}

	@Override
	public void refreshView()
	{
		bp.setId("loginviewbg");
	}

}
