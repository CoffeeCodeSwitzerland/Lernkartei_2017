package views.components;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
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
	
	public CheckBoxOption (String configKey, String description, String cbDescription, FXController controller)
	{
		this.description = new Label(description);
		this.description.setWrapText(true);
		checkBox = new CheckBox(cbDescription);
		
		oldValue = false;
		
		boolean noEntry = false;
		ArrayList<String> data = controller.getModel("config").getDataList(configKey);
		if (data != null)
		{
			String dataValue = data.get(0);
			if (dataValue != null)
			{
				if (dataValue.equals("true"))
				{
					oldValue = true;
				}
			} else { noEntry = true; }
		} else { noEntry = true; }
		
		if (noEntry)
		{
//TODO			controller.getModel("config").doAction(Command.SET, configKey, "false");
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
