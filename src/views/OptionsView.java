package views;

import controls.Globals;
import debug.Debugger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import mvc.fx.FXController;
import mvc.fx.FXView;


/**
 * Optionen
 * 
 * @author miro
 *
 */
public class OptionsView extends FXView
{
	public OptionsView (String newName, FXController newController)
	{
		super(newName, newController);
		construct();
	}
	
	boolean resetChange = false;
	String lastValidCardLimit;
	
	@Override
	public Parent constructContainer ()
	{
		Label cardLimitDescription = new Label("Anzahl Karten, die auf einmal gelernt werden, limitieren.");
		TextField cardLearnLimit = new TextField(getController().getModel("config").getDataList("cardLimit").get(0)); // Achtung
		
		lastValidCardLimit = cardLearnLimit.getText();
		
		cardLimitDescription.setMaxWidth(200);
		cardLimitDescription.setWrapText(true);
		cardLearnLimit.setMaxWidth(200);
		cardLearnLimit.textProperty().addListener(e -> {
			if (!resetChange)
			{
				try
				{
					Integer.parseInt(cardLearnLimit.getText());
					getController().getModel("config").doAction("setValue", "cardLimit" + Globals.SEPARATOR + cardLearnLimit.getText());
					lastValidCardLimit = cardLearnLimit.getText();
				}
				catch (Exception ex)
				{
					Alert.complexChoiceBox("Achtung", "Es muss eine gültige Ganzzahl eingegeben werden!", new String[]{"OK"});
					resetChange = true;
					cardLearnLimit.setText(lastValidCardLimit);
					resetChange = false;
				}
			}
			
		});
		
		
		Label autoWidthDescription = new Label("Wenn aktiviert, werden alle Stapel dem grössten angepasst. Sonst orientiert sich die Grösse jeweils am Namen des Stapels");
		autoWidthDescription.setMaxWidth(200);
		autoWidthDescription.setWrapText(true);
		// Achtung
		boolean oldValue = false;
		if (getFXController().getModel("config").getDataList("widthState") != null)
		{
			String configString = getFXController().getModel("config").getDataList("widthState").get(0);
			if (configString != null)
				oldValue = configString.equals("true") ? true : false;
		}
		CheckBox autoWidth = new CheckBox("Biggy is the ruler");
		autoWidth.setSelected(oldValue);
		autoWidth.selectedProperty().addListener(e -> {
			Debugger.out("Width property has changed");
			String value = autoWidth.selectedProperty().getValue() ? "true" : "000";
			getFXController().getModel("config").doAction("setValue", "widthState" + Globals.SEPARATOR + value);
		});		
		
		AppButton back = new AppButton("_Zurück");

		
		VBox mainLayout = new VBox();
		mainLayout.setPadding(new Insets(10));
		mainLayout.setSpacing(10);
		mainLayout.setAlignment(Pos.CENTER);
		mainLayout.getChildren().addAll(cardLimitDescription, cardLearnLimit, autoWidthDescription, autoWidth, back);

		back.setOnAction(e -> getController().showMainView());
		
		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
	}
}
