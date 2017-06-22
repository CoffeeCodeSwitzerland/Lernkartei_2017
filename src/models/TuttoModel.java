package models;


import mvc.Model;
import processing.core.PApplet;

public class TuttoModel extends Model implements Runnable
{
	public static Thread tutto = null;

	public void init ()
	{
		debug.Debugger.out("Tutto Model: init game model...");
	}

	public Thread getMyOnlyThread() {
		if (tutto == null) {
			tutto = new Thread(this);
		}
		return tutto;
	}

	private void createProcessingContent () 
	{
		debug.Debugger.out("Tutto Model: clear game...");
		tutto = null; // dereference last thread in case of restarting game...
		try {
			Thread.sleep(1000); // just wait a little for a clean termination
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		debug.Debugger.out("Tutto Model: (re-)starting game...");
		getMyOnlyThread().start();
	}
	
	/**
	 * Called when closing MainView
	 */
	public void dispose ()
	{
		debug.Debugger.out("Tutto Model: disposing game...");
		Class<?> calcClass;
		try {
			calcClass = Class.forName("UsingProcessing");
			Terminator tm = (Terminator)calcClass.newInstance();
			tm.terminate(); // do this only here, this kills all remainig processing threads
			Thread.sleep(1000); // wait a little
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	@Override
	public void run() {
		debug.Debugger.out("Tutto Model: runnig game...");
		PApplet.main("UsingProcessing");
	}

	@Override
	public int doAction (Command command, String... param)
	{
		switch (command)
		{
			case NEW:
				createProcessingContent();
				return 1;
			default:
				return super.doAction(command, param);
		}
	}
}