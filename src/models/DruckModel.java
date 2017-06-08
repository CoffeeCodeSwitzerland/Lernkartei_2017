package models;

import javax.swing.SwingUtilities;

import debug.Supervisor;
import javafx.embed.swing.SwingNode;
import mvc.fx.FXModel;
import printing.Printer;

public class DruckModel extends FXModel
{
	private void createSwingContent(final SwingNode swingNode)
	{

		String param = this.getString(null);
		debug.Debugger.out();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run()
			{
				
				//Erstellt "Haus(Klasse)" in diesem "Haus(Klasse)" sind "Objekte(Methoden)" 
				Printer p = new Printer();
				//Zugriff auf "Objekte im Haus"(Methoden)
				try {
					p.giveToPrint(param);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
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
