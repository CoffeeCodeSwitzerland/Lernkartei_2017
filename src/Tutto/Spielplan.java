package Tutto;
import processing.core.PApplet;

public class Spielplan extends WuerfelRahmen {
	Spielplan(PApplet p) {
		super(p);

		mySpielerverwalter = new Spielerverwalter(parent);
		mySpielzustand = new Spielzustand(parent);
	}

	/**
	 * Ein Spielplan koordiniert ein Spielerverwalter und ein Spielzustand.
	 */

	// Weitere Elemente des Spielplans:
	Spielzustand mySpielzustand;
	Spielerverwalter mySpielerverwalter;

	/**
	 * führt den Würfel befehl aus.
	 */
	void naechsterSpielzug(boolean cheat) {
		if (mySpielzustand.istGewonnen())
			return;
		if (getAnzahlMoeglicheAuswahl() == 0) {
			mySpielerverwalter.setActPunkte(0);
			alleWuerfelReset();
			mySpielerverwalter.nextSpieler();
		} else {
			wuerfelPunkteZaehlen();
			mySpielerverwalter.sumActPunkte(istTutto());
		}
		resetNachTutto();
		// if (mySpielzustand.istAngehalten() == true) istAuswahlGueltig();
		zeigeUndWuerfleAlle(cheat);
		checkSpielEnde();
	}

	/**
	 * zeichnet den gesamten Spielplan.
	 */
	void myDraw() {
		parent.background(255);
		mySpielerverwalter.drawAllSpielerUndKarte();
		drawAllWuerfel();
		zeigeAnleitung();
		if (mySpielzustand.istGewonnen()) {
			parent.background(255);
			mySpielerverwalter.drawEnde();
		}
	}

	/**
	 * berechnet Punkte des aktuellen Wurfs.
	 */
	void wuerfelPunkteZaehlen() {
		for (int aZ = 1; aZ <= 6; aZ++) {
			mySpielerverwalter.berechneActPunkte(aZ, getAnzahlMarkierteWuerfel(aZ));
			markiereAlleAlsGezaehlt(aZ);
			// System.out.println("Punkte: " + actPunkte);
		}
	}

	/**
	 * rechnet die aktuelle Punkte mit der Spilerpunktzahl zusammen.
	 */
	void sumPunkteToPlayer() {
		if (mySpielzustand.istGewonnen())
			return;
		wuerfelPunkteZaehlen();
		mySpielerverwalter.sumActPunkteToPlayer(istTutto());
		alleWuerfelReset();

		checkSpielEnde();
		if (!mySpielzustand.istGewonnen()) {
			mySpielerverwalter.nextSpieler();
			mySpielerverwalter.setActPunkte(0);
			wuerfleAll(false);
		}
	}

	/**
	 * überprüft ob der aktuelle Spieler 6000 Punkte erreicht hat.
	 */
	void checkSpielEnde() {
		if (mySpielerverwalter.actSpielerHatErreicht(6000)) {
			mySpielzustand.setGewonnen();
		}
	}

	/**
	 * zeigt die Anleitung an.
	 */
	void zeigeAnleitung() {
		parent.textSize(15);
		parent.text("h = hilfe", 50, 750);
		mySpielzustand.hilfeAnzeigen();
	}

	/**
	 * Verweisst zum Spielzustand zur toggleHelp Methode.
	 */
	void toggleHelp() {
		mySpielzustand.toggleHelp();
	}

}
