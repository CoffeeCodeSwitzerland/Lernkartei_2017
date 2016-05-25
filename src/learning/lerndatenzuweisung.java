package learning;

import java.time.LocalDate;
import java.util.ArrayList;

public class lerndatenzuweisung {
	public ArrayList<String[]> learnToday = new ArrayList<String[]>();
	public LocalDate Datum;

	public lerndatenzuweisung(ArrayList<String[]> card) {

		// jeden tag wiederholen
		// wenn karte = priority 1| tu si in Arraylist learnEveryday
		if(card.get(0)[3].equals("1"))
		{
			learnToday.add(card.get(0));
		}

		// wenn karte = priority 2| tu si in Arraylist learn EverySecondday
		if(card.get(0)[3].equals("2"))
		{			
			if(Datum.isEqual(Datum.plusDays(2)))
			{
				learnToday.add(card.get(0));
			}
		}

		// wenn karte = priority 3| tu si in Arraylist learnEveryThirdday
		if(card.get(0)[3].equals("3"))
		{
			
			if(Datum.isEqual(Datum.plusDays(7)))
			{
				learnToday.add(card.get(0));
			}
		}

		// wenn karte = priority 4| tu si in Arraylist learnFourthday
		if(card.get(0)[3].equals("4"))
		{
			if(Datum.isEqual(Datum.plusDays(14)))
			{
				learnToday.add(card.get(0));
			}
		}

		// wenn karte = priority 5| tu si in Arraylist learnFifthday
		if(card.get(0)[3].equals("5"))
		{
			if(Datum.isEqual(Datum.plusDays(21)))
			{
				learnToday.add(card.get(0));
			}
		}
		
		card = learnToday;
		
	
	}
	public void datenZuweiser()
	{//TODO		LocalDate.now().(viele möglichkeiten		
		
		
	}
	

}