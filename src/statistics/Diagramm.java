package statistics;

import java.util.ArrayList;
import database.Stack;
import debug.Logger;
import database.Database;
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
		if (Stack.getStacknames() == null || Stack.getStacknames().get(0).equals(""))
		{
			return Stacks = null;
		} else
		{
			return Stacks = Stack.getStacknames();
		}
	}

	private static ArrayList<Double> getPunkte()
	{
		if (Stack.getStacknames() == null || Stack.getStacknames().get(0).equals("")) {
			return null;
		} else {
			try
			{
				for (int i = 0; i < Stacks.size(); i++)
				{
					Double[] temp = Database.getScore(Stacks.get(i).toString());
					Double result = (100 / temp[0]) * temp[1];
					Punkte.add(result);
				}
				return Punkte;
			} catch (Exception e)
			{
				return null;
			}
		}
	}

	static ObservableList<XYChart.Series<String, Number>> Data = FXCollections.observableArrayList();

	public static ObservableList<XYChart.Series<String, Number>> getChartData()
	{
		if (Stacks.isEmpty() || Punkte.isEmpty()) {
			Data = null;
		} else {
			if (Data != null || Data.get(0).equals(null))
			{
				resetData();
			} else
			{
				Logger.log("ObservableList Data in Diagramm is empty");
			}
	
			getKarteien();
			getPunkte();
	
			if (Stacks == null || Stacks.get(0).equals(""))
			{
				return Data = null;
			} else
			{
				for (int i = 0; i < Stacks.size(); i++)
				{
					Series<String, Number> thisSerie = new Series<String, Number>();
					thisSerie.setName(Stacks.get(i));
					Number forChart = (Number) Punkte.get(i);
					thisSerie.getData().add(new Data<String, Number>(Stacks.get(i), forChart));
					Data.add(thisSerie);
				}
			}
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
