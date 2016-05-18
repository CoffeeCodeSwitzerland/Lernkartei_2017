package models;

import java.util.ArrayList;

import controls.Globals;
import mvc.fx.FXModel;
import statistics.Standings;

public class StatisticsModel extends FXModel
{

	public StatisticsModel(String myName)
	{
		super(myName);
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<String> getDataList(String query) {
		return null;
	}
	
	Standings s = new Standings();
	public ArrayList<Double> getDoubleList(String CombinedString) {
		String[] Decision = CombinedString.split(Globals.SEPARATOR);
		if (Decision[1].equals("start")){
			return s.getStart(CombinedString);
		} else if (Decision[1].equals("end")){
			return s.getEnd(CombinedString);
		} else if (Decision[1].equals("difference")) {
			return s.getDifference(CombinedString);
		} else {
			return null;
		}
	}

}
