package tutto;
import processing.core.PApplet;

//irgendtwas geändert
/**
 * Ein Würfel verwaltet seine position und seine grösse. Ein Würfel überprüft ob
 * er blockiert ist und ob er gezählt wird.
 */
class Wuerfel {
	PApplet parent;

	int xposRec;
	int yposRec;
	int hoeheRec;
	int breiteRec;
	int r; // augenRadius
	int augenAnzahl = 0;
	boolean istBlockiert = false;
	boolean istGezaehlt = false;

	Wuerfel(int x, int y, int s, PApplet p) {
		hoeheRec = s;
		breiteRec = s;
		r = hoeheRec / 6;
		xposRec = x;
		yposRec = y;
		parent = p;
		wuerfle(false);
		myDraw();
	}

	/**
	 * zeichnet die Würfeln.
	 */
	void myDraw() {
		if (istBlockiert == true) {
			parent.fill(255);
		} else {
			parent.fill(0);
			parent.stroke(255);
		}
		parent.rect(xposRec, yposRec, hoeheRec, breiteRec);

		parent.fill(parent.color(parent.random(256),parent.random(256),parent.random(256)));

		if (augenAnzahl == 1 || augenAnzahl == 5 || augenAnzahl == 3) {
			parent.ellipse(xposRec + breiteRec / 2, yposRec + hoeheRec / 2, r, r);
		}
		if (augenAnzahl != 1) {
			parent.ellipse(xposRec + breiteRec / 4, yposRec + hoeheRec / 4, r, r);
			parent.ellipse(xposRec + 3 * breiteRec / 4, yposRec + 3 * hoeheRec / 4, r, r);
		}
		if (augenAnzahl == 4 || augenAnzahl == 5 || augenAnzahl == 6) {
			parent.ellipse(xposRec + 3 * breiteRec / 4, yposRec + hoeheRec / 4, r, r);
			parent.ellipse(xposRec + breiteRec / 4, yposRec + 3 * hoeheRec / 4, r, r);
		}
		if (augenAnzahl == 6) {
			parent.ellipse(xposRec + breiteRec / 4, yposRec + hoeheRec / 2, r, r);
			parent.ellipse(xposRec + 3 * breiteRec / 4, yposRec + hoeheRec / 2, r, r);
		}
	}

	/**
	 * würflet die würfeln. return: zufälliger Anzahl augenzahl.
	 */
	int wuerfle(boolean cheat) {
		if (istBlockiert == false) {
			augenAnzahl = (int) parent.random(6) + 1;
			if (cheat) {
				augenAnzahl = 1;
				if (parent.random(2) > 1.0)
					augenAnzahl = 5;
			}
			// aaz=3; //nur augenzahl 3!!
			return augenAnzahl;
		}
		return 0;
	}

	/**
	 * überprüft ob der Würfel selektiert wurde. setzt die bedinungen das nur
	 * eine 5, 1 oder drillinge selektiert werden kann.
	 * 
	 * @param:
	 */
	void checkSelected(WuerfelRahmen callingFrame, int mxPos, int myPos) {
		if (mxPos >= xposRec && mxPos <= xposRec + breiteRec && myPos >= yposRec && myPos <= yposRec + hoeheRec) {
			int anz = callingFrame.getAnzahlNochNichtGezaehltenWuerfeln(augenAnzahl);
			if (callingFrame.getAnzahlMarkierteWuerfel(augenAnzahl) >= 3 && callingFrame.istAuswahlGueltig(augenAnzahl))
				anz = 0;
			if (augenAnzahl == 1 || augenAnzahl == 5 || anz >= 3) {
				istBlockiert = true;
			}
		}
	}

	/**
	 * definiert einen Würfel als gültig, wenn er markiert aber noch nicht
	 * gezählt wurde.
	 * 
	 * @param anzA
	 *            : anzahl Augen 1..6
	 * @return : T: wenn gültig, F: sonst.
	 */
	boolean istGueltig(int anzA) {
		if (anzA == augenAnzahl && istBlockiert == true && istGezaehlt == false) {
			return true;
		}
		return false;
	}

	/**
	 * setzt gezählt auf Wahr, wenn ein Würfel blockiert ist und die Augenzahl
	 * gleich gross wie die Augen anzahl.
	 * 
	 * @param aZ:
	 *            anzahl Augen 1..6
	 */
	void markiertAlsGezaehlt(int augenZahl) {
		if (augenZahl == augenAnzahl && istBlockiert == true)
			istGezaehlt = true;
	}

	/**
	 * definiert einen Würfel als vorhanden, wenn es nicht gezählt wird.
	 * 
	 * @param anzA:
	 *            anzahl Augen 1..6
	 * @return : T: wenn vorhanden, F: sonst.
	 */
	boolean istVorhanden(int anzA) {
		if (anzA == augenAnzahl && istGezaehlt == false) {
			return true;
		}
		return false;
	}

	/**
	 * resetet die blockierten und gezählten Würfeln.
	 */
	void wuerfelReset() {
		istBlockiert = false;
		istGezaehlt = false;
	}

}