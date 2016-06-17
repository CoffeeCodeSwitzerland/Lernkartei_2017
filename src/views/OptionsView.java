package views;

import java.util.ArrayList;

import globals.Globals;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import mvc.ModelInterface.Command;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.Alert;
import views.components.BackButton;
import views.components.ControlLayout;
import views.components.HoverButton;
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

	// Describe the Options
	String		descTxtCardLimit			= "Anzahl Karten, die auf einmal gelernt werden, limitieren.";
	String		descTxtEnableAutoWidth		= "Wenn aktiviert, werden alle Buttons dem Grössten angepasst. Sonst orientiert sich die Grösse jeweils am Namen des Buttons.";
	String		descTxtDisableTooltipps		= "Deaktiviere Tooltipps. Wenn diese Option aktiviert ist, werden keine Tooltipps angezeigt.";
	String		descTxtDontShowImageStacks	= "Zeige nur Stapel, die keine Bilder enthalten";

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

		ArrayList<String> data = getFXController().getModel("config").getDataList("tooltipp");
		if (data != null)
		{
			String dataValue = data.get(0);
			if (dataValue != null)
			{
				if (dataValue.equals("no"))
				{
					oldTooltippValue = true;
				}
			}
		}
		disableTooltips.setSelected(oldTooltippValue);
		disableTooltips.selectedProperty().addListener(e ->
		{
			debug.Debugger.out("Tooltipp property has changed");
			String value = disableTooltips.selectedProperty().getValue() ? "no" : "yes";
			getFXController().getModel("config").doAction(Command.SET, "tooltipp", value);
			HoverButton.clearSettings(); // TODO mvc einhalten
		});

		Label hideImgStacksDescription = new Label(descTxtDontShowImageStacks);
		CheckBox hideImgStacks = new CheckBox("Nur Stapel ohne Bilder");
		
		boolean oldshowImgStacksValue = false;
		data = getFXController().getModel("config").getDataList("hideImageStacks");
		if (data != null)
		{
			String dataValue = data.get(0);
			if (dataValue != null)
			{
				if (dataValue.equals("true"))
				{
					oldshowImgStacksValue = true;
				}
			}
		}
		else
		{
			getFXController().getModel("config").doAction(Command.SET, "hideImageStacks", "false");
		}
		
		hideImgStacks.setSelected(oldshowImgStacksValue);
		hideImgStacks.selectedProperty().addListener(e ->
		{
			debug.Debugger.out("HideImageStacks property has changed");
			String value = hideImgStacks.selectedProperty().getValue() ? "true" : "false";
			getFXController().getModel("config").doAction(Command.SET, "hideImageStacks", value);
		});
		
		
		BackButton backBtn = new BackButton(getFXController());

		VBox optionsLay = new VBox(20);

		optionsLay.setPadding(new Insets(30));
		optionsLay.setAlignment(Pos.CENTER);

		optionsLay.getChildren().addAll(cardLimitDescription, cardLearnLimit, sepp());
		optionsLay.getChildren().addAll(autoWidthDescription, autoWidth, sepp());
		optionsLay.getChildren().addAll(disableTooltipDescription, disableTooltips, sepp());
		optionsLay.getChildren().addAll(hideImgStacksDescription, hideImgStacks);

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
