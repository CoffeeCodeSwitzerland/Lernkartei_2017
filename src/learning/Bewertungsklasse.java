package learning;

import java.util.ArrayList;

import database.CardEntity;
import debug.Logger;
import globals.Globals;

public class Bewertungsklasse {

	public static ArrayList<String> getShuffledCards(String query) {
		ArrayList<String> result = new ArrayList<>();
		ArrayList<Integer> zufallsZahlen = new ArrayList<>();

		ArrayList<String[]> cards = CardEntity.pullFromStock(query);

		if (cards == null) {
			debug.Debugger.out("getData cards = null");
			return result;
		} else {

		//TODO	new lerndatenzuweisung(cards);

			while (result.size() < cards.size()) {
				Integer i = (int) (Math.random() * 50000) % cards.size();

				while (zufallsZahlen.contains(i)) {
					i = ++i % cards.size();
				}

				zufallsZahlen.add(i);
				String tempResult = cards.get(i)[0] + Globals.SEPARATOR + cards.get(i)[1] + Globals.SEPARATOR
						+ cards.get(i)[2] + Globals.SEPARATOR + cards.get(i)[3];
				result.add(tempResult);
			}

			return result;
		}

	}

	// Wenn Karte Richtig ist
	public static int cardIsCorrect(String cardID)
	{
		int oldPriority = CardEntity.getPriority(cardID);
		
		Logger.log("Call Upprio");
		CardEntity.upPrio(Integer.parseInt(cardID));
		Logger.log("End Upprio");
		
		datumZuweisen(cardID);

		if (oldPriority <= CardEntity.getPriority(cardID))
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}

	// Wenn Karte Falsch ist
	public static int cardIsFalse(String cardID)
	{
		Logger.log("Call Resetprio");
		CardEntity.resetPrio(Integer.parseInt(cardID));
		Logger.log("End Resetprio");

		if (CardEntity.getPriority(cardID) == 1)
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
	
	public static void datumZuweisen(String cardID)
	{
		//database.Database.InsertDate(cardID);
		//TODO Roger: localDate.now muss dort in Attribut Datum eingetragen werden 
	}
}
