package models;

import java.util.ArrayList;

import database.LKDatabase;
import debug.Debugger;
import debug.Supervisor;
import globals.Globals;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import learning.Bewertungsklasse;
import mvc.fx.FXModel;
import statistics.Diagramm;
import statistics.Rangliste;

//
public class StatisticsModel extends FXModel
{

	// public StatisticsModel(String myName)
	// {
	// super(myName);
	// }

	// ROL --> RanglisteObversableList
	ObservableList<String> rol = FXCollections.observableArrayList();
	public int anzahlKartenImStapel;
	public ObservableList<String> getObservableDataList (String query)
	{
		if (query.equals("Rangliste"))
		{
			rol = Rangliste.getRangliste();
			return rol;
		}
		else
		{
			return super.getObservableDataList(query);
		}
	}

	public ObservableList<XYChart.Series<String, Number>> getObservableDiagrammList (String query)
	{
		if (query.equals("saulendiagramm"))
		{
			Debugger.out("StatisticsModel 1");
			return Diagramm.getChartData();
		}
		else
		{
			return null;
		}
	}

	
	

	// Diese Methode ist dafür da um den Fortschritt
	// CombinedString --> STACKNAME + Globals.SEPARATOR + ANWEISUNG(end,
	// difference oder start)
	public ArrayList<Double> getDoubleList (String CombinedString)
	{
		
		ArrayList<Double>	temp			= new ArrayList<>();
		Double				tempStart		= 0.0;
		Double 				tempDifference 	= 0.0;
		String[] Decision = CombinedString.split(Globals.SEPARATOR);
		
		if (Decision[1].equals("difference"))
		{
			ArrayList<String[]> myCards = LKDatabase.myCards.pullFromStock(Decision[0]);
			tempDifference = ((100/ myCards.size() )* Bewertungsklasse.anzahlRichtige);
			
			
			if (tempDifference > 100) {
				
				tempDifference = ((100/ myCards.size() )* Bewertungsklasse.anzahlRichtige);
				temp.clear();
			}
			temp.add (tempDifference);
			return temp;
			
			
		}
		else if (Decision[1].equals("end"))
		{
			
			Double[] doubleArray = LKDatabase.myCards.getScore(Decision[0]);
			
			temp.clear();
			temp.add(100 / doubleArray[0] * doubleArray[1] + (tempDifference));
			return temp;
		}
		else if (Decision[1].equals("start"))
		{
			Double[] doubleArray = LKDatabase.myCards.getScore(Decision[0]);
			if (doubleArray != null)
			{
				Bewertungsklasse.anzahlRichtige = 0;
				Bewertungsklasse.anzahlFalsche = 0;
				tempStart  = 0.0;
				temp.clear();
				temp.add(tempStart);
				return temp;
			}

			return null;

		}
		else
		{
			return null;
		}
	}

	boolean success = false;

	public int doAction (String functionName, String paramS, double paramD)
	{
		Supervisor.errorAndDebug(this, "WARNING (StatisticsModel): Use of deprecated doAction. Use the new one instead");
		
		return -9;
	}
	
	@Override
	public int doAction (Command command, String... param)
	{
		switch (command)
		{
			case DELETE:
				Diagramm.resetData();
				Rangliste.resetData();
				return 1; // TODO check if successful
				
			case GET:
				success = Rangliste.checkDatabase();
				return success ? 1 : -1;
				
			default:
				return super.doAction(command, param);
		}
	}
	
	
	
}
