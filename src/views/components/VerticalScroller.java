package views.components;

import globals.Globals;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

public class VerticalScroller extends ScrollPane
{
	public VerticalScroller (Node node)
	{
		super(node);
		setHbarPolicy(ScrollBarPolicy.NEVER);
		setMaxWidth(Globals.defaultScrollerWidth);
		setFitToWidth(true);
	}
	
	@Override
	public void requestFocus ()
	{
	}
}
