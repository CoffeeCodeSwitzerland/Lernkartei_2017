package learning;

import java.time.LocalDate;
import java.util.ArrayList;

import database.LKDatabase;

public class lerndatenzuweisung {
	public ArrayList<String[]> learnToday = new ArrayList<String[]>();
	// hier muss aus der Datenbank genommen werden wann die karte zuletzt gelernt wurde  !!
	public LocalDate Datum = LocalDate.now();
	
	public String KartenDatum = LKDatabase.myLearns.getMyAttributes().getAttributeNamedAs("Date").getValue();
	public LocalDate CardDate =  LocalDate.parse(KartenDatum, globals.Globals.formatter);
 	

	public lerndatenzuweisung(ArrayList<String[]> card) {

		// jeden tag wiederholen
		// wenn karte = priority 1| tu si in Arraylist learnEveryday
		if(card.get(0)[3].equals("1"))
		{
			learnToday.add(card.get(0));
		}

		// Wenn seit letztem lernen 2 Tage vergangen sind tu Sie in die ArrayList LearnToday.
		if(card.get(0)[3].equals("2"))
		{			
			if(Datum.isEqual(CardDate.plusDays(2)))
			{
				learnToday.add(card.get(0));
			}
		}
		
		// Wenn seit letztem lernen 7 tage vergangen sind tu sie in die ArrayList LearnToday.
		if(card.get(0)[3].equals("3"))
		{
			
			if(Datum.isEqual(Datum.plusDays(7)))
			{
				learnToday.add(card.get(0));
			}
		}

		// Wenn seit letztem lernen 14 Tage vergangen sind tu sie in die ArrayList LearnToday.
		if(card.get(0)[3].equals("4"))
		{
			if(Datum.isEqual(Datum.plusDays(14)))
			{
				learnToday.add(card.get(0));
			}
		}

		// Wenn seit letztem lernen 21 Tage vergangen sind tu sie in die ArrayList LearnToday.
		if(card.get(0)[3].equals("5"))
		{
			if(Datum.isEqual(Datum.plusDays(21)))
			{
				learnToday.add(card.get(0));
			}
		}
		
		card = learnToday;
	}
	
}