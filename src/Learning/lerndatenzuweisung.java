package Learning;

import java.util.ArrayList;

public class lerndatenzuweisung {
	public ArrayList<String> learnEveryday = new ArrayList<String>();
	public ArrayList<String> learnEverySecondday = new ArrayList<String>();
	public ArrayList<String> learnEveryThirdday = new ArrayList<String>();
	public ArrayList<String> learnFourthday = new ArrayList<String>();
	public ArrayList<String> learnFifthday = new ArrayList<String>();

	public lerndatenzuweisung(ArrayList<String[]> card) {

		// jeden tag wiederholen
		// wenn karte = priority 1| tu si in Arraylist learnEveryday
		if(card.get(0)[3].equals("1"))
		{
			learnEveryday.add(card.get(0)[2]);
		}

		// wenn karte = priority 2| tu si in Arraylist learn EverySecondday
		if(card.get(0)[3].equals("2"))
		{
			learnEverySecondday.add(card.get(0)[2]);
		}

		// wenn karte = priority 3| tu si in Arraylist learnEveryThirdday
		if(card.get(0)[3].equals("3"))
		{
			learnEveryThirdday.add(card.get(0)[2]);
		}

		// wenn karte = priority 4| tu si in Arraylist learnFourthday
		if(card.get(0)[3].equals("4"))
		{
			learnFourthday.add(card.get(0)[2]);
		}

		// wenn karte = priority 5| tu si in Arraylist learnFifthday
		if(card.get(0)[3].equals("5"))
		{
			learnFifthday.add(card.get(0)[2]);
		}
		
	}

}