package Tutto;
import processing.core.PApplet;

class Spielzustand {
	PApplet parent;

	/**
	 * Ein Spielplan verwaltet der Spielzustand.
	 */
	Spielzustand(PApplet p) {
		parent = p;
	}

	// Spielzustand:
	boolean gewonnen = false;
	boolean help = false;

	/**
	 * legt die Bedinung zum gewinnen fest.
	 */
	boolean istGewonnen() {
		if (gewonnen == true)
			return true;
		return false;
	}

	/**
	 * setzt gewonnen auf Wahr
	 */
	void setGewonnen() {
		gewonnen = true;
	}

	/**
	 * zeichnet und zeigt die hilfe an
	 */
	void hilfeAnzeigen() {
		if (help == true) {
			parent.textSize(15);
			parent.fill(255,204,229);
			parent.text("w = würfel", 150, 750);
			parent.text("f = Spielzug beenden", 260, 750);
			parent.text("n = neu starten", 450, 750);
		}
	}

	/**
	 * togglet die hilfe anzeige
	 */
	void toggleHelp() {
		if (help == true) {
			help = false;
		} else {
			help = true;
		}
	}
}