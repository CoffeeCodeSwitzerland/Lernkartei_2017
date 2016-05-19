package views;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
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


/**
 * Zeigt alle Stapel an.
 * Navigation zu Lernen und SimpleEditor
 * Zur�ck zu DoorView
 * 
 * @author nina egger & miro albrecht
 *
 */
public class StackView extends FXViewModel
{
	public StackView (String newName, FXController newController)
	{
		// this constructor is the same for all view's
		super(newName, newController);
		construct();
	}

	VBox	boxLayout;
	VBox	options;

	@Override
	public Parent constructContainer ()
	{
		// Layouts f�r dynamische Inhalte
		boxLayout = new VBox(20);
		boxLayout.setAlignment(Pos.CENTER);

		options = new VBox(20);
		options.setAlignment(Pos.CENTER);
		options.setMinWidth(200);

		VBox placeholder = new VBox();
		placeholder.setMinWidth(200);

		// Buttons
		AppButton backBtn = new AppButton("Zur�ck");
		AppButton newBoxBtn = new AppButton("Neuer Stapel");

		Image trashImg = new Image("views/pictures/Papierkorb.png");
		ImageView trashImgView = new ImageView(trashImg);

		// Layout f�r Controls
		HBox hBox = new HBox(20);
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(backBtn, newBoxBtn, trashImgView);

		// Layout f�r die Scene
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(15));

		borderPane.setCenter(boxLayout);
		borderPane.setLeft(options);
		borderPane.setRight(placeholder);
		borderPane.setBottom(hBox);

		// Behaviour
		backBtn.setOnAction(e -> getController().showView("doorview"));

		newBoxBtn.setOnAction(e ->
		{
			final int choice = Alert.complexChoiceBox("Neuer Stapel", "Was f�r einen Stapel willst du erstellen?", "Leerer Stapel",
					"Quizlet");

			switch (choice)
			{
				case 0:
					final String stackName = Alert.simpleString("Neuer Stapel", "Wie soll der neue Stapel heissen?");
					if (this.getName() != null && stackName != null)
					{
						getController().getModel("stack").doAction("new",
								getData() + controls.Globals.SEPARATOR + stackName);
						// TODO Feedback f�r den User (Fehlermeldungen)
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
				if (Alert.ok("Achtung", "Willst du den Stapel '" + db.getString() + "' wirklich endg�ltig l�schen?"))
				{
					getController().getModel("stack").doAction("delete", db.getString());
					// TODO Feedback f�r den User (Fehlermeldungen)
					if (options.getChildren().get(0).getTypeSelector().equals("Label"))
					{
						Label temp = (Label) options.getChildren().get(0);
						if (temp.getText().equals(db.getString()))
							options.getChildren().clear();
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
			if (getFXController().getModel("config").getDataList("widthState") != null
					&& getFXController().getModel("config").getDataList("widthState").get(0) != null
					&& getFXController().getModel("config").getDataList("widthState").get(0).equals("true"))
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

	// F�llt Pane mit den Stapeloptionen
	private void setOptions (String stack)
	{
		options.getChildren().clear();

		Label stackTitle = new Label(stack);
		AppButton lernen = new AppButton("Lernen");
		AppButton edit = new AppButton("Bearbeiten");

		stackTitle.setId("bold");
		lernen.setOnAction(e ->
		{
			getController().setViewData("prelearn",stack);
			getController().showView("prelearn");
		});
		edit.setOnAction(e ->
		{
			getController().setViewData("simpleeditorview",stack);
			getController().showView("simpleeditorview");
		});

		options.getChildren().addAll(stackTitle, lernen, edit);
	}
}