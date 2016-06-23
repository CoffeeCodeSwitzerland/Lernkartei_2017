package views;

import java.util.HashMap;

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
	
	enum ConfigKeys
	{
		withState,
		hideImageStacks,
		tooltipp
	}
	
	boolean		isChangeingCardLimitValue	= false;
	
	String		lastValidCardLimit;
	TextField	cardLearnLimit;

	HashMap<ConfigKeys, String> checkboxDescriptions = new HashMap<>();
	HashMap<ConfigKeys, String> checkboxLabels = new HashMap<>();

	String		descTxtCardLimit			= "Anzahl Karten, die auf einmal gelernt werden, limitieren.";

	BackButton	backBtn						= new BackButton(getFXController());

	@Override
	public Parent constructContainer ()
	{
		// TODO : may put the strings in a file
		checkboxDescriptions.put(ConfigKeys.withState, "Wenn aktiviert, werden alle Buttons dem Grössten angepasst. Sonst orientiert sich die Grösse jeweils am Namen des Buttons.");
		checkboxDescriptions.put(ConfigKeys.hideImageStacks, "Zeige nur Stapel, die keine Bilder enthalten");
		checkboxDescriptions.put(ConfigKeys.tooltipp, "Deaktiviere Tooltipps. Wenn diese Option aktiviert ist, werden keine Tooltipps angezeigt.");

		checkboxLabels.put(ConfigKeys.withState, "Grösse Anpassen");
		checkboxLabels.put(ConfigKeys.hideImageStacks, "Nur Stapel ohne Bilder");
		checkboxLabels.put(ConfigKeys.tooltipp, "Tooltipps deaktivieren");
		
		Label cardLimitDescription = new Label(descTxtCardLimit);

		cardLearnLimit = new TextField(getFXController().getModel("config").getDataList("cardLimit").get(0)); // Achtung

		//lastValidCardLimit = cardLearnLimit.getText();

		cardLimitDescription.setWrapText(true);
		cardLearnLimit.focusedProperty().addListener(e ->
		{
			if (!cardLearnLimit.isFocused() && !isChangeingCardLimitValue)
			{
				// TODO : move to model
				try
				{
					int cardLimit = Integer.parseInt(cardLearnLimit.getText());
					cardLimit = cardLimit < Globals.minStackPartSize ? Globals.minStackPartSize : cardLimit;
					cardLimit = cardLimit > Globals.maxStackPartSize ? Globals.maxStackPartSize : cardLimit;
					String cardLimitParam = Integer.toString(cardLimit);
					getFXController().getModel("config").doAction(Command.SET, "cardLimit", cardLimitParam);
					isChangeingCardLimitValue = true;
					cardLearnLimit.setText(cardLimitParam);
					lastValidCardLimit = cardLearnLimit.getText();
					isChangeingCardLimitValue = false;
				}
				catch (Exception ex)
				{
					Alert.simpleInfoBox("Achtung", "Es muss eine gültige Ganzzahl eingegeben werden!");
					isChangeingCardLimitValue = true;
					if (lastValidCardLimit == null || lastValidCardLimit.equals(""))
					{
						lastValidCardLimit = Integer.toString(Globals.defaultStackPartSize);
					}
					cardLearnLimit.setText(lastValidCardLimit);
					isChangeingCardLimitValue = false;
				}
			}
		});
		
		VBox optionsLay = new VBox(20);

		optionsLay.setPadding(new Insets(30));
		optionsLay.setAlignment(Pos.CENTER);

		optionsLay.getChildren().addAll(cardLimitDescription, cardLearnLimit, sepp());
		
		for (ConfigKeys ck : ConfigKeys.values())
		{
			CheckBoxOption cbo = new CheckBoxOption(ck.toString(), checkboxDescriptions.get(ck), checkboxLabels.get(ck), getFXController());
			optionsLay.getChildren().addAll(cbo.toNodesWithSepp());
		}
		
		// Remove the last separator
		optionsLay.getChildren().remove(optionsLay.getChildren().size() - 1);
		
		VerticalScroller scroLay = new VerticalScroller(optionsLay);
		ControlLayout conLay = new ControlLayout(backBtn);
		MainLayout mainLay = new MainLayout(scroLay, conLay);

		getFXController().getModel("config").registerView(this);

		return mainLay;
	}

	@Override
	public void refreshView ()
	{
		backBtn.requestFocus();
	}

	private Separator sepp ()
	{
		return new Separator();
	}
}
