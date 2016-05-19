package models;

import java.util.ArrayList;

import database.Database;
import globals.Globals;
import javafx.collections.ObservableList;
import mvc.fx.FXModel;
import statistics.Rangliste;
import statistics.Diagramm;
import statistics.Standings;

public class StatisticsModel extends FXModel
{

	public StatisticsModel(String myName)
	{
		super(myName);
	}

	Diagramm SD = new Diagramm();
	public ArrayList<String> getDataList(String query) {
		if (query.equals("karteien")) {
			return SD.getKarteien();
		} else {
			return null;
		}
	}
	
	Rangliste R = new Rangliste();
	public ObservableList<String> getObservableDataList(String query) {
		if (query.equals("Rangliste")) {
			return R.getRangliste();
		} else {
			return super.getObservableDataList(query);
		}
	}
	
	Standings S = new Standings();
	ArrayList<Double> temp = new ArrayList<>();
	Double tempStart = 0.0;
	//CombinedString --> STACKNAME + Globals.SEPARATOR + ANWEISUNG(end, difference oder start)
	public ArrayList<Double> getDoubleList(String CombinedString) {
		String[] Decision = CombinedString.split(Globals.SEPARATOR);
		if (CombinedString.equals("punkte"))
		{
			return SD.getPunkte();
		}
		else if (Decision[1].equals("end"))
		{
			Double[] doubleArray = Database.getScore(Decision[0]);
			temp.clear();
			temp.add(100 / doubleArray[0] * doubleArray[1]);
			return temp;
		}
		else if (Decision[1].equals("difference"))
		{
			Double[] doubleArray = Database.getScore(Decision[0]);
			temp.clear();
			temp.add((100 / doubleArray[0] * doubleArray[1]) - tempStart);
			return temp;
		}
		else if (Decision[1].equals("start"))
		{
			Double[] doubleArray = Database.getScore(Decision[0]);
			tempStart = 100 / doubleArray[0] * doubleArray[1];
			temp.clear();
			temp.add(tempStart);
			return temp;
		}
		else
		{
			return null;
		}
	}
	
	

}
