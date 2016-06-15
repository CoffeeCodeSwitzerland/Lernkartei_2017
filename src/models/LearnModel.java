package models;

import java.util.ArrayList;

import learning.Bewertungsklasse;
import mvc.Model;


public class LearnModel extends Model
{
	/**
	 * @deprecated
	 */
	@Override
	public int doAction (String functionName, String freeStringParam, double freeDoubleParam)
	{

		// Aufruf der Bewertungs Klasse
		// Returns:
		// 0: Falsche Eingabe --> functionName existiert nicht
		// 1: Erfolgreich Punkt hinzugefügt
		// -1: Funktion wurde nicht ausgeeführt
		// 2: Punkt wurde erfolgreich von Karte abgezogen
		// -2: Punkt wurde nicht abgezogen
		// 3:
		// -3:

		if (functionName.equals("Richtig"))
		{
			int newPriorityIsValid = Bewertungsklasse.cardIsCorrect(freeStringParam);

			database.Score.correctCard();
			refreshViews();

			return newPriorityIsValid;
		}
		else if (functionName.equals("Falsch"))
		{
			int newPriorityIsValid = Bewertungsklasse.cardIsFalse(freeStringParam);

			refreshViews();

			return newPriorityIsValid;
		}
		else
		{
			return 0;
		}
	}

	@Override
	public int doAction (Command command, String... param)
	{
		int newPriorityIsValid = 0;

		switch (command)
		{
			case TRUE:

				if (param.length != 1) { return -2; }

				newPriorityIsValid = Bewertungsklasse.cardIsCorrect(param[0]);

				database.Score.correctCard();
				refreshViews();

				return newPriorityIsValid;

			case FALSE:

				if (param.length != 1) { return -2; }

				newPriorityIsValid = Bewertungsklasse.cardIsFalse(param[0]);

				refreshViews();

				return newPriorityIsValid;

			default:
				int successfulSuper = super.doAction(command, param);
				return successfulSuper;
		}
	};

	@Override
	public ArrayList<String> getDataList (String query)
	{
		if (query == null) { return super.getDataList(null); }

		ArrayList<String> memoList = super.getDataList(null);

		if (!getString(null).equals(query))
		{
			memoList = Bewertungsklasse.getShuffledCards(query);
			super.getDataList(null).clear();
			for (String s : memoList)
			{
				add(s);
			}
			setString(query);
		}

		return memoList;
	}

}
