package views.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;


/**
 * Layout für control-Buttons. Übernimmt das Setzten von Default-Einstellungen.
 * 
 * @author miro albrecht
 *
 */
public class ControlLayout extends HBox
{
	private static final Insets padding = new Insets(20, 0, 0, 0);

	public ControlLayout (Node... nodes)
	{
		setAlignment(Pos.CENTER);
		setPadding(padding);
		setSpacing(20);
		getChildren().addAll(nodes);
	}

	public void add (Node... nodes)
	{
		getChildren().addAll(nodes);
	}

	public void clear ()
	{
		getChildren().clear();
	}
}