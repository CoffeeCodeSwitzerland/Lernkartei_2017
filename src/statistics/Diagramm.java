package statistics;

import java.util.ArrayList;
import database.Categories;
import database.Database;
import debug.Debugger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

public class Diagramm
{	
	static ArrayList<String> Stacks = new ArrayList<String>();
	static ArrayList<Double> Punkte = new ArrayList<Double>(); 
	
	private static ArrayList<String> getKarteien() {
		Stacks = Categories.getStacknames();
		return Stacks;
	}
	
	private static ArrayList<Double> getPunkte()
	{		
		for (int i = 0; i < Stacks.size(); i++)
		{
			Double[] temp = Database.getScore(Stacks.get(i).toString());
			Double result = (100 / temp[0]) * temp[1];
			Debugger.out("Resultat : " + result);
			Punkte.add(result);
		}		
		return Punkte;
	}
	
	public static ObservableList<XYChart.Series<String, Number>> getChartData() {
		ObservableList<XYChart.Series<String, Number>> data = FXCollections.observableArrayList();
		
		getKarteien();
		getPunkte();
		
		if (Stacks == null) {
			return data = null;
		} else {
			for (int i = 0; i < Stacks.size(); i++)
			{
				Series<String, Number> thisSerie = new Series<String, Number>();
				thisSerie.setName(Stacks.get(i));
				Number forChart = (Number)  Punkte.get(i);
				thisSerie.getData().add(new Data<String, Number>(Stacks.get(i), forChart));
				data.add(thisSerie);
			}
		}
		return data;
	}
	
	public static Boolean resetData() {
		Stacks.clear();
		Punkte.clear();
		return true;
	}
	
	

}
