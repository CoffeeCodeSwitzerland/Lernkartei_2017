package models;

import javax.swing.SwingUtilities;

import javafx.embed.swing.SwingNode;
import mvc.Controller;
import mvc.Model;
import mvc.View;
import scrollyv8.ScrollyV8;
import scrollyv8.gamePanel;


public class GameModel extends Model
{

	private ScrollyV8			mf;

	public static Controller	gameController;
	// public GameModel(String myName) {
	// super(myName);
	// }

	public void init ()
	{
		debug.Debugger.out("Game model: starting game...");
		mf = new ScrollyV8(); // build game
		mf.init();
	}

	private void createSwingContent (final SwingNode swingNode)
	{
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run ()
			{
				debug.Debugger.out("Game model: starting swing thread...");
				if (mf == null) init();
				mf.setVisible(true);
				gamePanel.first = true;
			}
		});
	}

	public void dispose ()
	{
		debug.Debugger.out("Game model: disposing game...");
		if (mf != null)
		{
			mf.setVisible(false);
			mf.dispose();
			mf = null;
			System.exit(0);
		}

	}

	public void registerView (View theView, Controller c)
	{
		gameController = c;
		super.registerView(theView);
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