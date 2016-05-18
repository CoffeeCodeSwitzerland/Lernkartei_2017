package statistics;

import java.util.ArrayList;

public class Standings
{
	Double Start;
	Double End;
	Double Difference;
	
	ArrayList<Double> DoubleList = new ArrayList<Double>();
	
	public ArrayList<Double> getStart(String CombinedString) {
		Start = 0.0;
		DoubleList.add(Start);
		return DoubleList;
	}
	
	public ArrayList<Double> getEnd(String CombinedString) {
		End = 1.0;
		DoubleList.add(End);
		return DoubleList;
	}
	
	public ArrayList<Double> getDifference(String CombinedString){
		Difference = End - Start;
		DoubleList.add(Difference);
		return DoubleList;
	}
}
