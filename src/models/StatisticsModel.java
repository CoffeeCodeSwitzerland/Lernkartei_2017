package models;

import java.util.ArrayList;

import controls.Globals;
import database.Database;
import debug.Debugger;
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
	ArrayList<Double> temp = new ArrayList<>();
	public ArrayList<Double> getDoubleList(String CombinedString) {
		Debugger.out("Eingang getDoubleList");
		String[] Decision = CombinedString.split(Globals.SEPARATOR);
		
		Debugger.out("Debugpunkt 1 in getDoubleList");
		// Alle Karten des Stacks
		//ArrayList<String[]> stackData = Database.pullFromStock(Decision[0]);
		//Double sum = 0.0d;
		/*if (stackData != null)
		{
			// Berechne Punktzahl
			for (String[] s : stackData)
			{
				sum += Double.parseDouble(s[5]);
			}
			temp.clear();
			temp.add(100 / (4 * stackData.size()) * sum); // Berechne Prozent
			return temp;
		}*/
		
		Debugger.out("Debugpunkt 2 in getDoubleList");
		if (CombinedString.equals("punkte")) {
			Debugger.out("Debugpunkt 3 in getDoubleList");
			return SD.getPunkte();
		} else if (Decision[1].equals("end")){
			Debugger.out("Debugpunkt 4 in getDoubleList");
			return S.getEnd(Decision[0]);
		} else if (Decision[1].equals("difference")) {
			Debugger.out("Debugpunkt 5 in getDoubleList");
			return S.getDifference(Decision[0]);
		} else if (Decision[1].equals("start")){
			Debugger.out("Debugpunkt 6 in getDoubleList");
			return S.getStart(Decision[0]);
		} else {
			Debugger.out("Debugpunkt ELSE in getDoubleList");
			return null;
		}
	}
	
	

}
