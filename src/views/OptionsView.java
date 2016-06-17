package views;

import globals.Globals;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import mvc.ModelInterface.Command;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.Alert;
import views.components.BackButton;
import views.components.CheckBoxOption;
import views.components.ControlLayout;
import views.components.MainLayout;
import views.components.VerticalScroller;


/**
 * Optionen
 * 
 * @author miro albrecht
 *
 */
public class OptionsView extends FXView
{
	public OptionsView (String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}

	boolean		resetChange					= false;
	String		lastValidCardLimit;
	TextField	cardLearnLimit;

	// Config Keys
	String		autoWidthKey				= "widthState";
	String		hideImageStacksKey			= "hideImageStacks";
	String		disableTooltippsKey			= "tooltipp";

	// Describe the Options
	String		descTxtCardLimit			= "Anzahl Karten, die auf einmal gelernt werden, limitieren.";
	String		autoWidthDescription		= "Wenn aktiviert, werden alle Buttons dem Grössten angepasst. Sonst orientiert sich die Grösse jeweils am Namen des Buttons.";
	String		disableTooltippsDescription	= "Deaktiviere Tooltipps. Wenn diese Option aktiviert ist, werden keine Tooltipps angezeigt.";
	String		hideImageStacksDescription	= "Zeige nur Stapel, die keine Bilder enthalten";

	// Label the options
	String		autoWidthLabel				= "Grösse Anpassen";
	String		disableToolTippsLabel		= "Tooltipps deaktivieren";
	String		hideImageStacksLabel		= "Nur Stapel ohne Bilder";

	@Override
	public Parent constructContainer ()
	{
		Label cardLimitDescription = new Label(descTxtCardLimit);
		cardLearnLimit = new TextField(getFXController().getModel("config").getDataList("cardLimit").get(0)); // Achtung

		lastValidCardLimit = cardLearnLimit.getText();

		cardLimitDescription.setWrapText(true);
		cardLearnLimit.focusedProperty().addListener(e ->
		{
			if (!cardLearnLimit.isFocused() && !resetChange)
			{
				try
				{
					int cardLimit = Integer.parseInt(cardLearnLimit.getText());
					cardLimit = cardLimit < Globals.minStackPartSize ? Globals.minStackPartSize : cardLimit;
					cardLimit = cardLimit > Globals.maxStackPartSize ? Globals.maxStackPartSize : cardLimit;
					String cardLimitParam = Integer.toString(cardLimit);
					getFXController().getModel("config").doAction(Command.SET, "cardLimit", cardLimitParam);
					resetChange = true;
					cardLearnLimit.setText(cardLimitParam);
					lastValidCardLimit = cardLearnLimit.getText();
					resetChange = false;
				}
				catch (Exception ex)
				{
					Alert.simpleInfoBox("Achtung", "Es muss eine gültige Ganzzahl eingegeben werden!");
					resetChange = true;
					if (lastValidCardLimit == null || lastValidCardLimit.equals(""))
					{
						lastValidCardLimit = Integer.toString(Globals.defaultStackPartSize);
					}
					cardLearnLimit.setText(lastValidCardLimit);
					resetChange = false;
				}
			}
		});

		CheckBoxOption autoWidth = new CheckBoxOption(autoWidthKey, autoWidthDescription, autoWidthLabel, getFXController());

		CheckBoxOption disableTooltipps = new CheckBoxOption(disableTooltippsKey, disableTooltippsDescription, disableToolTippsLabel, getFXController());

		CheckBoxOption hideImageStacks = new CheckBoxOption(hideImageStacksKey, hideImageStacksDescription, hideImageStacksLabel, getFXController());

		BackButton backBtn = new BackButton(getFXController());

		VBox optionsLay = new VBox(20);

		optionsLay.setPadding(new Insets(30));
		optionsLay.setAlignment(Pos.CENTER);

		optionsLay.getChildren().addAll(cardLimitDescription, cardLearnLimit, sepp());
		optionsLay.getChildren().addAll(autoWidth.toNodesWithSepp());
		optionsLay.getChildren().addAll(disableTooltipps.toNodesWithSepp());
		optionsLay.getChildren().addAll(hideImageStacks.toNodes());

		VerticalScroller scroLay = new VerticalScroller(optionsLay);

		ControlLayout conLay = new ControlLayout(backBtn);

		MainLayout mainLay = new MainLayout(scroLay, conLay);

		getFXController().getModel("config").registerView(this);

		return mainLay;
	}

	@Override
	public void refreshView ()
	{
		// do nothing
	}

	private Separator sepp ()
	{
		return new Separator();
	}
}
