package Learning;

import java.util.ArrayList;

public class lerndatenzuweisung {
	public ArrayList<String> learnEveryday = new ArrayList<String>();
	public ArrayList<String> learnEverySecondday = new ArrayList<String>();
	public ArrayList<String> learnEveryThirdday = new ArrayList<String>();
	public ArrayList<String> learnFourthday = new ArrayList<String>();
	public ArrayList<String> learnFifthday = new ArrayList<String>();

	public lerndatenzuweisung(ArrayList<String[]> cards) {

		// jeden tag wiederholen
		// wenn karte = priority 1| tu si in Arraylist learnEveryday
		if(cards.get(0)[3].equals("1"))
		{
			learnEveryday.add(cards.get(0)[2]);
		}

		// wenn karte = priority 2| tu si in Arraylist learn EverySecondday

		// wenn karte = priority 3| tu si in Arraylist learnEveryThirdday

		// wenn karte = priority 4| tu si in Arraylist learnFourthday

		// wenn karte = priority 5| tu si in Arraylist learnFifthday
	}

}