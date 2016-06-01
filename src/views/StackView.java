package views;

import java.util.ArrayList;

import globals.Globals;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXViewModel;
import views.components.Alert;
import views.components.AppButton;


/**
 * Zeigt alle Stapel an. Navigation zu Lernen und SimpleEditor Zurück zu
 * DoorView
 * 
 * @author nina egger & miro albrecht
 *
 */
public class StackView extends FXViewModel
{
	public StackView (String newName, FXController newController)
	{
		// this constructor is the same for all view's
		super(newController);
		construct(newName);
	}

	VBox	boxLayout;
	VBox	options;

	@Override
	public Parent constructContainer ()
	{
		// Layouts für dynamische Inhalte
		boxLayout = new VBox(20);
		boxLayout.setAlignment(Pos.CENTER);
		ScrollPane scStacks = new ScrollPane(boxLayout);

		options = new VBox(20);
		options.setAlignment(Pos.CENTER);
		options.setMinWidth(200);

		VBox placeholder = new VBox();
		placeholder.setMinWidth(200);

		// Buttons
		AppButton backBtn = new AppButton("Zurück");
		AppButton newStackBtn = new AppButton("Neuer Stapel");
		AppButton renameBtn = new AppButton("Umbennen");

		Image trashImg = new Image("views/pictures/Papierkorb.png");
		ImageView trashImgView = new ImageView(trashImg);

		// Layout für Controls
		HBox hBox = new HBox(20);
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(backBtn, newStackBtn, renameBtn, trashImgView);

		// Layout für die Scene
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(15));

		borderPane.setCenter(scStacks);
		borderPane.setLeft(options);
		borderPane.setRight(placeholder);
		borderPane.setBottom(hBox);

		// Behaviour
		backBtn.setOnAction(e -> getController().showView("doorview"));

		newStackBtn.setOnAction(e ->
		{
			final int choice = Alert.complexChoiceBox("Neuer Stapel", "Was für einen Stapel willst du erstellen?", "Leerer Stapel", "Quizlet");

			switch (choice)
			{
				case 0:
					final String stackName = Alert.simpleString("Neuer Stapel", "Wie soll der neue Stapel heissen?");
					if (/* this.getName() != null && */ stackName != null)
					{
						getController().getModel("stack").doAction("new", getData() + globals.Globals.SEPARATOR + stackName);
						// TODO Feedback für den User (Fehlermeldungen)
					}
					break;
				case 1:
					getController().setViewData("quizlet", getData());
					getController().showView("quizlet");
					break;
				default:
					break;
			}

		});

		renameBtn.setOnAction(e ->
		{
			getFXController().setViewData("rename", "stack" + Globals.SEPARATOR + getData());
			getFXController().showView("rename");
		});

		trashImgView.setOnDragOver(e ->
		{
			if (e.getGestureSource() != trashImgView && e.getDragboard().hasString())
			{
				e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			}

			e.consume();
		});

		trashImgView.setOnDragDropped(event ->
		{
			Dragboard db = event.getDragboard();
			boolean success = false;
			if (db.hasString())
			{
				boolean canDeleteStack = Alert.ok("Achtung", "Willst du den Stapel '" + db.getString() + "' wirklich endgültig löschen?");
				if (canDeleteStack)
				{
					getController().getModel("stack").doAction("delete", db.getString());
					// TODO Feedback für den User (Fehlermeldungen)
					boolean isLabel = options.getChildren().get(0).getTypeSelector().equals("Label");
					if (isLabel)
					{
						Label temp = (Label) options.getChildren().get(0);
						if (temp.getText().equals(db.getString()))
						{
							options.getChildren().clear();
						}
					}
				}
				success = true;
			}

			event.setDropCompleted(success);
			event.consume();
		});
		getController().getModel("stack").registerView(this);
		return borderPane;
	}

	@Override
	public void refreshView ()
	{
		boxLayout.getChildren().clear();
		options.getChildren().clear();

		String localdata = getData();

		if (localdata != null)
		{
			ArrayList<String> setData = getController().getModel("stack").getDataList(localdata);
			ArrayList<AppButton> sets = new ArrayList<AppButton>();

			boolean allButtonsSameSize = false;
			if (getFXController().getModel("config").getDataList("widthState") != null && getFXController().getModel("config").getDataList("widthState").get(0) != null && getFXController().getModel("config").getDataList("widthState").get(0).equals("true"))
			{
				allButtonsSameSize = true;
			}

			int bigButton = 0;
			for (String s : setData)
			{
				AppButton a = new AppButton(s);
				if (allButtonsSameSize)
				{
					bigButton = bigButton >= a.getText().length() * 6 + 150 ? bigButton : a.getText().length() * 6 + 150;
				}
				else
				{
					a.setMinWidth(a.getText().length() * 6 + 150);
				}

				sets.add(a);
			}

			for (AppButton a : sets)
			{
				if (allButtonsSameSize)
				{
					a.setMinWidth(bigButton);
				}

				a.setId("BoxButtons");
				a.setOnAction(e ->

				{
					setOptions(a.getText());
				});
				a.setOnDragDetected(e ->
				{
					Dragboard db = a.startDragAndDrop(TransferMode.MOVE);

					ClipboardContent content = new ClipboardContent();
					content.putString(a.getText());
					db.setContent(content);

					e.consume();
				});
				a.setOnDragDone(event ->
				{
					if (event.getTransferMode() == TransferMode.MOVE)
					{
						sets.remove(a);
					}
					event.consume();
				});
			}

			boxLayout.getChildren().addAll(sets);
		}
	}

	// Füllt Pane mit den Stapeloptionen
	private void setOptions (String stack)
	{
		options.getChildren().clear();

		Label stackTitle = new Label(stack);
		AppButton lernen = new AppButton("Lernen");
		AppButton edit = new AppButton("Bearbeiten");
		//CheckBox switcher = new CheckBox("Seite 2 zuerst");

		stackTitle.setId("bold");
		lernen.setOnAction(e ->
		{
			getController().setViewData("prelearn", stack);
			getController().showView("prelearn");
		});
		edit.setOnAction(e ->
		{
			getController().setViewData("simpleeditorview", stack);
			getController().showView("simpleeditorview");
		});
		/*switcher.setSelected(getFXController().getModel("switcher").doAction("check", stack) == 1 ? true : false);
		switcher.selectedProperty().addListener(event ->
		{
			if (switcher.isSelected())
			{
				getFXController().getModel("switcher").doAction("new", stack);
			}
			else
			{
				getFXController().getModel("switcher").doAction("delete", stack);
			}
		});*/

		options.getChildren().addAll(stackTitle, lernen, edit/*, switcher*/);
	}
}