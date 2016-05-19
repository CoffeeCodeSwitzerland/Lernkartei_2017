package models;

import java.util.ArrayList;

import controls.Globals;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import mvc.fx.FXModel;
import statistics.Rangliste;
import statistics.Saulendiagramm;
import statistics.Standings;

public class StatisticsModel extends FXModel
{

	public StatisticsModel(String myName)
	{
		super(myName);
	}
	
	public ArrayList<String> getDataList(String query) {
		return null;
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
			return S.getStart(CombinedString);
		} else if (Decision[1].equals("end")){
			return S.getEnd(CombinedString);
		} else if (Decision[1].equals("difference")) {
			return S.getDifference(CombinedString);
		} else {
			return null;
		}
	}
	
	Saulendiagramm SD = new Saulendiagramm();
	public HBox getDiagram(String Diagramtyp) {
		if (Diagramtyp.equals("saule")); {
			return SD.getDiagramm();
		} 
	}
	
	public int doAction(String functionName, String paramS, double paramD)
	{
		if (functionName.equals("back"))
		{
			boolean success = false;
			SD.delOldStats();
			return success ? 1 : -1;
		} else if (functionName.equals("getData")){
			SD.genSaulendiagramm();
			return 1;
		} else {
			return -2;
		}
	}
	
	

}
