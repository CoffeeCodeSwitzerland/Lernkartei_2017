package statistics;

import java.util.ArrayList;
import database.Categories;
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
			Punkte.add(result);
		}		
		return Punkte;
	}

	static ObservableList<XYChart.Series<String, Number>> Data = FXCollections.observableArrayList();
	public static ObservableList<XYChart.Series<String, Number>> getChartData() {
		System.out.println("Daigramm 1");
		getKarteien();
		System.out.println("Daigramm 2 : Karteien : " + Stacks.get(0));
		getPunkte();
		System.out.println("Daigramm 3 : Punkte : " + Punkte.get(0));
		
		if (Stacks == null) {
			System.out.println("Daigramm if 1");
			return Data = null;
		} else {
			System.out.println("Daigramm else 1");
			for (int i = 0; i < Stacks.size(); i++)
			{
				System.out.println("Daigramm else for 1");
				Series<String, Number> thisSerie = new Series<String, Number>();
				System.out.println("Daigramm else for 2");
				thisSerie.setName(Stacks.get(i));
				System.out.println("Daigramm else for 3");
				Number forChart = (Number)  Punkte.get(i);
				System.out.println("Daigramm else for 4");
				thisSerie.getData().add(new Data<String, Number>(Stacks.get(i), forChart));
				System.out.println("Daigramm else for 5");
				Data.add(thisSerie);
			}
		}
		return Data;
	}
	
	public static Boolean resetData() {
		Stacks.clear();
		Punkte.clear();
		return true;
	}
	
	

}
