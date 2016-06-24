package learning;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import database.LKDatabase;
import database.sql.Attribute;
import database.sql.SQLHandler;
import debug.Logger;
import globals.Globals;

public class Bewertungsklasse {
	

	public static ArrayList<String> getShuffledCards(String query) {
		ArrayList<String> result = new ArrayList<>();
		ArrayList<Integer> zufallsZahlen = new ArrayList<>();

		ArrayList<String[]> cards = LKDatabase.myCards.pullFromStock(query);
		if (cards == null) {
			debug.Debugger.out("getData cards = null");
			return result;
		} else {

		//TODO	Implementiere Methode: new lerndatenzuweisung(cards);

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
		int oldPriority = LKDatabase.myCards.getPriority(cardID);
		
		Logger.log("Call Upprio");
		LKDatabase.myCards.upPrio(Integer.parseInt(cardID));
		Logger.log("End Upprio");
		
		// TODO: Datum updaten und Tabelle myLearn Datensatz einfügen
		
		if (oldPriority <= LKDatabase.myCards.getPriority(cardID))
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
		LKDatabase.myCards.resetPrio(Integer.parseInt(cardID));
		Logger.log("End Resetprio");
		
		// TODO: Datum updaten und Tabelle myLearn Datensatz einfügen
		
		if (LKDatabase.myCards.getPriority(cardID) == 1)
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
		// hier wird dem kärtchen ein neues Datum Zugewiesen. e
		LKDatabase.myLearns.getMyAttributes().seekKeyNamed("Date").setValue(LocalDate.now().format(globals.Globals.formatter));
		Attribute k = new Attribute("PK_CARD",cardID);
		String sql = SQLHandler.updateInTableCommand(LKDatabase.myLearns.getMyTableName(),LKDatabase.myLearns.getMyAttributes(),k); 
		debug.Debugger.out("SQL: datumZuweisen(): " + sql);
		LKDatabase.myLearns.executeCommand(sql);
	}
}
