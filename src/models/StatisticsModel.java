package models;

import java.util.ArrayList;

import database.Database;
import globals.Globals;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import mvc.fx.FXModel;
import statistics.Rangliste;
import statistics.Diagramm;

public class StatisticsModel extends FXModel
{

//	public StatisticsModel(String myName)
//	{
//		super(myName);
//	}
	
	//ROL --> RanglisteObversableList
	ObservableList<String> ROL = FXCollections.observableArrayList();
	public ObservableList<String> getObservableDataList(String query) {
		if (query.equals("Rangliste")) {
			debug.Debugger.out("StatisticsModel 1 Rangliste");
			ROL = Rangliste.getRangliste();
			debug.Debugger.out(ROL.get(0));
			return ROL;
		} else {
			return super.getObservableDataList(query);
		}
	}
	
	public ObservableList<XYChart.Series<String, Number>> getObservableDiagrammList(String query) {
		if (query.equals("saulendiagramm")){
			return Diagramm.getChartData();
		} else {
			return null;
		}
	}
	
	
	ArrayList<Double> temp = new ArrayList<>();
	Double tempStart = 0.0;
	//Diese Methode ist dafür da um den Fortschritt 
	//CombinedString --> STACKNAME + Globals.SEPARATOR + ANWEISUNG(end, difference oder start)
	public ArrayList<Double> getDoubleList(String CombinedString) {
		String[] Decision = CombinedString.split(Globals.SEPARATOR);
		if (Decision[1].equals("end"))
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
	
	public int doAction (String functionName, String paramS, double paramD) {
		if (functionName.equals("DeleteOldData")) {
			Boolean success = false;
			success = Diagramm.resetData();
			if (success){
				success = Rangliste.resetData();
				return success ? 1 : -1; 
			} else {
				return success ? 1 : -1; 
			}
		} else {
			return -2;
		}
	}
	

}
