package statistics;

import java.util.ArrayList;

import database.LKDatabase;

public class Standings
{
	private Double Start;
	private Double End;
	private Double Difference;

	private Boolean startSet = false;
	private Boolean endSet = false;
	
	public ArrayList<Double> getMax(String Stackname) {
		ArrayList<Double> start = new ArrayList<Double>();
		Double[] dbl = LKDatabase.myCards.getScore(Stackname);
		start.add(dbl[0]);
		return start;
	}
	
	public ArrayList<Double> getStart(String Stackname) {
		ArrayList<Double> start = new ArrayList<Double>();
		Double[] dbl = LKDatabase.myCards.getScore(Stackname);
		Start = dbl[1];
		start.add(dbl[1]);
		return start;
	}

	public ArrayList<Double> getEnd(String Stackname)
	{
		ArrayList<Double> end = new ArrayList<Double>();
		Double[] ende = LKDatabase.myCards.getScore(Stackname);
		End = ende[1];
		end.add(ende[1]);
		endSet = true;
		return end;
	}

	public ArrayList<Double> getDifference(String Stackname)
	{
		ArrayList<Double> difference = new ArrayList<Double>();
		if (startSet && endSet)
		{
			Difference = End - Start;
			difference.add(Difference);
		} else
		{
			return null;
		}
		return difference;
	}
}
