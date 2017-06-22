package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mvc.ModelInterface.Command;
import mvc.fx.FXController;
import mvc.fx.FXViewModel;
import views.components.AppButton;
import views.components.ControlLayout;
import views.components.MainLayout;

public class RenameView extends FXViewModel {
	// ArrayList<VBox> cards;
	
	String oldValue;
	String doorname;
	
	public RenameView (String newName, FXController newController)
	{
		// this constructor is the same for all view's
		super(newController);
		construct(newName);
	}

	VBox	renameLayout	= new VBox(10);
	Label	headLbl;
	ScrollPane scroller = new ScrollPane(){
		public void requestFocus(){}
	};
	
	@Override
	public Parent constructContainer ()
	{
		headLbl = new Label("Umbenennen");
		headLbl.setId("bold");

		AppButton backBtn = new AppButton("_Zurück");
		backBtn.setOnAction(e -> getFXController().showView("doorview"));

		BorderPane headLayout = new BorderPane(headLbl);
		headLayout.setPadding(new Insets(0, 0, 25, 0));

		renameLayout.setPadding(new Insets(10));
		renameLayout.setAlignment(Pos.TOP_CENTER);
		
		scroller.setMaxWidth(600);
		scroller.setFitToWidth(true);
		scroller.setPadding(new Insets(25));

		MainLayout maLay = new MainLayout(scroller, headLayout, new ControlLayout(backBtn));
		getFXController().getModel("stack").registerView(this);
		return maLay;
	}
	

	@Override
	public void refreshView ()
	{
		renameLayout.getChildren().clear();
		doorname="";
		if(getFXController().getLastViewName().matches("views.StackView.*"))
		{
			doorname = getMyModel().getDataList("").get(getMyModel().getDataList("").size()-1);
		}
		
			oldValue = getMyModel().getString("");
			TextField front = new TextField(getMyModel().getString(""));
			front.setPromptText("Eingabe erforderlich");

			Button saveBtn = new Button("Speichern"); // \u270d \u2055 \u2699 \u270E
			saveBtn.setId("small");
			saveBtn.setOnAction(e ->
			{		
				saveNameAndExit(oldValue, front.getText(), doorname);
			});
			saveBtn.setOnKeyReleased(e ->
			{
				if (e.getCode() == KeyCode.ENTER)
					saveNameAndExit(oldValue, front.getText(), doorname);
			});
			
			front.setOnKeyReleased(e ->
			{
				if (e.getCode() == KeyCode.ENTER)
				{
					saveNameAndExit(oldValue, front.getText(), doorname);
				}		
			});

			renameLayout.getChildren().addAll(front, saveBtn);
		
		scroller.setContent(renameLayout);
	}
	
	public void saveNameAndExit(String oldName, String newName, String doorname)
	{
		System.out.println("Doorname= " +doorname);
		if(doorname != "")
		{
			int canCreate = getFXController().getModel("stack").doAction(Command.CAN_CREATE, newName);
			if (canCreate == 1)
			{
				System.out.println("<><> oldValue: "+oldValue+ " newName "+newName+" doorname: "+doorname);
				//System.out.println(getFXController().getModel("stack").doAction(Command.UPDATE, oldValue, newName, doorname));
				getFXController().getModel("stack").doAction(Command.UPDATE, oldValue, newName, doorname);
			}
			getFXController().showLastView();
		} else
		{
			int canCreate = getFXController().getModel("door").doAction(Command.CAN_CREATE, newName);
			if (canCreate == 1)
			{
				getFXController().getModel("door").doAction(Command.UPDATE, oldValue, newName);
			}
			getFXController().showLastView();
		}
	}
}
