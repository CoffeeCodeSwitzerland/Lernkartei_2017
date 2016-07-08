package views.components;

import mvc.ModelInterface.Command;
import mvc.fx.FXController;

public class NumberLabelOption extends LabelOption
{
	int lastValidNr;
	boolean blockToWriteValue = false;
	
	public NumberLabelOption (String configKey, String description, int def, int max, int min, FXController controller)
	{
		super(configKey, description, Integer.toString(def), controller);
		updateListener(configKey, def, max, min, controller);
	}
	
	public NumberLabelOption (String configKey, String description, String def, int max, int min, FXController controller)
	{
		super(configKey, description, Integer.toString(validateNumber(def)), controller);
		updateListener(configKey, validateNumber(def), max, min, controller);
	}
	
	private void updateListener (String config, int def, int max, int min, FXController c)
	{
		lastValidNr = def;
		textField.textProperty().removeListener(listener);
		textField.focusedProperty().addListener(e -> 
		{
			if (!blockToWriteValue && !textField.isFocused())
			{
				String value = textField.getText();
				int i = validateNumber(value, lastValidNr);
				i = i > max ? max : i;
				i = i < min ? min : i;
				lastValidNr = i;
				value = Integer.toString(i);
				blockToWriteValue = true;
				textField.setText(value);
				blockToWriteValue = false;
				debug.Debugger.out(config + " property has changed to " + value);
				c.getModel("config").doAction(Command.SET, config, value);
			}
		});
	}
	
	private static int validateNumber (String nr)
	{
		return validateNumber(nr, 0);
	}
	
	private static int validateNumber (String nr, int def)
	{
		int i;
		try
		{
			i = Integer.parseInt(nr);
		}
		catch (Exception e)
		{
			Alert.simpleInfoBox("Achtung", "Es muss eine gültige Ganzzahl eingegeben werden!");
			i = def;
		}
		return i;
	}

}
