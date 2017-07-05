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
import javafx.scene.layout.HBox;
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
		super(newController);
		construct(newName);
	}

	// Layouts for dynamic content:
	final VBox	mainScrollBox = new VBox(20); // main pane
	final VBox	optionsLayout = new VBox(20); // lateral pane

	// Layout eLements:
	final VBox  placeholder   = new VBox();

	// Bottom buttons:
	final AppButton printBtn  = new AppButton("Drucken");
	final AppButton backBtn   = new AppButton("Zurück");
	final AppButton newStackBtn = new AppButton("Neuer Stapel");

	// Lateral buttons:
	final Label stackTitle = new Label();
	final AppButton lernen = new AppButton("Lernen");
	final AppButton edit   = new AppButton("Bearbeiten");

	// Main title:
	private Label headLbl = null;

	// Icons:
	final Image     trashImg = new Image("views/pictures/Papierkorb.png");
	final ImageView trashImgView = new ImageView(trashImg);

	// Local Data:
	private String selection = null;
	
	// set new selection:
	private void setSelection (String value)
	{
		if (value == null) value="";
		selection = value;
		stackTitle.setText(selection);
		getFXController().getModel("drucken").setString(selection);
	}
	
	// show next view if data is ok:
	private void tryToswitchToNexctView (String viewName)
	{
		if (selection != null && viewName != null && !selection.equals("")) {
			getFXController().setViewData(viewName, selection);
			getFXController().showView(viewName);
		}
	}
	
	// show next print-view if data is ok:
	private void tryToPrint ()
	{
		if (selection != null && !selection.equals("")) {
			getFXController().getModel("drucken").doAction(Command.NEW);
		}
	}
	
	// fill lateral pane with option buttons:
	private void setUpOptionButtons ()
		{
		optionsLayout.getChildren().clear();
		optionsLayout.setMaxWidth(optionsLayout.getWidth());
		stackTitle.setId("bold");
		stackTitle.setWrapText(true);
		lernen.setOnAction(e -> tryToswitchToNexctView ("prelearn"));
		edit.setOnAction(e -> tryToswitchToNexctView ("simpleeditorview"));
		printBtn.setOnAction(e -> tryToPrint() );
		optionsLayout.getChildren().addAll(stackTitle, lernen, edit, printBtn);
	}
	
	@Override
	public Parent constructContainer ()
	{
		
		// Settings for Layouts and elements:
		mainScrollBox.setAlignment(Pos.CENTER);
		optionsLayout.setAlignment(Pos.CENTER);
		optionsLayout.setMinWidth(200);
		placeholder.setMinWidth(200);

		// Setup scene layout:
		ControlLayout conLay = new ControlLayout(backBtn, newStackBtn, trashImgView);
		MainLayout maLay = new MainLayout(new VerticalScroller(mainScrollBox, 25), null, placeholder, conLay, optionsLayout);
		setUpOptionButtons();
		
		// register button listeners / setup behavior:
		backBtn.setOnAction(e -> getFXController().showView("doorview"));
		newStackBtn.setOnAction(e ->
		  {
			final int choice = Alert.complexChoiceBox("Neuer Stapel", "Was für einen Stapel willst du erstellen?", "Leerer Stapel", "Quizlet");

			switch (choice)
			{
				case 0:
					String stackName = Alert.simpleString("Neuer Stapel", "Wie soll der neue Stapel heissen?");
					if (/* this.getName() != null && */ stackName != null && !stackName.equals(""))
					{
						while (stackName.length() > Globals.maxNameLength)
						{
							stackName = Alert.simpleString("Name zu lang", "Bitte wählen Sie einen kürzeren Namen. (max "+Globals.maxNameLength+" Zeichen)", stackName.substring(0, Globals.maxNameLength), 300);
						}
						getFXController().getModel("stack").doAction(Command.NEW, stackName, getData());
						// TODO Feedback für den User (Fehlermeldungen)
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

		// behavior in main pane
		trashImgView.setOnDragOver(e ->
		{
			if (e.getGestureSource() != trashImgView && e.getDragboard().hasString())
			{
				e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			}

			e.consume();
		});

		trashImgView.setOnDragDropped(e ->
		  {
			Dragboard db = e.getDragboard();
			boolean success = false;
			if (db.hasString())
			{
				boolean canDeleteStack = Alert.ok("Achtung", "Willst du den Stapel '" + db.getString() + "' wirklich endgültig löschen?");
				if (canDeleteStack)
				{
					getFXController().getModel("stack").doAction(Command.DELETE, db.getString(), getData());
					// TODO Feedback für den User (Fehlermeldungen)
					boolean isLabel = optionsLayout.getChildren().get(0).getTypeSelector().equals("Label");
					if (isLabel)
					{
						Label temp = (Label) optionsLayout.getChildren().get(0);
						if (temp.getText().equals(db.getString()))
						{
							optionsLayout.getChildren().clear();
						}
					}
				}
				success = true;
			}

			e.setDropCompleted(success);
			e.consume();
		  });
		
		// register view for notifying:
		getFXController().getModel("stack").registerView(this);
		return maLay;
	}

	@Override
	public void refreshView () // notify...
	{
		if (headLbl == null) {
			headLbl = new Label(getData().toString());
			headLbl.setId("bold"); // css class
			setSelection(null);
		} else if (!getData().toString().equals(selection))  {
			headLbl.setText(getData().toString());
			setSelection(null);
		}
		String localdata = getData();
		mainScrollBox.getChildren().clear();
		if (localdata != null)
		{
			ArrayList<String> setData = getFXController().getModel("stack").getDataList(localdata);
			ArrayList<AppButton> sets = new ArrayList<AppButton>();
			//ArrayList<AppButton> pencils = new ArrayList<>();
			ArrayList<HBox> zeilen = new ArrayList<HBox>();

			boolean allButtonsSameSize = false;
			if (getFXController().getModel("config").getDataList("widthState") != null && getFXController().getModel("config").getDataList("widthState").get(0) != null && getFXController().getModel("config").getDataList("widthState").get(0).equals("true"))
			{
				allButtonsSameSize = true;
			}

			int bigButton = 0;
			for (String s : setData)
			{
				AppButton a = new AppButton(s);
				if (allButtonsSameSize) a.setMinWidth(bigButton);
				a.setId("BoxButtons"); // css calss
				a.setOnAction(e -> setSelection(a.getText()));
					
				a.setOnDragDetected(e -> {
						Dragboard db = a.startDragAndDrop(TransferMode.MOVE);
	
						ClipboardContent content = new ClipboardContent();
						content.putString(a.getText());
						db.setContent(content);
	
						e.consume();
					});
				a.setOnDragDone(event -> {
						if (event.getTransferMode() == TransferMode.MOVE)
						{
							sets.remove(a);
						}
						event.consume();
					});
				AppButton p = new AppButton("\u270E"); // unicode for lower right pencil
				p.setId("small"); // css class
				
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
								
				if (allButtonsSameSize)
				{
					bigButton = bigButton >= a.getText().length() * 6 + 150 ? bigButton : a.getText().length() * 6 + 150;
				} else {
					a.setMinWidth(a.getText().length() * 6 + 150);
				}
				if (selection == null) setSelection(a.getText());
				
				HBox hB = new HBox(10);
				hB.setAlignment(Pos.CENTER);
				hB.getChildren().addAll((AppButton) a, (AppButton) p);
				
				zeilen.add(hB);
				
			}

			mainScrollBox.getChildren().addAll(headLbl);
			mainScrollBox.getChildren().addAll(zeilen);
		}
	}
}