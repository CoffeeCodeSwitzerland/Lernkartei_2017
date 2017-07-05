package tutto;
import java.util.ArrayList;

import processing.core.PApplet;

/**
 * Ein Spielerverwalter verwaltet die Spieler und die Karten. Ein
 * Spielerverwalter verwaltet zudem der aktuellen Spieler und die aktuelle
 * Punktezahl.
 */
class Spielerverwalter {

	PApplet parent;

	final ArrayList<Spieler> spielerListe = new ArrayList<Spieler>();
	Karte karte;

	// Daten des Spielenden:
	Spieler actSpieler;
	int actSpielerNr;
	int actPunkte = 0;

	Spielerverwalter(PApplet p) {
		parent = p;
		karte = new Karte(parent);
		actSpieler = new Spieler(1, p);
		actSpielerNr = 1;
		spielerListe.add(actSpieler);
		karte.ziehen();
	}

	/**
	 * zeichnet die Karten und Spieler.
	 */
	void drawAllSpielerUndKarte() {
		parent.textSize(25);
		parent.fill(255, 204, 229);
		parent.text("aktueller Spieler " + (actSpieler.spielerNummer + 1) + " : " + actPunkte, 50, 580);
		for (Spieler sp : spielerListe) {
			sp.myDraw();
		}
		karte.anzeigen();
	}

	/**
	 * wechselt zum nächsten Spieler.
	 */
	void nextSpieler() {
		try {
			actSpieler = spielerListe.get(actSpielerNr++);
		} catch (Exception e) {
			actSpielerNr = 1;
			actSpieler = spielerListe.get(actSpielerNr - 1);
		}
		karte.ziehen();
	}

	/**
	 * zählt die aktuelle Punkte zusammen.
	 * 
	 * @param istTutto
	 *            : überprüft ob es ein Tutto ist.
	 */
	void sumActPunkte(boolean istTutto) {
		if (karte.typ == Kartentyp.KIRSCHE && istTutto) { // Karte Kirsche
			verdoppleActPunkte();
			if (istTutto) {
				karte.ziehen();
			}
		}
	}

	/**
	 * Gibt die Punkte am Spieler weiter.
	 * 
	 * @param istTutto
	 *            : überprüft ob es ein Tutto ist.
	 */
	void sumActPunkteToPlayer(boolean istTutto) {
		sumActPunkte(istTutto);
		actSpieler.addPunkte(actPunkte);
	}

	/**
	 * zeichnet der Zustand Ende.
	 */
	void drawEnde() {
		parent.fill(255, 204, 229);
		parent.textSize(35);
		parent.text("Spieler: " + (actSpieler.spielerNummer + 1) + " hat gewonnen!!!", 55, 400);
		for (Spieler sp : spielerListe) {
			parent.fill(255, 204, 229);
			parent.textSize(25);
			parent.text(sp.name + " Punktzahl " + sp.spielerPunkte, 150, 500 + 70 * sp.spielerNummer);
			parent.text("n = neu starten", 50, 750);
		}
	}

	/**
	 * aktuelle Punkte werden mit den etsprechenden Wert gelich gesetzt.
	 */
	void setActPunkte(int wert) {
		actPunkte = wert;
	}

	/**
	 * aktuelle Punkte werden mit den etsprechenden Wert erhöht.
	 */
	void addActPunkte(int wert) {
		actPunkte += wert;
	}

	/**
	 * aktuelle Punkte werden mit den etsprechenden Wert verdoppelt.
	 */
	void verdoppleActPunkte() {
		actPunkte *= 2;
	}

	/**
	 * berechnet Punkte des aktuellen Wurfs.
	 * 
	 * @param anzNeuAngewaehlt
	 *            : anzahl neu angewählten Würfeln.
	 * @return: T: wenn aktuelle Spielerpunktzahl grösser als die
	 *          Spielerpunktzahl F: sonst
	 */
	boolean actSpielerHatErreicht(int punkte) {
		if (actSpieler.spielerPunkte >= punkte)
			return true;
		return false;
	}

	/**
	 * berechnet die Punkte der gültigen aktuellen Augenzahl
	 * 
	 * @param: ???
	 */
	void berechneActPunkte(int augenZahl, int anzNeuAngewaehlt) {
		
		switch (anzNeuAngewaehlt) {
		case 3:
			addActPunkte(100 * augenZahl);
			if (augenZahl == 1)
				addActPunkte(900);
			break;
		case 4: // ???
			addActPunkte(100 * augenZahl);
			if (augenZahl == 1)
				addActPunkte(1000);
			if (augenZahl == 5)
				addActPunkte(50);
			break;
		case 5: // ???
			addActPunkte(100 * augenZahl);
			if (augenZahl == 1)
				addActPunkte(1100);
			if (augenZahl == 5)
				addActPunkte(100);
			break;
		case 6:
			addActPunkte(100 * augenZahl);
			if (augenZahl == 1)
				addActPunkte(900);
			verdoppleActPunkte();
			break;
		default:
			if (augenZahl == 1)
				addActPunkte(100 * anzNeuAngewaehlt);
			if (augenZahl == 5)
				addActPunkte(50 * anzNeuAngewaehlt);
			break;
		}
	}
}