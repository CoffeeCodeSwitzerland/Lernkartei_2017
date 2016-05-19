package models;

import java.util.ArrayList;

import controls.Globals;
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
			System.out.println("ProfilModel Ranking 2");
			return super.getObservableDataList(query);
		}
	}
	
	Standings S = new Standings();
	public ArrayList<Double> getDoubleList(String CombinedString) {
		String[] Decision = CombinedString.split(Globals.SEPARATOR);
		if (Decision[1].equals("start")){
			return S.getStart(Decision[0]);
		} else if (Decision[1].equals("end")){
			return S.getEnd(Decision[0]);
		} else if (Decision[1].equals("difference")) {
			return S.getDifference(Decision[0]);
		} else if (CombinedString.equals("punkte")) {
			return SD.getPunkte();
		} else {
			return null;
		}
	}
	
	public int doAction(String functionName, String paramS, double paramD)
	{
		if (functionName.equals("back"))
		{
			boolean success = false;
			SD.delOldStats();
			return success ? 1 : -1;
		} else {
			return -2;
		}
	}
	
	

}
