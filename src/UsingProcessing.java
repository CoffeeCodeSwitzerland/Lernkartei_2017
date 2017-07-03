/*
 * This Class must be in the default Package and remain static !!!
 */

import models.*;
import processing.core.PApplet;
import tutto.Spielplan;

public class UsingProcessing extends PApplet implements Terminator {

	private Spielplan spielplan = null;
	private static boolean terminated = false;
	
	@Override
	public void terminate () {
		terminated = true;
	}
	
	/**
	 * Called by this class when closing it
	 */
	public void dispose ()
	{
		debug.Debugger.out("UsingProcessing: terminating game...");
		try {
			while (!terminated) {
				Thread.sleep(1000); // do not remove this, else terminating will not work correctly!
			}
			debug.Debugger.out("UsingProcessing: all threads disposed...");
		} catch (Exception e1) {
			//e1.printStackTrace();
		}
	}
	
	public void settings() {
		size(600, 800);
	}

	public void setup() {
		fill(120, 50, 240);
		initNeuerSpielplan();
	}

	public void draw() {
		spielplan.myDraw();
		spielplan.buttonUpdate(mouseX, mouseY);
	}

	public void initNeuerSpielplan() {
		spielplan = new Spielplan(this);
	}

	/**
	 * führt die Aktionen beim Tastendruck.
	 */
	public void keyPressed() {
		switch (keyCode) {
		case 'F':
			spielplan.sumPunkteToPlayer();
			break;
		case 'W':
			spielplan.naechsterSpielzug(false);
			break;
		case 'N':
			initNeuerSpielplan();
			break;
		case 'H':
			spielplan.toggleHelp();
			break;
		case 'I':
			spielplan.naechsterSpielzug(true);
			break;
		}
	}

	/**
	 * führt die Aktion beim Maus klick.
	 */
	public void mousePressed() {
		spielplan.checkSelected(mouseX, mouseY);
		
		spielplan.buttonPressed(mouseX, mouseY);
		
	}

	/* for stand alone testing only
	 
	public static void main(String[] args) {
		PApplet.main("UsingProcessing");
	}
	 */
}
