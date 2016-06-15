package views.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;


/**
 * Layout für control buttons. Übernimmt das Setzten von Defaulteinstellungen.
 * 
 * @author miro albrecht
 *
 */
public class ControlLayout extends HBox
{
	public ControlLayout (Node... nodes)
	{
		setAlignment(Pos.CENTER);
		setPadding(new Insets(20, 0, 0, 0));
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