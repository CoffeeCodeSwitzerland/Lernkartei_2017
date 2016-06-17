package views;

import java.util.ArrayList;

import globals.Globals;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.ModelInterface.Command;
import mvc.fx.FXController;
import mvc.fx.FXViewModel;
import views.components.AppButton;
import views.components.ControlLayout;
import views.components.MainLayout;


public class SimpleEditorView extends FXViewModel
{
	// ArrayList<VBox> cards;

	public SimpleEditorView (String newName, FXController newController)
	{
		// this constructor is the same for all view's
		super(newController);
		construct(newName);
	}

	VBox	editLayout	= new VBox(10);
	Label	headLbl;
	ScrollPane scroller = new ScrollPane(){
		public void requestFocus(){}
	};
	
	boolean justCreatedCard = false;

	@Override
	public Parent constructContainer ()
	{
		headLbl = new Label("");
		headLbl.setId("bold");

		AppButton backBtn = new AppButton("_Zurück");
		backBtn.setOnAction(e -> getFXController().showView("stack"));

		//Info Button
		AppButton infobtn = new AppButton("Hilfe");
		infobtn.setOnAction(e ->
		getFXController().showView("bbcodeinfo"));
		
		BorderPane headLayout = new BorderPane(headLbl);
		headLayout.setPadding(new Insets(0, 0, 25, 0));

		editLayout.setPadding(new Insets(10));
		editLayout.setAlignment(Pos.TOP_CENTER);
		
		scroller.setMaxWidth(600);
		scroller.setFitToWidth(true);
		scroller.setPadding(new Insets(25));
		

		MainLayout maLay = new MainLayout(scroller, headLayout, new ControlLayout(backBtn, infobtn));
		getFXController().getModel("cards").registerView(this);
		return maLay;
	}
	
	AnimationTimer setVPos = new AnimationTimer() {
		
		@Override
		public void handle (long now)
		{
			if (justCreatedCard)
			{
				justCreatedCard = false;
			}
			else
			{
				scroller.setVvalue(scroller.getVmax());
				setVPos.stop();
			}
		}
	};

	@Override
	public void refreshView ()
	{
		editLayout.getChildren().clear();

		String data = getData();

		if (data != null)
		{
			headLbl.setText(data + " - " + getFXController().getViewData("stack"));

			ArrayList<String> cardStrings = getFXController().getModel("cards").getDataList(data);
			ArrayList<HBox> cards = new ArrayList<>();
			debug.Debugger.out("" + cardStrings.size());
			for (String s : cardStrings)
			{
				String[] cardSides = s.split(Globals.SEPARATOR);
				TextField front = new TextField(cardSides[1]);
				front.setPromptText("Eingabe erforderlich");
				TextField back = new TextField(cardSides[2]);
				back.setPromptText("Eingabe erforderlich");

				front.focusedProperty().addListener(e ->
				{
					if (!front.isFocused())
					{
						if (back.getText() != null && !back.getText().equals("") && front.getText() != null
								&& !front.getText().equals(""))
						{
							getFXController().getModel("cards").doAction(Command.UPDATE, cardSides[0], front.getText(), back.getText());
						}
					}
				});

				back.focusedProperty().addListener(e ->
				{
					if (!back.isFocused())
					{
						if (back.getText() != null && !back.getText().equals("") && front.getText() != null
								&& !front.getText().equals(""))
						{
							getFXController().getModel("cards").doAction(Command.UPDATE, cardSides[0], front.getText(), back.getText());
						}
					}
				});

				Button delete  = new Button("X");
				Button editBtn = new Button("\u270E"); // \u270d \u2055 \u2699 \u270E
				delete.setId("small");
				editBtn.setId("small");
				delete.setOnAction(e -> getFXController().getModel("cards").doAction(Command.DELETE, cardSides[0]));
				delete.setOnKeyReleased(e ->
				{
					if (e.getCode() == KeyCode.ENTER)
						delete.fire();
				});
				editBtn.setOnAction(e ->
				{
					getFXController().setViewData("editorview",cardSides[0] + Globals.SEPARATOR + front.getText() + Globals.SEPARATOR + back.getText());
					getFXController().showView("editorview");
				});
				editBtn.setOnKeyReleased(e ->
				{
					if (e.getCode() == KeyCode.ENTER)
						editBtn.fire();
				});
				HBox v = new HBox(8);
				v.setAlignment(Pos.CENTER);
				v.getChildren().addAll(front, back, delete, editBtn);
				cards.add(v);
			}

			TextField front = new TextField();
			TextField back = new TextField();
			
			
			
			//Button editBtn = new Button("\u270E"); // \u270d \u2055 \u2699 \u270E
			
			Button addBtn = new Button("\u2713");
			addBtn.setId("small");
			addBtn.setOnAction(e ->
			{
				justCreatedCard = true;
				if (back.getText() != null && !back.getText().equals("") && front.getText() != null
						&& !front.getText().equals(""))
				{
					getFXController().getModel("cards").doAction(Command.NEW, front.getText(), back.getText(), data);
				}
			});
			addBtn.setOnKeyReleased(e ->
			{
				if (e.getCode() == KeyCode.ENTER)
					addBtn.fire();
			});
			front.setOnKeyReleased(e ->
			{
				if (e.getCode() == KeyCode.ENTER)
					addBtn.fire();
			});
			back.setOnKeyReleased(e ->
			{
				if (e.getCode() == KeyCode.ENTER)
					addBtn.fire();
			});

			Button placeholder = new Button("_");
			placeholder.setVisible(false);
			placeholder.setId("small");
			
			HBox v = new HBox(8);

			v.setAlignment(Pos.CENTER);
			v.getChildren().addAll(front, back, addBtn, placeholder);
			cards.add(v);

			editLayout.getChildren().addAll(cards);
			
			front.requestFocus();
		}
		
		scroller.setContent(editLayout);
		
		if (justCreatedCard) {setVPos.start();}
	}
}
