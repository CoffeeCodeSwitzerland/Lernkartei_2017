package gui;

import java.util.ArrayList;

import application.Constants;
import application.MainController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Gamestartfenster
 * 
 * @author nina egger & miro albrecht
 *
 */
public class BoxView extends View
{
	VBox	boxLayout;
	VBox	options;

	public BoxView (String setName, Stage primaryStage, MainController controller)
	{
		super(setName, primaryStage, controller);

		// Buttons
		AppButton backBtn = new AppButton("Zurück");
		AppButton newBoxBtn = new AppButton("Neue Box");

		Image trashImg = new Image("gui/pictures/Papierkorb.png");
		ImageView trashImgView = new ImageView(trashImg);

		// Layout für Controls
		HBox hBox = new HBox(20);
		hBox.setAlignment(Pos.CENTER);
		hBox.getChildren().addAll(backBtn, newBoxBtn, trashImgView);

		// Layouts für dynamische Inhalte
		boxLayout = new VBox(20);
		boxLayout.setAlignment(Pos.CENTER);
		options = new VBox(20);
		options.setAlignment(Pos.CENTER);

		// Layout für die Scene
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(15));

		borderPane.setCenter(boxLayout);
		borderPane.setRight(options);
		borderPane.setBottom(hBox);

		// Behaviour
		backBtn.setOnAction(e -> getController().show("doorview"));

		newBoxBtn.setOnAction(e ->
		{
			final String boxName = Alert.simpleString("Neue Box", "Wie soll die neue Box heissen?");
			if (setName != null || !boxName.equals(""))
			{
				getController().getMyModel("box").doAction("new",
						getData() + application.Constants.SEPARATOR + boxName);
				// TODO Feedback für den User (Fehlermeldungen)
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
				if (Alert.ok("Achtung", "Willst du die Box '" + db.getString() + "' wirklich löschen?"))
				{
					getController().getMyModel("box").doAction("delete", db.getString()); 
					// TODO Feedback für den User (Fehlermeldungen)
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

		this.setupScene(new Scene(borderPane, Constants.OPTIMAL_WIDTH, Constants.OPTIMAL_HEIGHT));
		getController().getMyModel("box").registerView(this);
	}

	@Override
	public void refreshView ()
	{
		boxLayout.getChildren().clear();

		String data = getData();

		if (data != null)
		{
			ArrayList<String> setData = getController().getMyModel("box").getData(data);
			ArrayList<AppButton> sets = new ArrayList<AppButton>();

			for (String s : setData)
			{
				AppButton a = new AppButton(s);

				sets.add(a);
			}

			for (AppButton a : sets)
			{
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

	private void setOptions (String set)
	{
		options.getChildren().clear();

		Label setTitle = new Label(set);
		AppButton lernen = new AppButton("Lernen");
		AppButton edit = new AppButton("Bearbeiten");

		setTitle.setId("bold");
		lernen.setOnAction(e ->
		{
			System.out.println("Lerne " + set);
		});
		edit.setOnAction(e -> System.out.println(set + " bearbeiten"));

		options.getChildren().addAll(setTitle, lernen, edit);
	}
}
