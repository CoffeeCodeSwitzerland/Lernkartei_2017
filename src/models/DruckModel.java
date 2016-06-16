package models;

import javax.swing.SwingUtilities;

import debug.Supervisor;
import javafx.embed.swing.SwingNode;
import mvc.Model;
import printing.Printer;

public class DruckModel extends Model
{
	private void createSwingContent(final SwingNode swingNode)
	{
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run()
			{
				//Erstellt "Haus(Klasse)" in diesem "Haus(Klasse)" sind "Objekte(Methoden)" 
				Printer p = new Printer();
				//Zugriff auf "Objekte im Haus"(Methoden)
				p.printer();				
			}
		});
	}
	
	public int doAction(String functionName, String paramS, double paramD)
	{
		Supervisor.errorAndDebug(this, "Deprecated method (DruckModel). Please use the new doAction");
		return -9;
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
