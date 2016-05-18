package models;

import java.util.ArrayList;

import Learning.Bewertungsklasse;
import mvc.Model;

public class LearnModel extends Model {

	public LearnModel(String myName) {
		super(myName);

	}

	@Override
	public int doAction(String functionName, String freeStringParam, double freeDoubleParam) {

		// Aufruf der Bewertungs Klasse
		// Returns:
		// 0: Falsche Eingabe --> functionName existiert nicht
		// 1: Erfolgreich Punkt hinzugefügt
		// -1: Funktion wurde nicht ausgeeführt
		// 2: Punkt wurde erfolgreich von Karte abgezogen
		// -2: Punkt wurde nicht abgezogen
		// 3:
		// -3:

		int KartenPunkt = 0;

		if (functionName.equals("Richtig")) {
			KartenPunkt = Bewertungsklasse.CardCorrect(freeStringParam, KartenPunkt);
			refreshViews();
			database.Score.correctCard();
			
			if (KartenPunkt != 1) {
				
				return 1;
			} else {
				return -1;
			}
		}
		else if (functionName.equals("Falsch")) {
			KartenPunkt =  Bewertungsklasse.CardFalse(freeStringParam, KartenPunkt);
			refreshViews();
			if (KartenPunkt == 1) {
				
				return 2;
			} else {
				return -2;
			}
		}
		else {
			return 0; // no error
		}
	}

	@Override
	public ArrayList<String> getDataList(String query) {

		if (query == null) {
			return super.getDataList(null);
		}

		ArrayList<String> memoList = super.getDataList(null);

		if (!getString(null).equals(query)) {
			memoList = Bewertungsklasse.getShuffledCards(query);
			super.getDataList(null).clear();
			for (String s : memoList) {
				add(s);
			}
			setString(query);
		}

		return memoList;
	}
}
