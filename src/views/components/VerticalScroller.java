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
		initialize(padding);
	}

	public VerticalScroller (Node node)
	{
		super(node);
		initialize(0);
	}

	private void initialize (double padding)
	{
		setHbarPolicy(ScrollBarPolicy.NEVER);
		setMaxWidth(Globals.defaultScrollerWidth);
		setFitToWidth(true);
		setPadding(new Insets(padding));
	}

	@Override
	public void requestFocus ()
	{
		// do nothing. prevents focus on this scroll pane
	}
}
