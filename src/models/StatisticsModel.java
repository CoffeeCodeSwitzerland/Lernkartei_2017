package models;

import java.util.ArrayList;

import database.Database;
import debug.Debugger;
import debug.Supervisor;
import globals.Globals;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import mvc.fx.FXModel;
import statistics.Rangliste;
import statistics.Diagramm;


public class StatisticsModel extends FXModel
{

	// public StatisticsModel(String myName)
	// {
	// super(myName);
	// }

	// ROL --> RanglisteObversableList
	ObservableList<String> rol = FXCollections.observableArrayList();

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

	ArrayList<Double>	temp		= new ArrayList<>();
	Double				tempStart	= 0.0;

	// Diese Methode ist dafür da um den Fortschritt
	// CombinedString --> STACKNAME + Globals.SEPARATOR + ANWEISUNG(end,
	// difference oder start)
	public ArrayList<Double> getDoubleList (String CombinedString)
	{
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
			if (doubleArray != null)
			{
				tempStart = 100 / doubleArray[0] * doubleArray[1];
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
