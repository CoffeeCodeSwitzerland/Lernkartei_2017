package Learning;

import java.util.ArrayList;

import controls.Globals;
import database.Database;

public class Bewertungsklasse {

	public static ArrayList<String> getShuffledCards(String query) {
		ArrayList<String> result = new ArrayList<>();
		ArrayList<Integer> zufallsZahlen = new ArrayList<>();

		ArrayList<String[]> cards = Database.pullFromStock(query);

		if (cards == null) {
			debug.Debugger.out("getData cards = null");
			return result;
		} else {
			while (result.size() < cards.size()) {
				Integer i = (int) (Math.random() * 50000) % cards.size();

				while (zufallsZahlen.contains(i)) {
					i = ++i % cards.size();
				}

				zufallsZahlen.add(i);
				String tempResult = cards.get(i)[0] + Globals.SEPARATOR + cards.get(i)[1] + Globals.SEPARATOR
						+ cards.get(i)[2];
				result.add(tempResult);
			}

			return result;
		}

	}

	// Wenn Karte Richtig ist
	public static  int CardCorrect(String cardID, int KartenPunkt) {
		
		Database.upPrio(Integer.parseInt(cardID));		
		KartenPunkt += 1;
		
		return KartenPunkt;
	
	}

	// Wenn Karte Falsch ist
	public static int CardFalse(String cardID, int KartenPunkt) {
		
		Database.resetPrio(Integer.parseInt(cardID));
		KartenPunkt = 1;
		
		return KartenPunkt;
		
	}
}
