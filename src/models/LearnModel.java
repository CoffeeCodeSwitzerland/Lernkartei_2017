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
		// -1: Fehler bei Punkt hinzufügen
		// 2: Punkt wurde erfolgreich von Karte abgezogen
		// -2: Punkt wurde nicht abgezogen
		// 3:
		// -3:

		if (functionName.equals("Richtig")) {
			Bewertungsklasse.CardCorrect(freeStringParam);
		}
		if (functionName.equals("Falsch")) {
			Bewertungsklasse.CardFalse(freeStringParam);
		} else {
			return 0;
		}

		// funcName = correct OR false
		// stringParam = card ID

		return 0; // no error
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

	// TODO implement doAction & getDataList
}
