package statistics;

import java.util.ArrayList;

import database.LKDatabase;
import debug.Debugger;
import debug.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

public class Diagramm
{
	static ArrayList<String> Stacks = new ArrayList<String>();
	static ArrayList<Double> Punkte = new ArrayList<Double>();

	private static ArrayList<String> getKarteien()
	{
			return Stacks = LKDatabase.myStacks.getStacknames();
	}

	private static ArrayList<Double> getPunkte()
	{
				for (int i = 0; i < Stacks.size(); i++)
				{
					Double[] temp = LKDatabase.myCards.getScore(Stacks.get(i).toString());
					Double result = (100 / temp[0]) * temp[1];
					Punkte.add(result);
				}
				return Punkte;
	}

	static ObservableList<XYChart.Series<String, Number>> Data = FXCollections.observableArrayList();

	public static ObservableList<XYChart.Series<String, Number>> getChartData()
	{
		Debugger.out("Diagramm getChartData says : Hello ! ");
			if (Data != null || Data.get(0).equals(null))
			{
				resetData();
			} else
			{
				Logger.log("ObservableList Data in Diagramm is already empty");
			}

			getKarteien();
			getPunkte();

			for (int i = 0; i < Stacks.size(); i++)
			{
				Series<String, Number> thisSerie = new Series<String, Number>();
				thisSerie.setName(Stacks.get(i));
				Number forChart = (Number) Punkte.get(i);
				thisSerie.getData().add(new Data<String, Number>(Stacks.get(i), forChart));
				Data.add(thisSerie);
			}
		return Data;
	}

	public static Boolean resetData()
	{
		Data.clear();
		Stacks.clear();
		Punkte.clear();
		return true;
	}

}
