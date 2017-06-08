package views;

import java.util.ArrayList;

import globals.Globals;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import mvc.ModelInterface.Command;
import mvc.fx.FXController;
import mvc.fx.FXViewModel;
import views.components.Alert;
import views.components.AppButton;
import views.components.ControlLayout;
import views.components.MainLayout;
import views.components.VerticalScroller;


/**
 * Zeigt alle Stapel an. Navigation zu Lernen und SimpleEditor Zur�ck zu
 * DoorView
 * 
 * @author nina egger & miro albrecht
 *
 */
public class StackView extends FXViewModel
{
	public StackView (String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}

	VBox	boxLayout;
	VBox	options;
	
	AppButton printBtn = new AppButton("Drucken");

	@Override
	public Parent constructContainer ()
	{
		// Layouts f�r dynamische Inhalte
		boxLayout = new VBox(20);
		boxLayout.setAlignment(Pos.CENTER);
		
		VerticalScroller scroLay = new VerticalScroller(boxLayout, 25);

		options = new VBox(20);
		options.setAlignment(Pos.CENTER);
		options.setMinWidth(200);

		VBox placeholder = new VBox();
		placeholder.setMinWidth(200);

		// Buttons
		AppButton backBtn = new AppButton("Zur�ck");
		AppButton newStackBtn = new AppButton("Neuer Stapel");
		
		printBtn.setOnAction(e ->  getFXController().getModel("drucken").doAction(Command.NEW));
		

		Image trashImg = new Image("views/pictures/Papierkorb.png");
		ImageView trashImgView = new ImageView(trashImg);

		ControlLayout conLay = new ControlLayout(backBtn, newStackBtn, trashImgView);

		// Layout f�r die Scene
		MainLayout maLay = new MainLayout(scroLay, null, placeholder, conLay, options);

		// Behaviour
		backBtn.setOnAction(event -> getFXController().showView("doorview"));

		newStackBtn.setOnAction(e ->
		{
			final int choice = Alert.complexChoiceBox("Neuer Stapel", "Was f�r einen Stapel willst du erstellen?", "Leerer Stapel", "Quizlet");

			switch (choice)
			{
				case 0:
					String stackName = Alert.simpleString("Neuer Stapel", "Wie soll der neue Stapel heissen?");
					if (/* this.getName() != null && */ stackName != null && !stackName.equals(""))
					{
						while (stackName.length() > Globals.maxNameLength)
						{
							stackName = Alert.simpleString("Name zu lang", "Bitte w�hlen Sie einen k�rzeren Namen. (max "+Globals.maxNameLength+" Zeichen)", stackName.substring(0, Globals.maxNameLength), 300);
						}
						getFXController().getModel("stack").doAction(Command.NEW, stackName, getData());
						// TODO Feedback f�r den User (Fehlermeldungen)
					}
					break;
				case 1:
					getFXController().setViewData("quizlet", getData());
					getFXController().showView("quizlet");
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
				boolean canDeleteStack = Alert.ok("Achtung", "Willst du den Stapel '" + db.getString() + "' wirklich endg�ltig l�schen?");
				if (canDeleteStack)
				{
					getFXController().getModel("stack").doAction(Command.DELETE, db.getString(), getData());
					// TODO Feedback f�r den User (Fehlermeldungen)
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
		getFXController().getModel("stack").registerView(this);
		return maLay;
	}

	@Override
	public void refreshView ()
	{
		Label headLbl = new Label(getData().toString());
		headLbl.setId("bold");
		
		boxLayout.getChildren().clear();
		options.getChildren().clear();
		options.setMaxWidth(options.getWidth());

		String localdata = getData();

		if (localdata != null)
		{
			ArrayList<String> setData = getFXController().getModel("stack").getDataList(localdata);
			ArrayList<AppButton> sets = new ArrayList<AppButton>();
			ArrayList<AppButton> pencils = new ArrayList<>();

			boolean allButtonsSameSize = false;
			if (getFXController().getModel("config").getDataList("widthState") != null && getFXController().getModel("config").getDataList("widthState").get(0) != null && getFXController().getModel("config").getDataList("widthState").get(0).equals("true"))
			{
				allButtonsSameSize = true;
			}

			int bigButton = 0;
			for (String s : setData)
			{
				AppButton a = new AppButton(s);
				
				AppButton p = new AppButton("\u270E");
				
				p.setId("small");
				
				p.setOnAction(e -> {
				ArrayList<String> data = new ArrayList<>();
				data.add(s);
				data.add(headLbl.getText());
				getFXController().addViewData("rename", data);
				getFXController().setViewData("rename", s);
				getFXController().showView("rename");
				});
				p.setOnKeyReleased(e -> {
				if (e.getCode() == KeyCode.ENTER)
				p.fire();
				});
				
				pencils.add((AppButton) p);
				
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

			boxLayout.getChildren().addAll(headLbl);
			boxLayout.getChildren().addAll(sets);
			boxLayout.getChildren().addAll(pencils);
		}
	}

	// F�llt Pane mit den Stapeloptionen
	private void setOptions (String stack)
	{
		options.getChildren().clear();

		Label stackTitle = new Label(stack);
		AppButton lernen = new AppButton("Lernen");
		AppButton edit = new AppButton("Bearbeiten");
		// CheckBox switcher = new CheckBox("Seite 2 zuerst");

		stackTitle.setId("bold");
		stackTitle.setWrapText(true);
		lernen.setOnAction(e ->
		{
			getFXController().setViewData("prelearn", stack);
			getFXController().showView("prelearn");
		});
		edit.setOnAction(e ->
		{
			getFXController().setViewData("simpleeditorview", stack);
			getFXController().showView("simpleeditorview");
		});
		
		/*
		 * switcher.setSelected(getFXController().getModel("switcher").doAction(
		 * "check", stack) == 1 ? true : false);
		 * switcher.selectedProperty().addListener(event -> { if
		 * (switcher.isSelected()) {
		 * getFXController().getModel("switcher").doAction("new", stack); } else
		 * { getFXController().getModel("switcher").doAction("delete", stack); }
		 * });
		 */
									//setString �bergibt string und kann durch getString wieder geholt werden
		getFXController().getModel("drucken").setString(stack);
		
		options.getChildren().addAll(stackTitle, lernen, edit, printBtn/* , switcher */);
	}
}