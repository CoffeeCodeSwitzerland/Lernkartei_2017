package views;

import globals.Globals;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import mvc.ModelInterface.Command;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.Alert;
import views.components.BackButton;
import views.components.ControlLayout;


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

	boolean		resetChange				= false;
	String		lastValidCardLimit;
	TextField	cardLearnLimit;

	// Describe the Options
	String		descTxtCardLimit		= "Anzahl Karten, die auf einmal gelernt werden, limitieren.";
	String		descTxtEnableAutoWidth	= "Wenn aktiviert, werden alle Buttons dem Grössten angepasst. Sonst orientiert sich die Grösse jeweils am Namen des Buttons.";
	String		descTxtDisableTooltipps	= "Deaktiviere Tooltipps. Wenn diese Option aktiviert ist, werden keine Tooltipps angezeigt.";

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

		Label autoWidthDescription = new Label(descTxtEnableAutoWidth);
		autoWidthDescription.setWrapText(true);

		boolean oldValue = false;
		if (getFXController().getModel("config").getDataList("widthState") != null && getFXController().getModel("config").getDataList("widthState").get(0) != null && getFXController().getModel("config").getDataList("widthState").get(0).equals("true"))
		{
			oldValue = true;
		}

		CheckBox autoWidth = new CheckBox("Grösse Anpassen");
		autoWidth.setSelected(oldValue);
		autoWidth.selectedProperty().addListener(e ->
		{
			debug.Debugger.out("Width property has changed");
			String value = autoWidth.selectedProperty().getValue() ? "true" : "000";
			getFXController().getModel("config").doAction(Command.SET, "widthState", value);
		});

		Label disableTooltipDescription = new Label(descTxtDisableTooltipps);
		disableTooltipDescription.setWrapText(true);

		CheckBox disableTooltips = new CheckBox("Tooltipps deaktivieren");
		boolean oldTooltippValue = false;
		if (getFXController().getModel("config").getDataList("tooltipp") != null && getFXController().getModel("config").getDataList("tooltipp").get(0) != null && getFXController().getModel("config").getDataList("tooltipp").get(0).equals("yes"))
		{
			oldTooltippValue = true;
		}
		autoWidth.setSelected(oldTooltippValue);
		autoWidth.selectedProperty().addListener(e ->
		{
			debug.Debugger.out("Tooltipp property has changed");
			String value = autoWidth.selectedProperty().getValue() ? "no" : "yes";
			getFXController().getModel("config").doAction(Command.SET, "tolltipp", value);
		});

		BackButton back = new BackButton(getFXController());

		VBox vLayout = new VBox(20);
		vLayout.setPadding(new Insets(30));
		vLayout.setMaxWidth(400);
		vLayout.setAlignment(Pos.CENTER);
		vLayout.getChildren().addAll(cardLimitDescription, cardLearnLimit, sepp());
		vLayout.getChildren().addAll(autoWidthDescription, autoWidth, sepp());
		vLayout.getChildren().addAll(disableTooltipDescription, disableTooltips);

		ScrollPane sc = new ScrollPane(vLayout);
		sc.setMaxWidth(400);
		sc.setHbarPolicy(ScrollBarPolicy.NEVER);

		BorderPane mainLayout = new BorderPane(sc);
		mainLayout.setPadding(new Insets(30, 15, 15, 15));
		mainLayout.setBottom(new ControlLayout(back));

		getFXController().getModel("config").registerView(this);

		return mainLayout;
	}

	@Override
	public void refreshView ()
	{

	}

	private Separator sepp ()
	{
		return new Separator();
	}
}
