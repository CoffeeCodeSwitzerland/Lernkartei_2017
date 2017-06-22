/*
 * This File must be in the default Package!!!
 */

import processing.core.PApplet;
import tutto.Spielplan;

public class UsingProcessing extends PApplet {

	Spielplan spielplan;

	public static void main(String[] args) {
		PApplet.main("UsingProcessing");
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
	}
}
