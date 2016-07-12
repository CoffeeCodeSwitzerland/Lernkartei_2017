package views.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;


/**
 * Setzt Default-Einstellungen.
 * 
 * @author miro albrecht
 *
 */
public class StandardVBox extends VBox
{
	private static final Insets padding = new Insets(30);

	public StandardVBox ()
	{
		super(20);
		setPadding(padding);
		setAlignment(Pos.CENTER);
	}

	public void add (Node... nodes)
	{
		getChildren().addAll(nodes);
	}
}
