package views.components;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import mvc.Model;
import mvc.ModelInterface.Command;
import mvc.fx.FXController;

public class LabelOption implements OptionInterface
{
	protected Label description;
	protected TextField textField;
	protected String oldValue;
	
	protected InvalidationListener listener;
	
	public LabelOption (String configKey, String description, String def, FXController controller)
	{
		LabelOptionInit(configKey, description, def, controller);
	}
	
	public LabelOption (String configKey, String description, FXController controller)
	{
		LabelOptionInit(configKey, description, null, controller);
	}
	
	private void LabelOptionInit (String configKey, String description, String def, FXController controller)
	{
		this.description = new Label(description);
		this.description.setWrapText(true);
		
		textField = new TextField();
		oldValue = "";
		
		boolean noEntry = false;
		
		String dataValue = controller.getModel("config").getString(configKey);
		if (dataValue != null)
		{
			oldValue = dataValue;
		}
		else { noEntry = true; }
		
		if (noEntry)
		{
			Model m = controller.getModel("config");
			if (m != null) 
			{
				if (def == null)
				{
					m.doAction(Command.SET, configKey, "");
				}
				else
				{
					m.doAction(Command.SET, configKey, def);
					oldValue = def;
				}
			}
			else
			{
				debug.Debugger.out("Model config not found!");
			}
		}
		
		textField.setText(oldValue);
		listener = new InvalidationListener()
		{
			@Override
			public void invalidated (Observable observable)
			{
				String value = textField.getText();
				debug.Debugger.out(configKey + " property has changed to " + value);
				controller.getModel("config").doAction(Command.SET, configKey, value);
			}
		};
		textField.textProperty().addListener(listener);
	}
	
	public Node[] toNodes ()
	{
		return new Node[]{description, textField};
	}

	public Node[] toNodesWithSepp ()
	{
		return new Node[]{description, textField, new Separator()};
	}

}
