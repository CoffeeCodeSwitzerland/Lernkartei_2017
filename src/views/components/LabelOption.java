package views.components;

import debug.Debugger;
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
		try {
			LabelOptionInit(configKey, description, def, controller);
		} catch (Exception e) {
			Debugger.out("LabelOption Konstructor: did not found a Model named '...'!");
		}
	}
	
	public LabelOption (String configKey, String description, FXController controller)
	{
		try {
			LabelOptionInit(configKey, description, null, controller);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void LabelOptionInit (String configKey, String description, String def, FXController controller) throws Exception
	{
		this.description = new Label(description);
		this.description.setWrapText(true);
		
		textField = new TextField();
		oldValue = "";
		
		boolean noEntry = false;
		String dataValue=null;
		try {
			dataValue = controller.getModel("config").getString(configKey);
		} catch (Exception e) {
			Debugger.out("LabelOption-LabelOptionInit: did not found a Model named 'config'!");
		}
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
				try {
					controller.getModel("config").doAction(Command.SET, configKey, value);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		textField.textProperty().addListener(listener);
	}
	
	public Node[] toNodes ()
	{
		return new Node[]{description, textField};
	}

	public Node[] toNodesWithSeparator ()
	{
		return new Node[]{description, textField, new Separator()};
	}

}
