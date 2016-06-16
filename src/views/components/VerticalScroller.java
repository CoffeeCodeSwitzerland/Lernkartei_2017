package views.components;

import globals.Globals;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;


/**
 * 
 * @author miro albrecht
 *
 */
public class VerticalScroller extends ScrollPane
{
	public VerticalScroller (Node node, double padding)
	{
		super(node);
		setHbarPolicy(ScrollBarPolicy.NEVER);
		setMaxWidth(Globals.defaultScrollerWidth);
		setFitToWidth(true);
		setPadding(new Insets(padding));
	}

	public VerticalScroller (Node node)
	{
		super(node);
		setHbarPolicy(ScrollBarPolicy.NEVER);
		setMaxWidth(Globals.defaultScrollerWidth);
		setFitToWidth(true);
		setPadding(new Insets(0));
	}

	@Override
	public void requestFocus ()
	{
	}
}
