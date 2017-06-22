package models;

import javax.swing.SwingUtilities;

import Tutto.UsingProcessing;
import javafx.embed.swing.SwingNode;
import mvc.Controller;
import mvc.Model;
import scrollyv8.gamePanel;



public class TuttoModel extends Model
{

	private UsingProcessing			tutto;

	public static Controller	gameController;
	// public GameModel(String myName) {
	// super(myName);
	// }

	public void init ()
	{
		debug.Debugger.out("Game model: starting game...");
		tutto = new UsingProcessing(); // build game
		tutto.setup();
		tutto.draw();
		tutto.initNeuerSpielplan();
		tutto.keyPressed();
		tutto.mousePressed();
		
		
		
	}

	private void createSwingContent (final SwingNode swingNode)
	{
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run ()
			{
				debug.Debugger.out("Game model: starting swing thread...");
				gamePanel.first = true;
			}
		});
	}

	public void dispose ()
	{
		debug.Debugger.out("Game model: disposing game...");
		if (tutto != null)
		{

			System.exit(0);
		}

	}

	

	@Override
	public int doAction (Command command, String... param)
	{
		switch (command)
		{
			case NEW:
				final SwingNode swingNode = new SwingNode();
				createSwingContent(swingNode);
				return 1;
			default:
				return super.doAction(command, param);
		}
	}
}