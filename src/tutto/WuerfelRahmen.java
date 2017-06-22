package tutto;
import java.util.ArrayList;

import processing.core.PApplet;

/**
 * Ein W�rfel verwaltet seine position und seine gr�sse. Ein W�rfel �berpr�ft ob
 * er blockiert ist und ob er gez�hlt wird.
 */
class WuerfelRahmen {
	PApplet parent;

	// W�rfel des Spielplans:
	final int wuerfelAbstand = 10;
	final int anzW = 6; // anzahl W�rfel
	final ArrayList<Wuerfel> wuerfelListe = new ArrayList<Wuerfel>();

	// int anzNeuAngewaehlt; //Hilfsvariable

	int w;

	WuerfelRahmen(PApplet p) {
		parent = p;
		int anzZ = (int) PApplet.sqrt(anzW);
		int anzS = anzZ;
		int anzL = 0;
		if (anzS * anzZ < anzW) {
			anzS++;
			anzL = anzW - anzS * (anzZ - 1);
			w = p.width;
		}

		if (parent.height < w)
			w = parent.height;
		int s = (w - (anzS + 1) * wuerfelAbstand) / anzS;
		// int f = (w -(s+wuerfelAbstand) * anzL) /2;
		// xx if (anzL == 0) f = wuerfelAbstand;

		for (int j = 0; j < anzZ; j++) {
			for (int i = 0; i < anzS; i++) {
				int x = (wuerfelAbstand + s) * i + wuerfelAbstand;
				int y = (wuerfelAbstand + s) * j + wuerfelAbstand;
				if (anzL != 0 && j == anzZ - 1) {
					if (i >= anzL)
						break;
					// ... x = (wuerfelAbstand + s) * i + f; // HLU 19.5.17 line
					// commented
				}
				wuerfelListe.add(new Wuerfel(x, y, s, parent));
			}
		}
	}

	/**
	 * die Methode reset wird von der class Wuerfel aufgerufen
	 */
	void alleWuerfelReset() {
		for (Wuerfel w : wuerfelListe) {
			w.wuerfelReset();
		}
	}

	/**
	 * die Methoden myDraw und wuerfle werden von der class Wuerfel aufgerufen.
	 */
	void zeigeUndWuerfleAlle(boolean cheat) {
		for (Wuerfel w : wuerfelListe) {
			w.myDraw();
			w.wuerfle(cheat);
		}
	}

	/**
	 * z�hlt die anzahl Blockierte um eins, wenn die W�rfel blockiert sind. wenn
	 * anzahl Blockierten gleich gross wie die anzahl W�rfel ist, wird die
	 * Methode Reset aufgerufen.
	 */
	void resetNachTutto() {
		int anzB = 0;
		for (Wuerfel w : wuerfelListe) {
			if (w.istBlockiert == true)
				anzB++;
		}
		if (anzB == anzW) { // anzahl Blockierten
			for (Wuerfel w : wuerfelListe) {
				w.wuerfelReset();
			}
		}
	}

	/**
	 * die MEthode checkSelected von der class Wuerfel wird aufgeruft.
	 * 
	 * @param mx,
	 *            my: die mMaus positionen.
	 */
	public void checkSelected(int mouseXPos, int mouseYPos) {
		for (Wuerfel w : wuerfelListe) {
			w.checkSelected(this, mouseXPos, mouseYPos);
		}
	}

	/**
	 * Wenn w�rfeln vorhanden sind, wird anzNeuAngewaehlt auf eins erh�ht. Die
	 * anzahl W�rfeln werden gez�hlt.
	 * 
	 * @param aaz:
	 *            augen anzahl
	 * @return: die anzahl Neu angew�hlten W�rfeln.
	 */
	int getAnzahlNochNichtGezaehltenWuerfeln(int augenAnzahl) {
		int anzNeuAngewaehlt = 0;
		for (Wuerfel w : wuerfelListe) {
			if (w.istVorhanden(augenAnzahl))
				anzNeuAngewaehlt++;
		}
		return anzNeuAngewaehlt;
	}

	/**
	 * Wenn w�rfeln g�ltig sind, wird anzNeuAngewaehlt auf eins erh�ht. Die
	 * anzahl markierten W�rfeln werden gez�hlt.
	 * 
	 * @param aaz:
	 *            augen anzahl
	 * @return: die anzahl Neu angew�hlten W�rfeln.
	 */
	int getAnzahlMarkierteWuerfel(int augenAnzahl) {
		int anzNeuAngewaehlt = 0;
		for (Wuerfel w : wuerfelListe) {
			if (w.istGueltig(augenAnzahl))
				anzNeuAngewaehlt++;
		}
		return anzNeuAngewaehlt;
	}

	/**
	 * die Methode myDraw von der class Wuerfel wird aufgerufen.
	 */
	void drawAllWuerfel() {
		for (Wuerfel w : wuerfelListe) {
			w.myDraw();
		}
	}

	/**
	 * die Methode zaehlen von der class Wuerfel wird aufgerufen.
	 */
	void markiereAlleAlsGezaehlt(int augenZahl) {
		for (Wuerfel w : wuerfelListe) {
			w.markiertAlsGezaehlt(augenZahl);
		}
	}

	/**
	 * die Methode wuerfle von der class Wuerfel wird aufgerufen.
	 */
	void wuerfleAll(boolean cheat) {
		for (Wuerfel w : wuerfelListe) {
			w.wuerfle(cheat);
		}
	}

	/**
	 * Pr�ft ob eine Auswahl vom W�rfeln m�glich ist. return: Anzahl
	 * M�glichkeite: INT
	 */
	int getAnzahlMoeglicheAuswahl() {
		int anzNeuAngewaehlt = 0;
		for (int aZ = 1; aZ <= 6; aZ++) {
			int z = getAnzahlNochNichtGezaehltenWuerfeln(aZ);
			if (aZ == 1 || aZ == 5) {
				if (z >= 1)
					anzNeuAngewaehlt++;
			} else {
				if (z >= 3)
					anzNeuAngewaehlt++;
				if (z == 6)
					anzNeuAngewaehlt++;
			}
		}

		return anzNeuAngewaehlt;
	}

	boolean istTutto() {
		int anzNochNichtAngewaehlt = 0;
		for (int aZ = 1; aZ <= 6; aZ++) {
			if (anzNochNichtAngewaehlt > 0)
				return false;
			int z = getAnzahlNochNichtGezaehltenWuerfeln(aZ);
			if (z > 0)
				anzNochNichtAngewaehlt += z;
		}
		return true;
	}

	boolean istAuswahlGueltig(int augenAnzahl) {
		int anz = getAnzahlMarkierteWuerfel(augenAnzahl);
		if (anz != 3 || anz != 6)
			return true;
		return true;
	}
}