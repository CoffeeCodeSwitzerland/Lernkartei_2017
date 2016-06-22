package views.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * Um ein paar Zeilen Code zu sparen, habe ich eine VBox erstellt, die gleich funktioniert, wie das ControlLayout von Miro
 * 
 * @author Joel Häberli
 *
 */
public class ContainerLayout extends VBox
{	
	public ContainerLayout(Node... nodes)
	{
		setAlignment(Pos.CENTER);
		setPadding(new Insets(25));
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
