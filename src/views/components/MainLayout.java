package views.components;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;


/**
 * 
 * @author miro albrecht
 *
 */
public class MainLayout extends BorderPane
{
	public MainLayout (Node center, Node top, Node right, Node bottom, Node left)
	{
		super(center);
		setTop(top);
		setRight(right);
		setBottom(bottom);
		setLeft(left);
		setPadding(new Insets(30));
	}

	public MainLayout (Node center, Node top, Node bottom)
	{
		super(center);
		setTop(top);
		setBottom(bottom);
		setPadding(new Insets(30));
	}

	public MainLayout (Node center, Node bottom)
	{
		super(center);
		setBottom(bottom);
		setPadding(new Insets(30));
	}

	public MainLayout (Node center)
	{
		super(center);
		setPadding(new Insets(30));
	}
}
