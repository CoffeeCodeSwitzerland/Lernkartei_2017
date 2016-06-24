package views.components;

import globals.ConfigDefaults;
import globals.ConfigDefaults.Configurations;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import mvc.Model;
import mvc.ModelInterface.Command;
import mvc.fx.FXController;

/**
 * 
 * @author miro albrecht
 *
 */
public class CheckBoxOption
{
	public Label description;
	public CheckBox checkBox;
	public boolean oldValue;
	
	public CheckBoxOption (Configurations c, FXController fxc)
	{
		String[] s = ConfigDefaults.getConfig(c);
		CheckBoxOptionInit(c.toString(), s[1], s[0], s[2], fxc);
	}
	
	public CheckBoxOption (String configKey, String description, String cbDescription, FXController controller)
	{
		CheckBoxOptionInit(configKey, description, cbDescription, null, controller);
	}
	
	public void CheckBoxOptionInit (String configKey, String description, String cbDescription, String def, FXController controller)
	{
		this.description = new Label(description);
		this.description.setWrapText(true);
		checkBox = new CheckBox(cbDescription);
		
		oldValue = false;
		
		boolean noEntry = false;

		String dataValue = controller.getModel("config").getString(configKey);
		if (dataValue != null)
		{
			if (dataValue.equals("true"))
			{
				oldValue = true;
			}
		} else { noEntry = true; }

		
		if (noEntry)
		{
			Model m = controller.getModel("config");
			if (m!= null) 
			{
				if (def != null)
				{
					m.doAction(Command.SET, configKey, "false");
				}
				else
				{
					m.doAction(Command.SET, configKey, def);
				}
			} else
			{
				debug.Debugger.out("Model config not found!");
			}
		}
		
		checkBox.setSelected(oldValue);
		
		checkBox.selectedProperty().addListener(e ->
		{
			debug.Debugger.out(configKey + " property has changed");
			String value = checkBox.selectedProperty().getValue() ? "true" : "false";
			controller.getModel("config").doAction(Command.SET, configKey, value);
		});
	}
	
	public Node[] toNodes ()
	{
		return new Node[]{ description, checkBox };
	}
	
	public Node[] toNodesWithSepp ()
	{
		return new Node[]{ description, checkBox, new Separator() };
	}
}
